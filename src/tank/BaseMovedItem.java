/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;
import facecat.topin.core.*;
import java.util.Random;

/// <summary>
/// 移动项
/// </summary>
public class BaseMovedItem extends BaseItem {
    /// <summary>
    /// 随机种子
    /// </summary>
    protected Random m_random = new Random();

    /// <summary>
    /// 构造方法
    /// </summary>
    /// <param name="itemPosition">位置</param>
    /// <param name="tankSpeed">速度</param>
    /// <param name="itemGroup">阵营</param>
    /// <param name="tankWar">坦克大战</param>
    public BaseMovedItem(String imagePath, FCPoint itemPosition, int tankSpeed, ItemGroupEnum itemGroup, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_speed = tankSpeed;
    }

    /// <summary>
    /// 获取或设置的方向
    /// </summary>
    public DirectionEnum m_direction = DirectionEnum.Up;

    /// <summary>
    /// 获取或设置速度
    /// </summary>
    public int m_speed;

    /// <summary>
    /// 碰撞检测
    /// </summary>
    /// <returns>状态</returns>
    public boolean collide()
    {
        return false;
    }
}
