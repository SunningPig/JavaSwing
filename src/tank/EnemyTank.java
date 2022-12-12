package tank;
import facecat.topin.core.*;
import java.util.Random;

/*
 * 敌方坦克
 */
public class EnemyTank extends TankBase {
    /*
    * 构造方法
    */
    public EnemyTank(String imagePath, FCPoint itemPosition, int tankSpeed, ItemGroupEnum itemGroup, TreasureTypeEnum treasureType, TankWar tankWar) {
        super(imagePath, itemPosition, tankSpeed, itemGroup, tankWar);
        m_treasureType = treasureType;
        setBounds(new FCRect(itemPosition.x, itemPosition.y, itemPosition.x + m_tankWar.m_itemSize.cx,
            itemPosition.y + m_tankWar.m_itemSize.cy));
    }
    
    /*
    * 添加视图
    */
    public void onAdd(){
        super.onAdd();
        startTimer(m_timerID, 50);
    }

    /*
    * 移动记录
    */
    private float m_movingRecord = 0;

    /*
    * 秒表ID
    */
    private int m_timerID = FCView.getNewTimerID();

    /*
    * 获取或设置坦克携带的宝物
    */
    public TreasureTypeEnum m_treasureType = TreasureTypeEnum.None;

    /*
    * 位置改变
    */
    public void onLocationChanged() {
        super.onLocationChanged();
    }

    /*
    * 坦克移动
    */
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

    /*
    * 改变坦克方向
    */
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

    /*
    * 获取控件类型
    */
    public String getViewType() {
        return "EnemyTank";
    }

    /*
    * 绘图方法
    */
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
    
    /*
    * 定时操作
    */
    public void onTimer(int timerID) {
        callTimerEvents(FCEventID.Timer, timerID);
        if (timerID == m_timerID) {
            autoMove();
        }
    }
}
