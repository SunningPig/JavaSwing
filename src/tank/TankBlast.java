/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;
import java.util.*;

public class TankBlast extends BaseItem {
    /// <summary>
    /// 构造函数
    /// </summary>
    /// <param name="itemPosition">位置</param>
    /// <param name="itemGroup">阵营</param>
    /// <param name="parentTank">所属类型</param>
    /// <param name="tankWar">坦克大战</param>
    public TankBlast(String imagePath, FCPoint itemPosition, ItemGroupEnum itemGroup, TankBase parentTank, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_parentTank = parentTank;
        setBorderColor(FCColor.None);
        setBounds(getRegionRect());
    }

    /// <summary>
    /// 获取或设置爆炸所属的坦克
    /// </summary>
    public TankBase m_parentTank;

    /// <summary>
    /// 获取控件类型
    /// </summary>
    /// <returns>类型</returns>
    public String getViewType() {
        return "TankBlast";
    }

    /// <summary>
    /// 获取物体的区域大小
    /// </summary>
    /// <returns></returns>
    public FCRect getRegionRect() {
        FCPoint location = getLocation();
        return new FCRect(location.x, location.y, location.x + 10, location.y + 10);
    }
}
