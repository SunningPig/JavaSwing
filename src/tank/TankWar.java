package tank;
import facecat.topin.core.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/*
* 坦克大战
*/
public class TankWar extends FCView {
    /*
    * 创建战场
    */
    public TankWar() {
        setBackColor(FCColor.rgba(0, 0, 0, 255));
    }

    /*
    * GameOver 图片
    */
    private String m_gameOverTexture;

    /*
    * 游戏层
    */
    public PlayTank m_play;

    /*
    * 子弹集合
    */
    private ArrayList<TankMissile> m_missileList = new ArrayList<TankMissile>();

    /*
    * 爆炸列表
    */
    private ArrayList<TankBlast> m_tankBlastList = new ArrayList<TankBlast>();

    /*
    * 秒表ID
    */
    private int m_timerID = FCView.getNewTimerID();

    /*
    * 获取弹幕图片
    */
    public HashMap<BarrierTypeEnum, String> m_barrierImgs = new HashMap<BarrierTypeEnum, String>();

    /*
    * 获取或设置子弹图片
    */
    public String m_bulletImgs = "";

    /*
    * 获取或设置基地
    */
    public TankEagle m_eagle;

    /*
    * 获取或设置敌方坦克
    */
    public ArrayList<EnemyTank> m_enemyTanks = new ArrayList<EnemyTank>();

    /*
    * 获取或设置敌人坦克是否停止
    */
    public boolean m_enemyTankStop = true;

    /*
    * 获取或设置是否击中基地
    */
    public boolean m_hitEagle;

    /*
    * 获取或设置是否有时间偷取
    */
    public boolean m_isSteel = false;

    /*
    * 获取或设置需要绘图的集合
    */
    public ArrayList<BaseItem> m_items = new ArrayList<BaseItem>();

    /*
    * 获取或设置项的尺寸
    */
    public FCSize m_itemSize = new FCSize(40, 40);

    /*
    * 获取或设置生命
    */
    public int m_life = 3;

    /*
    * 获取或设置我的坦克
    */
    public MyTank m_myTank;

    /*
    * 获取或设置我的坦克是否停止
    */
    public boolean m_myTankStop;

    /*
    * 获取或设置分数
    */
    public int m_score;

    /*
    * 获取或设置护盾图片
    */
    public String m_shieldImg = "";

    /*
    * 获取皮肤图片
    */
    public HashMap<TreasureTypeEnum, String> m_treasureImgs = new HashMap<TreasureTypeEnum, String>();
    
    /*
    * 获取当前路径
    */
    public static String getCurrentPath(){
        return System.getProperty("user.dir");
    }

    /*
    * 添加我的坦克
    */
    private void addMyTank() {
        if (m_life > 0) {
            if (m_myTank == null) {
                String currentPath = getCurrentPath();
                // 初始化我方坦克
                MyTank myTank = new MyTank(currentPath + "\\config\\tank\\Content\\Images\\p1tank", new FCPoint(m_itemSize.cx * 4, m_itemSize.cy * 12), 1,
                    ItemGroupEnum.Friendly, this);
                myTank.m_missileImg = m_bulletImgs;
                myTank.m_blastImg = currentPath + "\\config\\tank\\Content\\Images\\explode1.bmp";
                m_items.add(myTank);
                addView(myTank);
                m_myTank = myTank;
            }
        }
    }

