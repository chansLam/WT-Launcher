package com.autopai.support.utils;

/* loaded from: classes.dex */
public class CommonUtil {
    public static final java.lang.String ACTION_AIR_CLOSE = "com.incall.apps.airvideo.close";
    public static final java.lang.String ACTION_AIR_OPEN = "com.incall.apps.airvideo.open";
    public static final java.lang.String ACTION_THEME_CHANGE = "com.incall.action.ThemeChange";
    public static final java.lang.String AIRCLEAN_SERVICE_NAME = "incall.airclean.service";
    public static final java.lang.String AIRCONDITION_SERVICE_NAME = "incall.aircondition.service";
    public static final java.lang.String CALL_SERVICE_NAME = "incall.callcenter.service";
    public static final java.lang.String FLOAT_SERVICE_NAME = "incall.float.service";
    public static final java.lang.String KUWO_SERVICE_NAME = "incall.kuwo.service";
    public static final int MAINTENANCE_CIRCLE = 2;
    public static final int MAINTENANCE_CURRENT_DISTANCE = 5;
    public static final int MAINTENANCE_DISTANCE = 3;
    public static final int MAINTENANCE_NOTIFY = 1;
    public static final java.lang.String MAINTENANCE_SERVICE_NAME = "incall.maintenance.service";
    public static final int MAINTENANCE_TIME = 4;
    public static final java.lang.String MSGCENTER_SERVICE_NAME = "incall.msgcenter.service";
    public static final int MSG_CIRCLE_DISTANCE = 4;
    public static final int MSG_NOTIFY = 3;
    public static final int MSG_RELEASE_DISTANCE = 2;
    public static final int MSG_RELEASE_TIME = 1;
    public static final int MSG_RESET = 8;
    public static final int MSG_SAVE_CIRCLE = 6;
    public static final int MSG_SAVE_DISTANCE = 7;
    public static final int MSG_SAVE_NOTIFY = 5;
    public static final java.lang.String PHONE_SERVICE_NAME = "incall.btphone.service";
    public static final java.lang.String RADIO_SERVICE_NAME = "incall.radio.service";
    public static final java.lang.String RECORDER_SERVICE_NAME = "incall.recorder.service";
    public static final java.lang.String SERVICE_NAME = "incall.c301.service";
    public static final java.lang.String SETTING_SERVICE_NAME = "incall.setting.service";
    public static final java.lang.String SRCEEN_SERVICE_NAME = "incall.srceen.service";
    public static final java.lang.String TOKEN_SERVICE_NAME = "incall.token.service";
    public static final java.lang.String UPDATE_SERVICE_NAME = "incall.update.service";
    public static final java.lang.String USER_SERVICE_NAME = "incall.user.service";
    public static final java.lang.String VEHICLE_LEVEL2_NAME = "L2";
    public static final java.lang.String VEHICLE_LEVEL3_NAME = "L3";
    public static final java.lang.String VEHICLE_LEVEL4_NAME = "L4";
    private static com.autopai.support.utils.CommonUtil commonUtil = null;
    public static final java.lang.String strCA_C211_CarType_1 = "CA_C211_CARTYPE_1";
    public static final java.lang.String strCA_C211_CarType_2 = "CA_C211_CARTYPE_2";
    public static final java.lang.String strCA_C211_CarType_3 = "CA_C211_CARTYPE_3";
    public static final java.lang.String strCA_C211_CarType_4 = "CA_C211_CARTYPE_4";
    public static final java.lang.String strCA_C211_CarType_5 = "CA_C211_CARTYPE_5";
    public static final java.lang.String strCA_C211_CarType_6 = "CA_C211_CARTYPE_6";
    public static final java.lang.String strCA_C211_CarType_7 = "CA_C211_CARTYPE_7";
    public static final java.lang.String strCA_C211_CarType_8 = "CA_C211_CARTYPE_8";

    public static synchronized com.autopai.support.utils.CommonUtil getInstance() {
        com.autopai.support.utils.CommonUtil commonUtil2;
        synchronized (com.autopai.support.utils.CommonUtil.class) {
            if (commonUtil == null) {
                commonUtil = new com.autopai.support.utils.CommonUtil();
            }
            commonUtil2 = commonUtil;
        }
        return commonUtil2;
    }

    public boolean isCunrrentApp(android.content.Context context, java.lang.String packname) {
        if (android.text.TextUtils.isEmpty(packname)) {
            return false;
        }
        android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(com.wt.statistics.persistentdata.EventConstants.EVENTS_ACTIVITY_TYPE);
        java.util.List<android.app.ActivityManager.RunningTaskInfo> taskinfo = activityManager.getRunningTasks(1);
        return !taskinfo.isEmpty() && taskinfo.get(0).topActivity.getPackageName().equals(packname);
    }

    public boolean isForeground(android.content.Context context, java.lang.String className) {
        if (android.text.TextUtils.isEmpty(className)) {
            return false;
        }
        android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(com.wt.statistics.persistentdata.EventConstants.EVENTS_ACTIVITY_TYPE);
        java.util.List<android.app.ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            android.content.ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public boolean getAppProcessState(android.content.Context context, java.lang.String appPackageName) {
        if (android.text.TextUtils.isEmpty(appPackageName)) {
            return false;
        }
        android.app.ActivityManager aManager = (android.app.ActivityManager) context.getSystemService(com.wt.statistics.persistentdata.EventConstants.EVENTS_ACTIVITY_TYPE);
        java.util.List<android.app.ActivityManager.RunningAppProcessInfo> processInfos = aManager.getRunningAppProcesses();
        for (android.app.ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (appPackageName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
