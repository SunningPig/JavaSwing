/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package choose;

import java.util.ArrayList;
import java.util.HashMap;

import facecat.topin.core.*;
import facecat.topin.date.*;
import facecat.topin.swing.*;

public class DayDivEx2 extends DayDiv {
    public DayDivEx2(FCCalendar calendar)
    {
        super(calendar);
    }

    public void onResetDiv(int state)
    {
        if (m_calendar != null)
        {
            int size = 0;
            CMonth thisMonth = m_calendar.getMonth();
            CMonth lastMonth = m_calendar.getLastMonth(thisMonth.getYear(), thisMonth.getMonth());
            CMonth nextMonth = m_calendar.getNextMonth(thisMonth.getYear(), thisMonth.getMonth());
            int left = 0;
            int headHeight = m_calendar.getHeadDiv().getHeight();
            int top = headHeight;
            int width = m_calendar.getWidth();
            int height = m_calendar.getHeight();
            height -= m_calendar.getTimeDiv().getHeight();
            int dayButtonHeight = height - top;
            if (dayButtonHeight < 1) dayButtonHeight = 1;
            int subH = 0, toY = 0;
            ArrayList<DayButton> dayButtons = new ArrayList<DayButton>();
            if (m_am_Direction == 1)
            {
                subH = (6 - (m_am_ClickRowTo - m_am_ClickRowFrom)) * (dayButtonHeight / 6);
                toY = -height + subH + headHeight;
                toY = toY * m_am_Tick / m_am_TotalTick;
                if (state == 1)
                {
                    thisMonth = nextMonth;
                    lastMonth = m_calendar.getLastMonth(thisMonth.getYear(), thisMonth.getMonth());
                    nextMonth = m_calendar.getNextMonth(thisMonth.getYear(), thisMonth.getMonth());
                }
            }
            else if (m_am_Direction == 2)
            {
                subH = (6 - (m_am_ClickRowFrom - m_am_ClickRowTo)) * (dayButtonHeight / 6);
                toY = height - subH - headHeight;
                toY = toY * m_am_Tick / m_am_TotalTick;
                if (state == 1)
                {
                    thisMonth = lastMonth;
                    lastMonth = m_calendar.getLastMonth(thisMonth.getYear(), thisMonth.getMonth());
                    nextMonth = m_calendar.getNextMonth(thisMonth.getYear(), thisMonth.getMonth());
                }
            }
            if (state == 0)
            {
                dayButtons = m_dayButtons;
            }
            else if (state == 1)
            {
                dayButtons = m_dayButtons_am;
            }
            int dheight = dayButtonHeight / 6;
            HashMap<Integer, CDay> days = thisMonth.getDays();
            CDay firstDay = days.get(1);
            int startDayOfWeek = FCCalendar.dayOfWeek(firstDay.getYear(), firstDay.getMonth(), firstDay.getDay());
            int buttonSize = dayButtons.size();

            for (int i = 0; i < buttonSize; i++)
            {
                if (i == 35)
                {
                    dheight = height - top;
                }
                DayButton dayButton = dayButtons.get(i);
                int vOffSet = 0;
                if (state == 1)
                {
                    if (m_am_Tick > 0)
                    {
                        dayButton.setVisible(true);
                        if (m_am_Direction == 1)
                        {
                            vOffSet = toY + dayButtonHeight;
                        }
                        else if (m_am_Direction == 2)
                        {
                            vOffSet = toY - dayButtonHeight;
                        }
                    }
                    else
                    {
                        dayButton.setVisible(false);
                        continue;
                    }
                }
                else
                {
                    vOffSet = toY;
                }
                if ((i + 1) % 7 == 0)
                {
                    FCPoint dp = new FCPoint(left, top + vOffSet);
                    FCSize ds = new FCSize(width - left, dheight);
                    ds.cx = 0;
                    dayButton.setBounds(new FCRect(dp.x, dp.y, dp.x + ds.cx, dp.y + ds.cy));
                    left = 0;
                    if (i != 0 && i != size - 1)
                    {
                        top += dheight;
                    }
                }
                else
                {
                    FCPoint dp = new FCPoint(left, top + vOffSet);
                    FCSize ds = new FCSize(width / 7 + ((i + 1) % 7) % 2, dheight);
                    if ((i + 1) % 7 == 1)
                    {
                        ds.cx = 0;
                    }
                    else
                    {
                        ds.cx = width / 5 + ((i + 1) % 5) % 2;
                    }
                    dayButton.setBounds(new FCRect(dp.x, dp.y, dp.x + ds.cx, dp.y + ds.cy));
                    left += ds.cx;
                }
                CDay cDay = null;
                dayButton.setInThisMonth(false);
                if (i >= startDayOfWeek && i <= startDayOfWeek + days.size() - 1)
                {
                    cDay = days.get(i - startDayOfWeek + 1);
                    dayButton.setInThisMonth(true);
                }
                else if (i < startDayOfWeek)
                {
                    cDay = lastMonth.getDays().get(lastMonth.getDays().size() - startDayOfWeek + i + 1);
                }
                else if (i > startDayOfWeek + days.size() - 1)
                {
                    cDay = nextMonth.getDays().get(i - startDayOfWeek - days.size() + 1);
                }
                int day = cDay.getDay();
                dayButton.setDay(cDay);
                if (state == 0 && dayButton.getDay() != null && dayButton.getDay() == m_calendar.getSelectedDay())
                {
                    dayButton.setSelected(true);
                }
                else
                {
                    dayButton.setSelected(false);
                }
            }
        }
    }
}
