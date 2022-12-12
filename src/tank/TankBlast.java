package tank;
import facecat.topin.core.*;
import java.util.*;

/*
* 爆炸
*/
public class TankBlast extends BaseItem {
    /*
    * 构造函数
    */
    public TankBlast(String imagePath, FCPoint itemPosition, ItemGroupEnum itemGroup, TankBase parentTank, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_parentTank = parentTank;
        setBorderColor(FCColor.None);
        setBounds(getRegionRect());
    }

    /*
    * 获取或设置爆炸所属的坦克
    */
    public TankBase m_parentTank;

    /*
    * 获取控件类型
    */
    public String getViewType() {
        return "TankBlast";
    }

    /*
    * 获取物体的区域大小
    */
    public FCRect getRegionRect() {
        FCPoint location = getLocation();
        return new FCRect(location.x, location.y, location.x + 10, location.y + 10);
    }
}
