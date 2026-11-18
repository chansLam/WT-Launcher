package com.autopai.common.utils.common;

/* loaded from: classes.dex */
public class StringUtils {
    public static boolean isEmpty(java.lang.String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmptyByTrim(java.lang.String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isMobile(java.lang.String mobile) {
        if (isEmptyByTrim(mobile)) {
            return false;
        }
        return java.util.regex.Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$").matcher(mobile).matches();
    }

    public static boolean isEmail(java.lang.String email) {
        if (isEmptyByTrim(email)) {
            return false;
        }
        return java.util.regex.Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(email).matches();
    }

    public static java.lang.String getString(java.lang.String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str;
    }

    public static java.lang.String getStringFromEditView(android.widget.EditText editText) {
        return editText.getText().toString().trim();
    }

    public static boolean isObjectDataNull(java.lang.Object checkData) {
        return "".equals(checkData) || checkData == null || "null".equals(checkData) || checkData == null;
    }

    public static boolean isTwoStringEqual(java.lang.String aString, java.lang.String bString) {
        return aString.equals(bString) || bString == aString;
    }

    public static boolean checkUrl1(java.lang.String url) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?", 2);
        java.util.regex.Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public static boolean checkEmail(java.lang.String emailText) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        java.util.regex.Matcher matcher = pattern.matcher(emailText);
        return matcher.matches();
    }

    public static boolean checkPhoneNum(java.lang.String mobiles) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        java.util.regex.Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean checkPassword(java.lang.String emailText) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^[a-zA-Z0-9_]\\w{5,19}$");
        java.util.regex.Matcher matcher = pattern.matcher(emailText);
        return matcher.matches();
    }

    public static boolean isNiceqq(java.lang.String content) {
        return content.matches("[1-9]\\d{4,}");
    }

    public static boolean isVideo(java.lang.String pInput) {
        if (isObjectDataNull(pInput)) {
            return false;
        }
        java.lang.String tmpName = pInput.substring(pInput.lastIndexOf(".") + 1, pInput.length());
        java.lang.String[] imgeArray = {"3gp", "mov", "wmv", "asf", "rm", "rmvb", "mpg", "mpeg", "mpe", "dat", "vob", "dv", "wmv", "asf", "asx", "mov", "avi", "mkv", "mp4", "m4v", "flv"};
        for (java.lang.String str : imgeArray) {
            if (str.equals(tmpName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[0] & 255;
        return addr | ((bytes[1] << 8) & android.support.v4.view.MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((bytes[2] << 16) & 16711680) | ((bytes[3] << 25) & android.support.v4.view.ViewCompat.MEASURED_STATE_MASK);
    }

    public static byte[] intToByte(int i) {
        byte[] abyte0 = {(byte) (i & 255), (byte) ((65280 & i) >> 8), (byte) ((16711680 & i) >> 16), (byte) (((-16777216) & i) >> 24)};
        return abyte0;
    }
}
