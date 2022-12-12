package facecat.topin.swing;

import facecat.topin.core.*;
import facecat.topin.chart.*;
import java.util.*;

/*
* K线示例
*/
public class ChartDemo {
    /*
    * 收盘价
    */
    public final static String CLOSE = "CLOSE";
    /*
    * 最高价
    */
    public final static String HIGH = "HIGH";
    /*
    * 最低价
    */
    public final static String LOW = "LOW";
    /*
    * 开盘价
    */
    public final static String OPEN = "OPEN";
    /*
    * 成交量
    */
    public final static String VOL = "VOL";
    /*
    * 成交额
    */
    public final static String AMOUNT = "AMOUNT";
    /*
    * 收盘价字段
    */
    public final static int CLOSE_INDEX = 0;
    /*
    * 最高价字段
    */
    public final static int HIGH_INDEX = 1;
    /*
    * 最低价字段
    */
    public final static int LOW_INDEX = 2;
    /*
    * 开盘价字段
    */
    public final static int OPEN_INDEX = 3;
    /*
    * 成交量字段
    */
    public final static int VOL_INDEX = 4;
    /*
    * 成交额字段
    */
    public final static int AMOUNT_INDEX = 5;
    
    private BarShape m_bar;
    
    private FCChart m_chart;
    
    private FCNative m_native;
    
    /*
    * 获取方法库
    */
    public FCNative getNative(){
        return m_native;
    }
    
    /*
    * 设置方法库
    */
    public void setNative(FCNative iNative){
        m_native = iNative;
    }
    
    /*
    *初始化图形
    */
    private void initChart(FCChart chart) {
        FCDataTable dataSource = m_chart.getDataSource();
        //设置可以拖动K线，成交量，线及标记
        m_chart.setCanMoveShape(true);
        //设置滚动加速
        m_chart.setScrollAddSpeed(true);
        //设置左右Y轴的宽度
        m_chart.setLeftVScaleWidth(85);
        m_chart.setRightVScaleWidth(85);
        //设置X轴刻度间距
        m_chart.setHScalePixel(20);
        //设置X轴
        m_chart.setHScaleFieldText("日期");
        m_chart.setIsMobile(true);
        //添加k线层
        ChartDiv candleDiv = m_chart.addDiv(50);
        //m_candleDiv.BackColor = FCDraw.FCCOLORS_BACKCOLOR4;
        candleDiv.getTitleBar().setText("日线");
        //设置主Div左右Y轴数值带下划线
        candleDiv.getVGrid().setVisible(true);
        candleDiv.getLeftVScale().setNumberStyle(NumberStyle.UnderLine);
        candleDiv.getLeftVScale().setPaddingTop(2);
        candleDiv.getLeftVScale().setPaddingBottom(2);
        candleDiv.getLeftVScale().setFont(new FCFont("Default", 14, false, false, false));
        candleDiv.getRightVScale().setNumberStyle(NumberStyle.UnderLine);
        candleDiv.getRightVScale().setFont(new FCFont("Default", 14, false, false, false));
        candleDiv.getRightVScale().setPaddingTop(2);
        candleDiv.getRightVScale().setPaddingBottom(2);
        ChartTitle priceTitle = new ChartTitle(CLOSE_INDEX, "", FCColor.rgba(255, 255, 255, 255), 2, true);
        priceTitle.setFieldTextMode(TextMode.Value);
        candleDiv.getTitleBar().getTitles().add(priceTitle);
        //添加K线图
        CandleShape candle = new CandleShape();
        candleDiv.addShape(candle);
        candle.setCloseField(CLOSE_INDEX);
        candle.setHighField(HIGH_INDEX);
        candle.setLowField(LOW_INDEX);
        candle.setOpenField(OPEN_INDEX);
        candle.setCloseFieldText("收盘");
        candle.setHighFieldText("最高");
        candle.setLowFieldText("最低");
        candle.setOpenFieldText("开盘");
        //添加成交量层
        ChartDiv volumeDiv = m_chart.addDiv(20);
        //设置成交量的单位
        volumeDiv.getLeftVScale().setDigit(0);
        volumeDiv.getLeftVScale().setFont(new FCFont("Default", 14, false, false, false));
        volumeDiv.getVGrid().setDistance(30);
        volumeDiv.getRightVScale().setDigit(0);
        volumeDiv.getRightVScale().setFont(new FCFont("Default", 14, false, false, false));
        //添加成交量
        m_bar = new BarShape();
        m_bar.setColorField (FCDataTable.getAutoField());
        m_bar.setStyleField(FCDataTable.getAutoField());
        volumeDiv.addShape(m_bar);
        m_bar.setFieldName(VOL_INDEX);
        //设置标题
        volumeDiv.getTitleBar().setText("成交量");
        //设置成交量显示名称
        m_bar.setFieldText("成交量");
        //设置成交量标题只显示值
        ChartTitle barTitle = new ChartTitle(VOL_INDEX, "成交量", m_bar.getDownColor(), 0, true);
        barTitle.setFieldTextMode(TextMode.Value);
        volumeDiv.getTitleBar().getTitles().add(barTitle);
        //添加指标层
        ChartDiv indDiv = m_chart.addDiv(30);
        indDiv.getVGrid().setDistance(40);
        indDiv.getLeftVScale().setPaddingTop(2);
        indDiv.getLeftVScale().setPaddingBottom(2);
        indDiv.getLeftVScale().setFont(new FCFont("Default", 14, false, false, false));
        indDiv.getRightVScale().setPaddingTop(2);
        indDiv.getRightVScale().setPaddingBottom(2);
        indDiv.getRightVScale().setFont(new FCFont("Default", 14, false, false, false));
        //设置X轴不可见
        candleDiv.getHScale().setVisible(false);
        candleDiv.getHScale().setHeight(0);
        volumeDiv.getHScale().setVisible(false);
        volumeDiv.getHScale().setHeight(0);
        indDiv.getHScale().setVisible(true);
        indDiv.getHScale().setHeight(22);
        //设置坐标轴的颜色
        dataSource.addColumn(CLOSE_INDEX);
        dataSource.addColumn(HIGH_INDEX);
        dataSource.addColumn(LOW_INDEX);
        dataSource.addColumn(OPEN_INDEX);
        dataSource.addColumn(VOL_INDEX);
        dataSource.addColumn(AMOUNT_INDEX);
        dataSource.addColumn(m_bar.getColorField());
        dataSource.addColumn(m_bar.getStyleField());
        dataSource.setColsCapacity(16);
        dataSource.setColsGrowStep(4);
    }