    /*
    * 添加敌方坦克
    */
    private void addEnemyTank() {
        String currentPath = getCurrentPath();
        try {
            String xmlPath = currentPath + "\\config\\tank\\Tank1.xml";
            String xml = "";
            RefObject<String> refXml = new RefObject<String>(xml);
            FCFile.read(xmlPath, refXml);
            xml = refXml.argvalue;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Document doc = builder.parse(is);
            
            //自动增加坦克
            int enemyTankSize = m_enemyTanks.size();
            Random rd = new Random();
            ArrayList<FCPoint> locations = new ArrayList<FCPoint>();
            locations.add(new FCPoint(0, 0));
            locations.add(new FCPoint(m_itemSize.cx * 6, 0));
            locations.add(new FCPoint(m_itemSize.cx * 12, 0));
            while (enemyTankSize < 3) {
                int index = rd.nextInt(3);
                TreasureTypeEnum treasureType = TreasureTypeEnum.None;
                String enemymissile = currentPath + "\\config\\tank\\Content\\Images\\enemymissile.png";
                int pos = 0;
                Node root = doc.getDocumentElement();
                NodeList nodeList = root.getChildNodes();
                int nodeListSize = nodeList.getLength();
                for(int i = 0; i < nodeListSize; ++i) {
                    Node node = nodeList.item(i);
                    String nodeName = node.getNodeName().toLowerCase();
                    if(node.getFirstChild() != null){
                        String nodeValue = node.getFirstChild().getNodeValue();
                        if (index == pos) {
                            EnemyTank enemyTank = null;
                            switch (nodeName) {
                                case "bomb":
                                    treasureType = TreasureTypeEnum.Bomb;
                                    break;
                                case "shield":
                                    treasureType = TreasureTypeEnum.Shield;
                                    break;
                                case "star":
                                    treasureType = TreasureTypeEnum.Star;
                                    break;
                                case "shovel":
                                    treasureType = TreasureTypeEnum.Shovel;
                                    break;
                                case "life":
                                    treasureType = TreasureTypeEnum.Life;
                                    break;
                                case "timer":
                                    treasureType = TreasureTypeEnum.Timer;
                                    break;
                                default:
                                    treasureType = TreasureTypeEnum.None;
                                    break;
                            }
                            FCPoint location = locations.get(rd.nextInt(locations.size()));
                            locations.remove(location);
                            switch (nodeName) {
                                case "slowtank":
                                    enemyTank = new EnemyTank(currentPath + "\\config\\tank\\Content\\Images\\enemy1", location, 2, ItemGroupEnum.Enemy, treasureType, this);
                                    enemyTank.m_missileImg = enemymissile;
                                    enemyTank.m_blastImg = currentPath + "\\config\\tank\\Content\\Images\\explode1.bmp";
                                    enemyTank.m_blood = 2;
                                    break;
                                case "quicktank":
                                    enemyTank = new EnemyTank(currentPath + "\\config\\tank\\Content\\Images\\enemy2", location, 1, ItemGroupEnum.Enemy, treasureType, this);
                                    enemyTank.m_missileImg = enemymissile;
                                    enemyTank.m_blastImg = currentPath + "\\config\\tank\\Content\\Images\\explode1.bmp";
                                    break;
                                case "armouredtank":
                                    enemyTank = new EnemyTank(currentPath + "\\config\\tank\\Content\\Images\\enemy3", location, 1, ItemGroupEnum.Enemy, treasureType, this);
                                    enemyTank.m_missileImg = enemymissile;
                                    enemyTank.m_blastImg = currentPath + "\\config\\tank\\Content\\Images\\explode1.bmp";
                                    enemyTank.m_blood = 3;
                                    enemyTank.m_missileQuantity = 2;
                                    break;
                            }
                            if (enemyTank != null) {
                                addView(enemyTank);
                                m_items.add(enemyTank);
                                m_enemyTanks.add(enemyTank);
                                enemyTankSize = m_enemyTanks.size();
                            }
                        }
                        pos++;
                    }
                }
            }
            locations.clear();            
        } catch (Exception var13) {
            boolean var4 = false;
        } 
    }

