package facecat.topin.swing;

/*
* 重置撤销
*/
public class iFCRedoUndoInfo {
    /*
    * 创建信息
    */
    public iFCRedoUndoInfo(String text, int selectionStart, int selectionLength, double time)
    {
        m_text = text;
        m_selectionStart = selectionStart;
        m_selectionLength = selectionLength;
        m_time = time;
    }

    /*
    * 选中开始位置
    */
    public int m_selectionStart = -1;

    /*
    * 选中长度
    */
    public int m_selectionLength = 0;

    /*
    * 文字
    */
    public String m_text = "";

    /*
    * 时间戳
    */
    public double m_time;
}
