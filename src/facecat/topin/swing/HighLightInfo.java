/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facecat.topin.swing;

import facecat.topin.core.*;

/**
 *
 * @author taode
 */
public class HighLightInfo {
    /// <summary>
    /// 描述信息
    /// </summary>
    public String m_desc = "";

    /// <summary>
    /// 结束索引
    /// </summary>
    public int m_end = -1;

    /// <summary>
    /// 开始索引
    /// </summary>
    public int m_start = - 1;

    /// <summary>
    /// 文字颜色
    /// </summary>
    public long m_textColor = FCColor.None;

    /// <summary>
    /// 类型
    /// </summary>
    public int m_type;
}
