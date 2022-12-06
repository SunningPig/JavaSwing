/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coin;
import facecat.topin.core.*;
import facecat.topin.div.*;
import facecat.topin.btn.*;
import facecat.topin.swing.*;
import facecat.topin.grid.*;
import java.util.*;

/// <summary>
/// 执行按钮
/// </summary>
public class PyButtonDiv extends FCButton {
    /// <summary>
    /// 是否包含点
    /// </summary>
    /// <param name="point"></param>
    /// <returns></returns>
    public boolean containsPoint(FCPoint point)
    {
        return false;
    }

    /// <summary>
    /// 重绘方法
    /// </summary>
    /// <param name="paint"></param>
    /// <param name="clipRect"></param>
    public void onPaint(FCPaint paint, FCRect clipRect)
    {
        int width = getWidth(), height = getHeight();
        FCRect drawRect = new FCRect(0, 0, width - 10, height);
        int r = 0, g = 0, b = 0, a = 0;
        RefObject<Integer> refR = new RefObject<Integer>(r);
        RefObject<Integer> refG = new RefObject<Integer>(g);
        RefObject<Integer> refB = new RefObject<Integer>(b);
        RefObject<Integer> refA = new RefObject<Integer>(a);
        FCColor.toRgba(paint, getPaintingBackColor(), refR, refG, refB, refA);
        r = refR.argvalue;
        g = refG.argvalue;
        b = refB.argvalue;
        a = refA.argvalue;
        long borderColor = FCColor.rgb(r, g, b);
        boolean isSelectedRow = false;
        ArrayList<FCGridRow> rows = m_cell.getGrid().getSelectedRows();
        if (rows.contains(m_cell.getRow()))
        {
            isSelectedRow = true;
        }
        if (m_tick > 0 && isSelectedRow)
        {
            int d1 = 5, d2 = 15, dTick = m_tick - 20;
            if (MyColor.m_style != 1)
            {
                if (true)
                {
                    FCPoint[] points1 = new FCPoint[3];
                    points1[0] = new FCPoint(drawRect.left + d1, drawRect.top + d2);
                    points1[1] = new FCPoint(drawRect.left + d1, drawRect.top + d1);
                    points1[2] = new FCPoint(drawRect.left + d2, drawRect.top + d1);
                    if (m_tick > 20)
                    {
                        for (int i = 0; i < points1.length; i++)
                        {
                            points1[i] = new FCPoint(points1[i].x + dTick * (width / 6) / 30, points1[i].y + dTick * (height / 6) / 30);
                        }
                    }
                    paint.drawPolyline(MyColor.USERCOLOR3, 2, 0, points1);
                }
                if (true)
                {
                    FCPoint[] points1 = new FCPoint[3];
                    points1[0] = new FCPoint(drawRect.left + d1, drawRect.bottom - d2);
                    points1[1] = new FCPoint(drawRect.left + d1, drawRect.bottom - d1);
                    points1[2] = new FCPoint(drawRect.left + d2, drawRect.bottom - d1);
                    if (m_tick > 20)
                    {
                        for (int i = 0; i < points1.length; i++)
                        {
                            points1[i] = new FCPoint(points1[i].x + dTick * (width / 6) / 30, points1[i].y - dTick * (height / 6) / 30);
                        }
                    }
                    paint.drawPolyline(MyColor.USERCOLOR3, 2, 0, points1);
                }
                if (true)
                {
                    FCPoint[] points1 = new FCPoint[3];
                    points1[0] = new FCPoint(drawRect.right - d1, drawRect.top + d2);
                    points1[1] = new FCPoint(drawRect.right - d1, drawRect.top + d1);
                    points1[2] = new FCPoint(drawRect.right - d2, drawRect.top + d1);
                    if (m_tick > 20)
                    {
                        for (int i = 0; i < points1.length; i++)
                        {
                            points1[i] = new FCPoint(points1[i].x - dTick * (width / 6) / 30, points1[i].y + dTick * (height / 6) / 30);
                        }
                    }
                    paint.drawPolyline(MyColor.USERCOLOR3, 2, 0, points1);
                }
                if (true)
                {
                    FCPoint[] points1 = new FCPoint[3];
                    points1[0] = new FCPoint(drawRect.right - d1, drawRect.bottom - d2);
                    points1[1] = new FCPoint(drawRect.right - d1, drawRect.bottom - d1);
                    points1[2] = new FCPoint(drawRect.right - d2, drawRect.bottom - d1);
                    if (m_tick > 20)
                    {
                        for (int i = 0; i < points1.length; i++)
                        {
                            points1[i] = new FCPoint(points1[i].x - dTick * (width / 6) / 30, points1[i].y - dTick * (height / 6) / 30);
                        }
                    }
                    paint.drawPolyline(MyColor.USERCOLOR3, 2, 0, points1);
                }
            }
            if (isSelectedRow)
            {
                if (m_tick > 0 && m_tick <= 20)
                {
                    int dPos = (20 - m_tick) * width / 20 - 50;
                    FCRect blockRect = new FCRect(dPos, drawRect.top, dPos + 50, drawRect.bottom);
                    if (MyColor.m_style != 1)
                    {
                        paint.fillGradientRect(MyColor.USERCOLOR11, MyColor.USERCOLOR2, blockRect, 0, 0);
                    }
                    else
                    {
                        paint.fillGradientRect(FCColor.rgba(42, 138, 195, 50), FCColor.rgba(42, 138, 195, 100), blockRect, 0, 0);
                    }
                }
            }
        }

        if (isSelectedRow)
        {
            paint.fillRoundRect(MyColor.USERCOLOR23, drawRect, 0);
            paint.drawRoundRect(MyColor.USERCOLOR10, 2, 0, drawRect, 0);
        }
        else
        {
            paint.fillRoundRect(FCColor.rgba(r, g, b, 50), drawRect, 0);
            paint.drawRoundRect(borderColor, 2, 0, drawRect, 0);
        }
        FCFont tFont = new FCFont("Default", 12, false, false, false);
        String drawText = getText();
        FCSize tSize = paint.textSize(drawText, tFont, -1);
        FCDraw.drawText(paint, drawText, MyColor.USERCOLOR11, tFont, (width - tSize.cx) / 2, (height - tSize.cy) / 2);
        paint.drawLine(MyColor.USERCOLOR8, 1, 0, drawRect.left, drawRect.bottom, drawRect.right, drawRect.bottom);
    }