    /*
    * 修改游戏状态
    */
    public void changeState()
    {
        m_play.bringToFront();
        //游戏中
        if (m_play.getGameState() == TankGameState.Playing)
        {
            m_play.setGameState(TankGameState.Suspend);
        }
        //失败
        else if (m_play.getGameState() == TankGameState.Lose)
        {
            onRemove();
            loadItems();
            m_play.setGameState(TankGameState.Begin);
        }
        //暂停或开始
        else if (m_play.getGameState() == TankGameState.Suspend || m_play.getGameState() == TankGameState.Begin)
        {
            m_play.setGameState(TankGameState.Playing);
        }
        setFocused(true);
        getNative().invalidate();
    }

    /*
    * 检查游戏状态
    */
    private boolean checkGameStatus() {
        boolean gameOver = false;
        if (m_life == 0 || m_hitEagle) {
            gameOver = true;
        }
        return gameOver;
    }

    /*
    * 添加控件方法
    */
    public void onAdd() {
        super.onAdd();
        //添加图片
        loadItems();
    }

    /*
    * 键盘方法
    */
    public void onKeyDown(char key) {
        super.onKeyDown(key);
        if (m_play != null) {
            m_play.onKeyDown(key);
        }
    }

    /*
    * 销毁资源
    */
    public void delete() {
        if (!isDeleted()) {
            onRemove();
        }
        super.delete();
    }

