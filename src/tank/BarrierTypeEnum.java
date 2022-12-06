/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tank;

import facecat.topin.core.FCLayoutStyle;
import static facecat.topin.core.FCLayoutStyle.values;

/**
 *
 * @author taode
 */
public enum BarrierTypeEnum {
      /// <summary>
        /// 墙
        /// </summary>
        Wall,
        /// <summary>
        /// 钢铁
        /// </summary>
        Steel,
        /// <summary>
        /// 草地
        /// </summary>
        Grass,
        /// <summary>
        /// 水
        /// </summary>
        Water,
        /// <summary>
        /// 大怪
        /// </summary>
        Boss,
        /// <summary>
        /// 失败的大怪
        /// </summary>
        FailedBoss;
    
    public int getValue() {
        return this.ordinal();
    }

    public static BarrierTypeEnum forValue(int value) {
        return values()[value];
    }
}
