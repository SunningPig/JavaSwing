/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package choose;

import facecat.topin.core.*;
import facecat.topin.date.*;
import facecat.topin.swing.*;

public class DayButtonEx2 extends DayButton {
    /// <summary>
    /// 创建按钮
    /// </summary>
    /// <param name="calendar"></param>
    public DayButtonEx2(FCCalendar calendar)
    {
        super(calendar);
        m_calendarEx = (FCCalendarEx2)calendar;
    }

    private FCCalendarEx2 m_calendarEx;

    public boolean isSelected()
    {
        CDay selectedDay = m_calendar.getSelectedDay();
        if (m_day != null && m_day.getDay() == selectedDay.getDay()
                && m_day.getMonth() == selectedDay.getMonth()
                && m_day.getYear() == selectedDay.getYear())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /// <summary>
    /// 获取要绘制的前景色
    /// </summary>
    /// <returns></returns>
    public long getPaintingTextColor()
    {
        CDay selectedDay = m_calendar.getSelectedDay();
        if (m_day != null && m_day.getDay() == selectedDay.getDay()
                && m_day.getMonth() == selectedDay.getMonth()
                && m_day.getYear() == selectedDay.getYear())
        {
            return MyColor.USERCOLOR10;
        }
        else
        {
            if (m_inThisMonth)
            {
                return MyColor.USERCOLOR2;
            }
            else
            {
                return MyColor.USERCOLOR6;
            }
        }
    }

    public void onClick(FCTouchInfo touchInfo)
    {
        super.onClick(touchInfo);
        ((FCCalendarEx2)m_calendar).m_overFlowDiv.onClick(touchInfo);
    }

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
        //paint.fillRect(backColor, m_bounds);
    }

    /// <summary>
    /// 重绘边线方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintBorder(FCPaint paint, FCRect clipRect)
    {
    }