    /*
    * 加载元素
    */
    private void loadItems() {
        if (m_treasureImgs.size() == 0)
        {
            String currentPath = getCurrentPath();
            // 加载宝物图片
            m_treasureImgs.put(TreasureTypeEnum.Bomb, currentPath + "\\config\\tank\\Content\\Images\\bomb.bmp");
            m_treasureImgs.put(TreasureTypeEnum.Star, currentPath + "\\config\\tank\\Content\\Images\\star.bmp");
            m_treasureImgs.put(TreasureTypeEnum.Shield, currentPath + "\\config\\tank\\Content\\Images\\shield.bmp");
            m_treasureImgs.put(TreasureTypeEnum.Shovel, currentPath + "\\config\\tank\\Content\\Images\\shovel.bmp");
            m_treasureImgs.put(TreasureTypeEnum.Timer, currentPath + "\\config\\tank\\Content\\Images\\timer.bmp");

            // 加载障碍物图片
            m_barrierImgs.put(BarrierTypeEnum.Grass, currentPath + "\\config\\tank\\Content\\Images\\smallgrass.bmp");
            m_barrierImgs.put(BarrierTypeEnum.Steel, currentPath + "\\config\\tank\\Content\\Images\\smallsteel.bmp");
            m_barrierImgs.put(BarrierTypeEnum.Wall, currentPath + "\\config\\tank\\Content\\Images\\smallwall.bmp");
            m_barrierImgs.put(BarrierTypeEnum.Water, currentPath + "\\config\\tank\\Content\\Images\\smallwater1.bmp");
            m_barrierImgs.put(BarrierTypeEnum.Boss, currentPath + "\\config\\tank\\Content\\Images\\boss.bmp");
            m_barrierImgs.put(BarrierTypeEnum.FailedBoss, currentPath + "\\config\\tank\\Content\\Images\\failedboss.bmp");

            // 加载护盾图片
            m_shieldImg = currentPath + "\\config\\tank\\Content\\Images\\shields.bmp";

            // 加载子弹图片
            m_bulletImgs = currentPath + "\\config\\tank\\Content\\Images\\bullet.bmp";

            // 加载GameOver图片
            m_gameOverTexture = currentPath + "\\config\\tank\\Content\\Images\\gameover.png";

            addMyTank();
            // 初始化飞鹰基地
            m_eagle = new TankEagle(m_barrierImgs.get(BarrierTypeEnum.Boss), m_barrierImgs.get(BarrierTypeEnum.FailedBoss), new FCPoint(m_itemSize.cx * 6, m_itemSize.cy * 12), ItemGroupEnum.Others, this);
            m_items.add(m_eagle);
            addView(m_eagle);
            addEnemyTank();
            // 加载地图信息
            try {
                String xmlPath = currentPath + "\\config\\tank\\Map1.xml";
                String xml = "";
                RefObject<String> refXml = new RefObject<String>(xml);
                FCFile.read(xmlPath, refXml);
                xml = refXml.argvalue;
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                InputStream is = new ByteArrayInputStream(xml.getBytes());
                Document doc = builder.parse(is);
                Node root = doc.getDocumentElement();
                NodeList nodeList = root.getChildNodes();
                int nodeListSize = nodeList.getLength();
                for(int i = 0; i < nodeListSize; ++i) {
                    Node node = nodeList.item(i);
                    String nodeName = node.getNodeName().toLowerCase();
                    if(node.getFirstChild() != null){
                        String nodeValue = node.getFirstChild().getNodeValue();
                        String[] strs = nodeValue.split("[,]");
                        WallBarrier barrier = null;
                        switch (nodeName)
                        {
                            case "wall":
                                barrier = new WallBarrier(m_barrierImgs.get(BarrierTypeEnum.Wall), new FCPoint(FCTran.strToInt(strs[0]), FCTran.strToInt(strs[1])), ItemGroupEnum.Others, BarrierTypeEnum.Wall, this);
                                break;
                            case "steel":
                                barrier = new WallBarrier(m_barrierImgs.get(BarrierTypeEnum.Steel), new FCPoint(FCTran.strToInt(strs[0]), FCTran.strToInt(strs[1])), ItemGroupEnum.Others, BarrierTypeEnum.Steel, this);
                                break;
                            case "grass":
                                barrier = new WallBarrier(m_barrierImgs.get(BarrierTypeEnum.Grass), new FCPoint(FCTran.strToInt(strs[0]), FCTran.strToInt(strs[1])), ItemGroupEnum.Others, BarrierTypeEnum.Grass, this);
                                break;
                            case "water":
                                barrier = new WallBarrier(m_barrierImgs.get(BarrierTypeEnum.Water), new FCPoint(FCTran.strToInt(strs[0]), FCTran.strToInt(strs[1])), ItemGroupEnum.Others, BarrierTypeEnum.Water, this);
                                break;
                        }
                        if (barrier != null)
                        {
                            m_items.add(barrier);
                            addView(barrier);
                        }
                    }
                }  
            } catch (Exception var13) {
                boolean var4 = false;
            } 
            //创建开始标签
            m_play = new PlayTank(this);
            m_play.setBounds(new FCRect(0, 0, getWidth(), getHeight()));
            m_play.setBackColor(FCColor.None);
            addView(m_play);
            for (EnemyTank enemyTank : m_enemyTanks)
            {
                enemyTank.bringToFront();
            }
            m_myTank.bringToFront();
            m_play.bringToFront();
            //重新设定秒表
            startTimer(m_timerID, 10);
        }
    }

    /*
    * 秒表回调函数
    */
    public void onTimer(int timerID) {
        callTimerEvents(FCEventID.Timer, timerID);
        if (m_timerID == timerID) {
            boolean isGameOver = checkGameStatus();
            if (isGameOver) {
                m_play.setGameState(TankGameState.Lose);
                changeState();
                return;
            }
            updateWar();
        }
    }

    /*
    * 删除元素
    */
    public void onRemove() {
        m_eagle = null;
        m_myTank = null;
        m_enemyTanks.clear();
        m_treasureImgs.clear();
        m_barrierImgs.clear();
        m_items.clear();
        ArrayList<FCView> controls = getViews();
        int controlsSize = controls.size();
        for (int i = 0; i < controlsSize; i++) {
            FCView control = controls.get(i);
            BaseItem baseItem = (BaseItem) ((control instanceof BaseItem) ? control : null);
            if (baseItem != null) {
                removeView(control);
                control.delete();
                i--;
                controlsSize--;
            }
        }
        if (m_play != null) {
            m_play.setGameState(TankGameState.Begin);
        }
        m_life = 3;
        m_hitEagle = false;
        if (m_play != null) {
            removeView(m_play);
            m_play.delete();
            m_play = null;
        }
        super.onRemove();
    }

