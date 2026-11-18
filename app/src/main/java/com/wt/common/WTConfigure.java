package com.wt.common;

/* loaded from: classes.dex */
public class WTConfigure {
    private static volatile com.wt.statistics.persistentdata.CommonBean mCommonBean;
    private static android.content.Context mContext;
    private static boolean isDebugLogEnabled = false;
    private static boolean isEncryptEnabled = false;
    private static boolean isSdkDisabled = false;
    private static boolean isDisableFileExist = false;
    private static java.lang.String LOGTAG = "WTConfigure";

    public static void init(android.content.Context context) {
        init(context, "", "");
    }

    public static void init(android.content.Context context, java.lang.String appID, java.lang.String channelID) {
        com.wt.statistics.utils.SLog.i(LOGTAG, "WTConfigure init");
        if (context == null) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "context is null !");
            return;
        }
        checkDisableFileExist();
        if (isSdkDisabled()) {
            com.wt.statistics.utils.SLog.i(LOGTAG, "sdk is disabled !");
            return;
        }
        mContext = context.getApplicationContext();
        if (android.text.TextUtils.isEmpty(appID)) {
            appID = com.wt.statistics.utils.WTUtils.getAppIDByXml(mContext);
            if (android.text.TextUtils.isEmpty(appID)) {
                android.util.Log.e(LOGTAG, "init appID is null");
                setSdkDisabled(true);
                return;
            }
        }
        initCommonInfo(appID, channelID);
        if (android.text.TextUtils.isEmpty(mCommonBean.tokenBean.token)) {
            com.wt.statistics.StatisticsManager.getInstance(mContext).getToken();
        }
        com.wt.statistics.StatisticsManager.getInstance(mContext).getServerConfig();
    }

    private static void checkDisableFileExist() {
        try {
            java.io.File file = new java.io.File("/sdcard/data/com.wt.statistics/files/sdk.disable");
            isDisableFileExist = file.exists();
        } catch (java.lang.Throwable th) {
        }
    }

    private static com.wt.statistics.persistentdata.CommonBean initCommonInfo(java.lang.String appKey, java.lang.String channelID) {
        if (android.text.TextUtils.isEmpty(channelID)) {
            channelID = com.wt.statistics.utils.WTUtils.getChannelIDByXml(mContext);
            if (android.text.TextUtils.isEmpty(channelID)) {
                channelID = com.wt.statistics.persistentdata.CommonBean.UNKNOW;
            }
        }
        mCommonBean = com.wt.statistics.persistentdata.CommonDataPreference.getInstance(mContext).pullFromPref(com.wt.statistics.persistentdata.CommonBean.class);
        java.lang.String app_version = com.wt.statistics.utils.WTUtils.getAppVersionName(mContext);
        java.lang.String os_verison = com.wt.statistics.utils.WTUtils.getOSVersion();
        if (mCommonBean == null) {
            mCommonBean = new com.wt.statistics.persistentdata.CommonBean();
            mCommonBean.app_id = appKey;
            mCommonBean.channel_id = channelID;
            mCommonBean.app_version = app_version;
            mCommonBean.os_verison = os_verison;
            mCommonBean.car_type = com.wt.statistics.utils.WTUtils.getCurCarType();
            mCommonBean.vin = com.wt.statistics.utils.WTUtils.getProp("persist.sys.mcutoapp.vin");
            java.lang.String mac = com.wt.statistics.utils.WTUtils.getMac(mContext);
            mCommonBean.vin += "," + mac;
            com.wt.statistics.utils.SLog.i(LOGTAG, "mCommonBean.vin = " + mCommonBean.vin);
            if (android.text.TextUtils.isEmpty(mCommonBean.vin)) {
                mCommonBean.vin = com.wt.statistics.persistentdata.CommonBean.UNKNOW;
            }
        } else {
            mCommonBean.app_version = app_version;
            mCommonBean.os_verison = os_verison;
        }
        com.wt.statistics.utils.SLog.i(LOGTAG, "getCommonInfo mCommonBean.vin=" + mCommonBean.vin + ";mCommonBean.app_version=" + mCommonBean.app_version + ";mCommonBean.car_type=" + mCommonBean.car_type + ";mCommonBean.os_verison=" + mCommonBean.os_verison);
        com.wt.statistics.persistentdata.CommonDataPreference.getInstance(mContext).pushToPref(mCommonBean);
        return mCommonBean;
    }

    public static synchronized com.wt.statistics.persistentdata.CommonBean getCommonBean() {
        com.wt.statistics.persistentdata.CommonBean commonBean;
        synchronized (com.wt.common.WTConfigure.class) {
            if (mCommonBean != null) {
                commonBean = mCommonBean;
            } else {
                com.wt.statistics.utils.SLog.i(LOGTAG, "此种情况理论来说不应该发生");
                java.lang.String appKey = com.wt.statistics.utils.WTUtils.getAppIDByXml(mContext);
                if (android.text.TextUtils.isEmpty(appKey)) {
                    setSdkDisabled(true);
                    com.wt.statistics.utils.SLog.i(LOGTAG, "getCommonBean appID is null");
                }
                initCommonInfo("", "");
                commonBean = mCommonBean;
            }
        }
        return commonBean;
    }

    public static synchronized void setCommonBean(com.wt.statistics.persistentdata.CommonBean bean) {
        synchronized (com.wt.common.WTConfigure.class) {
            mCommonBean = bean;
        }
    }

    public static void setLogEnabled(boolean enable) {
        isDebugLogEnabled = enable;
    }

    public static boolean isLogEnabled() {
        return isDebugLogEnabled;
    }

    public static void setEncryptEnabled(boolean enabled) {
        isEncryptEnabled = enabled;
    }

    public boolean isEncryptEnabled() {
        return isEncryptEnabled;
    }

    public static void setSdkDisabled(boolean enabled) {
        isSdkDisabled = enabled;
    }

    public static boolean isSdkDisabled() {
        return isSdkDisabled || isDisableFileExist;
    }

    public void setSessionIntervalTime(int sessionIntervalTime) {
        mCommonBean.session_time = sessionIntervalTime;
        com.wt.statistics.utils.SLog.i(LOGTAG, "setSessionIntervalTime bean.id" + mCommonBean.app_id);
        com.wt.statistics.persistentdata.CommonDataPreference.getInstance(mContext).pushToPref(mCommonBean);
    }

    public static void setCatchUncaughtExceptions(boolean isEnable) {
    }

    public static boolean getCatchUncaughtExceptions() {
        return false;
    }
}
