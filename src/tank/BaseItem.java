/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;

/// <summary>
/// 基础项
/// </summary>
public class BaseItem extends FCView {
    /// <summary>
    /// 一帧在位图中的大小
    /// </summary>
    protected FCPoint m_frameSize = new FCPoint(28, 28);

    /// <summary>
    /// 当前帧在位图中的坐标
    /// </summary>
    protected FCPoint m_currentFrame = new FCPoint(0, 0);

    /// <summary>
    /// 位图的行和列
    /// </summary>
    protected FCPoint m_sheetSize = new FCPoint(2, 4);

    public TankWar m_tankWar;

    /// <summary>
    /// 构造函数
    /// </summary>
    /// <param name="itemPosition">位置</param>
    /// <param name="itemGroup">阵营</param>
    /// <param name="itemGroup">类型</param>
    /// <param name="tankWar">坦克大战</param>
    public BaseItem(String imagePath, FCPoint itemPosition, ItemGroupEnum itemGroup, TankWar tankWar) {
        setBackImage(imagePath);
        setLocation(itemPosition);
        m_itemGroup = itemGroup;
        m_tankWar = tankWar;
        setBackColor(FCColor.None);
        setBorderColor(FCColor.None);
        setTextColor(FCColor.None);
    }

    /// <summary>
    /// 获取或设置是否删除
    /// </summary>
    public boolean m_isRemove = false;

    /// <summary>
    /// 获取或设置物体所属阵营
    /// </summary>
    public ItemGroupEnum m_itemGroup;

    /// <summary>
    /// 获取或设置物体图片的层级
    /// </summary>
    public float m_itemImgLayer;

    private int m_tick;

    /// <summary>
    /// 获取或设置时间
    /// </summary>
    public int getTick() {
       return m_tick;
    }

    public void setTick(int value)
    {
        m_tick = value;
        if (m_tick > 100000000)
        {
            m_tick = 0;
        }
    }

    /// <summary>
    /// 获取或设置上次的时间间隔
    /// </summary>
    public int m_timeSinceLastFrame = 0;

    /// <summary>
    /// 是否包含点
    /// </summary>
    /// <param name="point">坐标</param>
    /// <returns></returns>
    public boolean containsPoint(FCPoint point) {
        return false;
    }

    /// <summary>
    /// 物体状态改变
    /// </summary>
    public void move(DirectionEnum direction) {
    }

    /// <summary>
    /// 获取物体的区域大小
    /// </summary>
    /// <returns></returns>
    public FCRect getRegionRect() {
        FCPoint location = getLocation();
        return new FCRect(location.x, location.y, location.x + 20, location.y + 20);
    }
}
