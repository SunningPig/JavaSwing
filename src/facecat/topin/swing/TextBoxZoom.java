package facecat.topin.swing;
import facecat.topin.core.*;
import facecat.topin.div.*;
import facecat.topin.input.*;
import facecat.topin.scroll.*;
import java.util.*;

/*
* 文本框缩放
*/
public class TextBoxZoom extends FCDiv {
    /*
    * 构造函数
    */
    public TextBoxZoom()
    {
        setAllowPreviewsEvent(true);
        setAllowDragScroll(true);
        setShowVScrollBar(true);
        setBorderColor(FCColor.None);
    }

    /*
    * 文本框
    */
    public iTextBox m_iTextBox;

    /*
    * 是否跟随滚动条
    */
    public boolean displayOffset()
    {
        return false;
    }

    /*
    * 获取内容高度
    */
    public int getContentHeight()
    {
        if (m_iTextBox != null)
        {
            float yRate = 0.2f;
            int width = getWidth(), height = getHeight();
            int lineCount = m_iTextBox.m_lines.size();
            int lineHeight = m_iTextBox.m_multiline ? m_iTextBox.m_lineHeight : height;
            int totalHeight = (int)(lineHeight * lineCount * yRate);
            if (totalHeight > height)
            {
                return totalHeight;
            }
            else
            {
                return height;
            }
        }
        else
        {
            return super.getContentHeight();
        }
    }

    /*
    * 显示或隐藏
    */
    public void showOrHide()
    {
        if (isVisible())
        {
            FCPadding padding = m_iTextBox.getPadding();
            padding.right = 0;
            m_iTextBox.setPadding(padding);
            m_iTextBox.setShowZoom(false);
            setVisible(false);
        }
        else
        {
            FCPadding padding = m_iTextBox.getPadding();
            padding.right = 80;
            m_iTextBox.setPadding(padding);
            m_iTextBox.setShowZoom(true);
            setVisible(true);
        }
        m_iTextBox.m_textChanged = true;
        m_iTextBox.invalidate();
    }

    /*
    * 键盘按下
    */
    public void onKeyDown(char key)
    {
        super.onKeyDown(key);
        if (key == 27)
        {
            showOrHide();
        }
    }

    /*
    * 鼠标按下
    */
    public void onTouchDown(FCTouchInfo touchInfo)
    {
        super.onTouchDown(touchInfo);
        int width = getWidth(), height = getHeight();
        int subWidth = m_iTextBox.getWidth() - m_iTextBox.getPadding().right - m_iTextBox.getPadding().left;
        if (subWidth <= 0)
        {
            return;
        }
        float xRate = ((float)getWidth() - 10) / subWidth;
        float yRate = 0.2f;
        FCHScrollBar hScrollBar = getHScrollBar();
        FCVScrollBar vScrollBar = getVScrollBar();
        int scrollH = ((hScrollBar != null && hScrollBar.isVisible()) ? hScrollBar.getPos() : 0);
        int scrollV = ((vScrollBar != null && vScrollBar.isVisible()) ? vScrollBar.getPos() : 0);
        int lineCount = m_iTextBox.m_lines.size();
        int lineHeight = m_iTextBox.m_multiline ? m_iTextBox.m_lineHeight : height;
        int touchY = (int)((touchInfo.m_firstPoint.y + scrollV) / yRate);
        if (m_iTextBox.getVScrollBar() != null && m_iTextBox.getVScrollBar().isVisible())
        {
            m_iTextBox.getVScrollBar().setPos(touchY);
            m_iTextBox.update();
            m_iTextBox.invalidate();
        }
    }