    /*
    * 获取证券数据
    */
    public static ArrayList<SecurityData> getSecurityDatas() {
        ArrayList<SecurityData> datas = new ArrayList<SecurityData>();
        String filePath =  System.getProperty("user.dir") + "\\SH600000.txt";
        String content = "";
        RefObject<String> refContent = new RefObject<String>(content);
        FCFile.read(filePath, refContent);
        content = refContent.argvalue;
        String[] strs = content.split("[\r\n]");
        int strsSize = strs.length;
        for (int i = 2; i < strs.length - 1; i++) {
            String str = strs[i];
            String[] subStrs = str.split(",");
            if(subStrs.length > 5){
                SecurityData securityData = new SecurityData();
                securityData.m_date = i;
                securityData.m_open = FCTran.strToDouble(subStrs[1]);
                securityData.m_high = FCTran.strToDouble(subStrs[2]);
                securityData.m_low = FCTran.strToDouble(subStrs[3]);
                securityData.m_close = FCTran.strToDouble(subStrs[4]);
                securityData.m_volume = FCTran.strToDouble(subStrs[5]);
                securityData.m_amount = FCTran.strToDouble(subStrs[6]);
                datas.add(securityData);
            }
        }
        return datas;
    }

    /*
    * 创建指标
    */
    public static FCScript createIndicator(FCChart chart, FCDataTable dataSource, String text, ChartDiv chartDiv) {
        FCScript indicator = new FCScript();
        indicator.setDataSource(dataSource);
        indicator.setName("");
        indicator.setDiv(chartDiv);
        //indicator.FullName = "";
        if (dataSource != null) {
            indicator.setSourceField(CLOSE, CLOSE_INDEX);
            indicator.setSourceField(HIGH, HIGH_INDEX);
            indicator.setSourceField(LOW, LOW_INDEX);
            indicator.setSourceField(OPEN, OPEN_INDEX);
            indicator.setSourceField(AMOUNT, AMOUNT_INDEX);
            indicator.setSourceField(CLOSE.substring(0, 1), CLOSE_INDEX);
            indicator.setSourceField(HIGH.substring(0, 1), HIGH_INDEX);
            indicator.setSourceField(LOW.substring(0, 1), LOW_INDEX);
            indicator.setSourceField(OPEN.substring(0, 1), OPEN_INDEX);
            indicator.setSourceField(VOL.substring(0, 1), VOL_INDEX);
            indicator.setSourceField(AMOUNT.substring(0, 1), AMOUNT_INDEX);
        }
        if (text != null && text.length() > 0) {
            indicator.setScript(text);
        }
        return indicator;
    }