    /*
    * 更新战场
    */
    public void updateWar() {
        if (getNative() != null) {
            ArrayList<BaseItem> itemsAddList = new ArrayList<BaseItem>();
            ArrayList<BaseItem> itemsRemoveList = new ArrayList<BaseItem>();
            int itemsSize = m_items.size();
            for (int s = 0; s < itemsSize; s++) {
                BaseItem item = m_items.get(s);
                if (item.m_isRemove) {
                    itemsRemoveList.add(item);
                }
            }
            //删除碰撞消失的wall，tank,等元素
            int itemsRemoveSize = itemsRemoveList.size();
            for (int i = 0; i < itemsRemoveSize; i++) {
                BaseItem removeItem = itemsRemoveList.get(i);
                m_items.remove(removeItem);
                EnemyTank enemyTank = (EnemyTank) ((removeItem instanceof EnemyTank) ? removeItem : null);
                if (m_myTank == removeItem) {
                    m_myTank = null;
                }
                else if (enemyTank != null) {
                    if (m_myTank != null) {
                        m_myTank.m_isShield = false;
                    }
                    m_isSteel = false;
                    if (m_play != null && m_play.getGameState() == TankGameState.Playing) {
                        m_enemyTankStop = false;
                    }
                    m_enemyTanks.remove(enemyTank);
                    m_score++;
                }
                removeView(removeItem);
                removeItem.delete();
            }
            //刷新坦克子弹
            ArrayList<TankBase> tanks = new ArrayList<TankBase>();
            if (m_myTank != null) {
                tanks.add(m_myTank);
            }
            int enemyTanksSize = m_enemyTanks.size();
            for (int i = 0; i < enemyTanksSize; i++) {
                tanks.add(m_enemyTanks.get(i));
            }
            int tanksSize = tanks.size();
            for (int i = 0; i < tanksSize; i++) {
                TankBase tankItem = tanks.get(i);
                ArrayList<TankMissile> missileList = tankItem.m_tankMissiles;
                int missilesSize = missileList.size();
                for (int j = 0; j < missilesSize; j++) {
                    boolean exist = containsView(missileList.get(j));
                    if (exist == false) {
                        addView(missileList.get(j));
                        m_missileList.add(missileList.get(j));
                    }
                }
                if (tankItem.m_blast.size() > 0)
                {
                    for (TankBlast blast : tankItem.m_blast) {
                        m_tankBlastList.add(blast);
                        addView(blast);
                    }
                }
            }
            //删除飞行过程中碰撞的子弹
            ArrayList<TankMissile> missileRemoveList = new ArrayList<TankMissile>();
            for (TankMissile missile : m_missileList) {
                missile.move(missile.m_missileDirection);
                if (missile.m_isRemove) {
                    missileRemoveList.add(missile);
                }
            }
            int removeSize = missileRemoveList.size();
            for (int i = 0; i < removeSize; i++) {
                TankMissile missile = (TankMissile) ((missileRemoveList.get(i) instanceof TankMissile) ? missileRemoveList.get(i) : null);
                if (containsView(missile)) {
                    removeView(missile);
                }
                m_missileList.remove(missile);
                missile.m_parentTank.m_tankMissiles.remove(missile);
                missile.delete();
            }
            missileRemoveList.clear();
            //删除爆炸图像元素
            int tankBlastListSize = m_tankBlastList.size();
            for (int i = 0; i < tankBlastListSize; i++) {
                removeView(m_tankBlastList.get(i));
                m_tankBlastList.get(i).delete();
            }
            m_tankBlastList.clear();
            //自动添加坦克
            addMyTank();
            addEnemyTank();
            invalidate();
        }
    }
}
