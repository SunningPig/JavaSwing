/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;

import static tank.DirectionEnum.values;

/**
 *
 * @author taode
 */
public enum TankGameState {
    /// <summary>
    /// 开始
    /// </summary>
    Begin,
    /// <summary>
    /// 失败
    /// </summary>
    Lose,
    /// <summary>
    /// 正在游戏
    /// </summary>
    Playing,
    /// <summary>
    /// 暂停
    /// </summary>
    Suspend;
    public int getValue() {
        return this.ordinal();
    }

    public static TankGameState forValue(int value) {
        return values()[value];
    }
}
