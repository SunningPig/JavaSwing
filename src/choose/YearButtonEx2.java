package choose;

import java.util.ArrayList;
import java.util.Calendar;

import facecat.topin.core.*;
import facecat.topin.date.*;
import facecat.topin.swing.*;

/*
* 年的按钮
*/
public class YearButtonEx2 extends YearButton {
    /*
    * 创建按钮
    */
    public YearButtonEx2(FCCalendar calendar)
    {
        super(calendar);
        m_calendarEx = (FCCalendarEx2)calendar;
    }

    private FCCalendarEx2 m_calendarEx;

    /*
    * 重绘背景方法
    */
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

    /*
    * 重绘边线方法
    */
    public void onPaintBorder(FCPaint paint, FCRect clipRect)
    {
        long borderColor = getPaintingBorderColor();
        //paint.drawLine(borderColor, 1, 0, m_bounds.left, m_bounds.bottom - 1, m_bounds.right - 1, m_bounds.bottom - 1);
        //paint.drawLine(borderColor, 1, 0, m_bounds.right - 1, m_bounds.top, m_bounds.right - 1, m_bounds.bottom - 1);
    }

    /*
    * 重绘前景方法
    */
    public void onPaintForeground(FCPaint paint, FCRect clipRect)
    {
        int width = m_bounds.right - m_bounds.left;
        int height = m_bounds.bottom - m_bounds.top;
        FCFont font = new FCFont("Default", 18 - 4, false, true, false);
        String yearStr = FCTran.intToStr(m_year);
        FCSize textSize = paint.textSize(yearStr, font, -1);
        //创建渐变刷
        FCRect tRect = new FCRect();
        tRect.left = m_bounds.left + 15;
        tRect.top = m_bounds.top + 15;
        tRect.right = tRect.left + textSize.cx;
        tRect.bottom = tRect.top + textSize.cy;

        int dataCount = 0;
        ArrayList<UpDownInfo> upDownInfos = new ArrayList<UpDownInfo>();
        double max = 100, min = 100, rate = 100;
        for (int i = 0; i < m_calendarEx.m_mainFrame.m_upDownInfos.size(); i++)
        {
            UpDownInfo upDownInfo = m_calendarEx.m_mainFrame.m_upDownInfos.get(i);
            Calendar dt = FCTran.numToDate(m_calendarEx.m_mainFrame.m_upDownInfos.get(i).m_date);
            if (dt.get(Calendar.YEAR) == getYear())
            {
                dataCount++;
                upDownInfos.add(upDownInfo);
                rate = rate * (1 + upDownInfo.m_avgRange);
                if (max < rate)
                {
                    max = rate;
                }
                if (min > rate)
                {
                    min = rate;
                }
            }
        }
        if (dataCount > 0)
        {
            if (dataCount >= 2)
            {
                double distance = (double)(width - 15) / (upDownInfos.size() - 1);
                double left = m_bounds.left + 5;
                FCPoint[] points = new FCPoint[dataCount];
                FCPoint[] points2a = new FCPoint[dataCount + 2];
                double rate2 = 100;
                FCRect kkRect = new FCRect(m_bounds.left + 5, m_bounds.top + 5, m_bounds.right - 5, m_bounds.bottom - 48);
                //paint.drawRect(MyColor.USERCOLOR19, 1, 0, kkRect);
                //paint.drawLine(MyColor.USERCOLOR6, 1, 1, kkRect.left, kkRect.top + (kkRect.bottom - kkRect.top) / 3, kkRect.right, kkRect.top + (kkRect.bottom - kkRect.top) / 3);
                //paint.drawLine(MyColor.USERCOLOR6, 1, 1, kkRect.left, kkRect.top + (kkRect.bottom - kkRect.top) * 2 / 3, kkRect.right, kkRect.top + (kkRect.bottom - kkRect.top) * 2 / 3);
                int month = 0;
                FCFont monthStrFont = new FCFont("Default", 16 - 4, false, false, false);
                for (int i = 0; i < upDownInfos.size(); i++)
                {
                    UpDownInfo iudf = upDownInfos.get(i);
                    double closeRate = rate2;
                    if (i > 0)
                    {
                        closeRate = rate2 * (1 + iudf.m_avgRange);
                    }
                    int barHeight = height - 20 - 50;
                    int y = (int)(m_bounds.top + height - 10 - barHeight * (closeRate - min) / (max - min)) - 40;
                    points[i] = new FCPoint((int)left, y);
                    if (i == 0)
                    {
                        paint.drawLine(MyColor.USERCOLOR19, 1, 0, kkRect.left, y, kkRect.right, y);
                    }
                    rate2 = rate2 * (1 + iudf.m_avgRange);
                    points2a[i + 1] = points[i];
                    Calendar thisDateTime = FCTran.numToDate(upDownInfos.get(i).m_date);
                    if (month != thisDateTime.get(Calendar.MONTH) + 1)
                    {
                        paint.drawLine(MyColor.USERCOLOR62, 1, 0, (int)left, y, (int)left, kkRect.bottom - 1);
                        month = thisDateTime.get(Calendar.MONTH) + 1;
                        String monthStr = FCTran.intToStr(month);
                        String monthStr2 =FCTran.intToStr(month);
                        FCSize monthStrSize = paint.textSize(monthStr2, monthStrFont);
                        FCRect dayStrRect = new FCRect((int)left - monthStrSize.cx / 2, kkRect.bottom, (int)left + monthStrSize.cx / 2, kkRect.bottom + monthStrSize.cy);
                        paint.drawText(monthStr2, MyColor.USERCOLOR11, monthStrFont, dayStrRect);
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

                points2a[0] = new FCPoint(m_bounds.left + 5, m_bounds.top + height - 50);
                points2a[points2a.length - 1] = new FCPoint(points[points.length - 1].x, m_bounds.top + height - 50);
                paint.fillGradientPolygon(FCColor.rgba(43, 138, 195, 75), FCColor.rgba(43, 138, 195, 75), points2a, 90);
                paint.drawPolyline(MyColor.USERCOLOR10, 2, 0, points);
            }

            int alpha = 255, alpha2 = 150;

            int realHeight = height - 5;
            if (realHeight > 0)
            {
                String diffStr = FCTran.getValueByDigit(rate - 100, 2) + "%";
                long color = FCColor.rgba(219, 68, 83, alpha);
                long color2 = FCColor.rgba(219, 68, 83, 200);
                if (rate - 100 >= 0)
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
        paint.drawText(yearStr, MyColor.USERCOLOR11, font, tRect, -1);
    }
}

