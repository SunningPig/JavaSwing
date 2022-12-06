/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;

import static tank.BarrierTypeEnum.values;

/**
 *
 * @author taode
 */
public enum TreasureTypeEnum {
    /// <summary>
    /// 炸弹
    /// </summary>
    Bomb,
    /// <summary>
    /// 护罩
    /// </summary>
    Shield,
    /// <summary>
    /// 星星
    /// </summary>
    Star,
    /// <summary>
    /// 铁锹
    /// </summary>
    Shovel,
    /// <summary>
    /// 生命值
    /// </summary>
    Life,
    /// <summary>
    /// 时间
    /// </summary>
    Timer,
    /// <summary>
    /// 无
    /// </summary>
    None;
    public int getValue() {
        return this.ordinal();
    }

    public static TreasureTypeEnum forValue(int value) {
        return values()[value];
    }
}
