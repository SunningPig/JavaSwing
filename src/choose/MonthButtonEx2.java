/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package choose;

import java.util.ArrayList;
import java.util.Calendar;

import facecat.topin.core.*;
import facecat.topin.date.*;
import facecat.topin.swing.*;

public class MonthButtonEx2 extends MonthButton {
    /// <summary>
    /// 创建按钮
    /// </summary>
    /// <param name="calendar"></param>
    public MonthButtonEx2(FCCalendar calendar)
    {
        super(calendar);
        m_calendarEx = (FCCalendarEx2)calendar;
    }

    private FCCalendarEx2 m_calendarEx;

    /// <summary>
    /// 重绘背景方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintBackGround(FCPaint paint, FCRect clipRect)
    {
        if (m_bounds.right - m_bounds.left > 0)
        {
            long backColor = getPaintingBackColor();
            FCRect bounds = m_bounds.clone();
            bounds.left += 1;
            bounds.top += 1;
            bounds.right -= 1;
            bounds.bottom -= 1;
            paint.fillRoundRect(MyColor.USERCOLOR64, bounds, 4);
        }
    }

    /// <summary>
    /// 重绘边线方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintBorder(FCPaint paint, FCRect clipRect)
    {
        long borderColor = getPaintingBorderColor();
        //paint.drawLine(borderColor, 1, 0, m_bounds.left, m_bounds.bottom - 1, m_bounds.right - 1, m_bounds.bottom - 1);
        //paint.drawLine(borderColor, 1, 0, m_bounds.right - 1, m_bounds.top, m_bounds.right - 1, m_bounds.bottom - 1);
    }

