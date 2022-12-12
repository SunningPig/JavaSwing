package choose;

import facecat.topin.core.*;
import facecat.topin.swing.*;

/*
* 浮动特效层
*/
public class OverFlowDiv2 extends FCView {
    public int m_tick = 20;

    /*
    * 是否包含坐标
    */
    public boolean containsPoint(FCPoint point)
    {
        return false;
    }

    /*
    * 点击事件
    */
    public void onClick(FCTouchInfo touchInfo)
    {
        super.onClick(touchInfo);
        m_tick = 50;
        startTimer(m_timerID, 10);
    }

    /*
    * 秒表ID
    */
    private int m_timerID = FCView.getNewTimerID();

    /*
    * 秒表事件
    */
    public void onTimer(int timerID)
    {
        super.onTimer(timerID);
        if (m_timerID == timerID)
        {
            if (m_tick > 20)
            {
                m_tick--;
                if (m_tick <= 20)
                {
                    stopTimer(m_timerID);
                }
            }
            invalidate();
        }
    }

    /*
    * 重绘事件
    */
    public void onPaint(FCPaint paint, FCRect clipRect)
    {
        int width = getWidth(), height = getHeight();
        FCRect drawRect = new FCRect(0, 0, width, height);
        if (m_tick > 0)
        {
            int d1 = 5, d2 = 15, dTick = m_tick - 20;
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
                paint.drawPolyline(MyColor.USERCOLOR10, 2, 0, points1);
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
                paint.drawPolyline(MyColor.USERCOLOR10, 2, 0, points1);
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
                paint.drawPolyline(MyColor.USERCOLOR10, 2, 0, points1);
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
                paint.drawPolyline(MyColor.USERCOLOR10, 2, 0, points1);
            }
        }

        paint.fillRoundRect(MyColor.USERCOLOR38, drawRect, 0);
        paint.drawRoundRect(MyColor.USERCOLOR62, 2, 0, drawRect, 0);
    }

    /*
    * 重绘边线事件
    */
    public void onPaintBorder(FCPaint paint, FCRect clipRect)
    {
    }
}
