package facecat.topin.swing;

/*
* 指标数据
*/
public class IndicatorData {
    /*
    * 构造方法
    */
    public IndicatorData(String name, String chName, String script) {
        m_name = name;
        m_chName = chName;
        m_script = script;
    }

    /*
    * 中文名称
    */
    public String m_chName;

    /*
    * 名称
    */
    public String m_name;

    /*
    * 脚本
    */
    public String m_script;
}

