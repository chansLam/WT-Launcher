package me.weishu.reflection;

/* loaded from: classes.dex */
public class Reflection {
    private static final int ERROR_SET_APPLICATION_FAILED = -20;
    private static int UNKNOWN;
    private static int unsealed;

    private static native int unsealNative(int i);

    static {
        java.lang.System.loadLibrary("free-reflection");
        UNKNOWN = -9999;
        unsealed = UNKNOWN;
    }

    public static int unseal(android.content.Context context) {
        if (android.os.Build.VERSION.SDK_INT < 28) {
            return 0;
        }
        if (context == null) {
            return -10;
        }
        android.content.pm.ApplicationInfo applicationInfo = context.getApplicationInfo();
        int targetSdkVersion = applicationInfo.targetSdkVersion;
        synchronized (me.weishu.reflection.Reflection.class) {
            if (unsealed == UNKNOWN) {
                unsealed = unsealNative(targetSdkVersion);
                if (unsealed >= 0) {
                    try {
                        java.lang.reflect.Method setHiddenApiEnforcementPolicy = android.content.pm.ApplicationInfo.class.getDeclaredMethod("setHiddenApiEnforcementPolicy", java.lang.Integer.TYPE);
                        setHiddenApiEnforcementPolicy.invoke(applicationInfo, 0);
                    } catch (java.lang.Throwable e) {
                        e.printStackTrace();
                        unsealed = ERROR_SET_APPLICATION_FAILED;
                    }
                }
            }
        }
        return unsealed;
    }
}
