package tank;
import facecat.topin.core.*;

/*
 * 基础项
 */
public class BaseItem extends FCView {
    /*
    * 一帧在位图中的大小
    */
    protected FCPoint m_frameSize = new FCPoint(28, 28);

    /*
    * 当前帧在位图中的坐标
    */
    protected FCPoint m_currentFrame = new FCPoint(0, 0);

    /*
    * 位图的行和列
    */
    protected FCPoint m_sheetSize = new FCPoint(2, 4);

    public TankWar m_tankWar;

    /*
    * 构造函数
    */
    public BaseItem(String imagePath, FCPoint itemPosition, ItemGroupEnum itemGroup, TankWar tankWar) {
        setBackImage(imagePath);
        setLocation(itemPosition);
        m_itemGroup = itemGroup;
        m_tankWar = tankWar;
        setBackColor(FCColor.None);
        setBorderColor(FCColor.None);
        setTextColor(FCColor.None);
    }

    /*
    * 获取或设置是否删除
    */
    public boolean m_isRemove = false;

    /*
    * 获取或设置物体所属阵营
    */
    public ItemGroupEnum m_itemGroup;

    /*
    * 获取或设置物体图片的层级
    */
    public float m_itemImgLayer;

    private int m_tick;

    /*
    * 获取时间
    */
    public int getTick() {
       return m_tick;
    }

    /*
    * 设置时间
    */
    public void setTick(int value)
    {
        m_tick = value;
        if (m_tick > 100000000)
        {
            m_tick = 0;
        }
    }

    /*
    * 获取或设置上次的时间间隔
    */
    public int m_timeSinceLastFrame = 0;

    /*
    * 是否包含点
    */
    public boolean containsPoint(FCPoint point) {
        return false;
    }

    /*
    * 物体状态改变
    */
    public void move(DirectionEnum direction) {
    }

    /*
    * 获取物体的区域大小
    */
    public FCRect getRegionRect() {
        FCPoint location = getLocation();
        return new FCRect(location.x, location.y, location.x + 20, location.y + 20);
    }
}
