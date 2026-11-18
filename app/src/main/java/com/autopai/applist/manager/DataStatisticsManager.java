package com.autopai.applist.manager;

/* loaded from: classes.dex */
public class DataStatisticsManager {
    private static final java.lang.String APP_ID = "d2b6563fc050c92c99639b79f7799b27";
    private static final java.lang.String CHANNEL_ID = "changanS311";
    private static final java.lang.String TAG = "DataStatisticsManager";

    public static void initSdk(android.content.Context context) {
        android.util.Log.i(TAG, "initSdk: ---- init sdk ");
        com.wt.common.WTConfigure.init(context, APP_ID, CHANNEL_ID);
    }

    public static void sendEvents(android.content.Context context, java.lang.String packageName, java.lang.String eventLabel) {
        android.util.Log.i(TAG, "sendEvents: String packageName = " + packageName + " -- String eventLabel = " + eventLabel);
        if (packageName == null) {
            android.util.Log.w(TAG, "sendEvents: Current Event Id is null !");
        } else {
            com.wt.statistics.WTClickAgent.onEvent(context, packageName);
        }
    }
}
