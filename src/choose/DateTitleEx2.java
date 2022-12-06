/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package choose;

import facecat.topin.core.*;
import facecat.topin.swing.*;
import facecat.topin.date.CMonth;
import facecat.topin.date.DateTitle;
import facecat.topin.date.FCCalendar;
import facecat.topin.date.FCCalendarMode;

public class DateTitleEx2 extends DateTitle {
    public DateTitleEx2(FCCalendar calendar)
    {
        super(calendar);
        setFont(new FCFont("Default", 20, false, false, false));
        setTextColor(MyColor.USERCOLOR11);
        setSize(new FCSize(300, 50));
    }

    public int textWidth = 0;

    /// <summary>
    /// 重绘前景方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaintForeground(FCPaint paint, FCRect clipRect)
    {
        if (m_calendar != null)
        {
            int width = getWidth(), height = getHeight();
            FCFont font = getFont();
            String text = "";
            FCCalendarMode mode = m_calendar.getMode();
            //日
            if (mode == FCCalendarMode.Day)
            {
                CMonth month = m_calendar.getMonth();
                text = FCTran.intToStr(month.getYear()) + "年" + FCTran.intToStr(month.getMonth()) + "月";
            }
            //月
            else if (mode == FCCalendarMode.Month)
            {
                text = FCTran.intToStr(m_calendar.getMonthDiv().getYear()) + "年";
            }
            //年
            else if (mode == FCCalendarMode.Year)
            {
                int startYear = m_calendar.getYearDiv().getStartYear();
                text = FCTran.intToStr(startYear) + "年 - " + FCTran.intToStr((startYear + 12)) + "年";
            }
            FCSize tSize = paint.textSize(text, font, -1);
            FCRect tRect = new FCRect();
            tRect.left = (width - tSize.cx) / 2;
            tRect.top = (height - tSize.cy) / 2 - 5;
            tRect.right = tRect.left + tSize.cx + 1;
            tRect.bottom = tRect.top + tSize.cy + 1 - 5;
            paint.drawText(text, getPaintingTextColor(), font, tRect, -1);
            textWidth = tSize.cx;
        }
    }
}