    /// <summary>
    /// 重绘前景方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintForeground(FCPaint paint, FCRect clipRect)
    {
        if (m_day != null && m_bounds.right - m_bounds.left > 0)
        {
            int width = m_bounds.right - m_bounds.left;
            int height = m_bounds.bottom - m_bounds.top;
            String dayStr = FCTran.intToStr(m_day.getDay());
            FCFont font = new FCFont("Default", 18 - 4, false, false, false);
            FCSize textSize = paint.textSize(dayStr, font, -1);
            FCRect tRect = new FCRect();
            tRect.left = m_bounds.left + 10;
            tRect.top = m_bounds.top + 10;
            tRect.right = tRect.left + textSize.cx;
            tRect.bottom = tRect.top + textSize.cy;
            paint.drawText(dayStr, getPaintingTextColor(), font, tRect, -1);
            CDay day = m_calendar.getSelectedDay();
            double numDate = FCTran.getDateNum(m_day.getYear(), m_day.getMonth(), m_day.getDay(), 0, 0, 0, 0);
            int alpha = 200, alpha2 = 100;
            boolean selected = false;
            CDay selectedDay = m_calendar.getSelectedDay();
            if (m_day != null && m_day.getDay() == selectedDay.getDay()
                    && m_day.getMonth() == selectedDay.getMonth()
                    && m_day.getYear() == selectedDay.getYear())
            {
                selected = true;
            }
            if (!selected && !m_inThisMonth)
            {
                alpha = 50;
                alpha2 = 50;
            }
            if (m_calendarEx.m_mainFrame.m_upDownInfos2.containsKey(numDate))
            {
                paint.drawLine(MyColor.USERCOLOR19, 1, 0, m_bounds.left + 0, m_bounds.top + height - 45, m_bounds.left + width, m_bounds.top + height - 45);
                UpDownInfo upDownInfo = m_calendarEx.m_mainFrame.m_upDownInfos2.get(numDate);
                int upCount = (int)upDownInfo.m_upCount;
                int downCount = (int)upDownInfo.m_downCount;
                int paeceCount = (int)upDownInfo.m_allCount - upCount - downCount;
                int dWidth = width / 4;
                int dSize = 20;
                int txTop = m_bounds.bottom - 50;
                String strUp = FCTran.intToStr(upCount);
                FCFont tttFont = new FCFont("Default", 16 - 4, false, false, false);
                FCRect leftRect = new FCRect(m_bounds.left, txTop, m_bounds.left, txTop + dSize * 2);
                FCSize tSize2 = paint.textSize(strUp, tttFont);
                FCRect tRect2 = new FCRect(leftRect.left, leftRect.top + (dSize * 2 - tSize2.cy) / 2, leftRect.left + tSize2.cx, leftRect.top + (dSize * 2 + tSize2.cy) / 2);
                tRect2.left += 5;
                tRect2.right += 5;
                paint.drawText(strUp, FCColor.rgba(219, 68, 83, alpha), tttFont, tRect2);

                FCPoint[] points2 = new FCPoint[3];
                points2[0] = new FCPoint(tRect2.right + 1, tRect2.bottom - 2);
                points2[1] = new FCPoint(tRect2.right + 17, tRect2.bottom - 2);
                points2[2] = new FCPoint(tRect2.right + 11, tRect2.top + 2);
                //paint.fillPolygon(FCColor.rgba(219, 68, 83, alpha), points2);
                //paint.drawPolygon(FCColor.rgba(255, 255, 255, alpha2), 1, 0, points2);


                dWidth = width / 2;
                String strPeace = FCTran.intToStr(paeceCount);
                FCRect midRect = new FCRect(m_bounds.left + dWidth - dSize, txTop, m_bounds.left + dWidth + dSize, txTop + dSize * 2);
                tSize2 = paint.textSize(strPeace, tttFont);
                tRect2 = new FCRect(midRect.left + (dSize * 2 - tSize2.cx) / 2, midRect.top + (dSize * 2 - tSize2.cy) / 2, midRect.left + (dSize * 2 + tSize2.cx) / 2, midRect.top + (dSize * 2 + tSize2.cy) / 2);
                paint.drawText(strPeace, FCColor.rgba(170, 178, 189, alpha), tttFont, tRect2);

                dWidth = width * 3 / 4;
                String strDown = FCTran.intToStr(downCount);
                tSize2 = paint.textSize(strDown, tttFont);
                tRect2 = new FCRect(m_bounds.right - tSize2.cx, leftRect.top + (dSize * 2 - tSize2.cy) / 2, m_bounds.right, leftRect.top + (dSize * 2 + tSize2.cy) / 2);
                //tRect2.left -= 25;
                //tRect2.right -= 25;
                paint.drawText(strDown, FCColor.rgba(9, 174, 218, alpha), tttFont, tRect2);

                FCPoint[] points3 = new FCPoint[3];
                points3[0] = new FCPoint(tRect2.right + 11, tRect2.bottom - 2);
                points3[1] = new FCPoint(tRect2.right + 17, tRect2.top + 2);
                points3[2] = new FCPoint(tRect2.right + 1, tRect2.top + 2);
                //paint.fillPolygon(FCColor.rgba(9, 174, 218, alpha), points3);
                //paint.drawPolygon(FCColor.rgba(255, 255, 255, alpha2), 1, 0, points3);

                int midWidth = width - 4;
                int ttWidth1 = (int)(midWidth * upDownInfo.m_upCount / upDownInfo.m_allCount);
                FCRect ttRect1 = new FCRect(m_bounds.left + 2, m_bounds.bottom - 20, m_bounds.left + ttWidth1 + 2, m_bounds.bottom - 2);
                if (ttRect1.right - ttRect1.left < 5)
                {
                    paint.fillRect(FCColor.rgba(219, 68, 83, alpha), ttRect1);
                    paint.drawRect(FCColor.rgba(255, 255, 255, alpha2), 1, 0, ttRect1);
                }
                else
                {
                    for (int i = ttRect1.left; i <= ttRect1.right; i += 10)
                    {
                        int tRight = i + 9;
                        if (tRight > ttRect1.right)
                        {
                            tRight = ttRect1.right;
                        }
                        FCRect blockRect = new FCRect(i, ttRect1.top, tRight, ttRect1.bottom);
                        paint.fillRect(FCColor.rgba(219, 68, 83, alpha), blockRect);
                        paint.drawRect(FCColor.rgba(255, 255, 255, alpha2), 1, 0, blockRect);
                    }
                }

                int ttWidth2 = (int)(midWidth * paeceCount / upDownInfo.m_allCount);
                ttRect1 = new FCRect(ttRect1.right, m_bounds.bottom - 20, ttRect1.right + ttWidth2, m_bounds.bottom - 2);
                if (ttRect1.right - ttRect1.left < 5)
                {
                    paint.fillRect(FCColor.rgba(170, 178, 189, alpha), ttRect1);
                    paint.drawRect(FCColor.rgba(255, 255, 255, alpha2), 1, 0, ttRect1);
                }
                else
                {
                    for (int i = ttRect1.left; i <= ttRect1.right; i += 10)
                    {
                        int tRight = i + 9;
                        if (tRight > ttRect1.right)
                        {
                            tRight = ttRect1.right;
                        }
                        FCRect blockRect = new FCRect(i, ttRect1.top, tRight, ttRect1.bottom);
                        paint.fillRect(FCColor.rgba(170, 178, 189, alpha), blockRect);
                        paint.drawRect(FCColor.rgba(255, 255, 255, alpha2), 1, 0, blockRect);
                    }
                }

                int ttWidth3 = (int)(midWidth * upDownInfo.m_downCount / upDownInfo.m_allCount);
                ttRect1 = new FCRect(ttRect1.right, m_bounds.bottom - 20, ttRect1.right + ttWidth3, m_bounds.bottom - 2);
                if (ttRect1.right - ttRect1.left < 5)
                {
                    paint.fillRect(FCColor.rgba(59, 174, 218, alpha), ttRect1);
                    paint.drawRect(FCColor.rgba(255, 255, 255, alpha2), 1, 0, ttRect1);
                }
                else
                {
                    for (int i = ttRect1.left; i <= ttRect1.right; i += 10)
                    {
                        int tRight = i + 9;
                        if (tRight > ttRect1.right)
                        {
                            tRight = ttRect1.right;
                        }
                        FCRect blockRect = new FCRect(i, ttRect1.top, tRight, ttRect1.bottom);
                        paint.fillRect(FCColor.rgba(59, 174, 218, alpha), blockRect);
                        paint.drawRect(FCColor.rgba(255, 255, 255, alpha2), 1, 0, blockRect);
                    }
                }

                int realHeight = height - 50;
                if (realHeight > 0)
                {
                    String diffStr = FCTran.getValueByDigit(upDownInfo.m_avgRange * 100, 2) + "%";
                    long color = FCColor.rgba(219, 68, 83, alpha);
                    if (upDownInfo.m_avgRange >= 0)
                    {
                        diffStr = "+" + diffStr;
                    }
                    else
                    {
                        color = FCColor.rgba(59, 174, 218, alpha);
                    }
                    FCFont rangeFont = new FCFont("Default", 24 - 8, false, false, false);
                    FCSize rangeSize = paint.textSize(diffStr, rangeFont);
                    FCRect rangeRect = new FCRect(m_bounds.left + (width - rangeSize.cx) / 2, m_bounds.top + realHeight - rangeSize.cy, m_bounds.left + (width + rangeSize.cx) / 2, m_bounds.top + realHeight);
                    paint.drawText(diffStr, color, rangeFont, rangeRect);
                }
            }
        }
    }
}

