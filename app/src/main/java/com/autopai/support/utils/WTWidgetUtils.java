package com.autopai.support.utils;

/* loaded from: classes.dex */
public class WTWidgetUtils {
    public static int getThemeType(android.content.Context cxt) {
        return android.provider.Settings.System.getInt(cxt.getContentResolver(), "ThemeMonitorKey", 0);
    }
}