    /*
    * 绑定历史数据
    */
    public static void bindHistoryDatas(FCChart chart, FCDataTable dataSource, ArrayList<FCScript> indicators, int[] fields, ArrayList<SecurityData> historyDatas) {
        dataSource.clear();
        int size = historyDatas.size();
        dataSource.setRowsCapacity(size + 10);
        dataSource.setRowsGrowStep(100);
        int columnsCount = dataSource.getColumnsCount();
        for (int i = 0; i < size; i++) {
            SecurityData securityData = historyDatas.get(i);
            if (dataSource == chart.getDataSource()) {
                insertData(chart, dataSource, fields, securityData);
            }
            else {
                double[] ary = new double[columnsCount];
                ary[0] = securityData.m_close;
                ary[1] = securityData.m_high;
                ary[2] = securityData.m_low;
                ary[3] = securityData.m_open;
                ary[4] = securityData.m_volume;
                for (int j = 5; j < columnsCount; j++) {
                    ary[j] = Double.NaN;
                }
                dataSource.addRow(securityData.m_date, ary, columnsCount);
            }
        }
        int indicatorsSize = indicators.size();
        for (int i = 0; i < indicatorsSize; i++) {
            indicators.get(i).onCalculate(0);
        }
    }

    /*
    * 插入数据
    */
    public static int insertData(FCChart chart, FCDataTable dataSource, int[] fields, SecurityData securityData) {
        double close = securityData.m_close, high = securityData.m_high, low = securityData.m_low, open = securityData.m_open, volume = securityData.m_volume, amount = securityData.m_amount;
        if (volume > 0 || close > 0) {
            if (high == 0) {
                high = close;
            }
            if (low == 0) {
                low = close;
            }
            if (open == 0) {
                open = close;
            }
        }
        else {
            close = Double.NaN;
            high = Double.NaN;
            low = Double.NaN;
            open = Double.NaN;
            volume = Double.NaN;
            amount = Double.NaN;
        }
        double date = securityData.m_date;
        dataSource.set(date, fields[4], volume);
        int index = dataSource.getRowIndex(date);
        dataSource.set2(index, fields[0], close);
        dataSource.set2(index, fields[1], high);
        dataSource.set2(index, fields[2], low);
        dataSource.set2(index, fields[3], open);
        dataSource.set2(index, fields[5], amount);
        return index;
    }

    /*
    * 插入最新数据
    */
    public static int insertLatestData(FCChart chart, FCDataTable dataSource, List<FCScript> indicators, int[] fields, SecurityData latestData) {
        if (latestData.m_close > 0 && latestData.m_volume > 0) {
            int indicatorsSize = indicators.size();
            int index = insertData(chart, dataSource, fields, latestData);
            for (int i = 0; i < indicatorsSize; i++) {
                indicators.get(i).onCalculate(index);
            }
            return index;
        }
        else {
            return -1;
        }
    }

