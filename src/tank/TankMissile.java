/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;
import java.util.*;

/// <summary>
/// 子弹类
/// </summary>
public class TankMissile extends BaseMovedItem {
    /// <summary>
    /// 构造函数
    /// </summary>
    /// <param name="backgroundImage">背景图片</param>
    /// <param name="itemPosition">位置</param>
    /// <param name="missileSpeed">子弹速度</param>
    /// <param name="itemGroup">阵营</param>
    /// <param name="missileDirection">子弹方向</param>
    /// <param name="parentTank">所属坦克</param>
    /// <param name="tankWar">坦克大战</param>
    public TankMissile(String backgroundImage, FCPoint itemPosition, int missileSpeed, ItemGroupEnum itemGroup, DirectionEnum missileDirection, TankBase parentTank, TankWar tankWar){
        super(backgroundImage, itemPosition, missileSpeed, itemGroup, tankWar);
        m_parentTank = parentTank;
        m_missileDirection = missileDirection;
        m_speed = missileSpeed;
        m_itemImgLayer = 0.3f;
        setBorderColor(FCColor.None);
        setBounds(getRegionRect());
    }

    /// <summary>
    /// 一帧在位图中的大小
    /// </summary>
    private FCPoint m_bulletSize = new FCPoint(8, 8);

    /// <summary>
    /// 获取或设置子弹方向
    /// </summary>
    public DirectionEnum m_missileDirection;

    /// <summary>
    /// 获取或设置子弹的等级
    /// </summary>
    public int m_missileLevel = 1;

    /// <summary>
    /// 获取所属坦克
    /// </summary>
    public TankBase m_parentTank = null;

