package tank;

import static tank.BarrierTypeEnum.values;

/*
 * 移动方向
 */
public enum DirectionEnum {
    /*
    * 向上
    */
    Up,
    /*
    * 向下
    */
    Down,
    /*
    * 向左
    */
    Left,
    /*
    * 向右
    */
    Right,
    /*
    * 空方向
    */
    None;
    public int getValue() {
        return this.ordinal();
    }

    public static DirectionEnum forValue(int value) {
        return values()[value];
    }
}
