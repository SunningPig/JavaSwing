package coin;
import facecat.topin.core.*;
import facecat.topin.div.*;
import facecat.topin.grid.*;
import facecat.topin.swing.*;
import java.util.*;

/*
* 内容层
*/
public class CoinContentDiv extends FCDiv implements FCGridCellTouchEventCallBack
{
    /*
    * 表格
    */
    public FCGrid m_gridInfos;

    /*
    * 图形
    */
    public CoinChartDiv m_myChartDiv;

    public PyButtonDiv m_pyButton;

    public CoinMainFrame m_mainFrame;

    /*
    * 单元格点击事件
    */
    public void callGridCellTouchEvent(String eventName, Object sender, FCGridCell cell, FCTouchInfo touchInfo, Object invoke)
    {
        if (eventName.equals("ongridcellclick"))
        {
            if (sender == m_gridInfos)
            {
                if (m_mainFrame.m_currentTitle.equals("全部币种"))
                {
                    ArrayList<FCGridRow> selectedRows = m_gridInfos.getSelectedRows();
                    if (selectedRows.size() > 0)
                    {
                        getNative().invalidate();
                    }
                }
                else if (m_mainFrame.m_currentTitle.equals("全部交易所"))
                {
                    ArrayList<FCGridRow> selectedRows = m_gridInfos.getSelectedRows();
                    if (selectedRows.size() > 0)
                    {
                        m_mainFrame.m_currentExchange = selectedRows.get(0).getCell("id").getString();
                        getNative().invalidate();
                    }
                }
                else if (m_mainFrame.m_currentTitle.equals("币种列表(按交易所分)"))
                {
                    ArrayList<FCGridRow> selectedRows = m_gridInfos.getSelectedRows();
                    if (selectedRows.size() > 0)
                    {
                        m_mainFrame.m_currentPari = selectedRows.get(0).getCell("baseSymbol").getString() + "/" + selectedRows.get(0).getCell("quoteSymbol").getString();
                        m_mainFrame.m_currentQuoteID = selectedRows.get(0).getCell("exchangeId").getString() + "/" + selectedRows.get(0).getCell("baseId").getString() + "/" + selectedRows.get(0).getCell("quoteId").getString();
                        getNative().invalidate();
                    }
                }
                else if (m_mainFrame.m_currentTitle.equals("币种列表(按对子分)"))
                {
                    ArrayList<FCGridRow> selectedRows = m_gridInfos.getSelectedRows();
                    if (selectedRows.size() > 0)
                    {
                        m_mainFrame.m_currentPari = selectedRows.get(0).getCell("baseSymbol").getString() + "/" + selectedRows.get(0).getCell("quoteSymbol").getString();
                        m_mainFrame.m_currentQuoteID = selectedRows.get(0).getCell("exchangeId").getString() + "/" + selectedRows.get(0).getCell("baseId").getString() + "/" + selectedRows.get(0).getCell("quoteId").getString();
                        getNative().invalidate();
                    }
                }
            }
            else
            {
                FCView view = ((FCGridViewCell)cell).getView();
                PyButtonDiv pyButtonDiv = (PyButtonDiv)view;
                pyButtonDiv.onClick(touchInfo);
            }
        }
    }

    /*
    * 秒表ID
    */
    private int m_timerID = FCView.getNewTimerID();

    /*
    * 加载数据
    */
    public void loadData()
    {
        if(m_gridInfos!=null){
            m_gridInfos.setAutoEllipsis(true);
            m_gridInfos.addEvent(this, "ongridcellclick", this);
            m_gridInfos.setFont(new FCFont("Default", 16, true, false, false));
            m_gridInfos.addEvent(this, "ontimer", null);
            m_gridInfos.startTimer(m_timerID, 100);
            m_gridInfos.setHeaderHeight(25);
            m_gridInfos.setGridLineColor(FCColor.None);
        }
    }