    /// <summary>
    /// 碰撞检测
    /// </summary>
    /// <returns>是否碰撞</returns>
    public boolean collide() {
        TankTreasure treasure = null;
        ArrayList<BaseItem> removedItems = new ArrayList<BaseItem>();
        ArrayList<BaseItem> items = m_tankWar.m_items;
        int itemsSize = items.size();
        for (int i = 0; i < itemsSize; i++) {
            BaseItem itemBase = items.get(i);
            WallBarrier wallBarrier = (WallBarrier) ((itemBase instanceof WallBarrier) ? itemBase : null);
            if (wallBarrier != null) {
                WallBarrier barrier = wallBarrier;
                // 子弹可以穿过草地和水
                if (barrier.m_barrierType == BarrierTypeEnum.Grass || barrier.m_barrierType == BarrierTypeEnum.Water)
                {
                    continue;
                }
            }
            if (m_itemGroup != itemBase.m_itemGroup) {
                Boolean isCollide = false; // 是否发生碰撞
                BaseItem item = null; // 碰撞的物体
                int left = getLeft();
                int top = getTop();
                // 根据方向获取需要检测的区域
                if (m_missileDirection == DirectionEnum.Up)
                {
                    top -= m_speed;
                }
                else if (m_missileDirection == DirectionEnum.Down)
                {
                    top += m_speed;
                }
                else if (m_missileDirection == DirectionEnum.Left)
                {
                    left -= m_speed;
                }
                else if (m_missileDirection == DirectionEnum.Right)
                {
                    left += m_speed;
                }
                int margin = 4;
                FCRect missileRect = new FCRect(left - margin, top - margin, left + m_bulletSize.x + margin, top + m_bulletSize.y + margin);
                FCHost host = getNative().getHost();
                // 判断是否与障碍物或者坦克发生碰撞
                FCRect tempRect = new FCRect();
                FCRect itemRect = itemBase.getRegionRect();
                RefObject<FCRect> refTempRect =  new RefObject<FCRect>(tempRect);
                RefObject<FCRect> refRect =  new RefObject<FCRect>(missileRect);
                RefObject<FCRect> refItemRect =  new RefObject<FCRect>(itemRect);
                TankBase tankBase = (TankBase) ((itemBase instanceof TankBase) ? itemBase : null);
                if (host.getIntersectRect(refTempRect, refRect, refItemRect) > 0) {
                    isCollide = true;
                    item = itemBase;
                }
                // 判断是否与别的子弹发生碰撞
                else if (tankBase != null) {
                    TankBase tank = tankBase;
                    for (TankMissile missile : tank.m_tankMissiles) {
                        FCRect milRect = missile.getRegionRect();
                        refTempRect =  new RefObject<FCRect>(tempRect);
                        refRect =  new RefObject<FCRect>(missileRect);
                        refItemRect =  new RefObject<FCRect>(milRect);
                        if (host.getIntersectRect(refTempRect, refRect, refItemRect) > 0) {
                            item = missile;
                            isCollide = true;
                            break;
                        }
                    }
                }
                if (isCollide) {
                    WallBarrier wallBarrier2 = (WallBarrier) ((item instanceof WallBarrier) ? item : null);
                    TankBase tankBase2 = (TankBase) ((item instanceof TankBase) ? item : null);
                    TankMissile tankMissile = (TankMissile) ((item instanceof TankMissile) ? item : null);
                    TankEagle tankEagle = (TankEagle) ((item instanceof TankEagle) ? item : null);
                    if (wallBarrier2 != null) // 碰撞物是障碍物
                    {
                        WallBarrier barrier = wallBarrier2;
                        if (!removedItems.contains(this)) {
                            removedItems.add(this);
                        }
                        if (barrier.m_barrierType == BarrierTypeEnum.Steel && m_missileLevel == 2)
                        {
                            if (!removedItems.contains(barrier)) {
                                removedItems.add(barrier);
                            }
                        }
                        else if (barrier.m_barrierType == BarrierTypeEnum.Wall)
                        {
                            if (!removedItems.contains(barrier)) {
                                removedItems.add(barrier);
                            }
                        }
                        else if (barrier.m_barrierType == BarrierTypeEnum.Steel && m_missileLevel == 1 && m_itemGroup == ItemGroupEnum.Friendly)
                        {
                            Sound.play("cannotkill");
                        }
                    }
                    else if (tankBase2 != null) // 碰撞物是坦克
                    {
                        TankBase tank = tankBase2;
                        removedItems.add(this);
                        if (!tank.m_isShield) {
                            EnemyTank enemyTank = (EnemyTank) ((tank instanceof EnemyTank) ? tank : null);
                            MyTank myTank2 = (MyTank) ((tank instanceof MyTank) ? tank : null);
                            if (enemyTank != null) {
                                if (enemyTank.m_treasureType != TreasureTypeEnum.None)
                                {
                                    int treasureX = m_random.nextInt(480);
                                    int treasureY = 40 + m_random.nextInt(400);
                                    treasure = new TankTreasure(m_tankWar.m_treasureImgs.get(enemyTank.m_treasureType), new FCPoint(treasureX, treasureY), ItemGroupEnum.Others, enemyTank.m_treasureType, m_tankWar);
                                    enemyTank.m_treasureType = TreasureTypeEnum.None;
                                }
                                MyTank myTank = (MyTank) ((m_parentTank instanceof MyTank) ? m_parentTank : null);
                                //如果是我发坦克发出的子弹，地方坦克才会减血
                                if (myTank != null) {
                                    tank.m_blood--;
                                }
                                if (tank.m_blood != 0)
                                {
                                    Sound.play("cannotkill");
                                }
                                else {
                                    tank.m_isRemove = true;
                                }
                            }
                            else if (myTank2 != null) {
                                MyTank mytank = myTank2;
                                if (mytank.m_blood > 1)
                                {
                                    Sound.play("cannotkill");
                                    mytank.m_newSpeed = 4;
                                    mytank.m_missileQuantity = 1;
                                    mytank.m_newMissileSpeed = 4;
                                    mytank.m_blood = 1;
                                }
                                else {
                                    tank.m_blood = 0;
                                    mytank.m_isRemove = true;
                                    m_tankWar.m_life--;
                                }
                            }
                            if (tank.m_blood == 0)
                            {
                                removedItems.add(tank);
                                Sound.play("tankblast");
                            }
                            break;
                        }
                    }
                    else if (tankMissile != null) // 碰撞物是子弹
                    {
                        removedItems.add(this);
                        removedItems.add(item);
                        break;
                    }
                    else if (tankEagle != null) // 碰撞物是飞鹰基地
                    {
                        if (!m_tankWar.m_hitEagle) {
                            removedItems.add(this);
                            m_tankWar.m_hitEagle = true;
                            Sound.play("tankblast");
                        }
                        break;
                    }
                }
            }
        }
        if (treasure != null) {
            m_tankWar.addView(treasure);
            items.add(treasure);
        }
        if (removedItems.size() != 0) {
            for (BaseItem itemBase : removedItems) {
                itemBase.m_isRemove = true;
                TankMissile tankMissile2 = (TankMissile) ((itemBase instanceof TankMissile) ? itemBase : null);
                if (tankMissile2 != null) {
                    TankMissile missile = tankMissile2;
                    missile.m_parentTank.m_tankMissiles.remove(missile);
                    FCPoint blastPoint = new FCPoint(0, 0);
                    if (m_missileDirection == DirectionEnum.Up)
                    {
                        blastPoint = new FCPoint(m_parentTank.getLeft() + 14, getTop());
                    }
                    else if (m_missileDirection == DirectionEnum.Down)
                    {
                        blastPoint = new FCPoint(m_parentTank.getLeft() + 14, getTop() - 8);
                    }
                    else if (m_missileDirection == DirectionEnum.Left)
                    {
                        blastPoint = new FCPoint(getLeft(), m_parentTank.getTop() + 14);
                    }
                    else if (m_missileDirection == DirectionEnum.Right)
                    {
                        blastPoint = new FCPoint(getLeft() - 8, m_parentTank.getTop() + 14);
                    }
                    TankBlast blast = new TankBlast(m_parentTank.m_blastImg, blastPoint, ItemGroupEnum.Others, m_parentTank, m_tankWar);
                    m_parentTank.m_blast.add(blast);
                }
            }
            return true;
        }
        return false;
    }

