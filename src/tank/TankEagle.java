/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;
import java.util.*;

/// <summary>
/// 飞鹰基地
/// </summary>
public class TankEagle extends BaseItem {
    /// <summary>
    /// 获取设置失败图片
    /// </summary>
    public String m_failedImg = "";

    /// <summary>
    /// 构造函数
    /// </summary>
    /// <param name="imagePath">背景图片</param>
    /// <param name="failedImg">失败图片</param>
    /// <param name="itemPosition">位置</param>
    /// <param name="itemGroup">类型</param>
    /// <param name="tankWar">坦克大战</param>
    public TankEagle(String imagePath, String failedImg, FCPoint itemPosition, ItemGroupEnum itemGroup, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_failedImg = failedImg;
        setBorderColor(FCColor.None);
        setBounds(new FCRect(itemPosition.x, itemPosition.y, itemPosition.x + tankWar.m_itemSize.cx, itemPosition.y + tankWar.m_itemSize.cy));
    }

    /// <summary>
    /// 获取控件类型
    /// </summary>
    /// <returns>类型</returns>
    public String getViewType() {
        return "TankEagle";
    }
}
