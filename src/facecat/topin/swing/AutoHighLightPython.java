/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facecat.topin.swing;
import java.util.ArrayList;
import java.util.HashMap;
import facecat.topin.core.FCColor;
import facecat.topin.core.FCPadding;

public class AutoHighLightPython {
    public static HashMap<String, String> m_jsKeyWords = new HashMap<String, String>();

    public static HashMap<String, String> m_pyKeyWords = new HashMap<String, String>();
    public static HashMap<String, String> m_pyFunctions1 = new HashMap<String, String>();
    public static HashMap<String, String> m_pyFunctions2 = new HashMap<String, String>();
    public static HashMap<String, String> m_pyFunctions3 = new HashMap<String, String>();
    public static HashMap<String, String> m_pyVars = new HashMap<String, String>();

    public static long[] m_pySysColors;

    public static void analysis(iTextBox txtCode) {
        try {
            if (MyColor.m_style == 1) {
                m_pySysColors = new long[]{
                        FCColor.rgb(255 - 206, 255 - 145, 255 - 120), //双引号及字符串
                        FCColor.rgb(255 - 128, 255 - 128, 255 - 128), //标点符号1组
                        FCColor.rgb(255 - 212, 255 - 212, 255 - 212), //标点符号2组
                        FCColor.rgb(255 - 86, 255 - 156, 255 - 214), //关键字
                        FCColor.rgb(255 - 197, 255 - 134, 255 - 192), //自定义方法
                        FCColor.rgb(255 - 156, 255 - 220, 255 - 254), //变量
                        FCColor.rgb(255 - 181, 255 - 206, 255 - 168),//数字
                        FCColor.rgb(255 - 106, 255 - 153, 255 - 85),  //注释
                        FCColor.rgb(255 - 77, 255 - 198, 255 - 174),  //扩展色1
                        FCColor.rgb(255 - 220, 255 - 220, 255 - 170)};  //扩展色2
            } else {
                m_pySysColors = new long[]{
                        FCColor.rgb(206, 145, 120), //双引号及字符串
                        FCColor.rgb(128, 128, 128), //标点符号1组
                        FCColor.rgb(212, 212, 212), //标点符号2组
                        FCColor.rgb(86, 156, 214), //关键字
                        FCColor.rgb(197, 134, 192), //自定义方法
                        FCColor.rgb(156, 220, 254), //变量
                        FCColor.rgb(181, 206, 168),//数字
                        FCColor.rgb(106, 153, 85),  //注释
                        FCColor.rgb(77, 198, 174),  //扩展色1
                        FCColor.rgb(220, 220, 170)};  //扩展色2
            }
            txtCode.m_sysColors = m_pySysColors;
            String content = txtCode.getText();
            txtCode.m_highLightInfos.clear();
            txtCode.m_clickHighInfo = null;
            if(m_pyFunctions1.size() == 0){
                m_pyFunctions1.put("IMPORT", "");
                m_pyFunctions1.put("AS", "");
                m_pyFunctions1.put("PRINT", "");
                m_pyFunctions1.put("STR", "");
                m_pyFunctions1.put("FLOAT", "");
                m_pyFunctions1.put("FROM", "");
                m_pyFunctions1.put("LEN", "");
                m_pyFunctions1.put("SORTED", "");
                m_pyFunctions1.put("FOR", "");
                m_pyFunctions1.put("IF", "");
                m_pyFunctions1.put("BREAK", "");
                m_pyFunctions1.put("RETURN", "");
                m_pyFunctions1.put("CONTINUE", "");
                m_pyFunctions1.put("IN", "");
                m_pyFunctions1.put("WITH", "");
                m_pyFunctions1.put("ELSEIF", "");
                m_pyFunctions1.put("ELSE", "");
                m_pyFunctions1.put("TYPE", "");
                m_pyFunctions1.put("WHILE", "");
                m_pyFunctions1.put("FUNCTION", "");
                m_pyFunctions1.put("VAR", "");
                m_pyFunctions1.put("DEF", "");
                m_pyFunctions1.put("ASSERT", "");
            }
            HashMap<String, String> sysFunctions = new HashMap<String, String>();
            for(String key : m_pyFunctions1.keySet()){
                sysFunctions.put(key, m_pyFunctions1.get(key));
            }

            if(m_pyFunctions2.size() == 0) {
                m_pyFunctions2.put("FLOAT", "");
                m_pyFunctions2.put("TYPE", "");
                m_pyFunctions2.put("INT", "");
                m_pyFunctions2.put("STR", "");
                m_pyFunctions2.put("OS", "");
            }
            HashMap<String, String> sysFunctions2 = new HashMap<String, String>();
            for(String key : m_pyFunctions2.keySet()){
                sysFunctions2.put(key, m_pyFunctions2.get(key));
            }

            if(m_pyFunctions3.size() == 0){
                m_pyFunctions3.put("LEN", "");
                m_pyFunctions3.put("SORTED", "");
                m_pyFunctions3.put("PRINT", "");
                m_pyFunctions3.put("ABS", "");
                m_pyFunctions3.put("WRITE", "");
                m_pyFunctions3.put("CLOSE", "");
                m_pyFunctions3.put("OPEN", "");
                m_pyFunctions3.put("JOIN", "");
                m_pyFunctions3.put("WALK", "");
                m_pyFunctions3.put("APPEND", "");
                m_pyFunctions3.put("FIND", "");
            }
            HashMap<String, String> sysFunctions3 = new HashMap<>();
            for(String key : m_pyFunctions3.keySet()){
                sysFunctions3.put(key, m_pyFunctions3.get(key));
            }

            HashMap<String, String> sysVars = new HashMap<String, String>();
            if (m_pyVars.size() == 0)
            {
                m_pyVars.put("LEN", "");
                m_pyVars.put("SORTED", "");
                m_pyVars.put("PRINT", "");
                m_pyVars.put("ABS", "");
                m_pyVars.put("WRITE", "");
                m_pyVars.put("CLOSE", "");
                m_pyVars.put("OPEN", "");
                m_pyVars.put("JOIN", "");
                m_pyVars.put("WALK", "");
                m_pyVars.put("APPEND", "");
                m_pyVars.put("FIND", "");
            }
            for (String key : m_pyVars.keySet())
            {
                sysVars.put(key, m_pyVars.get(key));
            }

            if (m_pyKeyWords.size() == 0) {
                m_pyKeyWords.put("TRUE", "");
                m_pyKeyWords.put("NOT", "");
                m_pyKeyWords.put("AND", "");
                m_pyKeyWords.put("CLASS", "");
                m_pyKeyWords.put("AS", "");
                m_pyKeyWords.put("HTML", "");
                m_pyKeyWords.put("BODY", "");
                ;
                m_pyKeyWords.put("HEAD", "");
                m_pyKeyWords.put("SCRIPT", "");
                m_pyKeyWords.put("DIV", "");
                m_pyKeyWords.put("TABLE", "");
                m_pyKeyWords.put("TH", "");
                m_pyKeyWords.put("TD", "");
                m_pyKeyWords.put("TR", "");
                m_pyKeyWords.put("LABEL", "");
                m_pyKeyWords.put("INPUT", "");
                m_pyKeyWords.put("MENU", "");
                m_pyKeyWords.put("ITEM", "");
                m_pyKeyWords.put("XML", "");
                m_pyKeyWords.put("TREE", "");
                m_pyKeyWords.put("NODE", "");
                m_pyKeyWords.put("NODES", "");
                m_pyKeyWords.put("LEFTVSCALE", "");
                m_pyKeyWords.put("RIGHTVSCALE", "");
                m_pyKeyWords.put("HSCALE", "");
                m_pyKeyWords.put("HGRID", "");
                m_pyKeyWords.put("VGRID", "");
                m_pyKeyWords.put("TOOLTIP", "");
                m_pyKeyWords.put("TITLEBAR", "");
                m_pyKeyWords.put("CROSSLINE", "");
                m_pyKeyWords.put("CANDLESHAPE", "");
                m_pyKeyWords.put("POLYLINESHAPE", "");
                m_pyKeyWords.put("BARSHAPE", "");
                m_pyKeyWords.put("INDICATOR", "");
                m_pyKeyWords.put("CHARTDIV", "");
            }
            HashMap<Character, Integer> m_keys1 = new HashMap<Character, Integer>();
            m_keys1.put(',', 0);
            m_keys1.put(';', 0);
            m_keys1.put('{', 0);
            m_keys1.put('}', 0);
            m_keys1.put('(', 0);
            m_keys1.put(')', 0);
            m_keys1.put('[', 0);
            m_keys1.put(']', 0);
            m_keys1.put('.', 0);

            HashMap<Character, Integer> m_keys2 = new HashMap<Character, Integer>();
            m_keys2.put('+', 0);
            m_keys2.put('-', 0);
            m_keys2.put('*', 0);
            m_keys2.put('/', 0);
            m_keys2.put('<', 0);
            m_keys2.put('>', 0);
            m_keys2.put('=', 0);
            m_keys2.put('%', 0);
            m_keys2.put(':', 0);
            m_keys2.put('!', 0);

            HashMap<Character, Integer> m_keys3 = new HashMap<Character, Integer>();
            m_keys3.put(' ', 0);
            m_keys3.put('\r', 0);
            m_keys3.put('\n', 0);
            m_keys3.put('\t', 0);

            HashMap<Character, Integer> m_keys4 = new HashMap<Character, Integer>();
            m_keys4.put(',', 0);
            m_keys4.put(';', 0);
            m_keys4.put('{', 0);
            m_keys4.put('}', 0);
            m_keys4.put('(', 0);
            m_keys4.put(')', 0);
            m_keys4.put('+', 0);
            m_keys4.put('-', 0);
            m_keys4.put('*', 0);
            m_keys4.put('/', 0);
            m_keys4.put('<', 0);
            m_keys4.put('>', 0);
            m_keys4.put('=', 0);
            m_keys4.put('%', 0);
            m_keys4.put(':', 0);
            m_keys4.put('!', 0);
            m_keys4.put(' ', 0);
            m_keys4.put('\r', 0);
            m_keys4.put('\n', 0);
            m_keys4.put('\t', 0);
            m_keys4.put('.', 0);
            if (content.length() > 0) {
                char[] charArray = content.toCharArray();
                int start = 0, textLength = charArray.length;
                boolean isstr = false;
                boolean lastIsFunction = false;
                boolean lastIsVar = false;
                boolean isComt = false;
                char lastIsStr = '\0';
                int wordLength = 0;
                for (int i = 0; i < textLength; i++) {
                    char ch = charArray[i];
                    if(i > 0 && i < textLength - 1 && content.charAt(i - 1) == '\\'){
                        if(ch == '\r' || ch == '\n' || ch == '\t' || ch == '\"' || ch == '\''){
                            wordLength++;
                            continue;
                        }
                    }
                    if (isComt) {
                        if ((ch == '\r' || ch == '\n' || i == textLength - 1)) {
                            wordLength++;
                            HighLightInfo hli = new HighLightInfo();
                            hli.m_type = 7;
                            hli.m_start = i - wordLength + 1;
                            if (hli.m_start < 0) {
                                hli.m_start = 0;
                            }
                            hli.m_end = i;
                            hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                            txtCode.m_highLightInfos.add(hli);
                            isComt = false;
                            wordLength = 0;
                        } else {
                            wordLength++;
                        }
                    }else{
                        if (ch == '\'' || ch == '\"') {
                            if (!isstr) {
                                HighLightInfo hli = new HighLightInfo();
                                hli.m_type = 0;
                                hli.m_start = i;
                                hli.m_end = i;
                                hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                txtCode.m_highLightInfos.add(hli);
                                start = i;
                            } else {
                                if (lastIsStr == ch) {
                                    if (wordLength > 0) {
                                        HighLightInfo hli = new HighLightInfo();
                                        hli.m_type = 0;
                                        hli.m_start = start + 1;
                                        hli.m_end = i - 1;
                                        hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                        txtCode.m_highLightInfos.add(hli);
                                    }
                                    HighLightInfo hli2 = new HighLightInfo();
                                    hli2.m_type = 0;
                                    hli2.m_start = i;
                                    hli2.m_end = i;
                                    hli2.m_textColor = txtCode.m_sysColors[hli2.m_type];
                                    txtCode.m_highLightInfos.add(hli2);
                                    wordLength = 0;
                                } else {
                                    wordLength++;
                                }
                            }

                            if (isstr) {
                                if (lastIsStr == ch) {
                                    isstr = false;
                                    lastIsStr = '\0';
                                }
                            } else {
                                isstr = true;
                                lastIsStr = ch;
                            }
                        } else if (ch == '#' && !isstr) {
                            isComt = true;
                            wordLength++;
                        }
                        //else if (ch == '/' && !isComt)
                        //{
                        //    isComt = true;
                        //    word += ch;
                        //}
                        else {
                            if (isstr) {
                                wordLength++;
                            } else {
                                if (m_keys1.containsKey(ch)) {
                                    if (wordLength > 0)
                                    {
                                        HighLightInfo hli2 = new HighLightInfo();
                                        hli2.m_type = 5;
                                        hli2.m_start = i - wordLength;
                                        hli2.m_end = i - 1;
                                        hli2.m_textColor = txtCode.m_sysColors[hli2.m_type];
                                        txtCode.m_highLightInfos.add(hli2);
                                    }
                                    HighLightInfo hli = new HighLightInfo();
                                    hli.m_type = 1;
                                    hli.m_start = i;
                                    hli.m_end = i;
                                    hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                    txtCode.m_highLightInfos.add(hli);
                                    wordLength = 0;
                                } else if (m_keys2.containsKey(ch)) {
                                    if (wordLength > 0)
                                    {
                                        HighLightInfo hli2 = new HighLightInfo();
                                        hli2.m_type = 5;
                                        hli2.m_start = i - wordLength;
                                        hli2.m_end = i - 1;
                                        hli2.m_textColor = txtCode.m_sysColors[hli2.m_type];
                                        txtCode.m_highLightInfos.add(hli2);
                                    }
                                    HighLightInfo hli = new HighLightInfo();
                                    hli.m_type = 2;
                                    hli.m_start = i;
                                    hli.m_end = i;
                                    hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                    txtCode.m_highLightInfos.add(hli);
                                    wordLength = 0;
                                } else if (m_keys3.containsKey(ch)) {
                                    if (wordLength > 0)
                                    {
                                        HighLightInfo hli2 = new HighLightInfo();
                                        hli2.m_type = 5;
                                        hli2.m_start = i - wordLength;
                                        hli2.m_end = i - 1;
                                        hli2.m_textColor = txtCode.m_sysColors[hli2.m_type];
                                        txtCode.m_highLightInfos.add(hli2);
                                    }
                                     HighLightInfo hli = new HighLightInfo();
                                    hli.m_type = 2;
                                    hli.m_start = i;
                                    hli.m_end = i;
                                    hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                    txtCode.m_highLightInfos.add(hli);
                                    wordLength = 0;
                                } else {
                                    wordLength++;
                                    boolean isWord = false;
                                    if (i == textLength - 1) {
                                        isWord = true;
                                    } else {
                                        char nextCh = charArray[i + 1];
                                        if (m_keys4.containsKey(nextCh)) {
                                            isWord = true;
                                        }
                                    }
                                    if (isWord) {
                                        String word = content.substring(i - wordLength + 1, i + 1);
                                        String upperWord = word.toUpperCase();
                                        if (m_pyKeyWords.containsKey(upperWord)) {
                                            lastIsFunction = false;
                                            if (upperWord.equals("FUNCTION")) {
                                                lastIsFunction = true;
                                            }
                                            lastIsVar = false;
                                            if (upperWord.equals("VAR") || upperWord.equals("AS")) {
                                                lastIsVar = true;
                                            }
                                            HighLightInfo hli = new HighLightInfo();
                                            if (lastIsFunction) {
                                                hli.m_type = 7;
                                            } else {
                                                hli.m_type = 3;
                                            }
                                            hli.m_type = 3;
                                            hli.m_start = i - wordLength + 1;
                                            if (hli.m_start < 0) {
                                                hli.m_start = 0;
                                            }
                                            hli.m_end = i;
                                            hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                            txtCode.m_highLightInfos.add(hli);
                                        } else if (sysFunctions.containsKey(upperWord)) {
                                            lastIsFunction = false;
                                            lastIsVar = false;
                                            HighLightInfo hli = new HighLightInfo();
                                            hli.m_type = 4;
                                            if (sysFunctions2.containsKey(upperWord)) {
                                                hli.m_type = 8;
                                            } else if (sysFunctions3.containsKey(upperWord)) {
                                                hli.m_type = 9;
                                            }
                                            hli.m_start = i - wordLength + 1;
                                            if (hli.m_start < 0) {
                                                hli.m_start = 0;
                                            }
                                            hli.m_end = i;
                                            hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                            txtCode.m_highLightInfos.add(hli);
                                        } else if (sysVars.containsKey(upperWord)) {
                                            lastIsFunction = false;
                                            lastIsVar = false;
                                            HighLightInfo hli = new HighLightInfo();
                                            hli.m_type = 9;
                                            if (sysFunctions2.containsKey(upperWord)) {
                                                hli.m_type = 8;
                                            } else if (sysFunctions3.containsKey(upperWord)) {
                                                hli.m_type = 9;
                                            }
                                            hli.m_start = i - wordLength + 1;
                                            if (hli.m_start < 0) {
                                                hli.m_start = 0;
                                            }
                                            hli.m_end = i;
                                            hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                            txtCode.m_highLightInfos.add(hli);
                                        } else {
                                            double num = 0;
                                            boolean isNum = AutoHighLightCore.isNumeric(word);
                                            if (isNum) {
                                                lastIsFunction = false;
                                                lastIsVar = false;
                                                HighLightInfo hli = new HighLightInfo();
                                                hli.m_type = 6;
                                                hli.m_start = i - wordLength + 1;
                                                if (hli.m_start < 0) {
                                                    hli.m_start = 0;
                                                }
                                                hli.m_end = i;
                                                hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                                txtCode.m_highLightInfos.add(hli);
                                            } else {
                                                if (lastIsFunction) {
                                                    lastIsFunction = false;
                                                    lastIsVar = false;
                                                    sysFunctions.put(upperWord, "");
                                                    HighLightInfo hli = new HighLightInfo();
                                                    hli.m_type = 4;
                                                    hli.m_start = i - wordLength + 1;
                                                    if (hli.m_start < 0) {
                                                        hli.m_start = 0;
                                                    }
                                                    hli.m_end = i;
                                                    hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                                    txtCode.m_highLightInfos.add(hli);
                                                } else if (lastIsVar) {
                                                    lastIsFunction = false;
                                                    lastIsVar = false;
                                                    sysVars.put(upperWord, "");
                                                    HighLightInfo hli = new HighLightInfo();
                                                    hli.m_type = 5;
                                                    hli.m_start = i - wordLength + 1;
                                                    if (hli.m_start < 0) {
                                                        hli.m_start = 0;
                                                    }
                                                    hli.m_end = i;
                                                    hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                                    txtCode.m_highLightInfos.add(hli);
                                                } else {
                                                    HighLightInfo hli = new HighLightInfo();
                                                    boolean isVariable = false;
                                                    for (int t = i + 1; t < textLength; t++) {
                                                        if (charArray[t] != ' ') {
                                                            if (charArray[t] == '=') {
                                                                //isVariable = true;
                                                            }
                                                            break;
                                                        }
                                                    }
                                                    if (isVariable) {
                                                        sysVars.put(upperWord, "");
                                                        hli.m_type = 5;
                                                    } else {
                                                        hli.m_type = 5;
                                                    }
                                                    hli.m_start = i - wordLength + 1;
                                                    if (hli.m_start < 0) {
                                                        hli.m_start = 0;
                                                    }
                                                    hli.m_end = i;
                                                    hli.m_textColor = txtCode.m_sysColors[hli.m_type];
                                                    txtCode.m_highLightInfos.add(hli);
                                                }
                                            }
                                        }
                                        wordLength = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            FCPadding padding = txtCode.getPadding();
            if (padding.left > 0)
            {
                int len = content.length() / 50;
                if (len > 0)
                {
                    padding.left = 20;
                    while (len > 0)
                    {
                        padding.left += 10;
                        len = len / 10;
                    }
                }
                else
                {
                    padding.left = 20;
                }
            }
            txtCode.setPadding(padding);
        } catch (Exception ex) {
        }
    }
}
