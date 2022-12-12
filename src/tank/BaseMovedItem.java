package tank;
import facecat.topin.core.*;
import java.util.Random;

/*
* 移动项
*/
public class BaseMovedItem extends BaseItem {
    /*
    * 随机种子
    */
    protected Random m_random = new Random();

    /*
    * 构造方法
    */
    public BaseMovedItem(String imagePath, FCPoint itemPosition, int tankSpeed, ItemGroupEnum itemGroup, TankWar tankWar){
        super(imagePath, itemPosition, itemGroup, tankWar);
        m_speed = tankSpeed;
    }

    /*
    * 获取或设置的方向
    */
    public DirectionEnum m_direction = DirectionEnum.Up;

    /*
    * 获取或设置速度
    */
    public int m_speed;

    /*
    * 碰撞检测
    */
    public boolean collide()
    {
        return false;
    }
}
