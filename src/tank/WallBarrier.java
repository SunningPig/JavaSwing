/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;
import java.util.*;

/// <summary>
/// 障碍物石头
/// </summary>
public class WallBarrier extends BaseItem {
    /// <summary>
    /// 构造函数
    /// </summary>
    /// <param name="imagePath">背景图片</param>
    /// <param name="itemPosition">位置</param>
    /// <param name="itemGroup">阵营</param>
    /// <param name="barrierType">障碍物种类</param>
    /// <param name="tankWar">坦克大战</param>
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

    /// <summary>
    /// 获取或设置障碍物类型
    /// </summary>
    public BarrierTypeEnum m_barrierType;
}
