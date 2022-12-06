/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facecat.topin.swing;

/**
 *
 * @author taode
 */
public class iFCRedoUndoInfo {
    /// <summary>
    /// 创建信息
    /// </summary>
    public iFCRedoUndoInfo(String text, int selectionStart, int selectionLength, double time)
    {
        m_text = text;
        m_selectionStart = selectionStart;
        m_selectionLength = selectionLength;
        m_time = time;
    }

    /// <summary>
    /// 选中开始位置
    /// </summary>
    public int m_selectionStart = -1;

    /// <summary>
    /// 选中长度
    /// </summary>
    public int m_selectionLength = 0;

    /// <summary>
    /// 文字
    /// </summary>
    public String m_text = "";

    /// <summary>
    /// 时间戳
    /// </summary>
    public double m_time;
}
