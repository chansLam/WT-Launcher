package com.incall.apps.common.utils;

/* loaded from: classes.dex */
public class ClickMessage {
    private static com.incall.apps.common.utils.ClickMessage mClickMessage;
    private long lastClickTime;

    public static synchronized com.incall.apps.common.utils.ClickMessage getInstance() {
        com.incall.apps.common.utils.ClickMessage clickMessage;
        synchronized (com.incall.apps.common.utils.ClickMessage.class) {
            if (mClickMessage == null) {
                mClickMessage = new com.incall.apps.common.utils.ClickMessage();
            }
            clickMessage = mClickMessage;
        }
        return clickMessage;
    }

    public boolean isFastDoubleClick() {
        long time = java.lang.System.currentTimeMillis();
        if (time - this.lastClickTime < 2000) {
            return true;
        }
        this.lastClickTime = time;
        return false;
    }

    public void setByteNum(android.widget.TextView setpowername, java.lang.String value) {
        setpowername.setText(calceStringLenght(value));
    }

    private java.lang.String calceStringLenght(java.lang.String str) {
        int chineseCount = countChineseChar(str);
        if (chineseCount > 16 && chineseCount < 20) {
            java.lang.String subString = java.lang.String.valueOf(str.substring(0, chineseCount - 8)) + "...";
            android.util.Log.d("ClickMessage", "calceStringLenght--strvolue 1111111 == " + subString + ",strLenght = " + str.length());
            return subString;
        }
        if (chineseCount > 20) {
            return java.lang.String.valueOf(str.substring(0, chineseCount - 10)) + "...";
        }
        android.util.Log.d("ClickMessage", "calceStringLenght--strvolue == " + str + ",strLenght = " + str.length());
        return str;
    }

    public int countChineseChar(java.lang.CharSequence sequence) {
        android.util.Log.d("ClickMessage", "countChineseChar--  sequence == " + ((java.lang.Object) sequence) + ",TextUtils.isEmpty(sequence) == " + android.text.TextUtils.isEmpty(sequence));
        if (android.text.TextUtils.isEmpty(sequence)) {
            return 0;
        }
        int chineseNum = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (isChineseChar(sequence.charAt(i))) {
                chineseNum++;
            } else {
                chineseNum = sequence.length();
            }
        }
        android.util.Log.d("ClickMessage", "countChineseChar-- == " + chineseNum);
        return chineseNum;
    }

    private boolean isChineseChar(char c) {
        java.lang.Character.UnicodeBlock ub = java.lang.Character.UnicodeBlock.of(c);
        return ub == java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == java.lang.Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == java.lang.Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == java.lang.Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }
}
