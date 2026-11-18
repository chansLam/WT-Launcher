package com.autopai.support.utils;

/* loaded from: classes.dex */
public class SignalIcons {
    private static com.autopai.support.utils.SignalIcons sInstance;
    public int[][] JG_SIGNAL_STRENGTH;

    public static com.autopai.support.utils.SignalIcons getInstance(android.content.Context cxt) {
        if (sInstance == null) {
            synchronized (com.autopai.support.utils.SignalIcons.class) {
                if (sInstance == null) {
                    sInstance = new com.autopai.support.utils.SignalIcons(cxt);
                }
            }
        }
        return sInstance;
    }

    private SignalIcons(android.content.Context cxt) {
        android.content.Context context = cxt.getApplicationContext();
        initIcons(context);
    }

    private void initIcons(android.content.Context context) {
        this.JG_SIGNAL_STRENGTH = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, 4, 5);
        this.JG_SIGNAL_STRENGTH[0][0] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_no_signal");
        this.JG_SIGNAL_STRENGTH[0][1] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_no_signal_1");
        this.JG_SIGNAL_STRENGTH[0][2] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_no_signal_2");
        this.JG_SIGNAL_STRENGTH[0][3] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_no_signal_3");
        this.JG_SIGNAL_STRENGTH[0][4] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_no_signal_4");
        this.JG_SIGNAL_STRENGTH[1][0] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_edge_0");
        this.JG_SIGNAL_STRENGTH[1][1] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_edge_1");
        this.JG_SIGNAL_STRENGTH[1][2] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_edge_2");
        this.JG_SIGNAL_STRENGTH[1][3] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_edge_3");
        this.JG_SIGNAL_STRENGTH[1][4] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_edge_4");
        this.JG_SIGNAL_STRENGTH[2][0] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_3g_0");
        this.JG_SIGNAL_STRENGTH[2][1] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_3g_1");
        this.JG_SIGNAL_STRENGTH[2][2] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_3g_2");
        this.JG_SIGNAL_STRENGTH[2][3] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_3g_3");
        this.JG_SIGNAL_STRENGTH[2][4] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_3g_4");
        this.JG_SIGNAL_STRENGTH[3][0] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_4g_0");
        this.JG_SIGNAL_STRENGTH[3][1] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_4g_1");
        this.JG_SIGNAL_STRENGTH[3][2] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_4g_2");
        this.JG_SIGNAL_STRENGTH[3][3] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_4g_3");
        this.JG_SIGNAL_STRENGTH[3][4] = com.autopai.support.utils.AutoPaiResource.getIdentifierByDrawable(context, "jg_ic_signal_4g_4");
    }
}
