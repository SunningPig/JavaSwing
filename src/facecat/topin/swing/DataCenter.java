/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facecat.topin.swing;

public class DataCenter {
    /*
    * 文件分隔符号
    */
    //public static String m_seperator = "/"; //Linux
    public static String m_seperator = "\\"; //Windows
    
    /*
    * 获取程序路径
    */
    public static String getAppPath(){
        //return System.getProperty("user.dir").replace("\\", "/"); //Linux
        return System.getProperty("user.dir"); //Windows
    }
}
