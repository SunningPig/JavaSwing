package tank;
import facecat.topin.core.*;
import java.util.*;

/*
* 开始标签
*/
public class PlayTank extends FCView {
    /*
    * 构造函数
    */
    public PlayTank(TankWar tankwar) {
        m_tankWar = tankwar;
        setBorderColor(FCColor.None);
        setFont(new FCFont("Default", 30, true, false, true));
        setTopMost(true);
    }

    /*
    * 随机种子
    */
    private Random m_rd = new Random();

    /*
    * 系统颜色
    */
    private long[] m_sysColors = new long[] { FCColor.rgba(255, 255, 255, 255), FCColor.rgba(255, 255, 0, 255), FCColor.rgba(255, 0, 255, 255), FCColor.rgba(82, 255, 255, 255) };

    private TankGameState m_gameState = TankGameState.Begin;

    /*
    * 获取游戏状态
    */
    public TankGameState getGameState() {
        return m_gameState;
    }

    /*
    * 设置游戏状态
    */
    public void setGameState(TankGameState value)
    {
        if (m_gameState != value)
        {
            m_gameState = value;
            if (m_gameState == TankGameState.Playing)
            {
                Sound.play("start");
            }
            else if (m_gameState == TankGameState.Lose)
            {
                Sound.play("failed");
            }
            m_tankWar.m_enemyTankStop = m_gameState != TankGameState.Playing;
        }
    }

    /*
    * 获取或设置战场
    */
    public TankWar m_tankWar;

    /*
    * 键盘方法
    */
    public void onKeyDown(char key) {
        super.onKeyDown(key);
        MyTank myTank = m_tankWar.m_myTank;
        if (key == 13) {
            m_tankWar.changeState();
        }
        else if (myTank != null && m_gameState == TankGameState.Playing) {
            if (key == 74) {
                myTank.fire();
            }
            else {
                int value = (int)key;
                DirectionEnum direction = DirectionEnum.None;
                switch (value) {
                    //向左
                    case 37:
                    case 65:
                        direction = DirectionEnum.Left;
                        break;
                    //向上
                    case 38:
                    case 87:
                        direction = DirectionEnum.Up;
                        break;
                    //向右
                    case 39:
                    case 68:
                        direction = DirectionEnum.Right;
                        break;
                    //向下
                    case 40:
                    case 83:
                        direction = DirectionEnum.Down;
                        break;
                }
                if (direction != DirectionEnum.None) {
                    myTank.move(direction);
                }
            }
        }
    }

    /*
    * 鼠标按下方法
    */
    public void onTouchDown(FCTouchInfo touchInfo) {
        super.onTouchDown(touchInfo);
        if (getGameState() == TankGameState.Playing) {
            MyTank myTank = m_tankWar.m_myTank;
            if (myTank != null) {
                myTank.fire();
            }
        }
        else {
            setGameState(TankGameState.Playing);
        }
    }

    /*
    * 重绘背景方法
    */
    public void onPaintBackground(FCPaint paint, FCRect clipRect) {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0) {
            String text = null;
            //失败
            if (m_gameState == TankGameState.Lose)
            {
                text = "游戏失败，点击或按回车重新开始";
            }
            //暂停
            else if (m_gameState == TankGameState.Suspend)
            {
                text = "游戏暂停，点击或按回车回到游戏";
            }
            //开始
            else if (m_gameState == TankGameState.Begin)
            {
                text = "点击或按回车开始游戏";
            }
            //绘制文字
            if (text != null) {
                FCSize textSize = paint.textSize(text, getFont(), -1);
                FCRect tRect = new FCRect();
                tRect.left = width / 2 - textSize.cx / 2;
                tRect.top = height / 2 - textSize.cy / 2;
                tRect.right = tRect.left + textSize.cx;
                tRect.bottom = tRect.top + textSize.cy;
                paint.drawText(text, FCColor.rgba(255, 255, 255, 255), getFont(), tRect, -1);
            }
            //绘制敌方坦克
            ArrayList<EnemyTank> m_enemyTanks = m_tankWar.m_enemyTanks;
            int enemyTanksSize = m_enemyTanks.size();
            for (int i = 0; i < enemyTanksSize; i++) {
                EnemyTank enemyTank = m_enemyTanks.get(i);
                //画血量
                int left = enemyTank.getLeft() + 1, top = enemyTank.getBottom() + 3, avgWidth = enemyTank.getWidth() / 3;
                for (int j = 0; j < 3; j++) {
                    FCRect rRect = new FCRect(left, top + 20, left + avgWidth, top + 30);
                    if (j < enemyTank.m_blood) {
                        paint.fillRect(FCColor.rgba(0, 255, 0, 255), rRect);
                    }
                    paint.drawRect(FCColor.rgba(50, 50, 50, 255), 1, 0, rRect);
                    left += avgWidth;
                }
                int tTop = enemyTank.getBottom() + 10;
                if (enemyTank.getTextColor() == FCColor.None) {
                    enemyTank.setTextColor(m_sysColors[m_rd.nextInt(m_sysColors.length)]);
                }
            }
            //绘制生命
            FCFont scoreFont = new FCFont("Default", 20, true, false, false);
            String strLife = "LIFE:" + m_tankWar.m_life;
            FCSize tsSize = paint.textSize(strLife, scoreFont, -1);
            int strX = 2, strY = height - tsSize.cy;
            FCRect tsRect = new FCRect(strX, strY, strX + tsSize.cx, strY + tsSize.cy);
            paint.drawText(strLife, FCColor.rgba(255, 0, 0, 255), scoreFont, tsRect, -1);
            //绘制分数
            String strScore = "SCORE:" + FCTran.intToStr(m_tankWar.m_score);
            tsSize = paint.textSize(strScore, scoreFont, -1);
            strX = width - tsSize.cx - 2;
            strY = height - tsSize.cy;
            tsRect = new FCRect(strX, strY, strX + tsSize.cx, strY + tsSize.cy);
            paint.drawText(strScore, FCColor.rgba(255, 0, 0, 255), scoreFont, tsRect, -1);
        }
    }
}