    /*
    * 查找数据
    */
    public void searchData(String text)
    {
        long[] cellColors = new long[] { FCColor.rgb(80, 255, 255), FCColor.rgb(255, 255, 80),
                       FCColor.rgb(255, 80, 255), FCColor.rgb(80, 255, 80), FCColor.rgb(255, 80, 80) };
        cellColors = new long[] { MyColor.USERCOLOR3 };
        if (text.equals("币种列表(按交易所分)"))
        {
            ArrayList<CoinMarketInfo> marketInfos = CoinCapService.getMarketInfos(m_mainFrame.m_currentExchange, "");
            m_gridInfos.clearRows();
            m_gridInfos.clearColumns();
            m_gridInfos.invalidate();
            String tag1 = "exchangeId";
            String tag2 = "rank";
            String tag3 = "baseSymbol";
            String tag4 = "baseId";
            String tag5 = "quoteSymbol";
            String tag6 = "quoteId";
            String tag7 = "priceQuote";
            String tag8 = "priceUsd";
            String tag9 = "volumeUsd24Hr";
            String tag10 = "percentExchangeVolume";
            String tag11 = "tradesCount24Hr";
            String tag12 = "updated";
            ArrayList<String> subStrs = new ArrayList<String>();
            subStrs.add(tag1);
            subStrs.add(tag2);
            subStrs.add(tag3);
            subStrs.add(tag4);
            subStrs.add(tag5);
            subStrs.add(tag6);
            subStrs.add(tag7);
            subStrs.add(tag8);
            subStrs.add(tag9);
            subStrs.add(tag10);
            subStrs.add(tag11);
            subStrs.add(tag12);
            for (int j = 0; j < subStrs.size(); j++)
            {
                FCGridColumn column = new FCGridColumn();
                column.setText(subStrs.get(j));
                column.setName(subStrs.get(j));
                column.setAllowResize(true);
                column.setAllowDrag(true);
                column.setBorderColor(MyColor.USERCOLOR9);
                column.setBackColor(MyColor.USERCOLOR94);
                //if (subStrs[j].Length > 10
                //    || j == 3 || j == 4 || j == 6 || j == 7) {
                //    column.setWidth(200);
                //}
                m_gridInfos.addColumn(column);
            }
            for (int i = 0; i < marketInfos.size(); i++)
            {
                CoinMarketInfo data = marketInfos.get(i);
                GridRowEx row = new GridRowEx();
                row.setHeight(30);
                m_gridInfos.addRow(row);
                for (int j = 0; j < subStrs.size(); j++)
                {
                    FCGridStringCell cell = new FCGridStringCell();
                    row.addCell(j, cell);
                    switch (j)
                    {
                        case 0:
                            cell.setString(data.m_exchangeId);
                            break;
                        case 1:
                            cell.setString(data.m_rank);
                            break;
                        case 2:
                            cell.setString(data.m_baseSymbol);
                            break;
                        case 3:
                            cell.setString(data.m_baseId);
                            break;
                        case 4:
                            cell.setString(data.m_quoteSymbol);
                            break;
                        case 5:
                            cell.setString(data.m_quoteId);
                            break;
                        case 6:
                            cell.setString(data.m_priceQuote);
                            break;
                        case 7:
                            cell.setString(data.m_priceUsd);
                            break;
                        case 8:
                            cell.setString(data.m_volumeUsd24Hr);
                            break;
                        case 9:
                            cell.setString(data.m_percentExchangeVolume);
                            break;
                        case 10:
                            if (data.m_tradesCount24Hr != null)
                            {
                                cell.setString(data.m_tradesCount24Hr);
                            }
                            else
                            {
                                cell.setString("");
                            }
                            break;
                        case 11:
                            cell.setString(FCTran.doubleToStr(data.m_updated));
                            break;
                    }
                    FCGridCellStyle style3 = new FCGridCellStyle();
                    style3.setTextColor(cellColors[j % cellColors.length]);
                    style3.setFont(new FCFont("Default", 14, false, false, false));
                    cell.setStyle(style3);
                }
            }
        }
        else if (text.equals("币种列表(按对子分)"))
        {
            ArrayList<CoinMarketInfo> marketInfos = CoinCapService.getMarketInfos("", m_mainFrame.m_currentPari);
            m_gridInfos.clearRows();
            m_gridInfos.clearColumns();
            m_gridInfos.invalidate();
            String tag1 = "exchangeId";
            String tag2 = "rank";
            String tag3 = "baseSymbol";
            String tag4 = "baseId";
            String tag5 = "quoteSymbol";
            String tag6 = "quoteId";
            String tag7 = "priceQuote";
            String tag8 = "priceUsd";
            String tag9 = "volumeUsd24Hr";
            String tag10 = "percentExchangeVolume";
            String tag11 = "tradesCount24Hr";
            String tag12 = "updated";
            ArrayList<String> subStrs = new ArrayList<String>();
            subStrs.add(tag1);
            subStrs.add(tag2);
            subStrs.add(tag3);
            subStrs.add(tag4);
            subStrs.add(tag5);
            subStrs.add(tag6);
            subStrs.add(tag7);
            subStrs.add(tag8);
            subStrs.add(tag9);
            subStrs.add(tag10);
            subStrs.add(tag11);
            subStrs.add(tag12);
            for (int j = 0; j < subStrs.size(); j++)
            {
                FCGridColumn column = new FCGridColumn();
                column.setText(subStrs.get(j));
                column.setName(subStrs.get(j));
                column.setBorderColor(MyColor.USERCOLOR9);
                column.setBackColor(MyColor.USERCOLOR94);
                column.setAllowResize(true);
                column.setAllowDrag(true);
                //if (subStrs[j].Length > 10
                //    || j == 3 || j == 4 || j == 6 || j == 7) {
                //    column.setWidth(200);
                //}
                m_gridInfos.addColumn(column);
            }
            for (int i = 0; i < marketInfos.size(); i++)
            {
                CoinMarketInfo data = marketInfos.get(i);
                GridRowEx row = new GridRowEx();
                row.setHeight(30);
                m_gridInfos.addRow(row);
                for (int j = 0; j < subStrs.size(); j++)
                {
                    FCGridStringCell cell = new FCGridStringCell();
                    row.addCell(j, cell);
                    switch (j)
                    {
                        case 0:
                            cell.setString(data.m_exchangeId);
                            break;
                        case 1:
                            cell.setString(data.m_rank);
                            break;
                        case 2:
                            cell.setString(data.m_baseSymbol);
                            break;
                        case 3:
                            cell.setString(data.m_baseId);
                            break;
                        case 4:
                            cell.setString(data.m_quoteSymbol);
                            break;
                        case 5:
                            cell.setString(data.m_quoteId);
                            break;
                        case 6:
                            cell.setString(data.m_priceQuote);
                            break;
                        case 7:
                            cell.setString(data.m_priceUsd);
                            break;
                        case 8:
                            cell.setString(data.m_volumeUsd24Hr);
                            break;
                        case 9:
                            cell.setString(data.m_percentExchangeVolume);
                            break;
                        case 10:
                            if (data.m_tradesCount24Hr != null)
                            {
                                cell.setString(data.m_tradesCount24Hr);
                            }
                            else
                            {
                                cell.setString("");
                            }
                            break;
                        case 11:
                            cell.setString(FCTran.doubleToStr(data.m_updated));
                            break;
                    }
                    FCGridCellStyle style3 = new FCGridCellStyle();
                    style3.setTextColor(cellColors[j % cellColors.length]);
                    style3.setFont(new FCFont("Default", 14, false, false, false));
                    cell.setStyle(style3);
                }
            }
        }
        else if (text.equals("全部交易所"))
        {
            ArrayList<CoinExchangeInfo> exchangeInfos = CoinCapService.getExchangeInfos();
            m_gridInfos.clearRows();
            m_gridInfos.clearColumns();
            m_gridInfos.invalidate();
            String tag1 = "exchangeId";
            String tag2 = "name";
            String tag3 = "rank";
            String tag4 = "percentTotalVolume";
            String tag5 = "volumeUsd";
            String tag6 = "socket";
            String tag7 = "exchangeUrl";
            String tag8 = "updated";
            ArrayList<String> subStrs = new ArrayList<String>();
            subStrs.add(tag1);
            subStrs.add(tag2);
            subStrs.add(tag3);
            subStrs.add(tag4);
            subStrs.add(tag5);
            subStrs.add(tag6);
            subStrs.add(tag7);
            subStrs.add(tag8);
            for (int j = 0; j < subStrs.size(); j++)
            {
                FCGridColumn column = new FCGridColumn();
                column.setText(subStrs.get(j));
                column.setName(subStrs.get(j));
                column.setBorderColor(MyColor.USERCOLOR9);
                column.setBackColor(MyColor.USERCOLOR94);
                column.setAllowResize(true);
                column.setAllowDrag(true);
                if (subStrs.get(j).length() > 10
                    || j == 3 || j == 4 || j == 6 || j == 7)
                {
                    column.setWidth(200);
                }
                m_gridInfos.addColumn(column);
            }
            for (int i = 0; i < exchangeInfos.size(); i++)
            {
                CoinExchangeInfo data = exchangeInfos.get(i);
                GridRowEx row = new GridRowEx();
                row.setHeight(30);
                m_gridInfos.addRow(row);
                for (int j = 0; j < subStrs.size(); j++)
                {
                    FCGridStringCell cell = new FCGridStringCell();
                    row.addCell(j, cell);
                    switch (j)
                    {
                        case 0:
                            cell.setString(data.m_exchangeId);
                            break;
                        case 1:
                            cell.setString(data.m_name);
                            break;
                        case 2:
                            cell.setString(data.m_rank);
                            break;
                        case 3:
                            cell.setString(data.m_percentTotalVolume);
                            break;
                        case 4:
                            cell.setString(data.m_volumeUsd);
                            break;
                        case 5:
                            cell.setString(data.m_tradingPairs);
                            break;
                        case 6:
                            cell.setString(data.m_socket);
                            break;
                        case 7:
                            cell.setString(data.m_exchangeUrl);
                            break;
                        case 8:
                            cell.setString(FCTran.doubleToStr(data.m_updated));
                            break;
                    }
                    FCGridCellStyle style3 = new FCGridCellStyle();
                    style3.setTextColor(cellColors[j % cellColors.length]);
                    style3.setFont(new FCFont("Default", 14, false, false, false));
                    cell.setStyle(style3);
                }
            }
        }
        else if (text.equals("法币对美元汇率"))
        {
            List<CoinRateInfo> assetInfos = CoinCapService.getRateInfos();
            m_gridInfos.clearRows();
            m_gridInfos.clearColumns();
            m_gridInfos.invalidate();
            String tag1 = "id";
            String tag2 = "symbol";
            String tag3 = "currencySymbol";
            String tag4 = "rateUsd";
            String tag5 = "type"; ;
            ArrayList<String> subStrs = new ArrayList<String>();
            subStrs.add(tag1);
            subStrs.add(tag2);
            subStrs.add(tag3);
            subStrs.add(tag4);
            subStrs.add(tag5);
            for (int j = 0; j < subStrs.size(); j++)
            {
                FCGridColumn column = new FCGridColumn();
               column.setText(subStrs.get(j));
                column.setName(subStrs.get(j));
                column.setBorderColor(MyColor.USERCOLOR9);
                column.setBackColor(MyColor.USERCOLOR94);
                column.setAllowResize(true);
                column.setAllowDrag(true);
                if (subStrs.get(j).length() > 10)
                {
                    column.setWidth(150);
                }
                else if (j == 0 || j == 3)
                {
                    column.setWidth(200);
                }
                m_gridInfos.addColumn(column);
            }
            for (int i = 0; i < assetInfos.size(); i++)
            {
                CoinRateInfo data = assetInfos.get(i);
                GridRowEx row = new GridRowEx();
                row.setHeight(30);
                m_gridInfos.addRow(row);
                for (int j = 0; j < subStrs.size(); j++)
                {
                    FCGridStringCell cell = new FCGridStringCell();
                    row.addCell(j, cell);
                    switch (j)
                    {
                        case 0:
                            cell.setString(data.m_id);
                            break;
                        case 1:
                            cell.setString(data.m_symbol);
                            break;
                        case 2:
                            cell.setString(data.m_currencySymbol);
                            break;
                        case 3:
                            cell.setString(data.m_rateUsd);
                            break;
                        case 4:
                            cell.setString(data.m_type);
                            break;
                    }
                    FCGridCellStyle style3 = new FCGridCellStyle();
                    style3.setTextColor(cellColors[j % cellColors.length]);
                    style3.setFont(new FCFont("Default", 14, false, false, false));
                    cell.setStyle(style3);
                }
            }
        }
        else if (text.equals("全部币种"))
        {
            ArrayList<CoinAssetInfo> assetInfos = CoinCapService.getAssetInfos();
            m_gridInfos.clearRows();
            m_gridInfos.clearColumns();
            m_gridInfos.invalidate();
            String tag1 = "id";
            String tag2 = "rank";
            String tag3 = "symbol";
            String tag4 = "name";
            String tag5 = "supply";
            String tag6 = "maxSupply";
            String tag7 = "marketCapUsd";
            String tag8 = "volumeUsd24Hr";
            String tag9 = "priceUsd";
            String tag10 = "changePercent24Hr";
            String tag11 = "vwap24Hr";
            ArrayList<String> subStrs = new ArrayList<String>();
            subStrs.add(tag1);
            subStrs.add(tag2);
            subStrs.add(tag3);
            subStrs.add(tag4);
            subStrs.add(tag5);
            subStrs.add(tag6);
            subStrs.add(tag7);
            subStrs.add(tag8);
            subStrs.add(tag9);
            subStrs.add(tag10);
            subStrs.add(tag11);
            for (int j = 0; j < subStrs.size(); j++)
            {
                FCGridColumn column = new FCGridColumn();
                column.setText(subStrs.get(j));
                column.setName(subStrs.get(j));
                column.setAllowResize(true);
                column.setAllowDrag(true);
                column.setBorderColor(MyColor.USERCOLOR9);
                column.setBackColor(MyColor.USERCOLOR94);
                if (subStrs.get(j).length() > 10)
                {
                    column.setWidth(150);
                }
                m_gridInfos.addColumn(column);
            }
            for (int i = 0; i < assetInfos.size(); i++)
            {
                CoinAssetInfo data = assetInfos.get(i);
                GridRowEx row = new GridRowEx();
                row.setHeight(30);
                m_gridInfos.addRow(row);
                for (int j = 0; j < subStrs.size(); j++)
                {
                    FCGridStringCell cell = new FCGridStringCell();
                    row.addCell(j, cell);
                    switch (j)
                    {
                        case 0:
                            cell.setString(data.m_id);
                            break;
                        case 1:
                            cell.setString(data.m_rank);
                            break;
                        case 2:
                            cell.setString(data.m_symbol);
                            break;
                        case 3:
                            cell.setString(data.m_name);
                            break;
                        case 4:
                            cell.setString(data.m_supply);
                            break;
                        case 5:
                            cell.setString(data.m_maxSupply);
                            break;
                        case 6:
                            cell.setString(data.m_marketCapUsd);
                            break;
                        case 7:
                            cell.setString(data.m_volumeUsd24Hr);
                            break;
                        case 8:
                            cell.setString(data.m_priceUsd);
                            break;
                        case 9:
                            cell.setString(data.m_changePercent24Hr);
                            break;
                        case 10:
                            cell.setString(data.m_vwap24Hr);
                            break;
                    }
                    FCGridCellStyle style3 = new FCGridCellStyle();
                    style3.setTextColor(cellColors[j % cellColors.length]);
                    style3.setFont(new FCFont("Default", 14, false, false, false));
                    cell.setStyle(style3);
                }
            }
        }
        else
        {
            ArrayList<CoinSecurityData> securityDatas = null;
            String exchangeID = "";
            String baseId = "";
            String quoteId = "";
            if (m_mainFrame.m_currentQuoteID.length() == 0)
            {
                return;
            }
            String[] sss = m_mainFrame.m_currentQuoteID.split("[/]");
            exchangeID = sss[0];
            baseId = sss[1];
            quoteId = sss[2];
            //String url = String.Format("https://api.coincap.io/v2/candles?exchange=poloniex&interval={0}&baseId=ethereum&quoteId=bitcoin", interval);
            //united-states-dollar
            if (text.equals("1分钟线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "m1", baseId, quoteId);
            }
            else if (text.equals("5分钟线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "m5", baseId, quoteId);
            }
            else if (text.equals("15分钟线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "m15", baseId, quoteId);
            }
            else if (text.equals("30分钟线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "m30", baseId, quoteId);
            }
            else if (text.equals("1小时线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "h1", baseId, quoteId);
            }
            else if (text .equals("2小时线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "h2", baseId, quoteId);
            }
            else if (text.equals("4小时线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "h4", baseId, quoteId);
            }
            else if (text.equals("8小时线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "h8", baseId, quoteId);
            }
            else if (text.equals("12小时线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "h12", baseId, quoteId);
            }
            else if (text.equals("日线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "d1", baseId, quoteId);
            }
            else if (text.equals("周线"))
            {
                securityDatas = CoinCapService.getCoinSecurityDatas(exchangeID, "w1", baseId, quoteId);
            }
            m_myChartDiv.setVisible(true);
            m_myChartDiv.refreshData(securityDatas, true);
            m_myChartDiv.update();
            m_myChartDiv.invalidate();
        }
        if (m_gridInfos != null)
        {
            m_gridInfos.update();
        }
    }
}