    /*
    * 加载数据
    */
    public void load(){
        m_chart = new FCChart();
        m_chart.setDock(FCDockStyle.Fill);
        m_native.addView(m_chart);
        initChart(m_chart);

        ArrayList<IndicatorData> m_indicators = new ArrayList<IndicatorData>();
        m_indicators.add(new IndicatorData("ADTM", "动态买卖气指标", "CONST N=23;CONST M=8;CONST N3=10;CONST N4=20;\r\nDTM:=IF(OPEN<=REF(OPEN,1),0,MAX((HIGH-OPEN),(OPEN-REF(OPEN,1))));\rDBM:=IF(OPEN>=REF(OPEN,1),0,MAX((OPEN-LOW),(OPEN-REF(OPEN,1))));\rSTM:=SUM(DTM,N); \rSBM:=SUM(DBM,N);\rADTM:IF(STM>SBM,(STM-SBM)/STM,IF(STM=SBM,0,(STM-SBM)/SBM)); \rMAADTM:MA(ADTM,M);"));
        m_indicators.add(new IndicatorData("AMV", "成本价均线", "CONST M1=5;CONST M2=13;CONST M3=34;CONST M4=60;\r\nAMOV:=VOL*(OPEN+CLOSE)/2;\rAMV1:SUM(AMOV,M1)/SUM(VOL,M1); \rAMV2:SUM(AMOV,M2)/SUM(VOL,M2); \rAMV3:SUM(AMOV,M3)/SUM(VOL,M3);\rAMV4:SUM(AMOV,M4)/SUM(VOL,M4);"));
        m_indicators.add(new IndicatorData("AROON", "阿隆指标", "CONST N=25;\r\n上轨:(N-HHVBARS(HIGH,N))/N*100,COLORRED; \r下轨:(N-LLVBARS(HIGH,N))/N*100,COLORGREEN;"));
        m_indicators.add(new IndicatorData("ATR", "真实波幅", "CONST N=14;CONST M=8;\r\nMTR:MAX(MAX((HIGH-LOW),ABS(REF(CLOSE,1)-HIGH)),ABS(REF(CLOSE,1)-LOW));\rATR:MA(MTR,N);"));
        m_indicators.add(new IndicatorData("BRAR", "情绪指标", "CONST N=26;CONST M=8;\r\nBR:SUM(MAX(0,HIGH-REF(CLOSE,1)),N)/SUM(MAX(0,REF(CLOSE,1)-LOW),N)*100;\rAR:SUM(HIGH-OPEN,N)/SUM(OPEN-LOW,N)*100;"));
        m_indicators.add(new IndicatorData("BBI", "多空指标", "CONST N1=3;CONST N2=6;CONST N3=12;CONST N4=24;\r\nBBI:(MA(CLOSE,N1)+MA(CLOSE,N2)+MA(CLOSE,N3)+MA(CLOSE,N4))/4;"));
        m_indicators.add(new IndicatorData("BIAS", "乖离率", "CONST N1=6;CONST N2=12;CONST N3=24;CONST M4=60;\r\nBIAS1:(CLOSE-MA(CLOSE,N1))/MA(CLOSE,N1)*100; \rBIAS2:(CLOSE-MA(CLOSE,N2))/MA(CLOSE,N2)*100;\rBIAS3:(CLOSE-MA(CLOSE,N3))/MA(CLOSE,N3)*100;"));
        m_indicators.add(new IndicatorData("BOLL", "布林带", "CONST N=20;\r\nBOLL:MA(CLOSE,N); \rUB:BOLL+2*STD(CLOSE,N); \rLB:BOLL-2*STD(CLOSE,N);"));
        m_indicators.add(new IndicatorData("CCI", "商品路径指标", "CONST N=14;CONST M=9;\r\nTYP:=(HIGH+LOW+CLOSE)/3;\rCCI:(TYP-MA(TYP,N))/(0.015*AVEDEV(TYP,N));"));
        m_indicators.add(new IndicatorData("CR", "带状能量线", "CONST N=26;CONST M1=10;CONST M2=20;CONST M3=40;CONST M4=62;\r\nMID:=REF(HIGH+LOW,1)/2;\rCR:SUM(MAX(0,HIGH-MID),N)/SUM(MAX(0,MID-LOW),N)*100;\rMA1:REF(MA(CR,M1),M1/2.5+1);\rMA2:REF(MA(CR,M2),M2/2.5+1); \rMA3:REF(MA(CR,M3),M3/2.5+1); \rMA4:REF(MA(CR,M4),M4/2.5+1);"));
        m_indicators.add(new IndicatorData("DMA", "平均差", "CONST N1=10;CONST N2=50;\r\nDIF:MA(CLOSE,N1)-MA(CLOSE,N2); \rDIFMA:MA(DIF,N1);"));
        m_indicators.add(new IndicatorData("EMV", "简易波动指标", "CONST N=14;CONST M=9;\r\nVOLUME:=MA(VOL,N)/VOL; \rMID:=100*(HIGH+LOW-REF(HIGH+LOW,1))/(HIGH+LOW);\rEMV:MA(MID*VOLUME*(HIGH-LOW)/MA(HIGH-LOW,N),N); \rMAEMV:MA(EMV,M);"));
        m_indicators.add(new IndicatorData("KDJ", "随机指标", "CONST N=9;CONST M1=3;CONST M2=3;\r\nRSV:=(CLOSE-LLV(LOW,N))/(HHV(HIGH,N)-LLV(LOW,N))*100; K:SMA(RSV,M1,1); \rD:SMA(K,M2,1);\rJ:3*K-2*D;"));
        m_indicators.add(new IndicatorData("MA", "移动平均线", "CONST N1=5;CONST N2=10;CONST N3=20;CONST N4=30;CONST N5=120;CONST N6=250;\r\nMA5:MA(CLOSE,N1); \rMA10:MA(CLOSE,N2); \rMA20:MA(CLOSE,N3); \rMA30:MA(CLOSE,N4);\rMA120:MA(CLOSE,N5); \rMA250:MA(CLOSE,N6);"));
        m_indicators.add(new IndicatorData("MACD", "指数平滑异同平均线", "CONST SHORT=12;CONST LONG=26;CONST MID=9;\r\nDIF:EMA(CLOSE,SHORT)-EMA(CLOSE,LONG);\rDEA:EMA(DIF,MID);\rMACD:(DIF-DEA)*2,COLORSTICK;"));
        m_indicators.add(new IndicatorData("MFI", "资金流量指标", "CONST N=14;\r\nTYP:=(HIGH+LOW+CLOSE)/3; \rV1:=SUM(IF(TYP>REF(TYP,1),TYP*VOL,0),N)/SUM(IF(TYP<REF(TYP,1),TYP*VOL,0),N); \rMFI:100-(100/(1+V1));"));
        m_indicators.add(new IndicatorData("MTM", "动量线", "CONST N=12;CONST M=6;\r\nMTM:CLOSE-REF(CLOSE,N); \rMAMTM:MA(MTM,M);"));
        m_indicators.add(new IndicatorData("OBV", "累积能量线", "CONST N=30;\r\nVA:=IF(CLOSE>REF(CLOSE,1),VOL,-VOL); \rOBV:SUM(IF(CLOSE=REF(CLOSE,1),0,VA),0); \rMAOBV:MA(OBV,N);"));
        m_indicators.add(new IndicatorData("PBX", "瀑布线", "CONST N1=4;CONST N2=6;CONST N3=9;CONST N4=13;CONST N5=18;CONST N6=24;\r\nPBX1:(EMA(CLOSE,N1)+MA(CLOSE,N1*2)+MA(CLOSE,N1*4))/3; \rPBX2:(EMA(CLOSE,N2)+MA(CLOSE,N2*2)+MA(CLOSE,N2*4))/3;\rPBX3:(EMA(CLOSE,N3)+MA(CLOSE,N3*2)+MA(CLOSE,N3*4))/3; \rPBX4:(EMA(CLOSE,N4)+MA(CLOSE,N4*2)+MA(CLOSE,N4*4))/3;\rPBX5:(EMA(CLOSE,N5)+MA(CLOSE,N5*2)+MA(CLOSE,N5*4))/3; \rPBX6:(EMA(CLOSE,N6)+MA(CLOSE,N6*2)+MA(CLOSE,N6*4))/3;"));
        m_indicators.add(new IndicatorData("ROC", "变动率指标", "CONST N=12;CONST M=6;\r\nROC:100*(CLOSE-REF(CLOSE,N))/REF(CLOSE,N);\rMAROC:MA(ROC,M);"));
        m_indicators.add(new IndicatorData("RSI", "相对强弱指标", "CONST N1=6;CONST N2=12;CONST N3=24;\r\nLC:=REF(CLOSE,1);\rRSI1:SMA(MAX(CLOSE-LC,0),N1,1)/SMA(ABS(CLOSE-LC),N1,1)*100; \rRSI2:SMA(MAX(CLOSE-LC,0),N2,1)/SMA(ABS(CLOSE-LC),N2,1)*100;\r RSI3:SMA(MAX(CLOSE-LC,0),N3,1)/SMA(ABS(CLOSE-LC),N3,1)*100;"));
        m_indicators.add(new IndicatorData("SAR", "抛物线指标", "CONST N=5;CONST S=2;CONST A=20;\r\nSAR:SAR(HIGH,LOW,N,S,A),POINTDOT;"));
        m_indicators.add(new IndicatorData("TDXAROON", "通达信阿隆指标", "CONST N=25;CONST N2=6;\r\nL13:=LLV(LOW,13); \r下轨:(13-IF(REF(LOW,1)=L13,1,IF(REF(LOW,2)=L13,2,IF(REF(LOW,3)=L13,3, IF(REF(LOW,4)=L13,4,IF(REF(LOW,5)=L13,5,IF(REF(LOW,6)=L13,6,IF(REF(LOW,7)=L13,7, IF(REF(LOW,8)=L13,8,IF(REF(LOW,9)=L13,9,IF(REF(LOW,10)=L13,10,IF(REF(LOW,11)=L13,11, IF(REF(LOW,12)=L13,12,IF(REF(LOW,13)=L13,13,0))))))))))))))/13*100; H13:=HHV(HIGH,13); \r上轨:(13-IF(REF(HIGH,1)=H13,1,IF(REF(HIGH,2)=H13,2,IF(REF(HIGH,3)=H13,3, IF(REF(HIGH,4)=H13,4,IF(REF(HIGH,5)=H13,5,IF(REF(HIGH,6)=H13,6,IF(REF(HIGH,7)=H13,7, IF(REF(HIGH,8)=H13,8,IF(REF(HIGH,9)=H13,9,IF(REF(HIGH,10)=H13,10,IF(REF(HIGH,11)=H13,11, IF(REF(HIGH,12)=H13,12,IF(REF(HIGH,13)=H13,13,0))))))))))))))/13*100;"));
        m_indicators.add(new IndicatorData("TDXHCG", "通达信火车轨", "M1:=30; \rM2:=30; \rMA_HIGH:MA(HIGH,30),LINETHICK2;\rMA_LOW:MA(LOW,30),LINETHICK2; \rSTICKLINE(CLOSE>=OPEN,CLOSE,OPEN,3.4,0),COLOR0000AA; \rSTICKLINE(CLOSE>=OPEN,CLOSE,OPEN,3.0,0),COLOR0000BB ;\rSTICKLINE(CLOSE>=OPEN,CLOSE,OPEN,2.4,0),COLOR0000CC;\rSTICKLINE(CLOSE>=OPEN,CLOSE,OPEN,2.0,0),COLOR0000DD ; \rSTICKLINE(CLOSE>=OPEN,CLOSE,OPEN,1.6,0),COLOR0000EE; \rSTICKLINE(CLOSE>=OPEN,CLOSE,OPEN,1.2,0),COLOR0000FF;\rSTICKLINE(CLOSE>=OPEN,CLOSE,OPEN,0.6,0),COLOR0000FF;\rSTICKLINE(CLOSE>=OPEN,HIGH,LOW,0,0),COLORRED;\rSTICKLINE(CLOSE<=OPEN,CLOSE,OPEN,3.4,0),COLORAAAA00; \rSTICKLINE(CLOSE<OPEN,CLOSE,OPEN,3.0,0),COLORBBBB00; \rSTICKLINE(CLOSE<OPEN,CLOSE,OPEN,2.4,0),COLORCCCC00; \rSTICKLINE(CLOSE<OPEN,CLOSE,OPEN,2.0,0),COLORDDDD00;\rSTICKLINE(CLOSE<OPEN,CLOSE,OPEN,1.6,0),COLOREEEE00;\rSTICKLINE(CLOSE<OPEN,CLOSE,OPEN,1.2,0),COLORFFFF00; \rSTICKLINE(CLOSE<OPEN,CLOSE,OPEN,0.6,0),COLORFFFF00; \rSTICKLINE(CLOSE<OPEN,HIGH,LOW,0,0),COLORFFFF00;"));
        m_indicators.add(new IndicatorData("TDXJMBD", "通达信寂寞波段", "CONST N1=6;CONST N2=12;CONST N3=24;\r\nVAR1:=((CLOSE-LLV(LOW,15))/(HHV(HIGH,15)-LLV(LOW,15)))*100; \rVAR2:=REVERSE(VAR1); VAR3:=SMA(VAR1,5,1); \rK:=SMA(VAR3,3,1); D:=SMA(K,3,1);\rDRAWTEXT(CROSS(K,D) AND (D<8),15,'买'),COLOR00FF00; \rDRAWTEXT(CROSS(D,K) AND (D>75),85,'卖'),COLORFFFFFF;\rSTICKLINE((K>=D),K,D,6,0),COLOR0000A8; \rSTICKLINE((K>=D),K,D,5,0),COLOR0000C0; \rSTICKLINE((K>=D),K,D,4,0),COLOR0000E0; \rSTICKLINE((K>=D),K,D,3,0),COLOR0000F0; \rSTICKLINE((K>=D),K,D,2,0),COLOR0000D0; \rSTICKLINE((K>=D),K,D,1,0),COLOR8080FF;\rSTICKLINE((K<=D),K,D,6,0),COLOR008800;\rSTICKLINE((K<=D),K,D,5,0),COLOR009900;\rSTICKLINE((K<=D),K,D,4,0),COLOR00AA00;\rSTICKLINE((K<=D),K,D,3,0),COLOR00BB00; \rSTICKLINE((K<=D),K,D,2,0),COLOR00CC00;\rSTICKLINE((K<=D),K,D,1,0),COLOR00DD00;"));
        m_indicators.add(new IndicatorData("TDXMBKX", "通达信明白K线", "VAR1:=REF(MA(CLOSE,20),10); \r开:=OPEN-VAR1; \r高:=HIGH-VAR1; \r低:=LOW-VAR1; \r收:=CLOSE-VAR1; \rMID:=MA(CLOSE,20);\rUPPER:=MID+2*STD(CLOSE,20);\rLOWER:=MID-2*STD(CLOSE,20); \rV1:=(CLOSE-LOWER)/(UPPER-LOWER)*100; \rV2:=V1-EMA(V1,5);\rK1:=SUM(LLV(V2,4),4)/4;\rV3:=EMA(V2,64)*10; \rV4:=EMA(0.4*V2*(-1),3); \rV5:=-1*EMA(V2,39)*10; \rA:=V3>10; \rB:=V2>V4; \rD:=V5>10; \rE:=V3<=10 OR V2<=V4 OR V5<=10; \rSTICKLINE(收>=开 AND E,CLOSE,OPEN,2.5,0) ,COLORYELLOW; \rSTICKLINE(收<开 AND E,CLOSE,OPEN,2.5,0),COLORYELLOW;\rSTICKLINE(开>收 AND A,CLOSE,OPEN,2.5,0),COLORFF0000; \rSTICKLINE(开<=收 AND A,CLOSE,OPEN,2.5,0),COLORYELLOW; \rSTICKLINE(开>收 AND B,CLOSE,OPEN,2.5,0),COLORFF00FF; \rSTICKLINE(开<=收 AND B,CLOSE,OPEN,2.5,0),COLORFF00FF; \rSTICKLINE(开>收 AND D,CLOSE,OPEN,2.5,0),COLORGREEN;"));
        m_indicators.add(new IndicatorData("TDXQFQSW", "通达信潜伏趋势王", "V1:=(CLOSE-LLV(LOW,36))/(HHV(HIGH,36)-LLV(LOW,36))*100; \rV2:=SMA(V1,4,1); \rV3:=SMA(V2,4,1); \rV4:=SMA(V3,4,1); \r顶线:100; D:V4; \r底线:0; \rK:V3; \r低吸线:10,COLORGREEN,LINETHICK2;\r高抛线:90,COLORRED,LINETHICK2; \rDRAWTEXT(CURRBARSCOUNT()=5,3,'潜伏'),COLORRED;\rV6:=CROSS(V3,V4) AND V3<20; \rSTICKLINE(K>D , K,D ,2.5,0),COLORRED,LINETHICK2; \rSTICKLINE(D>K,K,D,2.5,0),COLORGREEN,LINETHICK2; \rV7:=CROSS(V4,V3) AND V3>80; \rV8:=CROSS(V2,V3) AND V3>80 AND V3>V4; \rDRAWTEXT(CROSS(K,11),5,'低买'); \rDRAWTEXT(CROSS(K,92),96,'大风险'); \rDRAWTEXT(CROSS(K,79),85,'小风险');"));
        m_indicators.add(new IndicatorData("TDXSSTDLT", "通达信顺势通道楼梯", "JJ:=(CLOSE+HIGH+LOW)/3; \rA:=EMA(JJ,10); \rB:=REF(A,1);\rSTICKLINE(A>B,A,B,2,0),COLORYELLOW; \rSTICKLINE(A<B,A,B,2,0),COLORGREEN; \r引力线a:(MA(CLOSE,30)+MA(CLOSE,35))/2; \r引力区1:引力线a*1.1; \r引力区2:引力线a*0.9;"));
        m_indicators.add(new IndicatorData("TRIX", "三重指数平均线", "CONST N=12;CONST M=9;\r\nMTR:=EMA(EMA(EMA(CLOSE,N),N),N); \rTRIX:(MTR-REF(MTR,1))/REF(MTR,1)*100; \rMATRIX:MA(TRIX,M);"));
        m_indicators.add(new IndicatorData("UDL", "引力线", "CONST N1=3;CONST N2=5;CONST N3=10;CONST N4=20;CONST M=6;\r\nUDL:(MA(CLOSE,N1)+MA(CLOSE,N2)+MA(CLOSE,N3)+MA(CLOSE,N4))/4; \rMAUDL:MA(UDL,M);"));
        m_indicators.add(new IndicatorData("WR", "威廉指标", "CONST N1=5;CONST N2=10;\r\nWR1:100*(HHV(HIGH,N1)-CLOSE)/(HHV(HIGH,N1)-LLV(LOW,N1)); \rWR2:100*(HHV(HIGH,N2)-CLOSE)/(HHV(HIGH,N2)-LLV(LOW,N2));"));
        m_indicators.add(new IndicatorData("TDXKDJYJ", "通达信KDJ预警", "N:=28; \rM1:=60; \rM2:=4;\rRSV:=(CLOSE-LLV(LOW,N))/(HHV(HIGH,N)-LLV(LOW,N))*100;\rK:=SMA(RSV,M1,1);\rD:=SMA(K,M2,1);\rJ:=3*K-2*D;\rYJ:CROSS(J,K) AND CROSS(J,D);"));
        m_indicators.add(new IndicatorData("TDXKMDCX", "通达信K买点出现指标", "HLM1:=MA(HHV(HIGH,485),17);\rHLM2:=MA(HHV(HIGH,222),17);\rHLM3:=MA(HHV(HIGH,96),17);\rHLM4:=MA(LLV(LOW,485),17);\rHLM5:=MA(LLV(LOW,222),17);\rHLM6:=MA(LLV(LOW,96),17);\rHLM7:=MA((HLM1*0.558+HLM2*0.558+HLM3*0.558+HLM4*0.96+HLM5*0.96+HLM6*0.96)/6,17);\rHLM8:=MA((HLM1*0.55+HLM2*0.55+HLM3*0.65+HLM4*1.25+HLM5*1.23+HLM6*1.2)/6,17);\rHLM9:=MA((HLM1*0.68+HLM2*0.68+HLM3*0.68+HLM4*1.3+HLM5*1.3+HLM6*1.3)/6,17);\rHLM10:=MA((HLM7*3+HLM8*2+HLM9)/6*1.738,17);\rHLM11:=SMA(ABS(LOW-REF(LOW,1)),3,1)/SMA(MAX(LOW-REF(LOW,1),0),3,1)*100;\rHLM12:=MA(IF(CLOSE*1.35<=HLM10,HLM11*10,HLM11/10),3);\rHLMH:=HHV(HLM12,30);\r关注:MA(IF(LOW<=LLV(LOW,13),(HLMH+HLM11*2)/2,0),3)/120,COLORFFFFFF;\rDRAWTEXT(CROSS(关注,1.2),关注*0.5-6,'↑▲'),COLORFF00FF;\rDRAWTEXT(CROSS(关注,3),关注,'买点出现'),COLOR00FFFF;\rDRAWICON(CROSS(关注,7.0),关注*0.5-20,14);"));

        ArrayList<SecurityData> datas = getSecurityDatas();
        ArrayList<FCScript> list = new ArrayList<FCScript>();
        int[] fields = new int[6];
        fields[0] = CLOSE_INDEX;
        fields[1] = HIGH_INDEX;
        fields[2] = LOW_INDEX;
        fields[3] = OPEN_INDEX;
        fields[4] = VOL_INDEX;
        fields[5] = AMOUNT_INDEX;
        bindHistoryDatas(m_chart, m_chart.getDataSource(), list, fields, datas);

        int colorField = m_bar.getColorField();
        int styleField = m_bar.getStyleField();
        int rowsCount = m_chart.getDataSource().getRowsCount();
        for (int i = 0; i < rowsCount; i++ ) {
            Double open = m_chart.getDataSource().get2(i, OPEN_INDEX);
            Double close = m_chart.getDataSource().get2(i, CLOSE_INDEX);
            if (open >= close) {
                m_chart.getDataSource().set2(i, colorField, FCColor.rgba(80, 255, 255, 255));
                m_chart.getDataSource().set2(i, styleField, 0);
            } 
            else {
                m_chart.getDataSource().set2(i, colorField, FCColor.rgba(255, 80, 80, 255)); 
                m_chart.getDataSource().set2(i, styleField, 1);
            }
        }

        IndicatorData indicatorData = m_indicators.get(26);
        FCScript script = createIndicator(m_chart, m_chart.getDataSource(), indicatorData.m_script, m_chart.getDivs().get(2));
        script.onCalculate(0);

        IndicatorData indicatorData2 = m_indicators.get(7);
        FCScript script2 = createIndicator(m_chart, m_chart.getDataSource(), indicatorData2.m_script, m_chart.getDivs().get(0));
        script2.onCalculate(0);
    }
}
