package facecat.topin.swing;
import java.util.*;

import facecat.topin.core.*;
import facecat.topin.div.*;
import facecat.topin.div.*;
import facecat.topin.grid.*;
import facecat.topin.input.*;
import static facecat.topin.input.FCTextBox.TICK;
import facecat.topin.scroll.*;

/*
* 文本框
*/
public class iTextBox extends FCDiv {
    /*
    * 创建视图
    */
    public iTextBox() {
        FCSize size = new FCSize(100, 20);
        setSize(size);
        setLineHeight(30);
        setAllowPreviewsEvent(true);
        //setTextColor(MyColor.USERCOLOR88);
    }

    public int m_changeID = FCNative.getChangeID();

    /*
    * 点击的高亮信息
    */
    public HighLightInfo m_clickHighInfo;

    /*
    * 代码高亮信息
    */
    public ArrayList<HighLightInfo> m_highLightInfos = new ArrayList<HighLightInfo>();

    public boolean m_isCopy;

    /*
    * 键盘是否按下
    */
    public boolean m_isKeyDown;

    /*
    * 是否触摸按下
    */
    public boolean m_isTouchDown;

    /*
    * 横向偏移量
    */
    public int m_offsetX = 0;

    /*
    * 文字矩形范围
    */
    public ArrayList<FCRectF> m_ranges = new ArrayList<FCRectF>();

    /*
    * 重做栈
    */
    public Stack<iFCRedoUndoInfo> m_redoStack = new Stack<iFCRedoUndoInfo>();

    /*
    * 是否显示光标
    */
    public boolean m_showCursor = false;

    /*
    * 是否显示光标
    */
    public int m_startMovingIndex = -1;

    /*
    * 结束移动的坐标
    */
    public int m_stopMovingIndex = -1;

    /*
    * 文字是否改变
    */
    public boolean m_textChanged = false;

    /*
    * 光标闪烁频率
    */
    public int TICK = 500;

    /*
    * 秒表ID
    */
    private int m_timerID = getNewTimerID();

    /*
    * 撤销栈
    */
    public Stack<iFCRedoUndoInfo> m_undoStack = new Stack<iFCRedoUndoInfo>();

    /*
    * 文字大小
    */
    public ArrayList<FCSizeF> m_wordsSize = new ArrayList<FCSizeF>();

    private boolean m_alwaysAddEnd;

    /*
    * 是否在尾部追加
    */
    public boolean alwaysAddEnd()
    {
        return m_alwaysAddEnd;
    }

    /*
    * 设置是否总是在尾部追加
    */
    public void setAlwaysAddEnd(boolean value)
    {
        m_alwaysAddEnd = value;
    }

    protected FCPoint m_cursorPoint = new FCPoint();

    /*
    * 获取光标位置
    */
    public FCPoint getCursorPoint() {
        return m_cursorPoint.clone();
    }

    /*
    * 设置光标位置
    */
    public void setCursorPoint(FCPoint value) {
        m_cursorPoint = value.clone();
    }

    /*
    * 获取行数
    */
    public int getLinesCount() {
        return m_lines.size();
    }

    public int m_lineHeight = 20;

    /*
    * 获取行高
    */
    public int getLineHeight() {
        return m_lineHeight;
    }

    /*
    * 设置行高
    */
    public void setLineHeight(int value) {
        m_lineHeight = value;
    }

    public ArrayList<FCWordLine> m_lines = new ArrayList<FCWordLine>();

    /*
    * 获取所有行
    */
    public ArrayList<FCWordLine> getLines() {
        return m_lines;
    }

    public boolean m_multiline = false;

    /*
    * 获取是否多行显示
    */
    public boolean isMultiline() {
        return m_multiline;
    }

    /*
    * 设置是否多行显示
    */
    public void setMultiline(boolean value) {
        if (m_multiline != value) {
            m_multiline = value;
            m_textChanged = true;
        }
        setShowVScrollBar(m_multiline);
    }

    public char m_passwordChar;

    /*
    * 获取密码字符
    */
    public char getPasswordChar() {
        return m_passwordChar;
    }

    /*
    * 获取密码字符
    */
    public void setPasswordChar(char value) {
        m_passwordChar = value;
        m_textChanged = true;
    }

    public boolean m_readOnly = false;

    /*
    * 获取是否只读
    */
    public boolean isReadOnly() {
        return m_readOnly;
    }

    /*
    * 设置是否只读
    */
    public void setReadOnly(boolean value) {
        m_readOnly = value;
    }

    public boolean m_rightToLeft;

    /*
    * 获取是否从右向左绘制
    */
    public boolean rightToLeft() {
        return m_rightToLeft;
    }

    /*
    * 设置是否从右向左绘制
    */
    public void setRightToLeft(boolean value) {
        m_rightToLeft = value;
        m_textChanged = true;
    }

    /*
    * 获取选中的文字
    */
    public String getSelectionText() {
        String text = getText();
        if (text == null) {
            text = "";
        }
        int textLength = text.length();
        if (textLength > 0 && m_selectionStart != textLength && m_selectionStart != -1) {
            String selectedText = text.substring(m_selectionStart, m_selectionStart + m_selectionLength);
            return selectedText;
        }
        return "";
    }

    public long m_selectionBackColor = MyColor.USERCOLOR63;

    /*
    * 获取选中的背景色
    */
    public long getSelectionBackColor() {
        return m_selectionBackColor;
    }

    /*
    * 设置选中的背景色
    */
    public void setSelectionBackColor(long value) {
        m_selectionBackColor = value;
    }

    public long m_selectionTextColor = MyColor.USERCOLOR3;

    /*
    * 获取选中的文字色
    */
    public long getSelectionTextColor() {
        return m_selectionTextColor;
    }

    /*
    * 设置选中的文字色
    */
    public void setSelectionTextColor(long value) {
        m_selectionTextColor = value;
    }

    public int m_selectionLength;

    /*
    * 获取选中长度
    */
    public int getSelectionLength() {
        return m_selectionLength;
    }

    /*
    * 设置选中长度
    */
    public void setSelectionLength(int value) {
        m_selectionLength = value;
    }

    public int m_selectionStart = -1;

    /*
    * 获取选中开始位置
    */
    public int getSelectionStart() {
        return m_selectionStart;
    }

    /*
    * 设置选中开始位置
    */
    public void setSelectionStart(int value) {
        m_selectionStart = value;
        if (m_selectionStart > getText().length()) {
            m_selectionStart = getText().length();
        }
    }

    public String m_tempText;

    /*
    * 获取临时文字
    */
    public String getTempText() {
        return m_tempText;
    }

    /*
    * 设置临时文字
    */
    public void setTempText(String tempText) {
        m_tempText = tempText;
    }

    public long m_tempTextColor = FCColor.DisabledText;

    /*
    * 设置临时文字
    */
    public long getTempTextColor() {
        return m_tempTextColor;
    }

    /*
    * 设置临时文字的颜色
    */
    public void setTempTextColor(long tempTextColor) {
        m_tempTextColor = tempTextColor;
    }

    public FCHorizontalAlign m_textAlign = FCHorizontalAlign.Left;

    /*
    * 获取内容的横向排列样式
    */
    public FCHorizontalAlign getTextAlign() {
        return m_textAlign;
    }

    /*
    * 设置内容的横向排列样式
    */
    public void setTextAlign(FCHorizontalAlign value) {
        m_textAlign = value;
    }

    public boolean m_wordWrap = false;

    /*
    * 获取多行编辑视图是否启动换行
    */
    public boolean wordWrap() {
        return m_wordWrap;
    }

    /*
    * 设置多行编辑视图是否启动换行
    */
    public void setWordWrap(boolean value) {
        if (m_wordWrap != value) {
            m_wordWrap = value;
            m_textChanged = true;
        }
        setShowHScrollBar(!m_wordWrap);
    }

    /*
    * 判断是否可以重复
    */
    public boolean canRedo() {
        if (m_redoStack.size() > 0) {
            return true;
        }
        return false;
    }

    /*
    * 判断是否可以撤销
    */
    public boolean canUndo() {
        if (m_undoStack.size() > 0) {
            return true;
        }
        return false;
    }

    /*
    * 重置撤销重复
    */
    public void clearRedoUndo() {
        m_undoStack.clear();
        m_redoStack.clear();
    }
    
    /*
    * 光标向下移动
    */
    public void cursorDown()
    {
        FCHost host = getNative().getHost();
        int rangeSize = m_ranges.size();
        int start = m_selectionStart + m_selectionLength < rangeSize - 1 ? m_selectionStart + m_selectionLength : rangeSize - 1;
        if (host.isKeyPress((char)0x10))
        {
            start = m_stopMovingIndex + 1;
        }
        else
        {
            if (m_selectionLength > 0)
            {
                m_selectionLength = 1;
            }
        }
        int lineCount = m_lines.size();
        for (int i = 0; i < lineCount; i++)
        {
            FCWordLine line = m_lines.get(i);
            if (start >= line.m_start && start <= line.m_end)
            {
                if (i != lineCount - 1)
                {
                    FCWordLine newLine = m_lines.get(i + 1);
                    int findIndex = -1;
                    if (m_ranges.get(newLine.m_end).left <= m_ranges.get(start).left)
                    {
                        findIndex = newLine.m_end;
                    }
                    else
                    {
                        for (int j = newLine.m_end; j >= newLine.m_start; j--)
                        {
                            if (m_ranges.get(j).right < m_ranges.get(start).right)
                            {
                                findIndex = j;
                                break;
                            }
                        }
                        if (findIndex == -1)
                        {
                            findIndex = newLine.m_end;
                        }
                    }
                    if (host.isKeyPress((char)0x10))
                    {
                        setMovingIndex(m_startMovingIndex, findIndex);
                    }
                    else
                    {
                        m_selectionStart = findIndex;
                        m_selectionLength = 0;
                        m_startMovingIndex = m_selectionStart;
                        m_stopMovingIndex = m_selectionStart;
                    }
                    m_showCursor = true;
                    startTimer(m_timerID, TICK);
                    break;
                }
            }
        }
    }

