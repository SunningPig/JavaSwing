package tank;
import facecat.topin.core.*;
import java.util.*;

/*
* 飞鹰基地
*/
public class TankEagle extends BaseItem {
    /*
    * 获取设置失败图片
    */
    public String m_failedImg = "";

    /*
    * 构造函数
    */
    public TankEagle(String imagePath, String failedImg, FCPoint itemPosition, ItemGroupEnum itemGroup, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_failedImg = failedImg;
        setBorderColor(FCColor.None);
        setBounds(new FCRect(itemPosition.x, itemPosition.y, itemPosition.x + tankWar.m_itemSize.cx, itemPosition.y + tankWar.m_itemSize.cy));
    }

    /*
    * 获取控件类型
    */
    public String getViewType() {
        return "TankEagle";
    }
}
