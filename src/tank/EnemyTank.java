/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;
import java.util.Random;

/// <summary>
/// 敌方坦克
/// </summary>
public class EnemyTank extends TankBase {
    /// <summary>
    /// 构造方法
    /// </summary>
    /// <param name="textureImg">背景图片</param>
    /// <param name="itemPosition">位置</param>
    /// <param name="tankSpeed">速度</param>
    /// <param name="itemGroup">阵营</param>
    /// <param name="isTreasure">是否为宝物</param>
    /// <param name="treasureType">类型</param>
    /// <param name="tankWar">坦克大战</param>
    public EnemyTank(String imagePath, FCPoint itemPosition, int tankSpeed, ItemGroupEnum itemGroup, TreasureTypeEnum treasureType, TankWar tankWar) {
        super(imagePath, itemPosition, tankSpeed, itemGroup, tankWar);
        m_treasureType = treasureType;
        setBounds(new FCRect(itemPosition.x, itemPosition.y, itemPosition.x + m_tankWar.m_itemSize.cx,
            itemPosition.y + m_tankWar.m_itemSize.cy));
        //设置秒表
    }
    
    public void onAdd(){
        super.onAdd();
        startTimer(m_timerID, 50);
    }

    /// <summary>
    /// 移动记录
    /// </summary>
    private float m_movingRecord = 0;

    private int m_timerID = FCView.getNewTimerID();

    /// <summary>
    /// 获取或设置坦克携带的宝物
    /// </summary>
    public TreasureTypeEnum m_treasureType = TreasureTypeEnum.None;

    public void onLocationChanged() {
        super.onLocationChanged();
    }

    /// <summary>
    /// 坦克移动
    /// </summary>
    private void autoMove()
    {
        // 是否被定时
        if (!m_tankWar.m_enemyTankStop)
        {
            FCRect bounds = getBounds();
            setTick(getTick() + 1);
            // 碰撞检测
            if (collide())
            {
                changeDirection();
                m_movingRecord = 0;
                return;
            }
            if (getTick() % m_speed == 0)
            {
                int step = 4;
                // 坦克运行
                if (m_direction == DirectionEnum.Up)
                {
                    if (getTop() >= step)
                    {
                        setLocation(new FCPoint(getLeft(), getTop() - step));
                        m_movingRecord += step;
                    }
                    else
                    {
                        changeDirection();
                    }
                }
                else if (m_direction == DirectionEnum.Down)
                {
                    if (getTop() <= 480 - step)
                    {
                        setLocation(new FCPoint(getLeft(), getTop() + step));
                        m_movingRecord += step;
                    }
                    else
                    {
                        changeDirection();
                    }
                }
                else if (m_direction == DirectionEnum.Left)
                {
                    if (getLeft() >= step)
                    {
                        setLocation(new FCPoint(getLeft() - step, getTop()));
                        m_movingRecord += step;
                    }
                    else
                    {
                        changeDirection();
                    }
                }
                else if (m_direction == DirectionEnum.Right)
                {
                    if (getLeft() <= 480 - step)
                    {
                        setLocation(new FCPoint(getLeft() + step, getTop()));
                        m_movingRecord += step;
                    }
                    else
                    {
                        changeDirection();
                    }
                }
                if (m_movingRecord % 20 == 0)
                {
                    int randomNumber = m_random.nextInt(10);
                    if (randomNumber == 0)
                    {
                        changeDirection();
                    }
                    randomNumber = m_random.nextInt(5);
                    if (randomNumber == 1)
                    {
                        fire();
                    }
                }
            }
        }
    }

    /// <summary>
    /// 改变坦克方向
    /// </summary>
    private void changeDirection() {
        int randomNumber = m_random.nextInt(4);
        switch (randomNumber) {
            case 0:
                m_direction = DirectionEnum.Up;
                break;
            case 1:
                m_direction = DirectionEnum.Down;
                break;
            case 2:
                m_direction = DirectionEnum.Left;
                break;
            case 3:
                m_direction = DirectionEnum.Right;
                break;
        }
    }

    /// <summary>
    /// 获取控件类型
    /// </summary>
    /// <returns>类型</returns>
    public String getViewType() {
        return "EnemyTank";
    }

    /// <summary>
    /// 绘图方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintBackground(FCPaint paint, FCRect clipRect)
    {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0)
        {
            autoMove();
            setBounds(new FCRect(getLeft(), getTop(), getLeft() + width, getTop() + height));
            FCRect rect = new FCRect(0, 0, width, height);
            String imagePath = getBackImage();
            if (m_direction == DirectionEnum.Up)
            {
                imagePath += "U.png";
            }
            else if (m_direction == DirectionEnum.Down)
            {
                imagePath += "D.png";
            }
            else if (m_direction == DirectionEnum.Left)
            {
                imagePath += "L.png";
            }
            else if (m_direction == DirectionEnum.Right)
            {
                imagePath += "R.png";
            }
            paint.drawImage(imagePath, rect);
        }
    }

    /// <summary>
    /// 定时操作
    /// </summary>
    /// <param name="timerID">定时器ID</param>
    public void onTimer(int timerID) {
        callTimerEvents(FCEventID.Timer, timerID);
        if (timerID == m_timerID) {
            autoMove();
        }
    }
}
