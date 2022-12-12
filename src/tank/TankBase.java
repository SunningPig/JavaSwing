package tank;
import facecat.topin.core.*;
import java.util.*;

/*
* 坦克基类
*/
public class TankBase extends BaseMovedItem {
    /*
    * 构造函数
    */
    public TankBase(String imagePath, FCPoint itemPosition, int tankSpeed, ItemGroupEnum itemGroup, TankWar tankWar){
        super(imagePath, itemPosition, tankSpeed, itemGroup, tankWar);
        m_itemImgLayer = 0.5f;
    }

    /*
    * 获取或设置爆炸集合
    */
    public ArrayList<TankBlast> m_blast = new ArrayList<TankBlast>();

    /*
    * 获取或设置爆炸的图片
    */
    public String m_blastImg;

    /*
    * 获取或设置坦克生命
    */
    public int m_blood = 1;

    /*
    * 获取或设置坦克是否无敌
    */
    public boolean m_isShield = false;

    /*
    * 获取或设置一次发出的子弹数量
    */
    public int m_missileQuantity = 1;

    /*
    * 获取或设置子弹的图片
    */
    public String m_missileImg;

    /*
    * 获取或设置子弹的速度
    */
    private int m_missileSpeed = 1;

    /*
    * 获取或设置坦克的子弹集合
    */
    public ArrayList<TankMissile> m_tankMissiles = new ArrayList<TankMissile>();

    /*
    * 切换基地周围的障碍物类型
    */
    private void ChangeBossBarrier(BarrierTypeEnum barrierType) {

    }

    /*
    * 判断是否碰撞
    */
    public boolean collide()
    {
        TankTreasure treasure = null;
        FCHost host = getNative().getHost();
        FCRect tempRect = new FCRect();
        ArrayList<BaseItem> items = m_tankWar.m_items;
        int itemsSize = items.size();
        for (int i = 0; i < itemsSize; i++)
        {
            BaseItem itemBase = items.get(i);
            FCRect itemRect = itemBase.getRegionRect();
            if (this != itemBase)
            {
                TankEagle tankEagle = (TankEagle) ((itemBase instanceof TankEagle) ? itemBase : null);
                if (tankEagle != null && m_tankWar.m_hitEagle) // 如果基地被击中后不进行检测
                {
                    continue;
                }
                WallBarrier wallBarrier = (WallBarrier) ((itemBase instanceof WallBarrier) ? itemBase : null);
                if (wallBarrier != null) // 如果是草地则不进行碰撞检测
                {
                    WallBarrier barrier = wallBarrier;
                    if (barrier.m_barrierType == BarrierTypeEnum.Grass)
                    {
                        return false;
                    }
                }
                MyTank myTank = (MyTank) ((this instanceof MyTank) ? this : null);
                TankTreasure tankTreasure = (TankTreasure) ((itemBase instanceof TankTreasure) ? itemBase : null);
                if (myTank != null && tankTreasure != null) // 吃到宝物
                {
                    // 判断是否与障碍物或者坦克发生碰撞     
                    FCRect rect = getBounds();
                    RefObject<FCRect> refTempRect =  new RefObject<FCRect>(tempRect);
                    RefObject<FCRect> refRect =  new RefObject<FCRect>(rect);
                    RefObject<FCRect> refItemRect =  new RefObject<FCRect>(itemRect);
                    if (host.getIntersectRect(refTempRect, refRect, refItemRect) > 0)
                    {
                        treasure = tankTreasure;
                        break;
                    }
                    continue;
                }
                else if (tankTreasure != null)
                {
                    return false;
                }
                int left = 0, top = 0;
                if (m_direction == DirectionEnum.Up)
                {
                    left = getLeft();
                    top = getTop() - m_speed;
                    FCRect tankRect = new FCRect(left, top, left + m_tankWar.m_itemSize.cx, top + m_tankWar.m_itemSize.cy);
                    RefObject<FCRect> refTempRect =  new RefObject<FCRect>(tempRect);
                    RefObject<FCRect> refRect =  new RefObject<FCRect>(tankRect);
                    RefObject<FCRect> refItemRect =  new RefObject<FCRect>(itemRect);
                    if (host.getIntersectRect(refTempRect, refRect, refItemRect) > 0)
                    {
                        return true;
                    }
                }
                else if (m_direction == DirectionEnum.Down)
                {
                    left = getLeft();
                    top = getTop() + m_speed;
                    FCRect tankRect = new FCRect(left, top, left + m_tankWar.m_itemSize.cx, top + m_tankWar.m_itemSize.cy);
                    RefObject<FCRect> refTempRect =  new RefObject<FCRect>(tempRect);
                    RefObject<FCRect> refRect =  new RefObject<FCRect>(tankRect);
                    RefObject<FCRect> refItemRect =  new RefObject<FCRect>(itemRect);
                    if (host.getIntersectRect(refTempRect, refRect, refItemRect) > 0)
                    {
                        return true;
                    }
                }
                else if (m_direction == DirectionEnum.Left)
                {
                    left = getLeft() - m_speed;
                    top = getTop();
                    FCRect tankRect = new FCRect(left, top, left + m_tankWar.m_itemSize.cx, top + m_tankWar.m_itemSize.cy);
                     RefObject<FCRect> refTempRect =  new RefObject<FCRect>(tempRect);
                    RefObject<FCRect> refRect =  new RefObject<FCRect>(tankRect);
                    RefObject<FCRect> refItemRect =  new RefObject<FCRect>(itemRect);
                    if (host.getIntersectRect(refTempRect, refRect, refItemRect) > 0)
                    {
                        return true;
                    }
                }
                else if (m_direction == DirectionEnum.Right)
                {
                    left = getLeft() + m_speed;
                    top = getTop();
                    FCRect tankRect = new FCRect(left, top, left + m_tankWar.m_itemSize.cx, top + m_tankWar.m_itemSize.cy);
                      RefObject<FCRect> refTempRect =  new RefObject<FCRect>(tempRect);
                    RefObject<FCRect> refRect =  new RefObject<FCRect>(tankRect);
                    RefObject<FCRect> refItemRect =  new RefObject<FCRect>(itemRect);
                    if (host.getIntersectRect(refTempRect, refRect, refItemRect) > 0)
                    {
                        return true;
                    }
                }
            }
        }
        if (treasure != null)
        {
            getTreasure(treasure);
            treasure.m_isRemove = true;
            return false;
        }
        return false;
    }

