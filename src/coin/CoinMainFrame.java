package coin;
import facecat.topin.btn.FCButton;
import facecat.topin.core.*;
import facecat.topin.chart.*;
import java.util.*;
import facecat.topin.swing.*;
import facecat.topin.grid.*;
import org.w3c.dom.Node;

/*
* 主框架
*/
public class CoinMainFrame extends UIXmlEx implements FCTouchEventCallBack, FCGridCellTouchEventCallBack, FCPaintEventCallBack
{
    /*
    * 创建行情系统
    */
    public CoinMainFrame() {
    }

    public FCView createView(Node node, String type)
    {
            FCNative inative = getNative();
            if (type.equals("ribbonbuttonx"))
            {
                    return new CoinRibbonButtonX();
            }
            else
            {
                    return super.createView(node, type);
            }
    }

    /*
    * 当前标题
    */
    public String m_currentTitle = "";

    /*
    * 当前交易所
    */
    public String m_currentExchange = "binance";

    /*
    * 当前行情
    */
    public String m_currentQuoteID = "binance/bitcoin/tether";

    /*
    * 选择对子
    */
    public String m_currentPari = "BTC/USDT";

    /*
    * 隐藏其他的内容
    */
    private void hideOtherContents(PyButtonDiv thisButton)
    {
        if (true)
        {
            FCGrid divButtons = getGrid("divButtons");
            ArrayList<FCGridRow> rows = divButtons.m_rows;
            for (int i = 0; i < rows.size(); i++)
            {
                FCGridRow row = rows.get(i);
                PyButtonDiv pyButton = (PyButtonDiv)(((FCGridViewCell)row.getCell(0)).getView());
                if (pyButton != thisButton)
                {
                    pyButton.hideContent();
                }
            }
        }
    }

    /*
    * 单元格点击事件
    */
    public void callGridCellTouchEvent(String eventName, Object sender, FCGridCell cell, FCTouchInfo touchInfo, Object invoke)
    {
        if (eventName.equals("ongridcellclick"))
        {
            PyButtonDiv pyButtonDiv = (PyButtonDiv)(((FCGridViewCell)cell).getView());
            pyButtonDiv.onClick(touchInfo);
        }
    }

    /*
    * 获取选中的按钮
    */
    public PyButtonDiv getSelectedPyButton()
    {
        FCGrid divButtons = getGrid("divButtons");
        ArrayList<FCGridRow> rows = divButtons.getSelectedRows();
        if (rows.size() > 0)
        {
            FCGridViewCell cell = (FCGridViewCell)rows.get(0).getCell(0);
            PyButtonDiv pyButton = (PyButtonDiv)cell.getView();
            return pyButton;
        }
        return null;
    }

    /*
    * 查询按钮、重置按钮点击事件
    */
    public void callTouchEvent(String eventName, Object sender, FCTouchInfo touchInfo, Object invoke)
    {
        if (touchInfo.m_firstTouch && touchInfo.m_clicks == 1)
        {
            FCView control = (FCView)sender;
            String name = control.getName();
            if (name.startsWith("BtnSelect"))
            {
                PyButtonDiv selectedButton = getSelectedPyButton();
                selectedButton.m_contentDiv.searchData(selectedButton.getText());
            }
            else if (control.getTag() != null)
            {
                String text = control.getTag().toString();
                //searchData(text);
                m_currentTitle = text;
                ArrayList<String> strs = new ArrayList<String>();
                strs.add("1分钟线");
                strs.add("5分钟线");
                strs.add("15分钟线");
                strs.add("30分钟线");
                strs.add("1小时线");
                strs.add("2小时线");
                strs.add("4小时线");
                strs.add("8小时线");
                strs.add("12小时线");
                strs.add("日线");
                strs.add("周线");
                PyButtonDiv selectedButton = getSelectedPyButton();
                int type = 0;
                if (strs.contains(text))
                {
                    type = 1;
                }
                hideOtherContents(selectedButton);
                selectedButton.addContent(type);
            }
            getNative().invalidate();
        }
    }