    /*
    * 光标移动到最右端
    */
    public void cursorEnd()
    {
        FCHost host = getNative().getHost();
        int rangeSize = m_ranges.size();
        int start = m_selectionStart + m_selectionLength < rangeSize - 1 ? m_selectionStart + m_selectionLength : rangeSize - 1;
        if (host.isKeyPress((char)0x10))
        {
            start = m_stopMovingIndex;
        }
        int lineCount = m_lines.size();
        for (int i = 0; i < lineCount; i++)
        {
            FCWordLine line = m_lines.get(i);
            for (int j = line.m_start; j <= line.m_end; j++)
            {
                if (j == start)
                {
                    if (j == line.m_start && i > 0)
                    {
                        line = m_lines.get(i - 1);
                    }
                    if (host.isKeyPress((char)0x10))
                    {
                        setMovingIndex(m_startMovingIndex, line.m_end + 1);
                    }
                    else
                    {
                        m_selectionStart = line.m_end + 1;
                        m_selectionLength = 0;
                        m_startMovingIndex = m_selectionStart;
                        m_stopMovingIndex = m_selectionStart;
                    }
                    m_showCursor = true;
                    startTimer(m_timerID, TICK);
                    return;
                }
            }
        }
    }

    /*
    * 光标移动到最左端
    */
    public void cursorHome()
    {
        FCHost host = getNative().getHost();
        int rangeSize = m_ranges.size();
        int start = m_selectionStart < rangeSize - 1 ? m_selectionStart : rangeSize - 1;
        if (host.isKeyPress((char)0x10))
        {
            start = m_stopMovingIndex;
        }
        int lineCount = m_lines.size();
        for (int i = 0; i < lineCount; i++)
        {
            FCWordLine line = m_lines.get(i);
            for (int j = line.m_start; j <= line.m_end; j++)
            {
                if (j == start)
                {
                    if (j == line.m_start && i > 0)
                    {
                        line = m_lines.get(i - 1);
                    }
                    if (host.isKeyPress((char)0x10))
                    {
                        setMovingIndex(m_startMovingIndex, line.m_start + 1);
                    }
                    else
                    {
                        m_selectionStart = line.m_start + 1;
                        m_selectionLength = 0;
                        m_startMovingIndex = m_selectionStart;
                        m_stopMovingIndex = m_selectionStart;
                    }
                    m_showCursor = true;
                    startTimer(m_timerID, TICK);
                    return;
                }
            }
        }
    }

    /*
    * 光标向左移动
    */
    public void cursorLeft()
    {
        FCHost host = getNative().getHost();
        if (host.isKeyPress((char)0x10))
        {
            setMovingIndex(m_startMovingIndex, m_stopMovingIndex - 1);
        }
        else
        {
            if (m_selectionStart > 0)
            {
                m_selectionStart -= 1;

                FCRectF sRange = m_ranges.get(m_selectionStart);
                if (m_selectionStart > 0 && sRange.right - sRange.left == 0)
                {
                    m_selectionStart -= 1;
                }
            }
            m_selectionLength = 0;
            m_startMovingIndex = m_selectionStart;
            m_stopMovingIndex = m_selectionStart;
        }
    }

    /*
    * 光标向右移动
    */
    public void cursorRight()
    {
        FCHost host = getNative().getHost();
        if (host.isKeyPress((char)0x10))
        {
            setMovingIndex(m_startMovingIndex, m_stopMovingIndex + 1);
        }
        else
        {
            int rangeSize = m_ranges.size();
            int start = m_selectionStart + m_selectionLength < rangeSize - 1 ? m_selectionStart + m_selectionLength : rangeSize - 1;
            if (start < rangeSize)
            {
                m_selectionStart = start + 1;

                if (m_selectionStart < m_ranges.size())
                {
                    FCRectF sRange = m_ranges.get(m_selectionStart);
                    if (sRange.right - sRange.left == 0)
                    {
                        m_selectionStart++;
                    }
                }
            }
            m_selectionLength = 0;
            m_startMovingIndex = m_selectionStart;
            m_stopMovingIndex = m_selectionStart;
        }
    }

    /*
    * 光标向上移动
    */
    public void cursorUp()
    {
        FCHost host = getNative().getHost();
        int rangeSize = m_ranges.size();
        int start = m_selectionStart < rangeSize - 1 ? m_selectionStart : rangeSize - 1;
        if (host.isKeyPress((char)0x10))
        {
            start = m_stopMovingIndex;
        }
        else
        {
            if (m_selectionLength > 0)
            {
                m_selectionLength = 1;
            }
        }
        int lineCount = m_lines.size();
        for (int i = 0; i < lineCount; i++)
        {
            FCWordLine line = m_lines.get(i);
            if (start >= line.m_start && start <= line.m_end)
            {
                if (i != 0)
                {
                    FCWordLine newLine = m_lines.get(i - 1);
                    int findIndex = -1;
                    if (m_ranges.get(newLine.m_end).left <= m_ranges.get(start).left)
                    {
                        findIndex = newLine.m_end;
                    }
                    else
                    {
                        for (int j = newLine.m_end; j >= newLine.m_start; j--)
                        {
                            if (m_ranges.get(j).right < m_ranges.get(start).right)
                            {
                                findIndex = j;
                                break;
                            }
                        }
                        if (findIndex == -1)
                        {
                            findIndex = newLine.m_end;
                        }
                    }
                    if (host.isKeyPress((char)0x10))
                    {
                        setMovingIndex(m_startMovingIndex, findIndex);
                    }
                    else
                    {
                        m_selectionStart = findIndex;
                        m_selectionLength = 0;
                        m_startMovingIndex = m_selectionStart;
                        m_stopMovingIndex = m_selectionStart;
                    }
                    m_showCursor = true;
                    startTimer(m_timerID, TICK);
                    break;
                }
            }
        }
    }

    /*
    * 删除字符
    */
    public void deleteWord() {
        String text = getText();
        if (text == null) {
            text = "";
        }
        int textLength = text.length();
        if (textLength > 0) {
            int oldLines = m_lines.size();
            String left = "", right = "";
            int rightIndex = -1;
            if (m_selectionLength > 0) {
                if(m_selectionStart > 1){
                    if(text.charAt(m_selectionStart - 1) == '\r' || text.charAt(m_selectionStart - 1) == '\n'){
                        if(text.charAt(m_selectionStart - 2) != '\r' && text.charAt(m_selectionStart - 2) != '\n'){
                            m_selectionStart--;
                        }
                    }
                }
                left = m_selectionStart > 0 ? text.substring(0, m_selectionStart) : "";
                rightIndex = m_selectionStart + m_selectionLength;
            }
            else {
                if (m_selectionStart > 1)
                {
                    if (text.charAt(m_selectionStart - 1) == '\r' || text.charAt(m_selectionStart - 1) == '\n')
                    {
                        if (text.charAt(m_selectionStart - 2) != '\r' && text.charAt(m_selectionStart - 2) != '\n')
                        {
                            m_selectionStart--;
                        }
                    }
                }
                left = m_selectionStart > 0 ? text.substring(0, m_selectionStart - 1) : "";
                rightIndex = m_selectionStart + m_selectionLength;
            }
            if (rightIndex < textLength) {
                right = text.substring(rightIndex);
            }
            if (left.length() > 1)
            {
                if (left.substring(left.length() - 1).equals("\r"))
                {
                    left = left.substring(0, left.length() - 1);
                }
            }
            if (right.length() > 1)
            {
                if (right.substring(0).equals("\n"))
                {
                    right = right.substring(1);
                }
            }
            String newText = left + right;
            m_text = newText;
            textLength = newText.length();
            m_selectionStart = left.length();
            if (textLength == 0) {
                m_selectionStart = 0;
            }
            else {
                if (m_selectionStart > textLength) {
                    m_selectionStart = textLength;
                }
            }
            m_selectionLength = 0;
        }
    }

    /*
    * 销毁方法
    */
    public void delete() {
        if (!isDeleted()) {
            stopTimer(m_timerID);
            m_lines.clear();
            m_ranges.clear();
            m_redoStack.clear();
            m_undoStack.clear();
            m_wordsSize.clear();
        }
        super.delete();
    }

    /*
    * 获取内容的高度
    */
    public int getContentHeight() {
        int hmax = super.getContentHeight();
        int cheight = 0;
        int rangeSize = m_ranges.size();
        for (int i = 0; i < rangeSize; i++) {
            if (cheight < m_ranges.get(i).bottom) {
                cheight = (int)m_ranges.get(i).bottom;
            }
        }
        int contentHeight = hmax > cheight ? hmax : cheight;
        return contentHeight + getNative().getSize().cy / 2;
    }

    /*
    * 获取内容的宽度
    */
    public int getContentWidth() {
        int wmax = super.getContentWidth();
        int cwidth = 0;
        int rangeSize = m_ranges.size();
        for (int i = 0; i < rangeSize; i++) {
            if (cwidth < m_ranges.get(i).right) {
                cwidth = (int)m_ranges.get(i).right;
            }
        }
        if (showHScrollBar())
        {
            FCPadding padding = getPadding();
            cwidth += padding.left + padding.right;
        }
        return wmax > cwidth ? wmax : cwidth;
    }

    /*
    * 获取视图类型
    */
    public String getViewType() {
        return "TextBox";
    }