    /// <summary>
    /// 单元格
    /// </summary>
    public FCGridViewCell m_cell;

    /// <summary>
    /// 计时器
    /// </summary>
    public int m_tick = 0;

    /// <summary>
    /// 点击方法
    /// </summary>
    /// <param name="touchInfo"></param>
    public void onClick(FCTouchInfo touchInfo)
    {
        super.onClick(touchInfo);
        m_tick = 50;
        startTimer(m_timerID, 10);
    }

    /// <summary>
    /// 秒表ID
    /// </summary>
    private int m_timerID = FCView.getNewTimerID();

    /// <summary>
    /// 秒表方法
    /// </summary>
    /// <param name="timerID"></param>
    public void onTimer(int timerID)
    {
        super.onTimer(timerID);
        if (m_timerID == timerID)
        {
            if (m_tick > 0)
            {
                m_tick--;
                if (m_tick == 0)
                {
                    stopTimer(m_timerID);
                }
            }
            invalidate();
        }
    }

    /// <summary>
    /// 重绘边框方法
    /// </summary>
    /// <param name="paint"></param>
    /// <param name="clipRect"></param>
    public void onPaintBorder(FCPaint paint, FCRect clipRect)
    {
    }

    public CoinContentDiv m_contentDiv;

    public CoinMainFrame m_mainFrame;

    /// <summary>
    /// 隐藏内容
    /// </summary>
    public void hideContent()
    {
        if (m_contentDiv != null)
        {
            FCDiv mainDiv = m_contentDiv;
            mainDiv.setVisible(false);
        }
    }

    /// <summary>
    /// 添加内容
    /// </summary>
    public void addContent(int type)
    {
        if (m_contentDiv == null)
        {
            FCDiv contentDiv = m_mainFrame.getDiv("divContent");
            m_contentDiv = new CoinContentDiv();
            m_contentDiv.setBorderColor(FCColor.None);
            m_contentDiv.setBorderColor(FCColor.None);
            m_contentDiv.setNative(m_mainFrame.getNative());
            m_contentDiv.m_mainFrame = m_mainFrame;
            m_contentDiv.m_pyButton = this;
            contentDiv.addView(m_contentDiv);
            m_contentDiv.setDock(FCDockStyle.Fill);
            if (type == 0)
            {
                m_contentDiv.m_gridInfos = new FCGrid();
                m_contentDiv.m_gridInfos.setBackColor(MyColor.USERCOLOR64);
                m_contentDiv.m_gridInfos.setBorderColor(FCColor.None);
                m_contentDiv.m_gridInfos.setDock(FCDockStyle.Fill);
                m_contentDiv.addView(m_contentDiv.m_gridInfos);
            }
            else if (type == 1)
            {
                m_contentDiv.m_myChartDiv = new CoinChartDiv();
                m_contentDiv.m_myChartDiv.setDock(FCDockStyle.Fill);
                m_contentDiv.m_myChartDiv.setBackColor(MyColor.USERCOLOR64);
                m_contentDiv.m_myChartDiv.setBorderColor(FCColor.None);
                m_contentDiv.addView(m_contentDiv.m_myChartDiv);
            }
            m_contentDiv.loadData();
            getNative().update();
            getNative().invalidate();
            m_contentDiv.searchData(m_mainFrame.m_currentTitle);
        }
        if (m_contentDiv != null)
        {
            FCDiv mainDiv = m_contentDiv;
            mainDiv.setVisible(true);
        }
    }
}
