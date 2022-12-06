/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package choose;

import facecat.topin.core.FCColor;
import facecat.topin.core.FCDockStyle;
import facecat.topin.date.*;
import facecat.topin.swing.*;

public class FCCalendarEx2 extends FCCalendar {
    public FCCalendarEx2()
    {
        setBackColor(FCColor.Back);
        setBorderColor(FCColor.None);
    }

    public Choose2 m_mainFrame;

    public HeadDiv createHeadDiv()
    {
        HeadDivEx2 headDiv = new HeadDivEx2(this);
        headDiv.setWidth(getWidth());
        headDiv.setDock(FCDockStyle.Top);
        return headDiv;
    }

    public DateTitle createDateTitle()
    {
        DateTitleEx2 dateTitleExe = new DateTitleEx2(this);
        return dateTitleExe;
    }

    /// <summary>
    /// 创建月份的层
    /// </summary>
    /// <returns>月份层</returns>
    public MonthButton createMonthButton()
    {
        MonthButton monthButton = new MonthButtonEx2(this);
        return monthButton;
    }

    public YearButton createYearButton()
    {
        YearButton yearButton = new YearButtonEx2(this);
        return yearButton;
    }

    /// <summary>
    /// 创建箭头按钮
    /// </summary>
    /// <returns>箭头按钮</returns>
    public ArrowButton createArrowButton()
    {
        return new ArrowButtonEx2(this);
    }

    public DayDiv createDayDiv()
    {
        return new DayDivEx2(this);
    }

    public OverFlowDiv2 m_overFlowDiv;

    public void onAdd()
    {
        super.onAdd();
        m_overFlowDiv = new OverFlowDiv2();
        m_overFlowDiv.setTopMost(true);
        addView(m_overFlowDiv);
    }

    public void update()
    {
        super.update();
        if (m_overFlowDiv != null)
        {
            if (m_dayDiv != null && m_mode == FCCalendarMode.Day)
            {
                m_overFlowDiv.setVisible(true);
                for (int i = 0; i < m_dayDiv.m_dayButtons.size(); i++)
                {
                    if (m_dayDiv.m_dayButtons.get(i).isSelected())
                    {
                        m_overFlowDiv.setBounds(m_dayDiv.m_dayButtons.get(i).getBounds());
                    }
                }
            }
            else
            {
                m_overFlowDiv.setVisible(false);
            }
        }
    }
}
