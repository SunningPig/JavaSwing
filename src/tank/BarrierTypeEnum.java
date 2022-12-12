package tank;

import facecat.topin.core.FCLayoutStyle;
import static facecat.topin.core.FCLayoutStyle.values;

/*
 * 素材类型
 */
public enum BarrierTypeEnum {
        /*
        * 墙
        */
        Wall,
        /*
        * 钢铁
        */
        Steel,
        /*
        * 草地
        */
        Grass,
        /*
        * 水
        */
        Water,
        /*
        * 大怪
        */
        Boss,
        /*
        * 失败的大怪
        */
        FailedBoss;
    
    public int getValue() {
        return this.ordinal();
    }

    public static BarrierTypeEnum forValue(int value) {
        return values()[value];
    }
}
