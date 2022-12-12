package tank;
import facecat.topin.core.*;
import java.util.*;

/*
* 宝物类
*/
public class TankTreasure extends BaseItem {
    /*
    * 构造方法
    */
    public TankTreasure(String imagePath, FCPoint itemPosition, ItemGroupEnum itemGroup, TreasureTypeEnum treasureType, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_treasureType = treasureType;
        setBorderColor(FCColor.None);
        setBounds(new FCRect(itemPosition.x, itemPosition.y, itemPosition.x + m_tankWar.m_itemSize.cx, itemPosition.y + m_tankWar.m_itemSize.cy));
    }

    /*
    * 获取或设置宝物类型
    */
    public TreasureTypeEnum m_treasureType;

    /*
    * 获取控件类型
    */
    public String getViewType() {
        return "TankTreasure";
    }
}
