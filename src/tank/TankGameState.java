package tank;

import static tank.DirectionEnum.values;

/*
* 游戏状态
*/
public enum TankGameState {
    /*
    * 开始
    */
    Begin,
    /*
    * 失败
    */
    Lose,
    /*
    * 正在游戏
    */
    Playing,
    /*
    * 暂停
    */
    Suspend;
    public int getValue() {
        return this.ordinal();
    }

    public static TankGameState forValue(int value) {
        return values()[value];
    }
}
