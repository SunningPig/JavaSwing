package coin;
import facecat.topin.core.*;
import facecat.topin.chart.*;
import java.util.*;
import facecat.topin.swing.*;

/*
* K线
*/
public class CoinChartDiv extends FCChart {
    /*
    * 创建K线
    */
    public CoinChartDiv()
    {
    }

    /*
    * 添加一个新的图层，按照所设置的比例调节纵向高度
    */
    public ChartDiv addDiv2(float vPercent)
    {
        if (vPercent <= 0) return null;
        //创建层
        ChartDiv cDiv = new ChartDiv();
        cDiv.setVerticalPercent(vPercent);
        cDiv.setChart(this);
        m_divs.add(cDiv);
        update();
        return cDiv;
    }

    public static int COLUMN_CLOSE = 0;
    public static int COLUMN_HIGH = 1;
    public static int COLUMN_LOW = 2;
    public static int COLUMN_OPEN = 3;
    public static int COLUMN_VOL = 4;

    /*
    * 绘制成交量
    */
    public void onPaintBar(FCPaint paint, ChartDiv div, BarShape bs)
    {
        if (div == m_divs.get(2))
        {
            super.onPaintBar(paint, div, bs);
            return;
        }
        int ciFieldName1 = m_dataSource.getColumnIndex(bs.getFieldName());
        int ciFieldName2 = m_dataSource.getColumnIndex(bs.getFieldName2());
        int ciStyle = m_dataSource.getColumnIndex(bs.getStyleField());
        int ciClr = m_dataSource.getColumnIndex(bs.getColorField());
        int closeFieldIndex = m_dataSource.getColumnIndex(COLUMN_CLOSE);
        int openFieldIndex = m_dataSource.getColumnIndex(COLUMN_OPEN);
        double visibleMax = Double.NaN, visibleMin = Double.NaN;
        if (bs.fillVScale())
        {
            RefObject<Double> refVisibleMax = new RefObject<Double>(visibleMax);
            RefObject<Double> refVisibleMin = new RefObject<Double>(visibleMin);
            getShapeMaxMin(bs, refVisibleMax, refVisibleMin);
            visibleMax = refVisibleMax.argvalue;
            visibleMin = refVisibleMin.argvalue;
        }
        int defaultLineWidth = 1;
        if (!isOperating() && m_crossStopIndex != -1)
        {
            if (selectBar(div, getTouchPoint().y, bs.getFieldName(), bs.getFieldName2(), bs.getStyleField(), bs.getAttachVScale(), m_crossStopIndex, visibleMax, visibleMin))
            {
                defaultLineWidth = 2;
            }
        }
        for (int i = m_firstVisibleIndex; i <= m_lastVisibleIndex; i++)
        {
            int thinLineWidth = 1;
            if (i == m_crossStopIndex)
            {
                thinLineWidth = defaultLineWidth;
            }
            //样式
            int style = -10000;
            if(bs.getStyle() == BarStyle.Line){
                style = 2;
            }else if(bs.getStyle() == BarStyle.Rect){
                style = 0;
            }
            //自定义样式
            if (ciStyle != -1)
            {
                double defineStyle = m_dataSource.get3(i, ciStyle);
                if (!Double.isNaN(defineStyle))
                {
                    style = (int)defineStyle;
                }
            }
            if (style == -10000)
            {
                continue;
            }
            double value = m_dataSource.get3(i, ciFieldName1);
            double close = m_dataSource.get3(i, closeFieldIndex);
            double open = m_dataSource.get3(i, openFieldIndex);
            int scaleX = (int)getX(i);
            double midValue = 0;
            if (ciFieldName2 != -1)
            {
                midValue = m_dataSource.get3(i, ciFieldName2);
            }
            float midY = getY(div, midValue, bs.getAttachVScale(), visibleMax, visibleMin);
            midY = div.getHeight() - div.getHScale().getHeight();
            if (!Double.isNaN(value))
            {
                float barY = getY(div, value, bs.getAttachVScale(), visibleMax, visibleMin);
                int startPX = scaleX;
                int startPY = (int)midY;
                int endPX = scaleX;
                int endPY = (int)barY;
                if (bs.getStyle() == BarStyle.Rect)
                {
                    //修正
                    if (startPY == div.getHeight() - div.getHScale().getHeight())
                    {
                        startPY = div.getHeight() - div.getHScale().getHeight() + 1;
                    }
                }
                int x = 0, y = 0, width = 0, height = 0;
                width = (int)(m_hScalePixel * 2 / 3);
                if (width % 2 == 0)
                {
                    width += 1;
                }
                if (width < 3)
                {
                    width = 1;
                }
                x = scaleX - width / 2;
                //获取阴阳柱的矩形
                if (startPY >= endPY)
                {
                    y = endPY;
                }
                else
                {
                    y = startPY;
                }
                height = Math.abs(startPY - endPY);
                if (height < 1)
                {
                    height = 1;
                }
                //获取自定义颜色
                long barColor = FCColor.None;
                if (ciClr != -1)
                {
                    double defineColor = m_dataSource.get3(i, ciClr);
                    if (!Double.isNaN(defineColor))
                    {
                        barColor = (long)defineColor;
                    }
                }
                if (barColor == FCColor.None)
                {
                    if (startPY >= endPY)
                    {
                        barColor = bs.getUpColor();
                    }
                    else
                    {
                        barColor = bs.getDownColor();
                    }
                }
                switch (style)
                {
                    //虚线空心矩形
                    case -1:
                        if (m_hScalePixel <= 3)
                        {
                            drawThinLine(paint, barColor, thinLineWidth, startPX, y, startPX, y + height);
                        }
                        else
                        {
                            FCRect rect = new FCRect(x, y, x + width, y + height);
                            paint.fillRect(div.getBackColor(), rect);
                            paint.drawRect(barColor, thinLineWidth, 2, rect);
                        }
                        break;
                    //实心矩形
                    case 0:
                        if (m_hScalePixel <= 3)
                        {
                            if (close >= open)
                            {
                                drawThinLine(paint, MyColor.USERCOLOR69, thinLineWidth, startPX, y, startPX, y + height);
                            }
                            else
                            {
                                drawThinLine(paint, MyColor.USERCOLOR70, thinLineWidth, startPX, y, startPX, y + height);
                            }
                        }
                        else
                        {
                            FCRect rect = new FCRect(x, y, x + width, y + height);
                            //paint.fillRect(barColor, rect);
                            if (close >= open)
                            {
                                paint.fillGradientRect(MyColor.USERCOLOR71, MyColor.USERCOLOR71, rect, 0, 0);
                            }
                            else
                            {
                                paint.fillGradientRect(MyColor.USERCOLOR72, MyColor.USERCOLOR72, rect, 0, 0);
                            }
                            //paint.drawRect(MyColor.USERCOLOR17, thinLineWidth, 0, rect);
                            if (thinLineWidth > 1)
                            {
                                if (startPY >= endPY)
                                {
                                    paint.drawRect(bs.getDownColor(), thinLineWidth, 0, rect);
                                }
                                else
                                {
                                    paint.drawRect(bs.getUpColor(), thinLineWidth, 0, rect);
                                }
                            }
                        }
                        break;
                    //空心矩形
                    case 1:
                        if (m_hScalePixel <= 3)
                        {
                            drawThinLine(paint, barColor, thinLineWidth, startPX, y, startPX, y + height);
                        }
                        else
                        {
                            FCRect rect = new FCRect(x, y, x + width, y + height);
                            paint.fillRect(div.getBackColor(), rect);
                            paint.drawRect(barColor, thinLineWidth, 0, rect);
                        }
                        break;
                    //线
                    case 2:
                        if (startPY <= 0)
                        {
                            startPY = 0;
                        }
                        if (startPY >= div.getHeight())
                        {
                            startPY = div.getHeight();
                        }
                        if (endPY <= 0)
                        {
                            endPY = 0;
                        }
                        if (endPY >= div.getHeight())
                        {
                            endPY = div.getHeight();
                        }
                        //画线
                        if (bs.getLineWidth() <= 1)
                        {
                            drawThinLine(paint, barColor, thinLineWidth, startPX, startPY, endPX, endPY);
                        }
                        else
                        {
                            float lineWidth = bs.getLineWidth();
                            if (lineWidth > m_hScalePixel)
                            {
                                lineWidth = (float)m_hScalePixel;
                            }
                            if (lineWidth < 1)
                            {
                                lineWidth = 1;
                            }
                            paint.drawLine(barColor, lineWidth + thinLineWidth - 1, 0, startPX, startPY, endPX, endPY);
                        }
                        break;
                }
                if (bs.isSelected())
                {
                    //画选中框
                    int kPInterval = m_maxVisibleRecord / 30;
                    if (kPInterval < 2)
                    {
                        kPInterval = 2;
                    }
                    if (i % kPInterval == 0)
                    {
                        if (barY >= div.getTitleBar().getHeight()
                                && barY <= div.getHeight() - div.getHScale().getHeight())
                        {
                            FCRect sRect = new FCRect(scaleX - 3, (int)barY - 4, scaleX + 4, (int)barY + 3);
                            paint.fillRect(bs.getSelectedColor(), sRect);
                        }
                    }
                }
            }
            //画零线
            if (i == m_lastVisibleIndex && div.getVScale(bs.getAttachVScale()).getVisibleMin() < 0)
            {
                if (m_reverseHScale)
                {
                    float left = (float)(m_leftVScaleWidth + m_workingAreaWidth - (m_lastVisibleIndex - m_firstVisibleIndex + 1) * m_hScalePixel);
                    paint.drawLine(bs.getDownColor(), 1, 0, m_leftVScaleWidth + m_workingAreaWidth, (int)midY, (int)left, (int)midY);
                }
                else
                {
                    float right = (float)(m_leftVScaleWidth + (m_lastVisibleIndex - m_firstVisibleIndex + 1) * m_hScalePixel);
                    paint.drawLine(bs.getDownColor(), 1, 0, m_leftVScaleWidth, (int)midY, (int)right, (int)midY);
                }
            }
        }
    }

