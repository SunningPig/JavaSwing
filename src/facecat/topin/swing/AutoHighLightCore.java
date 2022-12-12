package facecat.topin.swing;

/*
* 高亮处理
*/
public class AutoHighLightCore {
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                if (str.charAt(i) != '.') {
                    return false;
                }
            }
        }
        return true;
    }
}
