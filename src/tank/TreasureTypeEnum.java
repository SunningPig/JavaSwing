package tank;

import static tank.BarrierTypeEnum.values;

/*
* 道具类型战场
*/
public enum TreasureTypeEnum {
    /*
    *  炸弹
    */
    Bomb,
    /*
    *  护罩
    */
    Shield,
    /*
    *  星星
    */
    Star,
    /*
    *  铁锹
    */
    Shovel,
    /*
    *  生命值
    */
    Life,
    /*
    *  时间
    */
    Timer,
    /*
    *  无
    */
    None;
    public int getValue() {
        return this.ordinal();
    }

    public static TreasureTypeEnum forValue(int value) {
        return values()[value];
    }
}
