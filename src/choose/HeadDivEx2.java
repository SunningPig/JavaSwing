
package choose;

import facecat.topin.core.*;
import facecat.topin.date.*;
import facecat.topin.swing.*;

/*
* 头部图层
*/
public class HeadDivEx2 extends HeadDiv {
    /*
    * 构造函数
    */
    public HeadDivEx2(FCCalendar calendar)
    {
        super(calendar);
        m_weekDays = new String[] { "周一", "周二", "周三", "周四", "周五" };
        m_calendarEx = (FCCalendarEx2)calendar;
        setBackColor(MyColor.USERCOLOR64);
        setTextColor(MyColor.USERCOLOR73);
        setHeight(70);
        setBorderColor(FCColor.None);
    }

    /*
    * 日历
    */
    private FCCalendarEx2 m_calendarEx;

    /*
    * 鼠标移动事件
    */
    public void onTouchMove(FCTouchInfo touchInfo)
    {
        super.onTouchMove(touchInfo);
        update();
        invalidate();
    }

    /*
    * 鼠标按下事件
    */
    public void onTouchDown(FCTouchInfo touchInfo)
    {
        super.onTouchDown(touchInfo);
        int width = getWidth(), height = getHeight();
        FCPoint mp = touchInfo.m_firstPoint;
        if (mp.x > width - 50)
        {
            m_calendarEx.m_mainFrame.m_myChartDiv.setVisible(true);
            m_calendarEx.m_mainFrame.m_myChartDiv.bringToFront();
            m_calendarEx.m_mainFrame.m_myChartDiv.refreshData(true);
            m_calendarEx.m_mainFrame.m_myChartDiv.update();
            m_calendarEx.m_mainFrame.m_calendarEx.setVisible(false);
            m_calendarEx.m_mainFrame.m_myChartDiv.invalidate();
        }
    }

    /*
    * 重绘背景方法
    */
    public void onPaintBackground(FCPaint paint, FCRect clipRect)
    {
        int width = getWidth(), height = getHeight();
        FCRect rect = new FCRect(2, 2, width - 2, height - 2);
        paint.fillRoundRect(getPaintingBackColor(), rect, 4);
    }

    /*
    * 重绘边线方法
    */
    public void onPaintBorder(FCPaint paint, FCRect clipRect)
    {
    }

    /*
    * 重绘前景方法
    */
    public void onPaintForeground(FCPaint paint, FCRect clipRect)
    {
        int width = getWidth(), height = getHeight();
        FCCalendarMode mode = m_calendar.getMode();
        //画星期标题
        if (mode == FCCalendarMode.Day)
        {
            float left = 0;
            FCSize weekDaySize = new FCSize();
            FCFont tFont = new FCFont("Default", 14, false, false, false);
            long textColor = getPaintingTextColor();
            for (int i = 0; i < m_weekDays.length; i++)
            {
                weekDaySize = paint.textSize(m_weekDays[i], tFont);
                float textX = left + (width / 5.0f) / 2.0f - weekDaySize.cx / 2.9f;
                float textY = height - weekDaySize.cy - 2;
                FCRect tRect = new FCRect(textX, textY, textX + weekDaySize.cx, textY + weekDaySize.cy);
                paint.drawText(m_weekDays[i], textColor, tFont, tRect);
                left += getWidth() / 5.0f;
            }
        }
    }

    /*
    * 布局更新方法
    */
    public void update()
    {
        int width = getWidth(), height = getHeight();
        int textWidth = 140;
        if (m_calendar.getHeadDiv() != null)
        {
            DateTitleEx2 dateTitleEx = (DateTitleEx2)m_calendar.getHeadDiv().getDateTitle();
            if (dateTitleEx != null)
            {
                textWidth = dateTitleEx.textWidth;
            }
        }
        if (textWidth == 0)
        {
            textWidth = 140;
        }
        if (m_dateTitle != null)
        {
            m_dateTitle.setLocation(new FCPoint((width - m_dateTitle.getWidth()) / 2, (height - m_dateTitle.getHeight()) / 2));
        }
        if (m_lastBtn != null)
        {
            m_lastBtn.setSize(new FCSize(25, 25));
            m_lastBtn.setLocation(new FCPoint(2, height - m_lastBtn.getHeight() - 2));
        }
        if (m_nextBtn != null)
        {
            m_nextBtn.setSize(new FCSize(25, 25));
            m_nextBtn.setLocation(new FCPoint(width - m_nextBtn.getWidth() - 2, height - m_nextBtn.getHeight() - 2));
        }
    }
}
