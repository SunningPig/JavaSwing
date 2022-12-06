/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;

/// <summary>
/// 我方坦克
/// </summary>
public class MyTank extends TankBase
{
    /// <summary>
    /// 构造方法
    /// </summary>
    /// <param name="imagePath">图片</param>
    /// <param name="itemPosition">位置</param>
    /// <param name="tankSpeed">速度</param>
    /// <param name="itemGroup">阵营</param>
    /// <param name="tankWar">坦克大战</param>
    public MyTank(String imagePath, FCPoint itemPosition, int tankSpeed, ItemGroupEnum itemGroup, TankWar tankWar)
    {
        super(imagePath, itemPosition, tankSpeed, itemGroup, tankWar);
        setBounds(new FCRect(itemPosition.x, itemPosition.y, itemPosition.x + 40, itemPosition.y + 40));
    }

    /// <summary>
    /// 获取或设置坦克子弹的新速度
    /// </summary>
    public int m_newMissileSpeed = 4;

    /// <summary>
    /// 获取或设置坦克的新速度
    /// </summary>
    public int m_newSpeed = 4;

    /// <summary>
    /// 获取控件类型
    /// </summary>
    /// <returns>类型</returns>
    public String getViewType()
    {
        return "MyTank";
    }

    /// <summary>
    /// 移动
    /// </summary>
    /// <param name="direction">方向</param>
    public void move(DirectionEnum direction)
    {
        if (!m_tankWar.m_hitEagle)
        {
            if (!m_tankWar.m_myTankStop)
            {
                int step = 4;
                setTick(getTick() + 1);
                if (getTick() % m_speed == 0)
                {
                    if (direction == DirectionEnum.Up)
                    {
                        m_direction = DirectionEnum.Up;
                        if (!collide())
                        {
                            if (getTop() >= step)
                            {
                                setLocation(new FCPoint(getLeft(), getTop() - step));
                            }
                        }
                    }
                    else if (direction == DirectionEnum.Down)
                    {
                        m_direction = DirectionEnum.Down;
                        if (!collide())
                        {
                            if (getTop() <= 480 - step)
                            {
                                setLocation(new FCPoint(getLeft(), getTop() + step));
                            }
                        }
                    }
                    else if (direction == DirectionEnum.Left)
                    {
                        m_direction = DirectionEnum.Left;
                        if (!collide())
                        {
                            if (getLeft() >= step)
                            {
                                setLocation(new FCPoint(getLeft() - step, getTop()));
                            }
                        }
                    }
                    else if (direction == DirectionEnum.Right)
                    {
                        m_direction = DirectionEnum.Right;
                        if (!collide())
                        {
                            if (getLeft() <= 480 - step)
                            {
                                setLocation(new FCPoint(getLeft() + step, getTop()));
                            }
                        }
                    }
                }
            }
        }
    }

    /// <summary>
    /// 绘图函数
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintBackground(FCPaint paint, FCRect clipRect)
    {
        setBounds(new FCRect(getLeft(), getTop(), getLeft() + 40, getTop() + 40));          
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0)
        {
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
            else if (m_direction == DirectionEnum.Right)
            {
                imagePath += "R.png";
            }
            else if (m_direction == DirectionEnum.Left)
            {
                imagePath += "L.png";
            }
            paint.drawImage(imagePath, rect);
        }
    }
}
