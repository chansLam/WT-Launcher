package com.autopai.support.utils;

/* loaded from: classes.dex */
class WifiIcons {
    private static com.autopai.support.utils.WifiIcons sInstance;
    public int[][] JG_WIFI_SIGNAL_STRENGTH;
    public int WIFI_LEVEL_COUNT = 0;

    private WifiIcons(android.content.Context cxt) {
        android.content.Context context = cxt.getApplicationContext();
        initIcons(context);
    }

    private void initIcons(android.content.Context context) {
        this.JG_WIFI_SIGNAL_STRENGTH = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, 2, 4);
        this.JG_WIFI_SIGNAL_STRENGTH[0][0] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_wifi_full_0");
        this.JG_WIFI_SIGNAL_STRENGTH[0][1] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_wifi_full_1");
        this.JG_WIFI_SIGNAL_STRENGTH[0][2] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_wifi_full_2");
        this.JG_WIFI_SIGNAL_STRENGTH[0][3] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_wifi_full_3");
        this.JG_WIFI_SIGNAL_STRENGTH[1][0] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_wifi_full_0");
        this.JG_WIFI_SIGNAL_STRENGTH[1][1] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_wifi_full_1");
        this.JG_WIFI_SIGNAL_STRENGTH[1][2] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_wifi_full_2");
        this.JG_WIFI_SIGNAL_STRENGTH[1][3] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_wifi_full_3");
        this.WIFI_LEVEL_COUNT = this.JG_WIFI_SIGNAL_STRENGTH[0].length;
    }

    public static com.autopai.support.utils.WifiIcons getInstance(android.content.Context cxt) {
        if (sInstance == null) {
            synchronized (com.autopai.support.utils.WifiIcons.class) {
                if (sInstance == null) {
                    sInstance = new com.autopai.support.utils.WifiIcons(cxt);
                }
            }
        }
        return sInstance;
    }
}
