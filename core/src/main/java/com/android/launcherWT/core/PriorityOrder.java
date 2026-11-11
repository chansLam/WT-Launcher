package com.android.launcherWT.core;

public final class PriorityOrder {
    private static final String[][] COMPONENTS = {
            {"com.incall.apps.airconditioner", "com.incall.apps.airconditioner.MainActivity"},
            {"com.incall.apps.vehiclesetting", "com.incall.apps.vehiclesetting.VehicleActivity"},
            {"com.incall.apps.btphone", "com.incall.apps.btphone.activity.BtActivity"},
            {"com.coagent.dvr", "com.coagent.dvr.DvrActivity"},
            {"com.incall.apps.callcenter", "com.incall.apps.callcenter.activity.CallCenterActivity"},
            {"com.incall.apps.setting", "com.incall.apps.setting.MainActivity"},
            {"com.incall.apps.filemanager", "com.incall.apps.filemanager.activity.FileMainActivity"},
            {"com.incall.apps.usercenter", "com.incall.apps.usercenter.MainActivity"},
            {"com.incall.apps.softmanager", "com.incall.apps.fota.MainActivity"},
            {"com.tencent.wecarnavi", "com.tencent.wecarnavi.MainActivity"},
            {"com.tencent.wecarmusicp", "com.tencent.wecarmusic.activity.MainActivity"},
            {"com.tencent.wecarnews", "com.tencent.wecarnews.UI.home.NewsMainTab"}
    };

    private PriorityOrder() {
    }

    public static int rank(String packageName, String className) {
        for (int i = 0; i < COMPONENTS.length; i++) {
            if (COMPONENTS[i][0].equals(packageName) && COMPONENTS[i][1].equals(className)) {
                return i;
            }
        }
        return COMPONENTS.length;
    }
}
