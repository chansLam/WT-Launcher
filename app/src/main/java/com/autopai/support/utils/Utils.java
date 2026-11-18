package com.autopai.support.utils;

/* loaded from: classes.dex */
public class Utils {
    private static final java.lang.String LOGTAG = "Utils";

    public static java.lang.String getCurCarType() {
        try {
            java.lang.Class clazz = java.lang.Class.forName("com.incall.proxy.setting.SettingManager");
            java.lang.reflect.Method method = clazz.getMethod("getInstance", (java.lang.Class[]) null);
            java.lang.Object obj = method.invoke(clazz, (java.lang.Object[]) null);
            java.lang.reflect.Method m = clazz.getMethod("getCurCarType", (java.lang.Class[]) null);
            java.lang.Object carType = m.invoke(obj, (java.lang.Object[]) null);
            com.autopai.support.utils.SLog.i(LOGTAG, "getCurCarType carType = " + carType);
            return (java.lang.String) carType;
        } catch (java.lang.Exception e) {
            com.autopai.support.utils.SLog.i(LOGTAG, "getCurCarType exception");
            e.printStackTrace();
            return "";
        }
    }
}
