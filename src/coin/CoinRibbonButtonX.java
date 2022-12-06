/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coin;
import facecat.topin.core.*;
import facecat.topin.btn.*;
import facecat.topin.swing.*;

/**
 *
 * @author taode
 */
/// <summary>
/// 透明按钮
/// </summary>
public class CoinRibbonButtonX extends FCButton
{
    /// <summary>
    /// 创建按钮
    /// </summary>
    public CoinRibbonButtonX()
    {
        setBorderColor(FCColor.None);
        setTextColor(MyColor.USERCOLOR11);
        setFont(new FCFont("Default", 12, false, false, false));
    }

    private int m_timerID = FCView.getNewTimerID();

    private int m_tick = 0;

    public void onTouchEnter(FCTouchInfo touchInfo)
    {
        super.onTouchEnter(touchInfo);
        m_tick = 60;
        startTimer(m_timerID, 10);
    }

    public void onTimer(int timerID)
    {
        super.onTimer(timerID);
        if (m_tick > 0)
        {
            m_tick--;
            if (m_tick == 0)
            {
                stopTimer(m_timerID);
            }
            invalidate();
        }
    }

    /// <summary>
    /// 重绘背景方法
    /// </summary>
    /// <param name="paint">绘图对象</param>
    /// <param name="clipRect">裁剪区域</param>
    public void onPaint(FCPaint paint, FCRect clipRect)
    {
        int width = getWidth(), height = getHeight();
        FCRect drawRect = new FCRect(1, 1, width - 1, height - 1);
        String text = getText();
        FCFont tFont = getFont();
        FCSize tSize = paint.textSize(getText(), tFont);
        FCRect tRect = new FCRect((width - tSize.cx) / 2, (height - tSize.cy) / 2 + 1, (width + tSize.cx) / 2, (height + tSize.cy) / 2 + 1);
        if (m_tick >= 0)
        {
            paint.drawRect(MyColor.USERCOLOR35, 1, 0, drawRect);
        }
        if (text.equals("提取数据"))
        {
            tRect.left -= 10;
            tRect.right -= 10;
            paint.drawText(text, MyColor.USERCOLOR11, tFont, tRect);
            int offsetX = 0, offsetY = 0;
            paint.drawLine(MyColor.USERCOLOR48, 3, 0, drawRect.right - 15 + offsetX, drawRect.top + 10 + offsetY, drawRect.right - 15 + offsetX, drawRect.bottom - 10 + offsetY);
            paint.drawLine(MyColor.USERCOLOR48, 3, 0, drawRect.right - 24 + offsetX, drawRect.top + 19 + offsetY, drawRect.right - 6 + offsetX, drawRect.top + 19 + offsetY);
        }
        else if (text.equals("导出到CSV"))
        {
            tRect.left -= 10;
            tRect.right -= 10;
            paint.drawText(text, MyColor.USERCOLOR11, tFont, tRect);
            int offsetX = 0, offsetY = 0;
            paint.drawLine(MyColor.USERCOLOR53, 3, 0, drawRect.right - 24 + offsetX, drawRect.top + 19 + offsetY, drawRect.right - 6 + offsetX, drawRect.top + 19 + offsetY);
        }
        else if (text.equals("功能3"))
        {
            tRect.left -= 10;
            tRect.right -= 10;
            paint.drawText(text, MyColor.USERCOLOR11, tFont, tRect);
            FCRect blockRect = new FCRect(drawRect.right - 24, drawRect.top + 10, drawRect.right - 6, drawRect.bottom - 10);
            int offsetX = 0, offsetY = 0;
            FCPoint[] pp = new FCPoint[3];
            pp[0] = new FCPoint(blockRect.left + offsetX, blockRect.top + 8 + offsetY);
            pp[1] = new FCPoint(blockRect.left + 6 + offsetX, blockRect.bottom + offsetY);
            pp[2] = new FCPoint(blockRect.right + offsetX - 1, blockRect.top + offsetY);
            paint.drawPolyline(MyColor.USERCOLOR51, 2, 0, pp);
        }
        else if (text.equals("功能4"))
        {
            tRect.left -= 10;
            tRect.right -= 10;
            paint.drawText(text, MyColor.USERCOLOR11, tFont, tRect);
            FCRect blockRect = new FCRect(drawRect.right - 24, drawRect.top + 10, drawRect.right - 6, drawRect.bottom - 10);
            int offsetX = 0, offsetY = 0;
            FCPoint[] pp = new FCPoint[4];
            pp[0] = new FCPoint(blockRect.left + offsetX, blockRect.bottom + offsetY);
            pp[1] = new FCPoint(blockRect.left + 6 + offsetX, blockRect.top + offsetY);
            pp[2] = new FCPoint(blockRect.right + offsetX - 1, blockRect.bottom + offsetY);
            pp[3] = new FCPoint(blockRect.right - 10 + offsetX - 1, blockRect.bottom - 6 + offsetY);
            paint.drawPolyline(MyColor.USERCOLOR52, 2, 0, pp);
        }
        else if (text.equals("功能5"))
        {
            tRect.left -= 10;
            tRect.right -= 10;
            paint.drawText(text, MyColor.USERCOLOR11, tFont, tRect);

            int offsetX = 0, offsetY = 0;
            FCRect blockRect = new FCRect(drawRect.right - 24 + offsetX, drawRect.top + 10 + offsetY, drawRect.right - 6 + offsetX, drawRect.bottom - 10 + offsetY);
            paint.drawEllipse(MyColor.USERCOLOR50, 2, 0, blockRect);
            FCRect innerRect = new FCRect(blockRect.left + 5, blockRect.top + 5, blockRect.left + 10, blockRect.top + 10);
            paint.fillEllipse(MyColor.USERCOLOR50, innerRect);
        }
        else if (text.equals("功能6"))
        {
            tRect.left -= 10;
            tRect.right -= 10;
            paint.drawText(text, MyColor.USERCOLOR11, tFont, tRect);
            FCRect blockRect = new FCRect(drawRect.right - 26, drawRect.top + 12, drawRect.right - 8, drawRect.bottom - 12);
            int offsetX = 0, offsetY = 0;
            FCRect bodyRect = new FCRect();
            bodyRect.left = blockRect.left + offsetX;
            bodyRect.top = blockRect.top + (blockRect.bottom - blockRect.top) / 3 + offsetY;
            bodyRect.right = blockRect.right + offsetX;
            bodyRect.bottom = blockRect.top + blockRect.bottom - blockRect.top + offsetY + 5;
            paint.fillEllipse(MyColor.USERCOLOR2, bodyRect);
            bodyRect.left += 2;
            bodyRect.right -= 2;
            bodyRect.top = blockRect.top + offsetY - 3;
            bodyRect.bottom = blockRect.top + (blockRect.bottom - blockRect.top) / 2 + offsetY;
            paint.fillEllipse(MyColor.USERCOLOR2, bodyRect);
        }
        else
        {
            paint.drawText(text, MyColor.USERCOLOR11, tFont, tRect);
        }
    }
}
