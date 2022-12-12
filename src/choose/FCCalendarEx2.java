package choose;

import facecat.topin.core.FCColor;
import facecat.topin.core.FCDockStyle;
import facecat.topin.date.*;
import facecat.topin.swing.*;

/*
* 日历
*/
public class FCCalendarEx2 extends FCCalendar {
    /*
    * 构造函数
    */
    public FCCalendarEx2()
    {
        setBackColor(FCColor.Back);
        setBorderColor(FCColor.None);
    }

    /*
    * 平均涨跌幅视图
    */
    public Choose2 m_mainFrame;

    /*
    * 创建头部图层
    */
    public HeadDiv createHeadDiv()
    {
        HeadDivEx2 headDiv = new HeadDivEx2(this);
        headDiv.setWidth(getWidth());
        headDiv.setDock(FCDockStyle.Top);
        return headDiv;
    }

    /*
    * 创建标题
    */
    public DateTitle createDateTitle()
    {
        DateTitleEx2 dateTitleExe = new DateTitleEx2(this);
        return dateTitleExe;
    }

    /*
    * 创建月份的层
    */
    public MonthButton createMonthButton()
    {
        MonthButton monthButton = new MonthButtonEx2(this);
        return monthButton;
    }

    /*
    * 创建年的按钮
    */
    public YearButton createYearButton()
    {
        YearButton yearButton = new YearButtonEx2(this);
        return yearButton;
    }

    /*
    * 创建箭头按钮
    */
    public ArrowButton createArrowButton()
    {
        return new ArrowButtonEx2(this);
    }

    /*
    * 创建日的层
    */
    public DayDiv createDayDiv()
    {
        return new DayDivEx2(this);
    }

    /*
    * 浮动特效层
    */
    public OverFlowDiv2 m_overFlowDiv;

    /*
    * 添加视图
    */
    public void onAdd()
    {
        super.onAdd();
        m_overFlowDiv = new OverFlowDiv2();
        m_overFlowDiv.setTopMost(true);
        addView(m_overFlowDiv);
    }

    /*
    * 更新布局
    */
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