    /// <summary>
    /// 重绘前景方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintForeground(FCPaint paint, FCRect clipRect)
    {
        int width = m_bounds.right - m_bounds.left;
        int height = m_bounds.bottom - m_bounds.top;
        String monthStr = GetMonthStr();
        FCFont font = new FCFont("Default", 16 - 4, false, false, false);
        FCSize textSize = paint.textSize(monthStr, font, -1);
        //创建渐变刷
        FCRect tRect = new FCRect();
        tRect.left = m_bounds.left + 15;
        tRect.top = m_bounds.top + 15;
        tRect.right = tRect.left + textSize.cx;
        tRect.bottom = tRect.top + textSize.cy;

        int dataCount = 0;
        ArrayList<UpDownInfo> upDownInfos = new ArrayList<UpDownInfo>();
        double max = 100, min = 100, rateClose = 100;
        for (int i = 0; i < m_calendarEx.m_mainFrame.m_upDownInfos.size(); i++)
        {
            UpDownInfo upDownInfo = m_calendarEx.m_mainFrame.m_upDownInfos.get(i);
            Calendar dt = FCTran.numToDate(m_calendarEx.m_mainFrame.m_upDownInfos.get(i).m_date);
            if (dt.get(Calendar.YEAR) == getYear() && dt.get(Calendar.MONTH) + 1 == getMonth())
            {
                dataCount++;
                upDownInfos.add(upDownInfo);
                rateClose = rateClose * (1 + upDownInfo.m_avgRange);
                double maxRate = rateClose * (1 + upDownInfo.m_avgRange);
                double minRate = rateClose * (1 + upDownInfo.m_avgRange2);
                if (max < Math.max(maxRate, minRate))
                {
                    max = Math.max(maxRate, minRate);
                }
                if (min > Math.min(maxRate, minRate))
                {
                    min = Math.min(maxRate, minRate);
                }
            }
        }
        if (dataCount > 0)
        {
            if (dataCount >= 2)
            {
                double distance = (double)(width - 15) / upDownInfos.size();
                if (distance > 1)
                {
                    distance = (int)distance;
                    if (distance % 2 == 0)
                    {
                        distance -= 1;
                    }
                }

                double left = m_bounds.left + 5;
                double rate2 = 100;
                FCRect kkRect = new FCRect(m_bounds.left + 5, m_bounds.top + 5, m_bounds.right - 5, m_bounds.bottom - 48);

                int day = 0;
                FCFont monthStrFont = new FCFont("Default", 16 - 4, false, false, false);
                int lastLeft = -1000;
                for (int i = 0; i < upDownInfos.size(); i++)
                {
                    UpDownInfo iudf = upDownInfos.get(i);
                    double closeRate = rate2;
                    if (i > 0)
                    {
                        closeRate = rate2 * (1 + iudf.m_avgRange);
                    }
                    double openRate = rate2 * (1 + iudf.m_avgRange2);
                    int barHeight = height - 20 - 50;
                    int y = (int)(m_bounds.top + height - 10 - barHeight * (closeRate - min) / (max - min)) - 40;
                    if (i == 0)
                    {
                        paint.drawLine(MyColor.USERCOLOR19, 1, 0, kkRect.left, y, kkRect.right, y);
                    }
                    int scaleX = (int)left;
                    int tWidth = 1;
                    if (distance > 1)
                    {
                        scaleX += (int)(distance / 2) + 1;
                        tWidth = (int)distance;
                    }
                    int xsub = tWidth / 2;
                    if (xsub < 1)
                    {
                        xsub = 1;
                    }

                    int yOpen = (int)(m_bounds.top + height - 10 - barHeight * (openRate - min) / (max - min)) - 40;
                    if (yOpen >= y)
                    {
                        FCRect barRect = new FCRect(scaleX - xsub, y, scaleX + xsub, yOpen);
                        paint.fillRect(MyColor.USERCOLOR48, barRect);
                    }
                    else
                    {
                        FCRect barRect = new FCRect(scaleX - xsub, yOpen, scaleX + xsub, y);
                        paint.fillRect(MyColor.USERCOLOR51, barRect);
                    }
                    rate2 = rate2 * (1 + iudf.m_avgRange);
                    Calendar thisDateTime = FCTran.numToDate(upDownInfos.get(i).m_date);
                    if (day != thisDateTime.get(Calendar.DAY_OF_MONTH))
                    {
                        day = thisDateTime.get(Calendar.DAY_OF_MONTH);
                        if ((int)left - lastLeft > 20)
                        {
                            //paint.drawLine(MyColor.USERCOLOR62, 1, 1, (int)left, y, (int)left, kkRect.bottom - 1);
                            String monthStr2 = FCTran.intToStr(day);
                            FCSize monthStrSize = paint.textSize(monthStr2, monthStrFont);
                            FCRect dayStrRect = new FCRect((int)scaleX - monthStrSize.cx / 2, kkRect.bottom, (int)scaleX + monthStrSize.cx / 2, kkRect.bottom + monthStrSize.cy);
                            paint.drawText(monthStr2, MyColor.USERCOLOR11, monthStrFont, dayStrRect);
                            lastLeft = (int)left;
                        }
                    }
                    left += distance;
                }

                String scaleStr = "+" + FCTran.getValueByDigit((max - 100), 2) + "%";
                FCSize scaleStrSize = paint.textSize(scaleStr, monthStrFont);
                FCRect scaleStrRect = new FCRect(kkRect.right - scaleStrSize.cx - 2, kkRect.top + 2, kkRect.right - 2, kkRect.top + scaleStrSize.cy + 2);
                paint.drawText(scaleStr, MyColor.USERCOLOR11, monthStrFont, scaleStrRect);

                scaleStr = FCTran.getValueByDigit((min - 100), 2) + "%";
                scaleStrSize = paint.textSize(scaleStr, monthStrFont);
                scaleStrRect = new FCRect(kkRect.right - scaleStrSize.cx - 2, kkRect.bottom - 2 - scaleStrSize.cy, kkRect.right - 2, kkRect.bottom - 2);
                paint.drawText(scaleStr, MyColor.USERCOLOR11, monthStrFont, scaleStrRect);
            }
            int alpha = 255, alpha2 = 150;

            int realHeight = height - 5;
            if (realHeight > 0)
            {
                String diffStr = FCTran.getValueByDigit(rateClose - 100, 2) + "%";
                long color = FCColor.rgba(219, 68, 83, alpha);
                long color2 = FCColor.rgba(219, 68, 83, 200);
                if (rateClose - 100 >= 0)
                {
                    diffStr = "+" + diffStr;
                }
                else
                {
                    color = FCColor.rgba(59, 174, 218, alpha);
                    color2 = FCColor.rgba(59, 174, 218, 200);
                }
                FCFont rangeFont = new FCFont("Default", 22 - 4, false, false, false);
                FCSize rangeSize = paint.textSize(diffStr, rangeFont);
                FCRect rangeRect = new FCRect(m_bounds.left + (width - rangeSize.cx) / 2, m_bounds.top + realHeight - rangeSize.cy + 3, m_bounds.left + (width + rangeSize.cx) / 2, m_bounds.top + realHeight + 3);
                FCRect backRect = new FCRect(m_bounds.left + 1, m_bounds.bottom - 30 + 1, m_bounds.right - 1, m_bounds.bottom - 1);
                FCPoint[] backPoints = new FCPoint[4];
                backPoints[0] = new FCPoint(backRect.left + 5 + 5, backRect.top);
                backPoints[1] = new FCPoint(backRect.right - 1 - 5, backRect.top);
                backPoints[2] = new FCPoint(backRect.right - 6 - 5, backRect.bottom);
                backPoints[3] = new FCPoint(backRect.left + 5, backRect.bottom);
                paint.fillPolygon(color2, backPoints);
                //paint.drawPolygon(MyColor.USERCOLOR2, 1, 0, backPoints);
                paint.drawText(diffStr, MyColor.USERCOLOR3, rangeFont, rangeRect);
            }
        }
        else
        {
            FCRect kkRect = new FCRect(m_bounds.left + 5, m_bounds.top + 5, m_bounds.right - 5, m_bounds.bottom - 48);
            paint.fillRect(MyColor.USERCOLOR7, kkRect);
            FCRect backRect = new FCRect(m_bounds.left + 1, m_bounds.bottom - 30 + 1, m_bounds.right - 1, m_bounds.bottom - 1);
            FCPoint[] backPoints = new FCPoint[4];
            backPoints[0] = new FCPoint(backRect.left + 5 + 5, backRect.top);
            backPoints[1] = new FCPoint(backRect.right - 1 - 5, backRect.top);
            backPoints[2] = new FCPoint(backRect.right - 6 - 5, backRect.bottom);
            backPoints[3] = new FCPoint(backRect.left + 5, backRect.bottom);
            paint.fillPolygon(MyColor.USERCOLOR115, backPoints);
        }
        paint.drawText(monthStr, MyColor.USERCOLOR11, font, tRect, -1);
    }
}
