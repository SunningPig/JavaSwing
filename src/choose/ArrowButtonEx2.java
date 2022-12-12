package choose;

import facecat.topin.core.*;
import facecat.topin.swing.*;
import facecat.topin.date.ArrowButton;
import facecat.topin.date.FCCalendar;

/*
* 日历箭头
*/
public class ArrowButtonEx2 extends ArrowButton {
    /*
    * 构造函数
    */
    public ArrowButtonEx2(FCCalendar calendar)
    {
        super(calendar);
        setSize(new FCSize(30, 30));
        setTextColor(MyColor.USERCOLOR1);
    }

    /*
    * 重绘背景方法
    */
    public void onPaintForeground(FCPaint paint, FCRect clipRect)
    {
        int width = getWidth() - 1, height = getHeight() - 1;
        FCRect drawRect = new FCRect(1, 1, width - 1, height - 1);
        //paint.drawEllipse(getPaintingTextColor(), 1, 0, drawRect);
        FCPoint p1 = new FCPoint(), p2 = new FCPoint(), p3 = new FCPoint();
        //计算三个点的位置
        if (m_toLast)
        {
            p1.x = 0;
            p1.y = height / 2;
            p2.x = width;
            p2.y = 0;
            p3.x = width;
            p3.y = height;
        }
        else
        {
            p1.x = 0;
            p1.y = 0;
            p2.x = 0;
            p2.y = height;
            p3.x = width;
            p3.y = height / 2;
        }
        FCPoint[] points = new FCPoint[3];
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        paint.fillPolygon(MyColor.USERCOLOR79, points);
        paint.drawPolygon(MyColor.USERCOLOR2, 1, 0, points);
    }
}