    /*
    * 获取属性值
    */
    public void getAttribute(String name, RefObject<String> value, RefObject<String> type) {
        if (name.equals("lineheight")) {
            type.argvalue = "int";
            value.argvalue = FCTran.intToStr(getLineHeight());
        } else if (name.equals("multiline")) {
            type.argvalue = "bool";
            value.argvalue = FCTran.boolToStr(isMultiline());
        } else if (name.equals("passwordchar")) {
            type.argvalue = "text";
            value.argvalue = (new Character(getPasswordChar())).toString();
        } else if (name.equals("readonly")) {
            type.argvalue = "bool";
            value.argvalue = FCTran.boolToStr(isReadOnly());
        } else if (name.equals("righttoleft")) {
            type.argvalue = "bool";
            value.argvalue = FCTran.boolToStr(rightToLeft());
        } else if (name.equals("selectionbackcolor")) {
            type.argvalue = "color";
            value.argvalue = FCTran.colorToStr(getSelectionBackColor());
        } else if (name.equals("selectiontextcolor")) {
            type.argvalue = "color";
            value.argvalue = FCTran.colorToStr(getSelectionTextColor());
        } else if (name.equals("temptext")) {
            type.argvalue = "text";
            value.argvalue = getTempText();
        } else if (name.equals("temptextcolor")) {
            type.argvalue = "color";
            value.argvalue = FCTran.colorToStr(getTempTextColor());
        } else if (name.equals("textalign")) {
            type.argvalue = "enum:FCHorizontalAlign";
            value.argvalue = FCTran.horizontalAlignToStr(getTextAlign());
        } else if (name.equals("wordwrap")) {
            type.argvalue = "bool";
            value.argvalue = FCTran.boolToStr(wordWrap());
        } else {
            super.getAttribute(name, value, type);
        }
    }

    /*
    * 获取属性名称列表
    */
    public ArrayList<String> getAttributeNames() {
        ArrayList<String> attributeNames = super.getAttributeNames();
        attributeNames.addAll(Arrays.asList(new String[]{"LineHeight", "Multiline", "PasswordChar", "ReadOnly", "RightToLeft", "SelectionBackColor", "SelectionTextColor", "TempText", "TempTextColor", "TextAlign", "WordWrap"}));
        return attributeNames;
    }

