/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;
import java.util.*;

/// <summary>
/// 宝物类
/// </summary>
public class TankTreasure extends BaseItem {
    /// <summary>
    /// 构造方法
    /// </summary>
    /// <param name="imagePath">图片路径</param>
    /// <param name="itemPosition">位置</param>
    /// <param name="itemGroup">阵营</param>
    /// <param name="tankWar">坦克大战</param>
    public TankTreasure(String imagePath, FCPoint itemPosition, ItemGroupEnum itemGroup, TreasureTypeEnum treasureType, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_treasureType = treasureType;
        setBorderColor(FCColor.None);
        setBounds(new FCRect(itemPosition.x, itemPosition.y, itemPosition.x + m_tankWar.m_itemSize.cx, itemPosition.y + m_tankWar.m_itemSize.cy));
    }

    /// <summary>
    /// 获取或设置宝物类型
    /// </summary>
    public TreasureTypeEnum m_treasureType;

    /// <summary>
    /// 获取控件类型
    /// </summary>
    /// <returns>类型</returns>
    public String getViewType() {
        return "TankTreasure";
    }
}
