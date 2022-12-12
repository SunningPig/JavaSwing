package tank;
import facecat.topin.core.*;
import java.util.*;

/*
* 障碍物石头
*/
public class WallBarrier extends BaseItem {
    /*
    *  构造函数
    */
    public WallBarrier(String imagePath, FCPoint itemPosition, ItemGroupEnum itemGroup, BarrierTypeEnum barrierType, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_barrierType = barrierType;
        setBounds(new FCRect(itemPosition.x, itemPosition.y, itemPosition.x + 20, itemPosition.y + 20));
        setBorderColor(FCColor.None);
        if (m_barrierType == BarrierTypeEnum.Wall || m_barrierType == BarrierTypeEnum.Steel)
        {
            m_itemImgLayer = 0.1f;
        }
        else if (m_barrierType == BarrierTypeEnum.Grass)
        {
            m_itemImgLayer = 0.9f;
        }
        else if (m_barrierType == BarrierTypeEnum.Water)
        {
            m_itemImgLayer = 0f;
        }
    }

    /*
    *  获取或设置障碍物类型
    */
    public BarrierTypeEnum m_barrierType;
}
