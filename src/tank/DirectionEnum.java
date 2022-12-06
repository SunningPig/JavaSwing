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
public enum DirectionEnum {
    /// <summary>
    /// 向上
    /// </summary>
    Up,
    /// <summary>
    /// 向下
    /// </summary>
    Down,
    /// <summary>
    /// 向左
    /// </summary>
    Left,
    /// <summary>
    /// 向右
    /// </summary>
    Right,
    /// <summary>
    /// 空方向
    /// </summary>
    None;
    public int getValue() {
        return this.ordinal();
    }

    public static DirectionEnum forValue(int value) {
        return values()[value];
    }
}
