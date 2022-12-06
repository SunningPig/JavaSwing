/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coin;
import facecat.topin.core.*;
import facecat.topin.grid.*;
import facecat.topin.swing.*;
import java.util.*;


/// <summary>
/// 行的扩展
/// </summary>
public class GridRowEx extends FCGridRow
{
    public GridRowEx()
    {
        setHeight(25);
    }

    /// <summary>
    /// 重绘方法
    /// </summary>
    public void onPaint(FCPaint paint, FCRect clipRect, boolean isAlternate)
    {
        FCGrid grid = getGrid();
        ArrayList<FCGridRow> rows = grid.getSelectedRows();
        int rowsSize = rows.size();
        if (rowsSize > 0 && rows.contains(this))
        {
            FCRect bounds = getBounds();
            int scrollH = 0, scrollV = 0;
            if (grid.getHScrollBar() != null && grid.getHScrollBar().isVisible())
            {
                scrollH = grid.getHScrollBar().getPos();
            }
            if (grid.getVScrollBar() != null && grid.getVScrollBar().isVisible())
            {
                scrollV = grid.getVScrollBar().getPos();
            }
            bounds.left = 1;
            bounds.top -= scrollV;
            bounds.right = bounds.right - bounds.left;
            bounds.bottom -= scrollV;
            if (grid.getVScrollBar() != null && grid.getVScrollBar().isVisible())
            {
                bounds.right -= grid.getVScrollBar().getWidth();
            }
            paint.fillRect(MyColor.USERCOLOR23, bounds);
        }
    }

    /// <summary>
    /// 重绘边线
    /// </summary>
    /// <param name="paint"></param>
    /// <param name="clipRect"></param>
    /// <param name="isAlternate"></param>
    public void onPaintBorder(FCPaint paint, FCRect clipRect, boolean isAlternate)
    {
        FCGrid grid = getGrid();
        ArrayList<FCGridRow> rows = grid.getSelectedRows();
        int rowsSize = rows.size();
        if (rowsSize > 0 && rows.contains(this))
        {
            FCRect bounds = getBounds();
            int scrollH = 0, scrollV = 0;
            if (grid.getHScrollBar() != null && grid.getHScrollBar().isVisible())
            {
                scrollH = grid.getHScrollBar().getPos();
            }
            if (grid.getVScrollBar() != null && grid.getVScrollBar().isVisible())
            {
                scrollV = grid.getVScrollBar().getPos();
            }
            bounds.left = 1;
            bounds.top -= scrollV;
            bounds.right = bounds.right - bounds.left;
            bounds.bottom -= scrollV;
            if (grid.getVScrollBar() != null && grid.getVScrollBar().isVisible())
            {
                bounds.right -= grid.getVScrollBar().getWidth();
            }
            paint.drawRect(MyColor.USERCOLOR10, 1, 0, bounds);
        }
    }
}