    /*
    * 鼠标按下
    */
    public void onPaint(FCPaint paint, FCRect clipRect)
    {
        if (m_iTextBox != null)
        {
            update();
            int width = getWidth(), height = getHeight();
            FCRect drawRect = new FCRect(0, 0, width, height);
            paint.fillRect(getPaintingBackColor(), drawRect);
            String text = m_iTextBox.getText();
            if (width > 0 && height > 0 && text.length() > 0)
            {
                FCPadding padding = getPadding();
                int subWidth = m_iTextBox.getWidth() - m_iTextBox.getPadding().right - m_iTextBox.getPadding().left;
                if (subWidth <= 0)
                {
                    return;
                }
                float xRate = (float)getWidth() / subWidth;
                float yRate = 0.2f;
                int drawLeft = 5;
                FCHScrollBar hScrollBar = getHScrollBar();
                FCVScrollBar vScrollBar = getVScrollBar();
                int scrollH = ((hScrollBar != null && hScrollBar.isVisible()) ? hScrollBar.getPos() : 0);
                int scrollV = ((vScrollBar != null && vScrollBar.isVisible()) ? vScrollBar.getPos() : 0);
                //paint.fillRect(MyColor.USERCOLOR61, new FCRect(drawLeft, 0, width - 10, height));
                //代码高亮
                int hiPos = 0;
                HighLightInfo highLightInfo = null;
                if (m_iTextBox.m_highLightInfos.size() > 0)
                {
                    highLightInfo = m_iTextBox.m_highLightInfos.get(hiPos);
                }
                int lineCount = m_iTextBox.m_lines.size();
                int lineHeight = m_iTextBox.m_multiline ? m_iTextBox.m_lineHeight : height;
                //paint.setOpacity(0.3f);
                int its = 0;
                int pos = -1;
                FCRect blockRect = new FCRect();
                ArrayList<FCRect> copyRanges = new ArrayList<FCRect>();
                ArrayList<Long> copyColors = new ArrayList<Long>();
                for (int i = 0; i < lineCount; i++)
                {
                    FCWordLine line = m_iTextBox.m_lines.get(i);
                    int its2 = 0;
                    for (int j = line.m_start; j <= line.m_end; j++)
                    {
                        if (highLightInfo != null)
                        {
                            if (j > highLightInfo.m_end)
                            {
                                hiPos++;
                                if (hiPos < m_iTextBox.m_highLightInfos.size())
                                {
                                    highLightInfo = m_iTextBox.m_highLightInfos.get(hiPos);
                                }
                            }
                        }
                        if (its2 == 0)
                        {
                            //获取绘制区域
                            FCRectF rangeRect = m_iTextBox.m_ranges.get(j).clone();
                            FCRect rRect = new FCRect(rangeRect.left, rangeRect.top + (lineHeight - m_iTextBox.m_wordsSize.get(j).cy) / 2,
                                    rangeRect.right, rangeRect.top + (lineHeight + m_iTextBox.m_wordsSize.get(j).cy) / 2);
                            FCRect copyRect = rRect.clone();
                            if (!Double.isInfinite(xRate) && !Double.isInfinite(yRate))
                            {
                                copyRect.left = (int)(Math.floor(drawLeft + (rRect.left - 50) * xRate)) + scrollH;
                                copyRect.top = (int)Math.floor (padding.top + (rRect.top - padding.top) * yRate) - scrollV + 3;
                                copyRect.right = (int)Math.ceil(drawLeft + (rRect.left - 50) * xRate + (rRect.right - rRect.left) * xRate) + scrollH;
                                copyRect.bottom = (int)Math.ceil(copyRect.top + (rRect.bottom - rRect.top) * yRate) - 3;
                                char ch = text.charAt(j);
                                if (ch != '\r' && ch != '\t' && ch != '\n' && ch != ' ')
                                {
                                    if (copyRect.bottom > 0 && copyRect.top < height)
                                    {
                                        if (pos == -1)
                                        {
                                            if (m_iTextBox.isLineVisible(j, 0.9))
                                            {
                                                blockRect.top = copyRect.top;
                                                pos = j;
                                            }
                                        }
                                        else
                                        {
                                            if (pos != 100000000)
                                            {
                                                if (!m_iTextBox.isLineVisible(j, 0.9) || i == lineCount - 1)
                                                {
                                                    if(i == lineCount - 1){
                                                        blockRect.bottom = copyRect.bottom;
                                                    }else{
                                                        blockRect.bottom = copyRect.top;
                                                    }
                                                    pos = 100000000;
                                                }
                                            }
                                        }
                                        its = 1;
                                        if (m_iTextBox.getSelectionLength() > 0)
                                        {
                                            if (j >= m_iTextBox.getSelectionStart() && j < m_iTextBox.getSelectionStart() + m_iTextBox.getSelectionLength())
                                            {
                                                copyRanges.add(copyRect.clone());
                                                copyColors.add(MyColor.USERCOLOR10);
                                                //paint.fillRect(MyColor.USERCOLOR10, copyRect);
                                                continue;
                                            }
                                        }
                                        if (highLightInfo != null && j >= highLightInfo.m_start && j <= highLightInfo.m_end)
                                        {
                                            copyRanges.add(copyRect);
                                            copyColors.add(highLightInfo.m_textColor);
                                        }
                                        else
                                        {
                                            copyRanges.add(copyRect);
                                            copyColors.add(getPaintingTextColor());
                                        }
                                    }
                                    else
                                    {
                                        if (pos != -1 && pos != 100000000)
                                        {
                                            blockRect.bottom = copyRect.top;
                                            pos = -1;
                                        }
                                        its2 = 1;
                                        if (its == 1)
                                        {
                                            its = 2;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (its == 2)
                    {
                        break;
                    }
                }
                int copyRangesSize = copyRanges.size();
                if(copyRangesSize > 0){
                    Long lastColor = copyColors.get(0);
                    int lastI = 0;
                    for (int i = 0; i < copyRangesSize; i++)
                    {
                        if((i < copyRangesSize - 1 && (lastColor != copyColors.get(i + 1) || copyRanges.get(i + 1).top > copyRanges.get(i).top)) || i == copyRangesSize - 1){
                            FCRect beginRange = copyRanges.get(lastI);
                            FCRect endRange = copyRanges.get(i);
                            FCRect newRect = new FCRect(beginRange.left, beginRange.top, endRange.right, endRange.bottom);
                            int r = 0, g = 0, b = 0, a = 0;
                            RefObject<Integer> refR = new RefObject<Integer>(r);
                            RefObject<Integer> refG = new RefObject<Integer>(g);
                            RefObject<Integer> refB = new RefObject<Integer>(b);
                            RefObject<Integer> refA = new RefObject<Integer>(a);
                            FCColor.toRgba(null, lastColor, refR, refG, refB, refA);
                            r = refR.argvalue;
                            g = refG.argvalue;
                            b = refB.argvalue;
                            a = refA.argvalue;
                            if (MyColor.m_style == 1)
                            {
                                if (r + g + b < 200)
                                {
                                    r = (int)(r * 1.5);
                                    g = (int)(g * 1.5);
                                    b = (int)(b * 1.5);
                                    if (r > 255)
                                    {
                                        r = 255;
                                    }
                                    if (g > 255)
                                    {
                                        g = 255;
                                    }
                                    if (b > 255)
                                    {
                                        b = 255;
                                    }
                                }
                            }
                            else
                            {
                                if (r + g + b > 500)
                                {
                                    r = (int)(r / 1.5);
                                    g = (int)(g / 1.5);
                                    b = (int)(b / 1.5);
                                    if (r < 0)
                                    {
                                        r = 0;
                                    }
                                    if (g < 0)
                                    {
                                        g = 0;
                                    }
                                    if (b < 0)
                                    {
                                        b = 0;
                                    }
                                }
                            }
                            paint.fillRect(FCColor.rgb(r, g, b), newRect);
                            if(i < copyRangesSize - 1){
                                lastColor = copyColors.get(i + 1);
                                lastI = i + 1;
                            }
                        }
                    }
                }
                copyRanges.clear();
                copyColors.clear();
                if (blockRect.bottom == 0 && blockRect.top > 0 && blockRect.top < height)
                {
                    blockRect.bottom = height;
                }
                if (blockRect.bottom > blockRect.top)
                {
                    blockRect.left = 1;
                    blockRect.right = width;
                    paint.setOpacity(0.5f);
                    if (MyColor.m_style == 1)
                    {
                        paint.fillRect(MyColor.USERCOLOR62, blockRect);
                    }
                    else
                    {
                        paint.fillRect(MyColor.USERCOLOR6, blockRect);
                    }
                    paint.setOpacity(1.0f);
                }
                paint.drawLine(MyColor.USERCOLOR79, 1, 0, 0, 0, 0, height);
                //paint.setOpacity(1.0f);
            }
        }
    }

    /*
    * 更新布局方法下
    */
    public void update()
    {
        if (m_vScrollBar != null)
        {
            m_vScrollBar.setWidth(0);
        }
        super.update();
    }
}