    /*
    * 绘制K线
    */
    public void onPaintCandle(FCPaint paint, ChartDiv div, CandleShape cs)
    {
        int visibleMaxIndex = -1, visibleMinIndex = -1;
        double visibleMax = 0, visibleMin = 0;
        double vmax = Double.NaN, vmin = Double.NaN;
        if (cs.fillVScale())
        {
            RefObject<Double> refVisibleMax = new RefObject<Double>(visibleMax);
            RefObject<Double> refVisibleMin = new RefObject<Double>(visibleMin);
            getShapeMaxMin(cs, refVisibleMax, refVisibleMin);
            visibleMax = refVisibleMax.argvalue;
            visibleMin = refVisibleMin.argvalue;
        }
        int x = 0, y = 0;
        ArrayList<FCPoint> points = new ArrayList<FCPoint>();
        int ciHigh = m_dataSource.getColumnIndex(cs.getHighField());
        int ciLow = m_dataSource.getColumnIndex(cs.getLowField());
        int ciOpen = m_dataSource.getColumnIndex(cs.getOpenField());
        int ciClose = m_dataSource.getColumnIndex(cs.getCloseField());
        int ciStyle = m_dataSource.getColumnIndex(cs.getStyleField());
        int ciClr = m_dataSource.getColumnIndex(cs.getColorField());
        int defaultLineWidth = 1;
        if (!isOperating() && m_crossStopIndex != -1)
        {
            if (selectCandle(div, getTouchPoint().y, cs.getHighField(), cs.getLowField(), cs.getStyleField(), cs.getAttachVScale(), m_crossStopIndex, vmax, vmin))
            {
                defaultLineWidth = 2;
            }
        }
        for (int i = m_firstVisibleIndex; i <= m_lastVisibleIndex; i++)
        {
            int thinLineWidth = 1;
            if (i == m_crossStopIndex)
            {
                thinLineWidth = defaultLineWidth;
            }
            //样式
            int style = -10000;
            if(cs.getStyle() == CandleStyle.Rect){
                style = 0;
            }else if(cs.getStyle() == CandleStyle.American) {
                style = 3;
            }else if(cs.getStyle() == CandleStyle.CloseLine) {
                style = 4;
            }else if(cs.getStyle() == CandleStyle.Tower) {
                style = 5;
            }
            //自定义样式
            if (ciStyle != -1)
            {
                double defineStyle = m_dataSource.get3(i, ciStyle);
                if (!Double.isNaN(defineStyle))
                {
                    style = (int)defineStyle;
                }
            }
            if (style == 10000)
            {
                continue;
            }
            //获取值
            double open = m_dataSource.get3(i, ciOpen);
            double high = m_dataSource.get3(i, ciHigh);
            double low = m_dataSource.get3(i, ciLow);
            double close = m_dataSource.get3(i, ciClose);
            if (Double.isNaN(open) || Double.isNaN(high) || Double.isNaN(low) || Double.isNaN(close))
            {
                if (i != m_lastVisibleIndex || style != 4)
                {
                    continue;
                }
            }
            int scaleX = (int)getX(i);
            if (cs.showMaxMin())
            {
                //设置可见部分最大最小值及索引
                if (i == m_firstVisibleIndex)
                {
                    //初始值
                    visibleMaxIndex = i;
                    visibleMinIndex = i;
                    visibleMax = high;
                    visibleMin = low;
                }
                else
                {
                    //最大值
                    if (high > visibleMax)
                    {
                        visibleMax = high;
                        visibleMaxIndex = i;
                    }
                    //最小值
                    if (low < visibleMin)
                    {
                        visibleMin = low;
                        visibleMinIndex = i;
                    }
                }
            }
            //获取各值所在Y值
            float highY = getY(div, high, cs.getAttachVScale(), vmax, vmin);
            float openY = getY(div, open, cs.getAttachVScale(), vmax, vmin);
            float lowY = getY(div, low, cs.getAttachVScale(), vmax, vmin);
            float closeY = getY(div, close, cs.getAttachVScale(), vmax, vmin);
            int cwidth = (int)(m_hScalePixel * 2 / 3);
            if (cwidth % 2 == 0)
            {
                cwidth += 1;
            }
            if (cwidth < 3)
            {
                cwidth = 1;
            }
            int xsub = cwidth / 2;
            if (xsub < 1)
            {
                xsub = 1;
            }
            switch (style)
            {
                //美国线
                case 3:
                {
                    long color = cs.getUpColor();
                    if (open > close)
                    {
                        color = cs.getDownColor();
                    }
                    if (ciClr != -1)
                    {
                        double defineColor = m_dataSource.get3(i, ciClr);
                        if (!Double.isNaN(defineColor))
                        {
                            color = (long)defineColor;
                        }
                    }
                    if ((int)highY != (int)lowY)
                    {
                        if (m_hScalePixel <= 3)
                        {
                            drawThinLine(paint, color, thinLineWidth, scaleX, highY, scaleX, lowY);
                        }
                        else
                        {
                            drawThinLine(paint, color, thinLineWidth, scaleX, highY, scaleX, lowY);
                            drawThinLine(paint, color, thinLineWidth, scaleX - xsub, openY, scaleX, openY);
                            drawThinLine(paint, color, thinLineWidth, scaleX, closeY, scaleX + xsub, closeY);
                        }
                    }
                    else
                    {
                        drawThinLine(paint, color, thinLineWidth, scaleX - xsub, closeY, scaleX + xsub, closeY);
                    }
                }
                break;
                //收盘线
                case 4:
                {
                    RefObject<Integer> refX = new RefObject<Integer>(x);
                    RefObject<Integer> refY = new RefObject<Integer>(y);
                    onPaintPolyline(paint, div, cs.getUpColor(), FCColor.None, cs.getColorField(), defaultLineWidth, PolylineStyle.SolidLine, close, cs.getAttachVScale(), scaleX, (int)closeY, i, points, refX, refX, vmax, vmin);
                    x = refX.argvalue;
                    y = refY.argvalue;
                    break;
                }
                default:
                {
                    //阳线
                    if (open <= close)
                    {
                        //获取阳线的高度
                        float recth = getUpCandleHeight(close, open, div.getVScale(cs.getAttachVScale()).getVisibleMax(), div.getVScale(cs.getAttachVScale()).getVisibleMin(), div.getWorkingAreaHeight() - div.getVScale(cs.getAttachVScale()).getPaddingBottom() - div.getVScale(cs.getAttachVScale()).getPaddingTop());
                        if (recth < 1)
                        {
                            recth = 1;
                        }
                        //获取阳线的矩形
                        int rcUpX = scaleX - xsub, rcUpTop = (int)closeY, rcUpBottom = (int)openY, rcUpW = cwidth, rcUpH = (int)recth;
                        if (openY < closeY)
                        {
                            rcUpTop = (int)openY;
                            rcUpBottom = (int)closeY;
                        }
                        long upColor = FCColor.None;
                        int colorField = cs.getColorField();
                        if (colorField != FCDataTable.NULLFIELD)
                        {
                            double defineColor = m_dataSource.get2(i, colorField);
                            if (!Double.isNaN(defineColor))
                            {
                                upColor = (long)defineColor;
                            }
                        }
                        if (upColor == FCColor.None)
                        {
                            upColor = cs.getUpColor();
                        }
                        switch (style)
                        {
                            //矩形
                            case 0:
                            case 1:
                            case 2:
                                if ((int)highY != (int)lowY)
                                {
                                    drawThinLine(paint, MyColor.USERCOLOR48, thinLineWidth, scaleX, highY, scaleX, lowY);
                                    if (m_hScalePixel > 3)
                                    {
                                        //描背景
                                        if ((int)openY == (int)closeY)
                                        {
                                            drawThinLine(paint, MyColor.USERCOLOR48, thinLineWidth, rcUpX, rcUpBottom, rcUpX + rcUpW, rcUpBottom);
                                        }
                                        else
                                        {
                                            FCRect rcUp = new FCRect(rcUpX, rcUpTop, rcUpX + rcUpW, rcUpBottom);
                                            if (style == 0 || style == 1)
                                            {
                                                paint.fillGradientRect(MyColor.USERCOLOR48, MyColor.USERCOLOR48, rcUp, 0, 0);
                                                //paint.drawRect(MyColor.USERCOLOR17, thinLineWidth, 0, rcUp);
                                                //paint.drawRect(upColor, thinLineWidth, 0, rcUp);
                                            }
                                            else if (style == 2)
                                            {
                                                paint.fillRect(upColor, rcUp);
                                                if (thinLineWidth > 1)
                                                {
                                                    paint.drawRect(upColor, thinLineWidth, 0, rcUp);
                                                }
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    drawThinLine(paint, upColor, thinLineWidth, scaleX - xsub, closeY, scaleX + xsub, closeY);
                                }
                                break;
                            //宝塔线
                            case 5:
                            {
                                double lOpen = m_dataSource.get3(i - 1, ciOpen);
                                double lClose = m_dataSource.get3(i - 1, ciClose);
                                double lHigh = m_dataSource.get3(i - 1, ciHigh);
                                double lLow = m_dataSource.get3(i - 1, ciLow);
                                float top = highY;
                                float bottom = lowY;
                                if ((int)highY > (int)lowY)
                                {
                                    top = lowY;
                                    bottom = highY;
                                }
                                if (i == 0 || Double.isNaN(lOpen) || Double.isNaN(lClose) || Double.isNaN(lHigh) || Double.isNaN(lLow))
                                {
                                    if (m_hScalePixel <= 3)
                                    {
                                        drawThinLine(paint, upColor, thinLineWidth, rcUpX, top, rcUpX, bottom);
                                    }
                                    else
                                    {
                                        int rcUpHeight = (int)Math.abs(bottom - top == 0 ? 1 : bottom - top);
                                        if (rcUpW > 0 && rcUpHeight > 0)
                                        {
                                            FCRect rcUp = new FCRect(rcUpX, top, rcUpX + rcUpW, top + rcUpHeight);
                                            paint.fillRect(upColor, rcUp);
                                            if (thinLineWidth > 1)
                                            {
                                                paint.drawRect(upColor, thinLineWidth, 0, rcUp);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    if (m_hScalePixel <= 3)
                                    {
                                        drawThinLine(paint, upColor, thinLineWidth, rcUpX, top, rcUpX, bottom);
                                    }
                                    else
                                    {
                                        int rcUpHeight = (int)Math.abs(bottom - top == 0 ? 1 : bottom - top);
                                        if (rcUpW > 0 && rcUpHeight > 0)
                                        {
                                            FCRect rcUp = new FCRect(rcUpX, top, rcUpX + rcUpW, top + rcUpHeight);
                                            paint.fillRect(upColor, rcUp);
                                            if (thinLineWidth > 1)
                                            {
                                                paint.drawRect(upColor, thinLineWidth, 0, rcUp);
                                            }
                                        }
                                    }
                                    //上一股价为下跌，画未超过最高点部分
                                    if (lClose < lOpen && low < lHigh)
                                    {
                                        //获取矩形
                                        int tx = rcUpX;
                                        int ty = (int)getY(div, lHigh, cs.getAttachVScale(), vmax, vmin);
                                        if (high < lHigh)
                                        {
                                            ty = (int)highY;
                                        }
                                        int width = rcUpW;
                                        int height = (int)lowY - ty;
                                        if (height > 0)
                                        {
                                            if (m_hScalePixel <= 3)
                                            {
                                                drawThinLine(paint, cs.getDownColor(), thinLineWidth, tx, ty, tx, ty + height);
                                            }
                                            else
                                            {
                                                if (width > 0 && height > 0)
                                                {
                                                    FCRect tRect = new FCRect(tx, ty, tx + width, ty + height);
                                                    paint.fillRect(cs.getDownColor(), tRect);
                                                    if (thinLineWidth > 1)
                                                    {
                                                        paint.drawRect(cs.getDownColor(), thinLineWidth, 0, tRect);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                    //阴线
                    else
                    {
                        //获取阴线的高度
                        float recth = getDownCandleHeight(close, open, div.getVScale(cs.getAttachVScale()).getVisibleMax(), div.getVScale(cs.getAttachVScale()).getVisibleMin(), div.getWorkingAreaHeight() - div.getVScale(cs.getAttachVScale()).getPaddingBottom() - div.getVScale(cs.getAttachVScale()).getPaddingTop());
                        if (recth < 1)
                        {
                            recth = 1;
                        }
                        //获取阴线的矩形
                        int rcDownX = scaleX - xsub, rcDownTop = (int)openY, rcDownBottom = (int)closeY, rcDownW = cwidth, rcDownH = (int)recth;
                        if (closeY < openY)
                        {
                            rcDownTop = (int)closeY;
                            rcDownBottom = (int)openY;
                        }
                        long downColor = FCColor.None;
                        if (ciClr != -1)
                        {
                            double defineColor = m_dataSource.get3(i, ciClr);
                            if (!Double.isNaN(defineColor))
                            {
                                downColor = (long)defineColor;
                            }
                        }
                        if (downColor == FCColor.None)
                        {
                            downColor = cs.getDownColor();
                        }
                        switch (style)
                        {
                            //标准
                            case 0:
                            case 1:
                            case 2:
                                if ((int)highY != (int)lowY)
                                {
                                    drawThinLine(paint, MyColor.USERCOLOR70, thinLineWidth, scaleX, highY, scaleX, lowY);
                                    if (m_hScalePixel > 3)
                                    {
                                        FCRect rcDown = new FCRect(rcDownX, rcDownTop, rcDownX + rcDownW, rcDownBottom);
                                        if (style == 1)
                                        {
                                            if (rcDownW > 0 && rcDownH > 0 && m_hScalePixel > 3)
                                            {
                                                paint.fillRect(div.getBackColor(), rcDown);
                                            }
                                            paint.drawRect(downColor, thinLineWidth, 0, rcDown);

                                        }
                                        else if (style == 0 || style == 2)
                                        {
                                            paint.fillGradientRect(MyColor.USERCOLOR70, MyColor.USERCOLOR70, rcDown, 0, 0);
                                            //paint.drawRect(MyColor.USERCOLOR17, thinLineWidth, 0, rcDown);
                                        }
                                    }
                                }
                                else
                                {
                                    drawThinLine(paint, MyColor.USERCOLOR4, thinLineWidth, scaleX - xsub, closeY, scaleX + xsub, closeY);
                                }
                                break;
                            //宝塔线
                            case 5:
                                double lOpen = m_dataSource.get3(i - 1, ciOpen);
                                double lClose = m_dataSource.get3(i - 1, ciClose);
                                double lHigh = m_dataSource.get3(i - 1, ciHigh);
                                double lLow = m_dataSource.get3(i - 1, ciLow);
                                float top = highY;
                                float bottom = lowY;
                                if ((int)highY > (int)lowY)
                                {
                                    top = lowY;
                                    bottom = highY;
                                }
                                if (i == 0 || Double.isNaN(lOpen) || Double.isNaN(lClose) || Double.isNaN(lHigh) || Double.isNaN(lLow))
                                {
                                    if (m_hScalePixel <= 3)
                                    {
                                        drawThinLine(paint, downColor, thinLineWidth, rcDownX, top, rcDownX, bottom);
                                    }
                                    else
                                    {
                                        int rcDownHeight = (int)Math.abs(bottom - top == 0 ? 1 : bottom - top);
                                        if (rcDownW > 0 && rcDownHeight > 0)
                                        {
                                            FCRect rcDown = new FCRect(rcDownX, top, rcDownX + rcDownW, rcDownBottom);
                                            paint.fillRect(downColor, rcDown);
                                            if (thinLineWidth > 1)
                                            {
                                                paint.drawRect(downColor, thinLineWidth, 0, rcDown);
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    //先画阳线部分
                                    if (m_hScalePixel <= 3)
                                    {
                                        drawThinLine(paint, downColor, thinLineWidth, rcDownX, top, rcDownX, bottom);
                                    }
                                    else
                                    {
                                        int rcDownHeight = (int)Math.abs(bottom - top == 0 ? 1 : bottom - top);
                                        if (rcDownW > 0 && rcDownHeight > 0)
                                        {
                                            FCRect rcDown = new FCRect(rcDownX, top, rcDownX + rcDownW, rcDownBottom);
                                            paint.fillRect(downColor, rcDown);
                                            if (thinLineWidth > 1)
                                            {
                                                paint.drawRect(downColor, thinLineWidth, 0, rcDown);
                                            }
                                        }
                                    }
                                    //上一股价为上涨，画未跌过最高点部分
                                    if (lClose >= lOpen && high > lLow)
                                    {
                                        //获取矩形
                                        int tx = rcDownX;
                                        int ty = (int)highY;
                                        int width = rcDownW;
                                        int height = (int)Math.abs(getY(div, lLow, cs.getAttachVScale(), vmax, vmin) - ty);
                                        if (low > lLow)
                                        {
                                            height = (int)lowY - ty;
                                        }
                                        if (height > 0)
                                        {
                                            if (m_hScalePixel <= 3)
                                            {
                                                drawThinLine(paint, cs.getUpColor(), thinLineWidth, tx, ty, tx, ty + height);
                                            }
                                            else
                                            {
                                                if (width > 0 && height > 0)
                                                {
                                                    FCRect tRect = new FCRect(tx, ty, tx + width, ty + height);
                                                    paint.fillRect(cs.getUpColor(), new FCRect(tx, ty, tx + width, ty + height));
                                                    if (thinLineWidth > 1)
                                                    {
                                                        paint.drawRect(cs.getUpColor(), thinLineWidth, 0, tRect);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                break;
                        }
                    }
                    break;
                }
            }
            //绘制选中
            if (cs.isSelected())
            {
                int kPInterval = m_maxVisibleRecord / 30;
                if (kPInterval < 2)
                {
                    kPInterval = 3;
                }
                if (i % kPInterval == 0)
                {
                    if (!Double.isNaN(open) && !Double.isNaN(high) && !Double.isNaN(low) && !Double.isNaN(close))
                    {
                        if (closeY >= div.getTitleBar().getHeight()
                                && closeY <= div.getHeight() - div.getHScale().getHeight())
                        {
                            FCRect rect = new FCRect(scaleX - 3, (int)closeY - 4, scaleX + 4, (int)closeY + 3);
                            paint.fillRect(cs.getSelectedColor(), rect);
                        }
                    }
                }
            }
        }
        onPaintCandleEx(paint, div, cs, visibleMaxIndex, visibleMax, visibleMinIndex, visibleMin);
    }

    /*
    * 创建指标
    */
    public static FCScript createIndicator(FCChart chart, FCDataTable dataSource, String text)
    {
        FCScript script = new FCScript();
        ArrayList<Long> sysColors = new ArrayList<Long>();
        sysColors.add(MyColor.USERCOLOR50);
        sysColors.add(MyColor.USERCOLOR53);
        sysColors.add(MyColor.USERCOLOR48);
        sysColors.add(MyColor.USERCOLOR51);
        sysColors.add(MyColor.USERCOLOR52);
        sysColors.add(MyColor.USERCOLOR22);
        script.setSystemColors(sysColors);

        script.m_upColor = MyColor.USERCOLOR48;
        script.m_downColor = MyColor.USERCOLOR51;
        script.m_midColor = FCColor.Back;
        script.setDataSource(dataSource);
        script.setName("");
        //indicator.FullName = "";
        if (dataSource != null)
        {
            script.setSourceField("CLOSE", 0);
            script.setSourceField("HIGH", 1);
            script.setSourceField("LOW", 2);
            script.setSourceField("OPEN", 3);
            script.setSourceField("VOL", 4);
            script.setSourceField("CLOSE".substring(0, 1), 0);
            script.setSourceField("HIGH".substring(0, 1), 1);
            script.setSourceField("LOW".substring(0, 1), 2);
            script.setSourceField("OPEN".substring(0, 1), 3);
            script.setSourceField("VOL".substring(0, 1), 4);
        }
        if (text != null && text.length() > 0)
        {
            script.setScript(text);
        }
        return script;
    }

    /*
    * 添加控件方法
    */
    public void onAdd()
    {
        super.onAdd();
        setCanMoveShape(false);
        setScrollAddSpeed(true);
        setIsMobile(true);
        setLeftVScaleWidth(100);
        setRightVScaleWidth(0);
        setHScalePixel(10);
        setHScaleFieldText("日期");
        ChartDiv div1 = addDiv2(50);
        ChartDiv div2 = addDiv2(25);
        //添加指标层
        ChartDiv div3 = addDiv2(25);
        div1.setBackColor(FCColor.None);
        div2.setBackColor(FCColor.None);
        div3.setBackColor(FCColor.None);
        div1.setBorderColor(FCColor.None);
        div2.setBorderColor(FCColor.None);
        div3.setBorderColor(FCColor.None);
        div1.getHScale().setHeight(0);
        div2.getHScale().setHeight(0);
        div1.getHScale().setVisible(false);
        div2.getHScale().setVisible(false);
        div3.getHScale().setHeight(30);

        div1.getTitleBar().setText("日线");
        div1.setFont(new FCFont("Default", 12, false, false, false));
        div2.setFont(new FCFont("Default", 12, false, false, false));
        div3.setFont(new FCFont("Default", 12, false, false, false));
        div1.getTitleBar().setFont(new FCFont("Default", 12, false, false, false));
        div1.getTitleBar().setTextColor(MyColor.USERCOLOR67);
        div2.getTitleBar().setFont(new FCFont("Default", 12, false, false, false));
        div2.getTitleBar().setTextColor(MyColor.USERCOLOR67);
        div3.getTitleBar().setFont(new FCFont("Default", 12, false, false, false));
        div3.getTitleBar().setTextColor(MyColor.USERCOLOR67);
        div1.getTitleBar().m_titles.add(new ChartTitle(COLUMN_CLOSE, "最新价", MyColor.USERCOLOR67, 2, true));
        div1.getTitleBar().m_titles.add(new ChartTitle(COLUMN_HIGH, "最高价", MyColor.USERCOLOR67, 2, true));
        div1.getTitleBar().m_titles.add(new ChartTitle(COLUMN_LOW, "最低价", MyColor.USERCOLOR67, 2, true));
        div1.getTitleBar().m_titles.add(new ChartTitle(COLUMN_OPEN, "开盘价", MyColor.USERCOLOR67, 2, true));
        div2.getTitleBar().m_titles.add(new ChartTitle(COLUMN_VOL, "成交量", MyColor.USERCOLOR67, 0, true));
        div1.getLeftVScale().setPaddingTop(20);
        div1.getLeftVScale().setPaddingBottom(20);
        div1.getLeftVScale().setFont(new FCFont("Default", 12, false, false, false));
        div2.getLeftVScale().setFont(new FCFont("Default", 12, false, false, false));
        div3.getLeftVScale().setFont(new FCFont("Default", 12, false, false, false));


        div1.getLeftVScale().setTextColor(MyColor.USERCOLOR67);
        div2.getLeftVScale().setTextColor(MyColor.USERCOLOR67);
        div3.getLeftVScale().setTextColor(MyColor.USERCOLOR67);

        div1.getLeftVScale().getCrossLineTip().setBackColor(MyColor.USERCOLOR8);
        div2.getLeftVScale().getCrossLineTip().setBackColor(MyColor.USERCOLOR8);
        div3.getLeftVScale().getCrossLineTip().setBackColor(MyColor.USERCOLOR8);
        div3.getHScale().getCrossLineTip().setBackColor(MyColor.USERCOLOR8);
        div3.getHScale().getCrossLineTip().setBackColor(MyColor.USERCOLOR8);


        div2.getLeftVScale().setScaleColor(MyColor.USERCOLOR8);
        div3.getLeftVScale().setScaleColor(MyColor.USERCOLOR8);
        div1.getLeftVScale().setScaleColor(MyColor.USERCOLOR8);
        div1.getHScale().setScaleColor(MyColor.USERCOLOR8);
        div2.getHScale().setScaleColor(MyColor.USERCOLOR8);
        div3.getHScale().setScaleColor(MyColor.USERCOLOR8);
        div1.getHGrid().setGridColor(MyColor.USERCOLOR8);
        div2.getHGrid().setGridColor(MyColor.USERCOLOR8);
        div3.getHGrid().setGridColor(MyColor.USERCOLOR8);
        div1.getTitleBar().setUnderLineColor(MyColor.USERCOLOR8);
        div2.getTitleBar().setUnderLineColor(MyColor.USERCOLOR8);
        div3.getTitleBar().setUnderLineColor(MyColor.USERCOLOR8);
        div3.getHScale().setTextColor(MyColor.USERCOLOR67);
        div2.getLeftVScale().setFont(new FCFont("Default", 12, false, false, false));
        div3.getLeftVScale().setFont(new FCFont("Default", 12, false, false, false));
        FCDataTable dataSource = getDataSource();
        dataSource.addColumn(COLUMN_CLOSE);
        dataSource.addColumn(COLUMN_HIGH);
        dataSource.addColumn(COLUMN_LOW);
        dataSource.addColumn(COLUMN_OPEN);
        dataSource.addColumn(COLUMN_VOL);

        CandleShape candleShape = new CandleShape();
        candleShape.setCloseField(COLUMN_CLOSE);
        candleShape.setHighField(COLUMN_HIGH);
        candleShape.setLowField(COLUMN_LOW);
        candleShape.setOpenField(COLUMN_OPEN);
        candleShape.setUpColor(MyColor.USERCOLOR69);
        candleShape.setDownColor(MyColor.USERCOLOR70);
        candleShape.setTagColor(MyColor.USERCOLOR3);
        candleShape.setCloseFieldText("收盘价");
        candleShape.setHighFieldText("最高价");
        candleShape.setLowFieldText("最低价");
        candleShape.setOpenFieldText("开盘价");
        div1.addShape(candleShape);
        div1.getLeftVScale().getCrossLineTip().setFont(new FCFont("Default", 14, false, false, false));
        div2.getLeftVScale().getCrossLineTip().setFont(new FCFont("Default", 14, false, false, false));
        div3.getLeftVScale().getCrossLineTip().setFont(new FCFont("Default", 14, false, false, false));

        div3.getHScale().setFont(new FCFont("Default", 14, false, false, false));
        div3.getHScale().getCrossLineTip().setFont(new FCFont("Default", 14, false, false, false));

        BarShape barShape = new BarShape();
        barShape.setFieldText("成交量");
        barShape.setFieldName(COLUMN_VOL);
        div2.addShape(barShape);


        div1.getToolTip().setFont(new FCFont("Default", 14, false, false, false));
        div2.getToolTip().setFont(new FCFont("Default", 14, false, false, false));
        div3.getToolTip().setFont(new FCFont("Default", 14, false, false, false));

        div3.getHScale().m_dateColors.put(DateType.Year, MyColor.USERCOLOR67);
        div3.getHScale().m_dateColors.put(DateType.Month, MyColor.USERCOLOR67);
        div3.getHScale().m_dateColors.put(DateType.Day, MyColor.USERCOLOR67);
        div3.getHScale().m_dateColors.put(DateType.Hour, MyColor.USERCOLOR67);
        div3.getHScale().m_dateColors.put(DateType.Minute, MyColor.USERCOLOR67);
        div3.getHScale().m_dateColors.put(DateType.Second, MyColor.USERCOLOR67);
        div3.getHScale().m_dateColors.put(DateType.Millisecond, MyColor.USERCOLOR67);

        FCScript subIndicator = createIndicator(this, getDataSource(), "CONST SHORT=12;CONST LONG=26;CONST MID=9;\r\nDIF:EMA(CLOSE,SHORT)-EMA(CLOSE,LONG);\rDEA:EMA(DIF,MID);\rMACD:(DIF-DEA)*2,COLORSTICK;");
        subIndicator.setDiv(getDivs().get(2));
        subIndicator.onCalculate(0);
        m_indicators.add(subIndicator);

        FCScript mainIndicator = createIndicator(this, getDataSource(), "CONST N1=5;CONST N2=10;CONST N3=20;CONST N4=30;CONST N5=120;CONST N6=250;\r\nMA5:MA(CLOSE,N1); \rMA10:MA(CLOSE,N2); \rMA20:MA(CLOSE,N3); \rMA30:MA(CLOSE,N4);\rMA120:MA(CLOSE,N5); \rMA250:MA(CLOSE,N6);");
        mainIndicator.setDiv(getDivs().get(0));
        mainIndicator.onCalculate(0);
        m_indicators.add(mainIndicator);

        FCScript volIndicator = createIndicator(this, getDataSource(), "CONST N1=5;CONST N2=10;\r\nMA5:MA(VOL,N1); \rMA10:MA(VOL,N2); ");
        volIndicator.setDiv(getDivs().get(1));
        volIndicator.onCalculate(0);
        m_indicators.add(volIndicator);
    }

    /*
    * 指标集合
    */
    private ArrayList<FCScript> m_indicators = new ArrayList<FCScript>();

    public String m_currentCode;
    public String m_currentSName;
    public int m_currentCycle = 5;

    /*
    * 刷新数据
    */
    public void refreshData(ArrayList<CoinSecurityData> datas, boolean clear)
    {
        FCDataTable dataSource = getDataSource();
        if (clear)
        {
            m_divs.get(0).getTitleBar().setText("日线");
            dataSource.clear();
            update();
        }
        if (datas != null)
        {
            int startIndex = dataSource.getRowsCount();
            for (int i = 0; i < datas.size(); i++)
            {
                CoinSecurityData data = datas.get(i);
                dataSource.set(data.m_date, COLUMN_CLOSE, data.m_close);
                dataSource.set(data.m_date, COLUMN_HIGH, data.m_high);
                dataSource.set(data.m_date, COLUMN_LOW, data.m_low);
                dataSource.set(data.m_date, COLUMN_OPEN, data.m_open);
                dataSource.set(data.m_date, COLUMN_VOL, data.m_volume);
            }
            int indicatorsSize = m_indicators.size();
            for (int i = 0; i < indicatorsSize; i++)
            {
                m_indicators.get(i).onCalculate(startIndex);
            }
        }
        update();
        invalidate();
    }
}
