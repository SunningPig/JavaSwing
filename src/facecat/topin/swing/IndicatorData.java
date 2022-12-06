/*
* FaceCat图形通讯框架(非开源)
* 著作权编号:2015SR229355
* 上海卷卷猫信息技术有限公司
*/

package facecat.topin.swing;

/// <summary>
/// 指标数据
/// </summary>
public class IndicatorData {
    public IndicatorData(String name, String chName, String script) {
        m_name = name;
        m_chName = chName;
        m_script = script;
    }

    public String m_chName;

    /// <summary>
    /// 名称
    /// </summary>
    public String m_name;

    /// <summary>
    /// 脚本
    /// </summary>
    public String m_script;
}