    /**
     * 判断字符索引所在行是否可见
     *
     * @param index 字符索引
     * @param visiblePercent 可见百分比
     * @returns 是否可见
     */
    public boolean isLineVisible(int index, double visiblePercent) {
        int rangeSize = m_ranges.size();
        if (rangeSize > 0) {
            if (index >= 0 && index < rangeSize) {
                int top = 0, scrollV = 0, sch = 0;
                FCHScrollBar hScrollBar = getHScrollBar();
                FCVScrollBar vScrollBar = getVScrollBar();
                if (hScrollBar != null && hScrollBar.isVisible()) {
                    sch = hScrollBar.getHeight();
                }
                if (vScrollBar != null && vScrollBar.isVisible()) {
                    scrollV = -vScrollBar.getPos();
                }
                top = scrollV;
                int cell = 1;
                int floor = getHeight() - cell - sch - 1;
                FCRectF indexRect = m_ranges.get(index);
                int indexTop = (int) indexRect.top + scrollV;
                int indexBottom = (int) indexRect.bottom + scrollV;
                if (indexTop < cell) {
                    indexTop = cell;
                } else if (indexTop > floor) {
                    indexTop = floor;
                }
                if (indexBottom < cell) {
                    indexBottom = cell;
                } else if (indexBottom > floor) {
                    indexBottom = floor;
                }
                if (indexBottom - indexTop >= (int)(m_lineHeight * visiblePercent)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    * 插入字符
    */
    public void insertWord2(String str) {
        int oldLines = m_lines.size();
        iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
        m_text = str;
        m_textChanged = true;
        m_selectionStart = 0;
        m_selectionLength = 0;
        onTextChanged();
        if (m_textChanged)
        {
            m_undoStack.push(newReoUndoInfo);
        }
    }

    /*
    * 插入字符
    */
    public void insertWord3(String str){
        int oldLines = m_lines.size();
        iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
        insertWord(str);
        m_textChanged = true;
        onTextChanged();
        if (m_textChanged)
        {
            m_undoStack.push(newReoUndoInfo);
        }
        invalidate();
    }

    /*
    * 设置字符
    */
    public void setText2(String str){
        m_text = str;
    }

    public boolean m_isShift;

    /**
     * 插入字符
     *
     * @param str 字符串
     */
    public void insertWord(String str) {
        String text = getText();
        if (text == null) {
            text = "";
        }
        if (text.length() == 0 || m_selectionStart == text.length()) {
            text = text + str;
            m_text = text;
            m_selectionStart = text.length();
        } else {
            if (str.equals("\t") && m_multiline && m_selectionLength > 0)
            {
                String[] strs = m_text.split("[\n]");
                int pos = 0;
                boolean flag = false;
                String newStr = "";
                int line = 0;
                FCHost host = getNative().getHost();
                for (int i = 0; i < strs.length; i++)
                {
                    String subStr = strs[i] + "\n";
                    if (subStr.length() > 0)
                    {
                        if (subStr.charAt(subStr.length() - 1) == '\r')
                        {
                            subStr += "\n";
                        }
                    }
                    int tStart = pos;
                    int tEnd = pos + subStr.length();
                    int start = Math.max(tStart, m_selectionStart);
                    int end = Math.min(tEnd, m_selectionStart + m_selectionLength);
                    if (start >= tStart && start <= tEnd && end >= tStart && end <= tEnd)
                    {
                        flag = true;
                        if (m_isShift)
                        {
                            int tIndex = subStr.indexOf("\t");
                            if (tIndex != -1)
                            {
                                if (line == 0)
                                {
                                    m_selectionStart--;
                                }
                                m_selectionLength--;
                                if (tIndex == 0)
                                {
                                    newStr += subStr.substring(1);
                                }
                                else if (tIndex == subStr.length() - 1)
                                {
                                    newStr += subStr.substring(subStr.length() - 1);
                                }
                                else
                                {
                                    newStr += subStr.substring(0, tIndex) + subStr.substring(tIndex + 1);
                                }
                            }
                            else
                            {
                                newStr += subStr;
                            }
                        }
                        else
                        {
                            m_selectionStart++;
                            newStr += "\t" + subStr;
                        }
                        line++;
                    }
                    else
                    {
                        newStr += subStr;
                    }
                    pos = tEnd;
                }
                if (flag)
                {
                    m_text = newStr;
                    return;
                }
            }
            int sIndex = m_selectionStart > text.length() ? text.length() - 1 : m_selectionStart;
            String left = sIndex > 0 ? text.substring(0, sIndex) : "";
            String right = "";
            int rightIndex = m_selectionStart + (m_selectionLength == 0 ? 0 : m_selectionLength);
            if (rightIndex < text.length()) {
                right = text.substring(rightIndex);
            }
            if (left.length() > 1 && right.length() > 1 && left.charAt(left.length() - 1) == '\r' && (right.charAt(0) == '\n' || text.charAt(left.length()) == '\n'))
            {
                if(right.charAt(0) == '\n'){
                    text = left.substring(0, left.length() - 1) + str + "\r\n" + right.substring(1);
                }else{
                    text = left.substring(0, left.length() - 1) + str + "\r\n" + right;
                }
            }
            else
            {
                text = left + str + right;
            }
            m_text = text;
            m_selectionStart += str.length();
            if (m_selectionStart > text.length()) {
                m_selectionStart = text.length();
            }
            m_selectionLength = 0;
        }
    }

    /*
    * 复制文字
    */
    public void onCopy() {
        String selectionText = getSelectionText();
        if (selectionText != null && selectionText.length() > 2) {
            if (selectionText.indexOf("\r\n") == 0) {
                selectionText = selectionText.substring(2);
            }
            if (selectionText.length() > 2) {
                if (selectionText.lastIndexOf("\r\n") == selectionText.length() - 2) {
                    selectionText = selectionText.substring(0, selectionText.length() - 2);
                }
            }
        }
        if (selectionText != null && selectionText.length() > 0) {
            FCHost host = getNative().getHost();
            host.copy(selectionText);
            super.onCopy();
        }
    }

    /*
    * 剪切
    */
    public void onCut() {
        if (!m_readOnly) {
            onCopy();
            int oldLines = m_lines.size();
            iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
            deleteWord();
            onTextChanged();
            if (m_textChanged) {
                m_undoStack.push(newReoUndoInfo);
            }
            invalidate();
            if (!isLineVisible(m_selectionStart, 1.0f))
            {
                int newLines = m_lines.size();
                if (newLines != oldLines)
                {
                    FCVScrollBar vScrollBar = getVScrollBar();
                    if (vScrollBar != null)
                    {
                        vScrollBar.setPos(vScrollBar.getPos() + m_lineHeight * (newLines - oldLines));
                        invalidate();
                    }
                }
            }
            super.onCut();
        }
    }

    /*
    * 获取焦点方法
    */
    public void onGotFocus() {
        super.onGotFocus();
        m_showCursor = true;
        invalidate();
        startTimer(m_timerID, TICK);
    }

    /*
    * 丢失焦点方法
    */
    public void onLostFocus() {
        super.onLostFocus();
        stopTimer(m_timerID);
        m_isKeyDown = false;
        m_showCursor = false;
        invalidate();
    }

    /*
    * 触摸按下方法
    */
    public void onTouchDown(FCTouchInfo touchInfo) {
        super.onTouchDown(touchInfo);
        if(wordWrap() && allowDragScroll()){
            return;
        }
        if (touchInfo.m_firstTouch) {
            FCPoint mp = touchInfo.m_firstPoint;
            if(touchInfo.m_clicks == 1 && !isReadOnly()) {
                m_isClickDown = true;
                m_touchDownPoint = mp.clone();
            }
            //单击
            if (touchInfo.m_clicks == 1) {
                int sIndex = -1;
                int linesCount = m_lines.size();
                m_selectionLength = 0;
                m_startMovingIndex = -1;
                m_stopMovingIndex = -1;
                if (linesCount > 0) {
                    FCHScrollBar hScrollBar = getHScrollBar();
                    FCVScrollBar vScrollBar = getVScrollBar();
                    int scrollH = (hScrollBar != null && hScrollBar.isVisible()) ? hScrollBar.getPos() : 0;
                    int scrollV = (vScrollBar != null && vScrollBar.isVisible()) ? vScrollBar.getPos() : 0;
                    int x = mp.x + scrollH, y = mp.y + scrollV;
                    FCRectF lastRange = new FCRectF();
                    int rangeSize = m_ranges.size();
                    if (rangeSize > 0) {
                        lastRange = m_ranges.get(rangeSize - 1).clone();
                    }
                    for (int i = 0; i < linesCount; i++) {
                        FCWordLine line = m_lines.get(i);
                        for (int j = line.m_start; j <= line.m_end; j++) {
                            FCRectF rect = m_ranges.get(j).clone();
                            if (i == linesCount - 1) {
                                rect.bottom += 3;
                            }
                            if (y >= rect.top && y <= rect.bottom) {
                                float sub = (rect.right - rect.left) / 2;
                                if (j == line.m_end)
                                {
                                    if (i == linesCount - 1)
                                    {
                                        sIndex = line.m_end + 1;
                                    }
                                    else
                                    {
                                        sIndex = line.m_end;
                                    }
                                    break;
                                }
                                else
                                {
                                    if (sub > 0)
                                    {
                                        float nLeft = rect.left;
                                        if ((j == 0 && x <= nLeft + sub) || (x >= nLeft && x <= nLeft + sub))
                                        {
                                            sIndex = j;
                                            break;
                                        }
                                        else if (x >= nLeft + sub && x <= rect.right)
                                        {
                                            sIndex = j + 1;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (sIndex != -1) {
                            break;
                        }
                    }
                    if (sIndex == -1) {
                        if ((y >= lastRange.top && y <= lastRange.bottom && x > lastRange.right) || (y >= lastRange.bottom)) {
                            sIndex = rangeSize;
                        }
                    }
                }
                FCHost host = getNative().getHost();
                if (sIndex != -1)
                {
                    m_selectionStart = sIndex;
                }
                else
                {
                    m_selectionStart = 0;
                }
                m_startMovingIndex = m_selectionStart;
                m_stopMovingIndex = m_selectionStart;
                m_clickHighInfo = null;
                for(int h = 0; h < m_highLightInfos.size(); h++){
                    HighLightInfo highLightInfo = m_highLightInfos.get(h);
                    if (m_selectionStart >= highLightInfo.m_start && m_selectionStart <= highLightInfo.m_end)
                    {
                        m_clickHighInfo = highLightInfo;
                        break;
                    }
                }
            }
            //双击
            else if (touchInfo.m_clicks == 2) {
                if (!m_multiline)
                {
                    selectAll();
                }
                else
                {
                    if(m_clickHighInfo != null){
                        m_selectionStart = m_clickHighInfo.m_start;
                        if (m_clickHighInfo.m_end - m_clickHighInfo.m_start == 0)
                        {
                            m_selectionLength = 1;
                        }
                        else
                        {
                            m_selectionLength = m_clickHighInfo.m_end - m_clickHighInfo.m_start + 1;
                        }
                    }
                }
            }
        }
        m_isTouchDown = true;
        m_showCursor = true;
        startTimer(m_timerID, TICK);
        invalidate();
    }

    /*
    * 触摸移动方法
    */
    public void onTouchMove(FCTouchInfo touchInfo) {
        super.onTouchMove(touchInfo);
        FCPoint mp = touchInfo.m_firstPoint;
        if(m_isClickDown && touchInfo.m_clicks == 1){
            if(Math.abs(mp.x - m_touchDownPoint.x) > 10 || Math.abs(mp.y - m_touchDownPoint.y) > 10){
                m_isClickDown = false;
            }
        }
        int width = getWidth(), height = getHeight();
        if (mp.x < 0)
        {
            mp.x = 0;
        }
        else if (mp.x > width)
        {
            mp.x = width;
        }
        if (mp.y < 0)
        {
            mp.y = 0;
        }
        else if (mp.y > height)
        {
            mp.y = height;
        }
        if (m_isTouchDown) {
            int linesCount = m_lines.size();
            if (linesCount > 0) {
                int eIndex = -1;
                FCPoint point = mp.clone();
                FCHScrollBar hScrollBar = getHScrollBar();
                FCVScrollBar vScrollBar = getVScrollBar();
                if (vScrollBar != null && vScrollBar.isVisible())
                {
                    if (point.y <= 0)
                    {
                        vScrollBar.setPos(vScrollBar.getPos() - m_lineHeight);
                        update();
                    }
                    else if (point.y >= getHeight())
                    {
                        vScrollBar.setPos(vScrollBar.getPos() + m_lineHeight);
                        update();
                    }
                }
                int scrollH = (hScrollBar != null && hScrollBar.isVisible()) ? hScrollBar.getPos() : 0;
                int scrollV = (vScrollBar != null && vScrollBar.isVisible()) ? vScrollBar.getPos() : 0;
                int x = point.x + scrollH, y = point.y + scrollV;
                FCRectF lastRange = new FCRectF();
                int rangeSize = m_ranges.size();
                if (rangeSize > 0) {
                    lastRange = m_ranges.get(rangeSize - 1).clone();
                }
                for (int i = 0; i < linesCount; i++) {
                    FCWordLine line = m_lines.get(i);
                    for (int j = line.m_start; j <= line.m_end; j++) {
                        FCRectF rect = m_ranges.get(j).clone();
                        if (i == linesCount - 1) {
                            rect.bottom += 3;
                        }
                        if (eIndex == -1) {
                            float tTop = rect.top;
                            if(i == 0){
                                tTop = 0;
                            }
                            boolean isFirst = false;
                            if(j == line.m_start){
                                if(x < rect.left){
                                    isFirst = true;
                                }
                            }
                            if (y >= tTop && y <= rect.bottom) {
                                float sub = (rect.right - rect.left) / 2;
                                if (j == line.m_end)
                                {
                                    if (i == linesCount - 1)
                                    {
                                        eIndex = line.m_end + 1;
                                    }
                                    else
                                    {
                                        if(x > rect.right){
                                            eIndex = line.m_end + 1;
                                        }else{
                                            eIndex = line.m_end;
                                        }
                                    }
                                    break;
                                }
                                else
                                {
                                    if(isFirst){
                                        eIndex = j;
                                        break;
                                    }else {
                                        if (sub > 0) {
                                            float nLeft = rect.left;
                                            if ((j == 0 && x <= nLeft + sub) || (x >= nLeft && x <= nLeft + sub)) {
                                                eIndex = j;
                                                break;
                                            } else if (x >= nLeft + sub && x <= rect.right) {
                                                eIndex = j + 1;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (eIndex != -1) {
                        break;
                    }
                }
                if (eIndex != -1) {
                    m_stopMovingIndex = eIndex;
                }
                if(touchInfo.m_clicks == 1) {
                    if (m_startMovingIndex == m_stopMovingIndex) {
                        m_selectionStart = m_startMovingIndex;
                        m_selectionLength = 0;
                    } else {
                        m_selectionStart = m_startMovingIndex < m_stopMovingIndex ? m_startMovingIndex : m_stopMovingIndex;
                        m_selectionLength = Math.abs(m_startMovingIndex - m_stopMovingIndex);
                    }
                    if (m_selectionLength > 0)
                    {
                        int endIndex = m_selectionStart + m_selectionLength - 1;
                        if (endIndex >= (int)m_text.length())
                        {
                            endIndex = (int)m_text.length() - 1;
                        }
                        boolean isAllNextLine = false;
                        for (int i = m_selectionStart; i <= endIndex; i++)
                        {
                            if (m_text.charAt(i) != '\r' && m_text.charAt(i) != '\n')
                            {
                                isAllNextLine = true;
                            }
                        }
                        if (!isAllNextLine)
                        {
                            m_selectionLength = 0;
                        }
                    }
                }
                invalidate();
            }
        }
    }

    /**
     * 预处理触摸事件
     *
     * @param eventName 事件ID
     * @param touchInfo 触摸信息
     * @returns 状态
     */
    @Override
    public boolean onPreviewsTouchEvent(String eventName, FCTouchInfo touchInfo) {
        if (callPreviewsTouchEvents(FCEventID.PreviewsTouchEvent, eventName, touchInfo.clone())) {
            return true;
        }
        if (allowPreviewsEvent()) {
            if (eventName.equals(FCEventID.TouchDown)) {
                if (touchInfo.m_firstTouch)
                {
                    FCPoint mp = getTouchPoint();
                    FCPadding padding = getPadding();
                    if (mp.x < padding.left || (!wordWrap() && m_selectionLength == 0))
                    {
                        setAllowDragScroll(true);
                    }
                    else
                    {
                        setAllowDragScroll(false);
                    }
                }
                onDragScrollstart();
            } else if (eventName.equals(FCEventID.TouchMove)) {
                onDragScrolling();
            } else if (eventName.equals(FCEventID.TouchUp)) {
                boolean state = m_isDragScrolling;
                onDragScrollEnd();
                if (state && !m_isDragScrolling2) {
                    return false;
                }
            }
        }
        return false;
    }
    
    /*
    * 触摸抬起方法
    */
    public void onTouchUp(FCTouchInfo touchInfo) {
        m_isTouchDown = false;
        super.onTouchUp(touchInfo);
        if(wordWrap() && allowDragScroll()){
            setAllowDragScroll(false);
            if (m_iTextBoxZoom != null && m_iTextBoxZoom.isVisible())
            {
                m_iTextBoxZoom.getVScrollBar().setPos(getVScrollBar().getPos() / 5);
                m_iTextBoxZoom.update();
                m_iTextBoxZoom.invalidate();
            }
            return;
        }
        if (m_iTextBoxZoom != null && m_iTextBoxZoom.isVisible())
        {
            m_iTextBoxZoom.getVScrollBar().setPos(getVScrollBar().getPos() / 5);
            m_iTextBoxZoom.update();
            m_iTextBoxZoom.invalidate();
        }
    }

    public TextBoxZoom m_iTextBoxZoom;

    private FindReplaceDiv m_findReplaceDiv;

    private boolean m_showZoom;

    /*
    * 显示缩放
    */
    public boolean showZoom()
    {
        return m_showZoom;
    }

    /*
    * 设置显示缩放
    */
    public void setShowZoom(boolean value){
        m_showZoom = value;
        if (value)
        {
            FCPadding padding = getPadding();
            padding.right = 80;
            setPadding(padding);
            if (m_iTextBoxZoom == null)
            {
                m_iTextBoxZoom = new TextBoxZoom();
                m_iTextBoxZoom.m_iTextBox = this;
                addView(m_iTextBoxZoom);
            }
            if (m_findReplaceDiv == null)
            {
                m_findReplaceDiv = new FindReplaceDiv();
                m_findReplaceDiv.m_iTextBox = this;
                addView(m_findReplaceDiv);
            }
        }else{
            FCPadding padding = getPadding();
            padding.right = 0;
            setPadding(padding);
            if (m_iTextBoxZoom == null)
            {
                m_iTextBoxZoom = new TextBoxZoom();
                m_iTextBoxZoom.m_iTextBox = this;
                addView(m_iTextBoxZoom);
                m_iTextBoxZoom.setVisible(false);
            }
            if (m_findReplaceDiv == null)
            {
                m_findReplaceDiv = new FindReplaceDiv();
                m_findReplaceDiv.m_iTextBox = this;
                addView(m_findReplaceDiv);
            }
        }
    }

    private boolean m_allowPaint = true;

    /*
    * 允许绘图
    */
    public boolean allowPaint()
    {
        return m_allowPaint;
    }

    /*
    * 设置允许绘图
    */
    public void setAllowPaint(boolean value) {
        m_allowPaint = value;
    }

    public String m_autoHighLightType = "";

    public int m_style = MyColor.m_style;
    
    /*
    * 文本输入方法
    */
    public void onChar(char ch)
    {
        if (!m_readOnly)
        {
            super.onChar(ch);
            FCHost host = getNative().getHost();
            if (!host.isKeyPress((char)0x11))
            {
                int oldLines = m_lines.size();
                if (ch != 8)
                {
                    if (m_multiline)
                    {
                        iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
                        String iWord = new Character(ch).toString();
                        if (ch == 13)
                        {
                            iWord = iWord + "\n";
                        }
                        insertWord(iWord);
                        onTextChanged();
                        if (m_textChanged)
                        {
                            m_undoStack.push(newReoUndoInfo);
                        }
                    }
                    else
                    {
                        if (ch != 13)
                        {
                            iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
                            String iWord = new Character(ch).toString();
                            insertWord(iWord);
                            onTextChanged();
                            if (m_textChanged)
                            {
                                m_undoStack.push(newReoUndoInfo);
                            }
                        }
                    }
                }
                invalidate();
                if (!isLineVisible(m_selectionStart, 1.0f))
                {
                    int newLines = m_lines.size();
                    if (newLines != oldLines)
                    {
                        FCVScrollBar vScrollBar = getVScrollBar();
                        if (vScrollBar != null)
                        {
                            vScrollBar.setPos(vScrollBar.getPos() + m_lineHeight * (newLines - oldLines));
                            invalidate();
                        }
                    }
                }
            }
        }
    }
    
    /*
    * 键盘方法
    */
    public void onKeyDown(char key)
    {
        m_isKeyDown = true;
        FCHost host = getNative().getHost();
        if (host.isKeyPress((char)0x11))
        {
            super.onKeyDown(key);
            //全选
            if (key == 65)
            {
                selectAll();
            }
            //重做
            else if (key == 89)
            {
                redo();
            }
            //撤销
            else if (key == 90)
            {
                undo();
            }
        }
        else
        {
            if (key >= 35 && key <= 40)
            {
                if (key == 38 || key == 40)
                {
                    callKeyEvents("onkeydown", key);
                    if (m_lines.size() > 1)
                    {
                        int offset = 0;
                        //光标向上移动
                        if (key == 38)
                        {
                            cursorUp();
                            if (!isLineVisible(m_stopMovingIndex, 0.9))
                            {
                                offset = -m_lineHeight;
                            }
                        }
                        //光标向下移动
                        else if (key == 40)
                        {
                            cursorDown();
                            if (!isLineVisible(m_stopMovingIndex, 0.9))
                            {
                                offset = m_lineHeight;
                            }
                        }
                        FCVScrollBar vScrollBar = getVScrollBar();
                        if (vScrollBar != null && vScrollBar.isVisible())
                        {
                            vScrollBar.setPos(vScrollBar.getPos() + offset);
                            vScrollBar.update();
                        }
                    }
                }
                else
                {
                    super.onKeyDown(key);
                    //光标移动到最右端
                    if (key == 35)
                    {
                        cursorEnd();
                    }
                    //光标移动到最左端
                    else if (key == 36)
                    {
                        cursorHome();
                    }
                    //光标向左移动
                    else if (key == 37)
                    {
                        cursorLeft();
                    }
                    //光标向右移动
                    else if (key == 39)
                    {
                        cursorRight();
                    }
                }
            }
            else
            {
                super.onKeyDown(key);
                //取消焦点
                if (key == 27)
                {
                    setFocused(false);
                }
                //删除
                else if (key == 8 || key == 46)
                {
                    if (!m_readOnly)
                    {
                        int oldLines = m_lines.size();
                        iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
                        deleteWord();
                        onTextChanged();
                        if (m_textChanged)
                        {
                            m_undoStack.push(newReoUndoInfo);
                        }
                        invalidate();
                        if (!isLineVisible(m_selectionStart, 1.0f))
                        {
                            int newLines = m_lines.size();
                            if (newLines != oldLines)
                            {
                                FCVScrollBar vScrollBar = getVScrollBar();
                                if (vScrollBar != null)
                                {
                                    vScrollBar.setPos(vScrollBar.getPos() + m_lineHeight * (newLines - oldLines));
                                    invalidate();
                                }
                            }
                        }
                    }
                }
            }
        }
        invalidate();
    }

    /*
    * 键盘抬起方法
    */
    public void onKeyUp(char key)
    {
        super.onKeyUp(key);
        FCHost host = getNative().getHost();
        if (!host.isKeyPress((char)0x10) && !m_isTouchDown)
        {
            m_startMovingIndex = m_selectionStart;
            m_stopMovingIndex = m_selectionStart;
        }
        m_isKeyDown = false;
    }

    /*
    * 绘制背景方法
    */
    public void onPaintBackground(FCPaint paint, FCRect clipRect)
    {
        FCRect rect = new FCRect(0, 0, getWidth(), getHeight());
        //绘制背景色
        paint.fillRoundRect(getPaintingBackColor(), rect, getCornerRadius());
        //绘制背景图
        String bkImage = getPaintingBackImage();
        if (bkImage != null && bkImage.length() > 0)
        {
            int oX = rect.left + (rect.right - rect.left) / 2;
            int oY = rect.top + (rect.bottom - rect.top) / 2;
            int r = 50;
            if (r > (rect.right - rect.left) / 2)
            {
                r = (rect.right - rect.left) / 2;
            }
            if (r > (rect.bottom - rect.top) / 2)
            {
                r = (rect.bottom - rect.top) / 2;
            }
            FCRect iRect = new FCRect(oX - r, oY - r, oX + r, oY + r);
            paint.drawImage(bkImage, iRect);
        }
    }

    private HashMap<Character, FCSizeF> m_textSizes = new HashMap<Character, FCSizeF>();

    /*
    * 重绘前景方法
    */
    public void onPaintForeground(FCPaint paint, FCRect clipRect) {
        if (!m_allowPaint)
        {
            return;
        }
        if (m_autoHighLightType != null && m_autoHighLightType.length() > 0)
        {
            if (m_style != MyColor.m_style)
            {
                if (m_autoHighLightType.equals("Python"))
                {
                    AutoHighLightPython.analysis(this);
                }
                m_style = MyColor.m_style;
            }
        }
        int width = getWidth(), height = getHeight();
        if (width > 0 && height > 0) {
            int lineHeight = m_multiline ? m_lineHeight : height;
            FCPadding padding = getPadding();
            FCRect leftRect = new FCRect(0, 0, padding.left - 5, height);
            FCHost host = getNative().getHost();
            FCRect rect = new FCRect(0, 0, width, height);
            FCFont font = getFont();
            FCSizeF tSize = paint.textSizeF(" ", font, -1);
            FCHScrollBar hScrollBar = getHScrollBar();
            FCVScrollBar vScrollBar = getVScrollBar();
            int vWidth = (vScrollBar != null) ? vScrollBar.getWidth() : 0;
            int scrollH = ((hScrollBar != null && hScrollBar.isVisible()) ? hScrollBar.getPos() : 0);
            int scrollV = ((vScrollBar != null && vScrollBar.isVisible()) ? vScrollBar.getPos() : 0);
            float strX = padding.left + 1;
            //绘制文字
            String text = getText();
            if (text == null) {
                text = "";
            }
            int length = text.length();
            FCSizeF bSize = paint.textSizeF("0", font, -1);
            if (m_textChanged || m_changeID != FCNative.getChangeID())
            {
                if (m_changeID != FCNative.getChangeID())
                {
                    m_textSizes.clear();
                }
                m_changeID = FCNative.getChangeID();
                int line = 0, count = 0;
                //获取绘制区域
                m_textChanged = !m_textChanged;
                m_lines.clear();
                m_ranges.clear();
                m_wordsSize.clear();
                int realLine = 1;
                for (int i = 0; i < length; i++) {
                    if (i == 0) {
                        count = 0;
                        line++;
                        strX = padding.left + 1;
                        FCWordLine wl = new FCWordLine(i, i);
                        wl.m_line = realLine;
                        m_lines.add(wl);
                    }
                    boolean isNextLine = false;
                    char ch = text.charAt(i);
                    String dch = (new Character(ch)).toString();
                    //制表符
                    if (ch == 9) {
                        int addCount = 4 - count % 4;
                        tSize.cx = bSize.cx * addCount;
                        tSize.cy = bSize.cy;
                        count += addCount;
                    }
                    else {
                        //密码替换
                        if (m_passwordChar != 0) {
                            dch = (new Character(m_passwordChar)).toString();
                        }
                        if (m_textSizes.containsKey(ch))
                        {
                            tSize = m_textSizes.get(ch).clone();
                        }
                        else
                        {
                            tSize = paint.textSizeF(dch, font, -1);
                            m_textSizes.put(ch, tSize.clone());
                        }
                        if (ch == 10 || ch == 13) {
                            tSize.cx = bSize.cx;
                        }
                        count++;
                        if (ch == ' ')
                        {
                            tSize.cx += 3;
                        }
                    }
                    //判断是否多行
                    if (m_multiline) {
                        if (ch == 13)
                        {
                            //修改
                            if (i < length - 1 && text.charAt(i + 1) == 10)
                            {

                            }
                            else
                            {
                                isNextLine = true;
                                realLine++;
                            }
                            tSize.cx = 0;
                            count = 0;
                        }
                        else if (ch == 10)
                        {
                            isNextLine = true;
                            realLine++;
                            count = 0;
                            tSize.cx = 0;
                        }
                        else {
                            //是否自动换行
                            if (m_wordWrap) {
                                if (strX + tSize.cx > width - vWidth - padding.right - tSize.cx) {
                                    if (i != length - 1 && (text.charAt(i + 1) == '\r' || text.charAt(i + 1) == '\n'))
                                    {

                                    }
                                    else
                                    {
                                        isNextLine = true;
                                    }
                                }
                            }
                        }
                        //换行
                        FCWordLine wl = new FCWordLine(m_lines.get(line - 1).m_start, i);
                        wl.m_line = m_lines.get(line - 1).m_line;
                        m_lines.set(line - 1, wl);
                    }
                    else {
                        FCWordLine wl = new FCWordLine(m_lines.get(line - 1).m_start, i);
                        wl.m_line = m_lines.get(line - 1).m_line;
                        m_lines.set(line - 1, wl);
                    }
                    if (ch > 1000) {
                        tSize.cx += 1;
                    }
                    //保存区域
                    FCRectF rangeRect = new FCRectF(strX, padding.top + (line - 1) * lineHeight, strX + tSize.cx, padding.top + line * lineHeight);
                    m_ranges.add(rangeRect);
                    m_wordsSize.add(tSize);
                    strX = rangeRect.right;
                    if (isNextLine)
                    {
                        line++;
                        strX = padding.left + 1;
                        if (i != length - 1)
                        {
                            FCWordLine wl = new FCWordLine(i + 1, i + 1);
                            wl.m_line = realLine;
                            m_lines.add(wl);
                        }
                    }
                }
                //从右向左
                if (m_rightToLeft) {
                    int lcount = m_lines.size();
                    for (int i = 0; i < lcount; i++) {
                        FCWordLine ln = m_lines.get(i);
                        float lw = width - vWidth - (m_ranges.get(ln.m_end).right - m_ranges.get(ln.m_start).left) - 2;
                        if (lw > 0) {
                            for (int j = ln.m_start; j <= ln.m_end; j++) {
                                FCRectF rangeRect = m_ranges.get(j);
                                rangeRect.left += lw;
                                rangeRect.right += lw;
                                m_ranges.set(j, rangeRect);
                            }
                        }
                    }
                }
                update();
            }
            scrollH += m_offsetX;
            FCRect tempRect = new FCRect();
            int rangesSize = m_ranges.size();
            int offsetX = m_offsetX;
            //判断当前索引是否可见
            if (!m_multiline) {
                FCRectF firstRange = new FCRectF();
                FCRectF lastRange = new FCRectF();
                if (rangesSize > 0) {
                    firstRange = m_ranges.get(0).clone();
                    lastRange = m_ranges.get(rangesSize - 1).clone();
                }
                scrollH -= offsetX;
                //居中
                if (m_textAlign == FCHorizontalAlign.Center) {
                    offsetX = -(int)(width - padding.right - (lastRange.right - firstRange.left)) / 2;
                }
                //远离
                else if (m_textAlign == FCHorizontalAlign.Right) {
                    offsetX = -(int)(width - padding.right - (lastRange.right - firstRange.left) - 3);
                }
                //居左
                else {
                    //显示超出边界
                    if (lastRange.right > width - padding.right) {
                        //获取总是可见的索引
                        int alwaysVisibleIndex = m_selectionStart + m_selectionLength;
                        if (m_startMovingIndex != -1) {
                            alwaysVisibleIndex = m_startMovingIndex;
                        }
                        if (m_stopMovingIndex != -1) {
                            alwaysVisibleIndex = m_stopMovingIndex;
                        }
                        if (alwaysVisibleIndex > rangesSize - 1) {
                            alwaysVisibleIndex = rangesSize - 1;
                        }
                        if (alwaysVisibleIndex != -1) {
                            FCRectF alwaysVisibleRange = m_ranges.get(alwaysVisibleIndex).clone();
                            int cw = width - padding.left - padding.right;
                            if (alwaysVisibleRange.left - offsetX > cw - 10) {
                                offsetX = (int)alwaysVisibleRange.right - cw + 3;
                                if (offsetX < 0) {
                                    offsetX = 0;
                                }
                            }
                            else if (alwaysVisibleRange.left - offsetX < 10) {
                                offsetX -= (int)bSize.cx * 4;
                                if (offsetX < 0) {
                                    offsetX = 0;
                                }
                            }
                            if (offsetX > lastRange.right - cw) {
                                offsetX = (int)lastRange.right - cw + 3;
                            }
                        }
                    }
                    //显示未超出边界
                    else {
                        if (m_textAlign == FCHorizontalAlign.Right) {
                            offsetX = -(int)(width - padding.right - (lastRange.right - firstRange.left) - 3);
                        }
                        else {
                            offsetX = 0;
                        }
                    }
                }
                m_offsetX = offsetX;
                scrollH += m_offsetX;
            }
            int lineCount = m_lines.size();
            //绘制矩形和字符
            ArrayList<FCRectF> selectedRanges = new ArrayList<FCRectF>();

            ArrayList<FCRectF> drawRanges = new ArrayList<FCRectF>();
            ArrayList<Long> drawColors = new ArrayList<Long>();
            ArrayList<Character> drawWords = new ArrayList<Character>();
            int hiPos = 0, hiPos2 = 0;
            HighLightInfo highLightInfo = null;
            if (m_highLightInfos.size() > 0)
            {
                highLightInfo = m_highLightInfos.get(hiPos);
            }
            HighLightInfo findReplaceHLi = null;
            if (m_findReplaceDiv != null && m_findReplaceDiv.isVisible())
            {
                if (m_findReplaceDiv.m_highLightInfos.size() > 0)
                {
                    findReplaceHLi = m_findReplaceDiv.m_highLightInfos.get(hiPos2);
                }
            }
            int breakState = 0;
            int its = 0;
            ArrayList<Integer> drawLines = new ArrayList<Integer>();
            for (int i = 0; i < lineCount; i++) {
                FCWordLine line = m_lines.get(i);
                boolean lineVisible = isLineVisible(line.m_start, 0.1f);
                int spos = 0;
                if (text.charAt(line.m_start) == '\r') {
                    spos += 1;
                }
                if (line.m_start != line.m_end && text.charAt(line.m_start + 1) == '\n') {
                    spos += 1;
                }
                int its2 = 0;
                for (int j = line.m_start; j <= line.m_end; j++) {
                    if (highLightInfo != null) {
                        if (j > highLightInfo.m_end) {
                            hiPos++;
                            if (hiPos < m_highLightInfos.size()) {
                                highLightInfo = m_highLightInfos.get(hiPos);
                            }
                        }
                    }
                    if (findReplaceHLi != null)
                    {
                        if (j > findReplaceHLi.m_end)
                        {
                            hiPos2++;
                            if (hiPos2 < m_findReplaceDiv.m_highLightInfos.size())
                            {
                                findReplaceHLi = m_findReplaceDiv.m_highLightInfos.get(hiPos2);
                            }
                        }
                    }
                    if (its2 == 0) {
                        char ch = text.charAt(j);
                        if (ch != 9) {
                            //密码替换
                            if (m_passwordChar > 0) {
                                ch = m_passwordChar;
                            }
                        }
                        //获取绘制区域
                        FCRectF rangeRect = m_ranges.get(j).clone();
                        rangeRect.left -= scrollH;
                        rangeRect.top -= scrollV;
                        rangeRect.right -= scrollH;
                        rangeRect.bottom -= scrollV;
                        FCRect rRect = new FCRect(rangeRect.left, rangeRect.top + (lineHeight - m_wordsSize.get(j).cy) / 2,
                                rangeRect.right, rangeRect.top + (lineHeight + m_wordsSize.get(j).cy) / 2);
                        if (rRect.right == rRect.left) {
                            rRect.right = rRect.left + 1;
                        }
                        float oldRangeTop = rangeRect.top;
                        rangeRect.top = oldRangeTop + (lineHeight - bSize.cy) / 2;
                        rangeRect.bottom = oldRangeTop + (lineHeight + bSize.cy) / 2;
                        //绘制文字
                        if (lineVisible) {
                            its = 1;
                            if (m_selectionLength > 0) {
                                if (j >= m_selectionStart && j < m_selectionStart + m_selectionLength) {
                                    selectedRanges.add(rangeRect);
                                    //continue;
                                }
                            }
                            if (j == line.m_start && leftRect.right > 10) {
                                //paint.drawText(FCTran.intToStr(i + 1), MyColor.USERCOLOR28, font, new FCRect(4, rangeRect.top + 1, 30, rangeRect.bottom + 1), -1);
                                drawLines.add(i);
                            }
                            if (findReplaceHLi != null && j >= findReplaceHLi.m_start && j <= findReplaceHLi.m_end)
                            {
                                if (hiPos2 == m_findReplaceDiv.m_selectedIndex)
                                {
                                    paint.fillRect(MyColor.USERCOLOR2, new FCRect(rangeRect.left, rangeRect.top, rangeRect.right, rangeRect.bottom));
                                }
                                else
                                {
                                    paint.fillRect(MyColor.USERCOLOR19, new FCRect(rangeRect.left, rangeRect.top, rangeRect.right, rangeRect.bottom));
                                }
                            }
                            if (highLightInfo != null && j >= highLightInfo.m_start && j <= highLightInfo.m_end) {
                                drawColors.add(highLightInfo.m_textColor);
                                drawRanges.add(rangeRect);
                                drawWords.add(ch);
                            } else {
                                drawRanges.add(rangeRect);
                                drawColors.add(getPaintingTextColor());
                                drawWords.add(ch);
                            }
                        } else {
                            its2 = 1;
                            if (its == 1) {
                                its = 2;
                                break;
                            }
                        }
                    }
                }
                if (its == 2) {
                    break;
                }
            }
            //绘制选中区域
            int selectedRangesSize = selectedRanges.size();
            if (selectedRangesSize > 0 && !m_isCopy) {
                int sIndex = 0;
                for (int i = 0; i < selectedRangesSize; i++){
                    FCRectF rRect = selectedRanges.get(i);
                    FCRectF sRect = selectedRanges.get(sIndex);
                    boolean newLine = false;
                    if(i < selectedRangesSize - 1){
                        newLine = selectedRanges.get(i + 1).top != rRect.top;
                        if(newLine){
                            sIndex = i + 1;
                        }
                    }
                    if (newLine || i == selectedRangesSize - 1){
                        FCRect unionRect = new FCRect(sRect.left, sRect.top, rRect.right + 1, sRect.bottom);
                        FCRect subClipRect = unionRect.clone();
                        if (subClipRect.top < 0)
                        {
                            subClipRect.top = 0;
                        }
                        if (subClipRect.bottom > height)
                        {
                            subClipRect.bottom = height;
                        }
                        paint.setClip(subClipRect);
                        for (int u = unionRect.left - 20; u < unionRect.right + 20; u += 10)
                        {
                            if (m_showCursor)
                            {
                                paint.drawLine(MyColor.USERCOLOR62, 5, 0, u + 5, unionRect.top + 3, u + 10 + 5, unionRect.bottom - 3);
                            }
                            else
                            {
                                paint.drawLine(MyColor.USERCOLOR62, 5, 0, u, unionRect.top + 3, u + 10, unionRect.bottom - 3);
                            }
                        }
                        paint.setClip(clipRect);

                        paint.drawRoundRect(MyColor.USERCOLOR36, 1, 0, unionRect, Math.min((unionRect.right - unionRect.left) / 2, 2));
                    }
                }
                selectedRanges.clear();
            }
            m_isCopy = false;
            /*int drawRangesSize = drawRanges.size();
            for (int i = 0; i < drawRangesSize; i++)
            {
                String drawWord = new Character(drawWords.get(i)).toString();
                paint.drawText(drawWord, drawColors.get(i), font, drawRanges.get(i), -1);
            }*/
            int drawRangesSize = (int)drawRanges.size();
            if(drawRangesSize > 0){
                Long lastColor = drawColors.get(0);
                int lastI = 0;
                for (int i = 0; i < drawRangesSize; i++)
                {
                    if((i < drawRangesSize - 1 && (lastColor != drawColors.get(i + 1) || drawRanges.get(i + 1).top > drawRanges.get(i).top)) || i == drawRangesSize - 1){
                        String newStr = "";
                        int cLen = 0;
                        for(int t = lastI; t <= i; t++){
                            if(drawWords.get(t) == '\r' || drawWords.get(t) == '\n'){
                            }else{
                                newStr += drawWords.get(t);
                                if(drawWords.get(t) != '\t' && drawWords.get(t) != ' ') {
                                    cLen++;
                                }else if (drawWords.get(t) == '\t')
                                {
                                    FCRect backRect = new FCRect((int)(drawRanges.get(t).left + 1), (int)(drawRanges.get(t).bottom - 2), (int)(drawRanges.get(t).right - 1), (int)(drawRanges.get(t).bottom - 1));
                                    paint.fillRect(MyColor.USERCOLOR6, backRect);
                                }
                            }
                        }
                        if(cLen > 0) {
                            FCRectF beginRange = drawRanges.get(lastI);
                            FCRectF endRange = drawRanges.get(i);
                            FCRectF newRect = new FCRectF(beginRange.left, beginRange.top, endRange.right, endRange.bottom);
                            paint.drawText(newStr, lastColor, font, newRect, -1);
                        }
                        if(i < drawRangesSize - 1) {
                            lastColor = drawColors.get(i + 1);
                            lastI = i + 1;
                        }
                    }
                   /*wchar_t str[2] ={0};
                   str[0] = drawWords[i];
                   str[1] = '\0';*/
                    //paint->drawText(str, drawColors[i], font, drawRanges[i], -1);
                }
            }
            drawWords.clear();
            drawColors.clear();
            drawRanges.clear();
            //绘制光标
            if ((isFocused() && !m_readOnly && m_selectionLength == 0 && (m_isKeyDown || m_showCursor))) {
                int index = m_selectionStart;
                if (index < 0) {
                    index = 0;
                }
                if (index > length) {
                    index = length;
                }
                //获取光标的位置
                int cursorX = offsetX;
                if (cursorX < padding.left)
                {
                    cursorX = padding.left;
                }
                int cursorY = 0;
                if (length > 0) {
                    int showIndex = index - 1;
                    if (showIndex != -1)
                    {
                        boolean nextLine = false;
                        if (m_multiline)
                        {
                            for (int i = 0; i < m_lines.size(); i++)
                            {
                                FCWordLine wordLine = m_lines.get(i);
                                if (showIndex == wordLine.m_end)
                                {
                                    if (i != m_lines.size() - 1)
                                    {
                                        nextLine = true;
                                        cursorX = (int)Math.floor(m_ranges.get(showIndex + 1).left);
                                        cursorY = (int)Math.ceil(m_ranges.get(showIndex + 1).top);
                                    }
                                    else
                                    {
                                        if (text.charAt(showIndex) == '\r' || text.charAt(showIndex) == '\n')
                                        {
                                            nextLine = true;
                                            cursorX = (int)Math.floor(m_ranges.get(0).left);
                                            cursorY = (int)Math.ceil(m_ranges.get(showIndex).top + m_lineHeight);
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                        if (!nextLine)
                        {
                            cursorX = (int)Math.floor(m_ranges.get(showIndex).right);
                            cursorY = (int)Math.ceil(m_ranges.get(showIndex).top);
                        }
                    }
                    else
                    {
                        cursorX = (int)Math.floor(m_ranges.get(0).left);
                        cursorY = (int)Math.ceil(m_ranges.get(0).top);
                    }
                    cursorY += lineHeight / 2 - (int)tSize.cy / 2;
                }
                else {
                    cursorY = padding.top + lineHeight / 2 - (int)tSize.cy / 2;
                }
                m_cursorPoint.x = cursorX - scrollH;
                m_cursorPoint.y = cursorY - scrollV;
                //绘制闪烁光标
                if (m_isKeyDown || m_showCursor) {
                    FCRect cRect = new FCRect(cursorX - scrollH, cursorY - scrollV, cursorX - scrollH + 2, cursorY + tSize.cy - scrollV);
                    paint.fillRect(getTextColor(), cRect);
                }
            }
            else {
                if (!isFocused() && text.length() == 0) {
                    if (m_tempText != null && m_tempText.length() > 0) {
                        FCSize pSize = paint.textSize(m_tempText, font, -1);
                        FCRect pRect = new FCRect();
                        pRect.left = padding.left;
                        pRect.top = (lineHeight - pSize.cy) / 2;
                        pRect.right = pRect.left + pSize.cx;
                        pRect.bottom = pRect.top + pSize.cy;
                        paint.drawText(m_tempText, m_tempTextColor, font, pRect, -1);
                    }
                }
            }
            if (leftRect.right > 10)
            {
                if(MyColor.m_style != 1){
                    paint.fillRect(MyColor.USERCOLOR79, leftRect);
                }else{
                    paint.fillRect(getBackColor(), leftRect);
                }
                paint.drawLine(FCColor.Border, 1, 0, leftRect.right, 0, leftRect.right, height);
                for (int i = 0; i < drawLines.size(); i++)
                {
                    FCWordLine wordLine = m_lines.get(drawLines.get(i));
                    if (i == 0 || wordLine.m_line != m_lines.get(drawLines.get(i) - 1).m_line)
                    {
                        FCRectF rangeRect = m_ranges.get(wordLine.m_start);
                        FCRect iRect = new FCRect(4, (int)(rangeRect.top + 1) - scrollV, 30, (int)(rangeRect.bottom + 1) - scrollV);
                        paint.drawText(FCTran.intToStr(wordLine.m_line), MyColor.USERCOLOR20, font, iRect, -1);
                    }
                }
                drawLines.clear();
            }
        }
    }

    /*
    * 粘贴方法
    */
    public void onPaste() {
        if (!m_readOnly) {
            FCHost host = getNative().getHost();
            String insert = host.paste();
            if (insert != null && insert.length() > 0) {
                int oldLines = m_lines.size();
                iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
                insertWord(insert);
                onTextChanged();
                if (m_textChanged) {
                    boolean replaceOld = false;
                    if (m_undoStack.size() > 1)
                    {
                        iFCRedoUndoInfo last = m_undoStack.peek();
                        if (Math.abs(last.m_time - newReoUndoInfo.m_time) < 2)
                        {
                            last.m_text = newReoUndoInfo.m_text;
                            last.m_selectionStart = newReoUndoInfo.m_selectionStart;
                            last.m_selectionLength = newReoUndoInfo.m_selectionLength;
                            replaceOld = true;
                        }
                    }
                    if (!replaceOld)
                    {
                        m_undoStack.push(newReoUndoInfo);
                    }
                }
                invalidate();
                if (!isLineVisible(m_selectionStart, 1.0f))
                {
                    int newLines = m_lines.size();
                    if (newLines != oldLines)
                    {
                        FCVScrollBar vScrollBar = getVScrollBar();
                        if (vScrollBar != null)
                        {
                            vScrollBar.setPos(vScrollBar.getPos() + m_lineHeight * (newLines - oldLines));
                            invalidate();
                        }
                    }
                }
                update();
                super.onPaste();
            }
        }
    }

    /*
    * 被使用TAB切换方法
    */
    public void onTabStop() {
        super.onTabStop();
        if (!m_multiline) {
            if (getText() != null)
            {
                int textSize = getText().length();
                if (textSize > 0) {
                    m_selectionStart = 0;
                    m_selectionLength = textSize;
                    onTimer(m_timerID);
                }
            }
        }
    }

    /*
    * 尺寸改变时触发
    */
    public void onSizeChanged() {
        super.onSizeChanged();
        if (m_wordWrap) {
            m_textChanged = true;
            invalidate();
        }
    }

    /*
    * 文字改变方法
    */
    public void onTextChanged() {
        m_textChanged = true;
        if (m_autoHighLightType.equals("Python"))
        {
            AutoHighLightPython.analysis(this);
        }
        super.onTextChanged();
    }

    /*
    * 秒表回调方法
    */
    public void onTimer(int timerID)
    {
        super.onTimer(timerID);
        if (m_timerID == timerID)
        {
            if (isVisible() && isFocused() && !m_textChanged)
            {
                //m_showCursor = !m_showCursor;
                //invalidate();
            }
        }
    }

    /*
    * 重复
    */
    public void redo()
    {
        if (canRedo())
        {
            iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
            m_undoStack.push(newReoUndoInfo);
            iFCRedoUndoInfo oldRedo = m_redoStack.pop();
            setText(oldRedo.m_text);
            setSelectionStart(oldRedo.m_selectionStart);
            setSelectionLength(oldRedo.m_selectionLength);
        }
    }

    /*
    * 全选
    */
    public void selectAll() {
        m_selectionStart = 0;
        if (getText() != null)
        {
            m_selectionLength = getText().length();
        }
        else {
            m_selectionLength = 0;
        }
    }

    /*
    * 设置移动索引
    */
    private void setMovingIndex(int sIndex, int eIndex) {
        int textSize = getText().length();
        if (textSize > 0) {
            if (sIndex < 0) {
                sIndex = 0;
            }
            if (sIndex > textSize) {
                sIndex = textSize;
            }
            if (eIndex < 0) {
                eIndex = 0;
            }
            if (eIndex > textSize) {
                eIndex = textSize;
            }
            m_startMovingIndex = sIndex;
            m_stopMovingIndex = eIndex;
            m_selectionStart = Math.min(m_startMovingIndex, m_stopMovingIndex);
            m_selectionLength = Math.abs(m_startMovingIndex - m_stopMovingIndex);
        }
    }

    /*
    * 设置属性
    */
    public void setAttribute(String name, String value) {
        if (name.equals("lineheight")) {
            setLineHeight(FCTran.strToInt(value));
        } else if (name.equals("multiline")) {
            setMultiline(FCTran.strToBool(value));
        } else if (name.equals("passwordchar")) {
            setPasswordChar(value.toCharArray()[0]);
        } else if (name.equals("readonly")) {
            setReadOnly(FCTran.strToBool(value));
        } else if (name.equals("righttoleft")) {
            setRightToLeft(FCTran.strToBool(value));
        } else if (name.equals("selectionbackcolor")) {
            setSelectionBackColor(FCTran.strToColor(value));
        } else if (name.equals("selectiontextcolor")) {
            setSelectionTextColor(FCTran.strToColor(value));
        } else if (name.equals("temptext")) {
            setTempText(value);
        } else if (name.equals("temptextcolor")) {
            setTempTextColor(FCTran.strToColor(value));
        } else if (name.equals("textalign")) {
            setTextAlign(FCTran.strToHorizontalAlign(value));
        } else if (name.equals("wordwrap")) {
            setWordWrap(FCTran.strToBool(value));
        } else {
            super.setAttribute(name, value);
        }
    }

    /*
    * 撤销
    */
    public void undo() {
        if (canUndo())
        {
            if (getText() != null)
            {
                iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
                m_redoStack.push(newReoUndoInfo);
            }
            iFCRedoUndoInfo oldUndo = m_undoStack.pop();
            setText(oldUndo.m_text);
            setSelectionStart(oldUndo.m_selectionStart);
            setSelectionLength(oldUndo.m_selectionLength);
        }
    }

    /*
    * 更新布局方法
    */
    public void update() {
        FCNative inative = getNative();
        if (inative != null) {
            FCVScrollBar vScrollBar = getVScrollBar();
            if (vScrollBar != null) {
                vScrollBar.setLineSize(m_lineHeight);
            }
        }
        if (m_iTextBoxZoom != null)
        {
            int width = getWidth(), height = getHeight();
            m_iTextBoxZoom.setLocation(new FCPoint(width - 75, 0));
            m_iTextBoxZoom.setSize(new FCSize(75, height));
        }
        if (m_findReplaceDiv != null)
        {
            int width = getWidth(), height = getHeight();
            m_findReplaceDiv.setLocation(new FCPoint(0, 0));
            m_findReplaceDiv.setSize(new FCSize(width, m_findReplaceDiv.getHeight()));
        }
        super.update();
    }

    /*
    * 显示查找
    */
    public void showFind()
    {
        if (m_findReplaceDiv != null)
        {
            if(m_findReplaceDiv.isVisible()){
                m_findReplaceDiv.setVisible(false);
                m_findReplaceDiv.showOrHideReplace(false);
                FCPadding padding = getPadding();
                padding.top = 0;
                setPadding(padding);
                m_textChanged = true;
            }else {
                m_findReplaceDiv.setVisible(true);
                m_findReplaceDiv.reset();
                m_findReplaceDiv.m_input.setFocused(true);
                m_findReplaceDiv.showOrHideReplace(false);
                FCPadding padding = getPadding();
                padding.top = 30;
                m_findReplaceDiv.m_choose.setSelectedIndex(0);
                setPadding(padding);
                m_textChanged = true;
            }
            update();
            invalidate();
        }
    }

    /*
    * 显示替换
    */
    public void showReplace()
    {
        if (m_findReplaceDiv != null)
        {
            m_findReplaceDiv.setVisible(true);
            m_findReplaceDiv.reset();
            m_findReplaceDiv.m_input.setFocused(true);
            m_findReplaceDiv.showOrHideReplace(true);
            FCPadding padding = getPadding();
            padding.top = 60;
            setPadding(padding);
            m_findReplaceDiv.m_choose.setSelectedIndex(1);
            m_textChanged = true;
            update();
            invalidate();
        }
    }

    /*
    * 系统颜色
    */
    public long[] m_sysColors = new long[] {MyColor.USERCOLOR48,
            FCColor.rgb(102, 108, 122),
            MyColor.USERCOLOR88,
            MyColor.USERCOLOR51,
            FCColor.rgb(220, 109, 157),
            FCColor.rgb(117 + 50, 37, 66),
            MyColor.USERCOLOR20,
            FCColor.rgb(244, 163, 60),
            MyColor.USERCOLOR53 };

    /*
    * 调用方法
    */
    public String callFunction(String funcName, String parameters) {
        String lowerName = funcName.toLowerCase();
        if (lowerName.equals("getcelltext")) {
            if (FCFile.isFileExist(parameters))
            {
                String content = "";
                RefObject<String> refContent = new RefObject<String>(content);
                FCFile.read(parameters, refContent);
                content = refContent.argvalue;
                setText(content);
                invalidate();
            }
            return "";
        }else{
            return super.callFunction(funcName, parameters);
        }
    }

    /*
    * 是否按下
    */
    public boolean m_isClickDown;
    
    /*
    * 按下时的位置
    */
    public FCPoint m_touchDownPoint = new FCPoint();

    /*
    * 删除选中
    */
    public void deleteSelect(){
        if (!m_readOnly)
        {
            int oldLines = m_lines.size();
            iFCRedoUndoInfo newReoUndoInfo = new iFCRedoUndoInfo(getText(), getSelectionStart(), getSelectionLength(), 0);
            deleteWord();
            onTextChanged();
            if (m_textChanged)
            {
                m_undoStack.push(newReoUndoInfo);
            }
            invalidate();
            if (!isLineVisible(m_selectionStart, 1.0f))
            {
                int newLines = m_lines.size();
                if (newLines != oldLines)
                {
                    FCVScrollBar vScrollBar = getVScrollBar();
                    if (vScrollBar != null)
                    {
                        vScrollBar.setPos(vScrollBar.getPos() + m_lineHeight * (newLines - oldLines));
                        invalidate();
                    }
                }
            }
        }
    }

    /*
    * 获取正在输入的高亮信息
    */
    public HighLightInfo getInputingHightLight()
    {
        HighLightInfo highLightInfo = new HighLightInfo();
        if (m_selectionStart != -1)
        {
            for (int i = 0; i < m_highLightInfos.size(); i++)
            {
                HighLightInfo info = m_highLightInfos.get(i);
                if (m_selectionStart >= info.m_start && m_selectionStart <= info.m_end)
                {
                    highLightInfo = info;
                    break;
                }
            }
        }
        return highLightInfo;
    }

    /*
    * 自动补全的表格
    */
    public FCGrid m_autoCompleteGrid;

    /*
    * 开始代码补全
    */
    public void startAutoComplete()
    {
    }
}
