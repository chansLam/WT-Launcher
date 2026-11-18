package com.incall.apps.common.utils;

/* loaded from: classes.dex */
public class CommonUtil {
    public static final java.lang.String ACTION_AIR_BOTTOM_VIEW_HIDE = "com.incall.action.AirBottomView.Hide";
    public static final java.lang.String ACTION_AIR_BOTTOM_VIEW_SHOW = "com.incall.action.AirBottomView.Show";
    public static final java.lang.String ACTION_AIR_CLEANER_CHANGE = "com.incall.apps.aircleaner.change";
    public static final java.lang.String ACTION_AIR_CLOSE = "com.incall.apps.airvideo.close";
    public static final java.lang.String ACTION_AIR_OPEN = "com.incall.apps.airvideo.open";
    public static final java.lang.String ACTION_CAMERA_CHANGE = "com.incall.apps.setting.camera";
    public static final java.lang.String ACTION_CARLIFE_ACTIVITY = "com.incall.apps.carlife";
    public static final java.lang.String ACTION_KUWO_ACTIVITY = "com.apps.kuwo.OPEN";
    public static final java.lang.String ACTION_MOVIE_ACTIVITY = "com.incall.apps.filemanager";
    public static final java.lang.String ACTION_MUSIC_ACTIVITY = "com.incall.apps.music";
    public static final java.lang.String ACTION_NOTIFY_CHANGE = "com.incall.apps.setting.notify";
    public static final java.lang.String ACTION_PLAY_CHANGE = "com.incall.apps.playing.change";
    public static final java.lang.String ACTION_QQMUSIC_ACTIVITY = "com.tencent.wecarmusicp";
    public static final java.lang.String ACTION_QQRADIO_ACTIVITY = "com.tencent.wecarnews";
    public static final java.lang.String ACTION_RADIO_ACTIVITY = "com.incall.apps.radio";
    public static final java.lang.String ACTION_STATUS_BAR_HIDE = "com.incall.action.StatusBar.Hide";
    public static final java.lang.String ACTION_STATUS_BAR_SHOW = "com.incall.action.StatusBar.Show";
    public static final java.lang.String ACTION_THEME_CHANGE = "com.incall.action.ThemeChange";
    public static final java.lang.String ACTION_USB_CHANGE = "com.coagent.scanStatus";
    public static final java.lang.String AIRCLEAN_SERVICE_NAME = "incall.airclean.service";
    public static final java.lang.String AIRCONDITION_SERVICE_NAME = "incall.aircondition.service";
    public static final int AIR_CLEAR_CLOSE = 0;
    public static final java.lang.String AIR_CLEAR_LEVEL = "air_cleanner_level";
    public static final int AIR_CLEAR_OPEN = 1;
    public static final java.lang.String AIR_CLEAR_STATE = "air_cleanner_state";
    public static final java.lang.String AI_SERVICE_NAME = "incall.airecommend.service";
    public static final java.lang.String CALL_SERVICE_NAME = "incall.callcenter.service";
    public static final java.lang.String CAR_STATUS_SERVICE_NAME = "incall.canstatus.service";
    public static final java.lang.String CA_B211_CAR_TYPE_10 = "CA_B211_CARTYPE_10";
    public static final java.lang.String CA_B211_CAR_TYPE_6 = "CA_B211_CARTYPE_6";
    public static final java.lang.String CA_B211_CAR_TYPE_7 = "CA_B211_CARTYPE_7";
    public static final java.lang.String CA_B211_CAR_TYPE_8 = "CA_B211_CARTYPE_8";
    public static final java.lang.String CA_B211_CAR_TYPE_9 = "CA_B211_CARTYPE_9";
    public static final java.lang.String COMMON_VIEW_SHOW = "incall.commonview.show,service";
    public static final java.lang.String ELF_SERVICE_NAME = "incall.elf.service";
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
    public static final java.lang.String MUSIC_SERVICE_NAME = "incall.music.service";
    public static final java.lang.String NAME = "PLAYNAME";
    public static final java.lang.String PHONE_SERVICE_NAME = "incall.btphone.service";
    public static final java.lang.String RADIO_SERVICE_NAME = "incall.radio.service";
    public static final java.lang.String RECORDER_SERVICE_NAME = "incall.recorder.service";
    public static final java.lang.String SERVICE_NAME = "incall.c301.service";
    public static final java.lang.String SETTING_SERVICE_NAME = "incall.setting.service";
    public static final java.lang.String SRCEEN_SERVICE_NAME = "incall.srceen.service";
    public static final java.lang.String STATE = "PLAYSTATE";
    public static final int STATE_PAUSE = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_STOP = -1;
    public static final int THEME_TYPE1 = 1;
    public static final int THEME_TYPE2 = 2;
    public static final int THEME_TYPE3 = 3;
    public static final java.lang.String TOKEN_SERVICE_NAME = "incall.token.service";
    public static final java.lang.String TYPE = "PLAYTYPE";
    public static final java.lang.String TYPE_CARLIFE = "CARLIFE";
    public static final java.lang.String TYPE_MOVIE = "MOVIE";
    public static final java.lang.String TYPE_MUSIC = "MUSIC";
    public static final java.lang.String TYPE_RADIO = "RADIO";
    public static final java.lang.String UPDATE_SERVICE_NAME = "incall.update.service";
    public static final java.lang.String USER_SERVICE_NAME = "incall.user.service";
    public static final java.lang.String VEHICLE_LEVEL2_NAME = "L2";
    public static final java.lang.String VEHICLE_LEVEL3_NAME = "L3";
    public static final java.lang.String VEHICLE_LEVEL4_NAME = "L4";
    private static com.incall.apps.common.utils.CommonUtil commonUtil = null;
    public static final java.lang.String strCA_C211_CarType_1 = "CA_C211_CARTYPE_1";
    public static final java.lang.String strCA_C211_CarType_2 = "CA_C211_CARTYPE_2";
    public static final java.lang.String strCA_C211_CarType_3 = "CA_C211_CARTYPE_3";
    public static final java.lang.String strCA_C211_CarType_4 = "CA_C211_CARTYPE_4";
    public static final java.lang.String strCA_C211_CarType_5 = "CA_C211_CARTYPE_5";
    public static final java.lang.String strCA_C211_CarType_6 = "CA_C211_CARTYPE_6";
    public static final java.lang.String strCA_C211_CarType_7 = "CA_C211_CARTYPE_7";
    public static final java.lang.String strCA_C211_CarType_8 = "CA_C211_CARTYPE_8";
    public static final java.lang.String strCA_S201_6AT_LEV1 = "S201_6AT_LEV1";
    public static final java.lang.String strCA_S201_6AT_LEV2 = "S201_6AT_LEV2";
    public static final java.lang.String strCA_S201_6AT_LEV3 = "S201_6AT_LEV3";
    public static final java.lang.String strCA_S201_6AT_LEV4 = "S201_6AT_LEV4";
    public static final java.lang.String strCA_S201_6MT_LEV1 = "S201_6MT_LEV1";
    public static final java.lang.String strCA_S201_6MT_LEV2 = "S201_6MT_LEV2";
    public static final java.lang.String strCA_S201_6MT_LEV3 = "S201_6MT_LEV3";
    public static final java.lang.String strCA_S201_6MT_LEV4 = "S201_6MT_LEV4";
    public static final java.lang.String strCA_S201_7DCT_LEV1 = "S201_7DCT_LEV1";
    public static final java.lang.String strCA_S201_7DCT_LEV2 = "S201_7DCT_LEV2";
    public static final java.lang.String strCA_S201_7DCT_LEV3 = "S201_7DCT_LEV3";

    public static synchronized com.incall.apps.common.utils.CommonUtil getInstance() {
        com.incall.apps.common.utils.CommonUtil commonUtil2;
        synchronized (com.incall.apps.common.utils.CommonUtil.class) {
            if (commonUtil == null) {
                commonUtil = new com.incall.apps.common.utils.CommonUtil();
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

    public static void updateActivityBackground(android.app.Activity activity, int themeType) {
        switch (themeType) {
            case 1:
                activity.getWindow().getDecorView().setBackgroundResource(com.android.launcherWT.R.drawable.common_bg_theme_classic);
                break;
            case 2:
                activity.getWindow().getDecorView().setBackgroundResource(com.android.launcherWT.R.drawable.common_bg_theme_sport);
                break;
            case 3:
                activity.getWindow().getDecorView().setBackgroundResource(com.android.launcherWT.R.drawable.common_bg_theme_simple);
                break;
        }
    }

    public static java.lang.String getApkVersionName(android.content.Context context) {
        try {
            java.lang.String nameString = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return nameString;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getApkVersionCode(android.content.Context context) {
        try {
            int code = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            return code;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
