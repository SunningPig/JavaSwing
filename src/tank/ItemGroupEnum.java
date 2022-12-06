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
public enum ItemGroupEnum {
    /// <summary>
    /// 我方阵营
    /// </summary>
    Friendly,
    /// <summary>
    /// 地方阵营
    /// </summary>
    Enemy,
    /// <summary>
    /// 其他阵营
    /// </summary>
    Others;
    public int getValue() {
        return this.ordinal();
    }

    public static ItemGroupEnum forValue(int value) {
        return values()[value];
    }
}