    /// <summary>
    /// 获取控件类型
    /// </summary>
    /// <returns>类型</returns>
    public String getViewType() {
        return "TankMissile";
    }

    /// <summary>
    /// 获取物体的区域大小
    /// </summary>
    /// <returns>矩形</returns>
    public FCRect getRegionRect() {
        FCPoint location = getLocation();
        return new FCRect(location.x, location.y, location.x + m_bulletSize.x, location.y + m_bulletSize.y);
    }

    /// <summary>
    /// 子弹移动
    /// </summary>
    /// <param name="direction">方向</param>
    public void move(DirectionEnum direction) {
        collide();
        setTick(getTick() + 1);
        if (getTick() % m_speed == 0)
        {
            int step = 8;
            if(m_missileDirection == DirectionEnum.Up){
                if (getTop() <= 0) {
                    m_isRemove = true;
                    TankBlast blast = new TankBlast(m_parentTank.m_blastImg, new FCPoint(getLeft(), -5), ItemGroupEnum.Others, this.m_parentTank, m_tankWar);
                    m_parentTank.m_blast.add(blast);
                    return;
                }
                setLocation(new FCPoint(getLeft(), getTop() - step));
            }
            else if(m_missileDirection ==  DirectionEnum.Down){
                if (getTop() >= 520) {
                    m_isRemove = true;
                    TankBlast blast = new TankBlast(m_parentTank.m_blastImg, new FCPoint(getLeft(), 515), ItemGroupEnum.Others, this.m_parentTank, m_tankWar);
                    m_parentTank.m_blast.add(blast);
                    return;
                }
                setLocation(new FCPoint(getLeft(), getTop() + step));
            }
            else if(m_missileDirection ==  DirectionEnum.Left){
                if (getLeft() <= 0) {
                    m_isRemove = true;
                    TankBlast blast = new TankBlast(m_parentTank.m_blastImg, new FCPoint(-5, getTop()), ItemGroupEnum.Others, this.m_parentTank, m_tankWar);
                    m_parentTank.m_blast.add(blast);
                    return;
                }
                setLocation(new FCPoint(getLeft() - step, getTop()));
            }
            else if(m_missileDirection ==  DirectionEnum.Right){
                if (getLeft() >= 520) {
                    m_isRemove = true;
                    m_parentTank.m_tankMissiles.remove(this);
                    TankBlast blast = new TankBlast(m_parentTank.m_blastImg, new FCPoint(515, getTop()), ItemGroupEnum.Others, this.m_parentTank, m_tankWar);
                    m_parentTank.m_blast.add(blast);
                    return;
                }
                setLocation(new FCPoint(getLeft() + step, getTop()));
            }
        }
    }

    /// <summary>
    /// 绘图方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintBackground(FCPaint paint, FCRect clipRect) {
        setBounds(getRegionRect());
        super.onPaintBackground(paint, clipRect);
    }
}