    /*
    * 加载XML
    */
    public void load(String xmlPath) {
        loadXml(xmlPath, null);
        FCView control = getNative().getViews().get(0);
        control.addEvent(this, "onclick", null);
        FCGrid divButtons = getGrid("divButtons");
        divButtons.setAllowDragRow(true);
        divButtons.addEvent(this, "ongridcellclick", this);
        divButtons.getRowStyle().setHoveredBackColor(FCColor.None);
        divButtons.getRowStyle().setSelectedBackColor(FCColor.None);
        divButtons.setCellEditMode(FCGridCellEditMode.DoubleClick);
        getDiv("divTop").addEvent(this, "onpaintborder", this);
        ArrayList<String> strs = new ArrayList<String>();
        strs.add("全部币种");
        strs.add("全部交易所");
        strs.add("币种列表(按交易所分)");
        strs.add("币种列表(按对子分)");
        strs.add("法币对美元汇率");
        strs.add("1分钟线");
        strs.add("5分钟线");
        strs.add("15分钟线");
        strs.add("30分钟线");
        strs.add("1小时线");
        strs.add("2小时线");
        strs.add("4小时线");
        strs.add("8小时线");
        strs.add("12小时线");
        strs.add("日线");
        strs.add("周线");
        for (int i = 0; i < strs.size(); i++) {
            FCGridRow row = new FCGridRow();
            row.setHeight(50);
            divButtons.addRow(row);
            FCGridViewCell viewCell = new FCGridViewCell();
            viewCell.setAllowEdit(true);
            PyButtonDiv pyButtonDiv = new PyButtonDiv();
            pyButtonDiv.m_mainFrame = this;
            pyButtonDiv.addEvent(this, "onclick", this);
            pyButtonDiv.addEvent(this, "ontouchdown", this);
            pyButtonDiv.m_cell = viewCell;
            pyButtonDiv.setText(strs.get(i));
            pyButtonDiv.setSize(new FCSize(180, 50));
            pyButtonDiv.setTag(strs.get(i));
            viewCell.setView(pyButtonDiv);
            row.addCell("colP1", viewCell);
            if (i == 0)
            {
                m_currentTitle = strs.get(i);
                pyButtonDiv.addContent(0);
            }
        }
        divButtons.update();
        getButton("BtnSelect").addEvent(this, "onclick", null);
        getButton("OpenFile").addEvent(this, "onclick", null);
        getButton("BtnFileSave").addEvent(this, "onclick", null);
        getButton("BtnSaveFile").addEvent(this, "onclick", null);
        getButton("SelectCell").addEvent(this, "onclick", null);
        getButton("BtnAllContact").addEvent(this, "onclick", null);
        //searchData("全部币种");
    }

    /*
    * 重绘方法
    */
    public boolean callPaintEvent(String eventName, Object sender, FCPaint paint, FCRect clipRect, Object invoke)
    {
        if (eventName.equals("onpaintborder"))
        {
            FCFont tFont = new FCFont("Default", 14);
            String str1 = "--";
            if (m_currentExchange.length() > 0)
            {
                str1 = m_currentExchange;
            }
            String str2 = "--";
            if (m_currentPari.length() > 0)
            {
                str2 = m_currentPari;
            }
            String str3 = "--";
            if (m_currentQuoteID.length() > 0)
            {
                str3 = m_currentQuoteID;
            }
            int right = 0;
            if (true)
            {
                FCSize tSize = paint.textSize(str1, tFont);
                FCRect b1 = new FCRect(22, 10, 200, 45);
                b1.right = b1.left + 80 + tSize.cx;
                right = b1.right;
                FCPoint[] points = new FCPoint[4];
                points[0] = new FCPoint(b1.left, b1.top);
                points[1] = new FCPoint(b1.left + 70, b1.top);
                points[2] = new FCPoint(b1.left + 60, b1.bottom - 1);
                points[3] = new FCPoint(b1.left, b1.bottom - 1);
                paint.fillPolygon(MyColor.USERCOLOR108, points);
                paint.drawRoundRect(MyColor.USERCOLOR108, 1, 0, b1, 0);
                FCDraw.drawText(paint, "交易所", MyColor.USERCOLOR3, tFont, b1.left + 10, b1.top + 10);
                FCDraw.drawText(paint, str1, MyColor.USERCOLOR3, tFont, b1.left + 70, b1.top + 10);
            }
            if (true)
            {
                FCSize tSize = paint.textSize(str2, tFont);
                FCRect b1 = new FCRect(right + 20, 10, 200, 45);
                b1.right = b1.left + 100 + tSize.cx;
                right = b1.right;
                FCPoint[] points = new FCPoint[4];
                points[0] = new FCPoint(b1.left, b1.top);
                points[1] = new FCPoint(b1.left + 90, b1.top);
                points[2] = new FCPoint(b1.left + 80, b1.bottom - 1);
                points[3] = new FCPoint(b1.left, b1.bottom - 1);
                paint.fillPolygon(MyColor.USERCOLOR109, points);
                paint.drawRoundRect(MyColor.USERCOLOR109, 1, 0, b1, 0);
                FCDraw.drawText(paint, "选择品种", MyColor.USERCOLOR3, tFont, b1.left + 10, b1.top + 10);
                FCDraw.drawText(paint, str2, MyColor.USERCOLOR3, tFont, b1.left + 90, b1.top + 10);
            }
            return false;
        }
        return false;
    }
}
