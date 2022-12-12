package tank;

import static tank.BarrierTypeEnum.values;


/*
 * 阵营组别
 */
public enum ItemGroupEnum {
    /*
    * 我方阵营
    */
    Friendly,
    /*
    * 地方阵营
    */
    Enemy,
    /*
    * 其他阵营
    */
    Others;
    public int getValue() {
        return this.ordinal();
    }

    public static ItemGroupEnum forValue(int value) {
        return values()[value];
    }
}