    /*
    * 开炮
    */
    public void fire()
    {
        if (m_tankMissiles.size() == 0)
        {
            FCPoint location = new FCPoint();
            for (int i = 0; i < m_missileQuantity; i++)
            {
                if (m_direction == DirectionEnum.Up)
                {
                    location = new FCPoint(getLeft() + 16, getTop() - 8 - i * m_tankWar.m_itemSize.cy / 2);
                }
                else if (m_direction == DirectionEnum.Down)
                {
                    location = new FCPoint(getLeft() + 16, getTop() + m_tankWar.m_itemSize.cy + i * m_tankWar.m_itemSize.cy / 2);
                }
                else if (m_direction == DirectionEnum.Left)
                {
                    location = new FCPoint(getLeft() - 8 - i * m_tankWar.m_itemSize.cx / 2, getTop() + 16);
                }
                else if (m_direction == DirectionEnum.Right)
                {
                    location = new FCPoint(getLeft() + m_tankWar.m_itemSize.cx + i * m_tankWar.m_itemSize.cx / 2, getTop() + 16);
                }
                TankMissile tankMissile = new TankMissile(m_missileImg, location, m_missileSpeed * 2, m_itemGroup, m_direction, this, m_tankWar);
                m_tankMissiles.add(tankMissile);
                if (m_itemGroup == ItemGroupEnum.Friendly)
                {
                    Sound.play("fire");
                }
            }
        }
    }

    /*
    * 获取控件类型
    */
    public String getViewType() {
        return "TankBase";
    }

    /*
    * 获取占用矩形
    */
    public FCRect getRegionRect() {
        FCPoint location = getLocation();
        return new FCRect(location.x, location.y, location.x + getWidth(), location.y + getHeight());
    }

    /*
    * 获取宝物
    */
    public void getTreasure(TankTreasure treasure)
    {
        if (treasure.m_treasureType == TreasureTypeEnum.Bomb)
        {
            Sound.play("bomb");
            if (m_itemGroup == ItemGroupEnum.Friendly)
            {
                ArrayList<EnemyTank> enemyTanks = m_tankWar.m_enemyTanks;
                int enemyTanksSize = enemyTanks.size();
                for (int i = enemyTanksSize - 1; i >= 0; i--)
                {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    TankBlast blast = new TankBlast(m_blastImg, new FCPoint(enemyTank.getLeft() + 10, enemyTank.getTop() + 10), ItemGroupEnum.Others, this, m_tankWar);
                    m_blast.add(blast);
                    enemyTank.m_isRemove = true;
                }
            }
            else if (m_itemGroup == ItemGroupEnum.Enemy)
            {
                MyTank myTank = m_tankWar.m_myTank;
                if (myTank != null)
                {
                    TankBlast blast = new TankBlast(m_blastImg, new FCPoint(myTank.getLeft() + 10, myTank.getTop() + 10), ItemGroupEnum.Others, this, m_tankWar);
                    m_blast.add(blast);
                    myTank.m_isRemove = true;
                }
            }
        }
        else if (treasure.m_treasureType == TreasureTypeEnum.Star)
        {
            Sound.play("gettreasure");
            MyTank mytank = (MyTank)this;
            mytank.m_newSpeed = 5;
            mytank.m_newMissileSpeed = 10;
            if (m_blood != 4)
            {
                m_blood++;
            }
            if (m_blood == 3)
            {
                m_missileQuantity = 2;
            }
        }
        else if (treasure.m_treasureType == TreasureTypeEnum.Shovel)
        {
            Sound.play("gettreasure");
            ChangeBossBarrier(BarrierTypeEnum.Steel);
            m_tankWar.m_isSteel = true;
        }
        else if (treasure.m_treasureType == TreasureTypeEnum.Shield)
        {
            Sound.play("gettreasure");
            m_isShield = true;
        }
        else if (treasure.m_treasureType == TreasureTypeEnum.Timer)
        {
            Sound.play("gettreasure");
            if (m_itemGroup == ItemGroupEnum.Friendly)
            {
                m_tankWar.m_enemyTankStop = true;
            }
            else if (m_itemGroup == ItemGroupEnum.Enemy)
            {
                m_tankWar.m_myTankStop = true;
            }
        }
    }

    /*
    * 移动
    */
    public void move(DirectionEnum direction) {

    }
}
