package com.android.launcherWT.home;

import android.content.Context;

import com.android.launcherWT.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Fixed desktop from LauncherWT 1.8.17.23. Each preset keeps the classic,
 * sport and simple icon from that package.
 */
public final class FavoriteCatalog {
    static final int THEME_CLASSIC = 0;
    static final int THEME_SPORT = 1;
    static final int THEME_SIMPLE = 2;

    static final class Entry {
        final String packageName;
        final String className;
        final String titleZh;
        final String titleEn;
        final int classicIcon;
        final int sportIcon;
        final int simpleIcon;

        Entry(String packageName, String className, String titleZh, String titleEn,
                int classicIcon, int sportIcon, int simpleIcon) {
            this.packageName = packageName;
            this.className = className;
            this.titleZh = titleZh;
            this.titleEn = titleEn;
            this.classicIcon = classicIcon;
            this.sportIcon = sportIcon;
            this.simpleIcon = simpleIcon;
        }

        String title() {
            if ("en".equals(Locale.getDefault().getLanguage())) {
                return titleEn;
            }
            return titleZh;
        }

        String key() {
            if (className == null) {
                return packageName;
            }
            return packageName + "/" + className;
        }

        int icon(int theme) {
            if (theme == THEME_SPORT) {
                return sportIcon;
            }
            if (theme == THEME_SIMPLE) {
                return simpleIcon;
            }
            return classicIcon;
        }
    }

    private FavoriteCatalog() {
    }

    static List<Entry> entries() {
        List<Entry> list = new ArrayList<Entry>();
        list.add(entry("com.tencent.wecarnavi", null, "腾讯地图", "Tencent Map",
                R.drawable.classic_icon_map, R.drawable.sport_icon_map, R.drawable.simple_icon_map));
        list.add(entry("com.wt.music", null, "QQ音乐", "QQ Music",
                R.drawable.icon_music, R.drawable.icon_music, R.drawable.icon_music));
        list.add(entry("com.tencent.wecarnews", null, "腾讯车载电台", "Tencent Radio",
                R.drawable.classic_icon_radio, R.drawable.sport_icon_radio, R.drawable.simple_icon_radio));
        list.add(entry("com.eryanet.vlink", null, "智慧互联", "CarAuto",
                R.drawable.classic_icon_eryanet_link, R.drawable.sport_icon_eryanet_link, R.drawable.simple_icon_eryanet_link));
        list.add(entry("com.incall.apps.airconditioner", null, "空调", "Air Conditioner",
                R.drawable.classic_icon_airconditioner, R.drawable.sport_icon_airconditioner, R.drawable.simple_icon_airconditioner));
        list.add(entry("com.autonavi.amapauto", null, "高德地图", "amap auto",
                R.drawable.icon_amap, R.drawable.icon_amap, R.drawable.icon_amap));
        list.add(entry("com.incall.apps.btphone", null, "电话", "Phone",
                R.drawable.classic_icon_phone, R.drawable.sport_icon_phone, R.drawable.simple_icon_phone));
        list.add(entry("com.coagent.dvr", null, "环视记录仪", "Circle view record",
                R.drawable.classic_icon_car_dvr_360, R.drawable.sport_icon_car_dvr_360, R.drawable.simple_icon_car_dvr_360));
        list.add(entry("com.incall.apps.vehiclesetting", null, "车辆设置", "Vehicle Setting",
                R.drawable.classic_icon_vehicle_setting, R.drawable.sport_icon_vehicle_setting, R.drawable.simple_icon_vehicle_setting));
        list.add(entry("com.incall.apps.setting", null, "系统设置", "Setting",
                R.drawable.classic_icon_system_settings, R.drawable.sport_icon_system_settings, R.drawable.simple_icon_system_settings));
        list.add(entry("com.incall.apps.comfortcockpit", null, "舒适氛围", "ComfortCockpit",
                R.drawable.classic_icon_comfort_cockpit, R.drawable.sport_icon_comfort_cockpit, R.drawable.simple_icon_comfort_cockpit));
        list.add(entry("com.incall.apps.softmanager", null, "系统升级", "FOTA",
                R.drawable.classic_icon_soft_manager, R.drawable.sport_icon_soft_manager, R.drawable.simple_icon_soft_manager));
        list.add(entry("com.incall.apps.filemanager", null, "文件管理", "File",
                R.drawable.classic_icon_file_manager, R.drawable.sport_icon_file_manager, R.drawable.simple_icon_file_manager));
        list.add(entry("com.incall.apps.callcenter", null, "一点通", "CallCenter",
                R.drawable.ic_apps_icon_call_center, R.drawable.ic_apps_icon_call_center, R.drawable.ic_apps_icon_call_center));
        list.add(entry("com.tencent.wecarspeech", "com.tencent.wecarspeech.app.MoreActivity", "语音设置", "Voice Assistant",
                R.drawable.classic_icon_speech, R.drawable.sport_icon_speech, R.drawable.simple_icon_speech));
        return list;
    }

    static int background(int theme) {
        if (theme == THEME_SPORT) {
            return R.drawable.sport_bg;
        }
        if (theme == THEME_SIMPLE) {
            return R.drawable.simple_bg;
        }
        return R.drawable.classic_bg;
    }

    /**
     * Setting service theme: 1 classic, 2 sport, 3 simple.
     * A missing service stays on classic, which is the APK default.
     */
    public static int theme(Context context) {
        try {
            Class<?> manager = Class.forName("com.incall.apps.service.setting.SettingServiceManager");
            Object instance = manager.getMethod("getInstance").invoke(null);
            int value = ((Integer) manager.getMethod("getTheme").invoke(instance)).intValue();
            if (value == 0) {
                value = ((Integer) manager.getMethod("getTheme", Context.class).invoke(instance, context)).intValue();
            }
            if (value == 2) {
                return THEME_SPORT;
            }
            if (value == 3) {
                return THEME_SIMPLE;
            }
        } catch (Throwable ignored) {
            return THEME_CLASSIC;
        }
        return THEME_CLASSIC;
    }

    private static Entry entry(String packageName, String className, String titleZh, String titleEn,
            int classicIcon, int sportIcon, int simpleIcon) {
        return new Entry(packageName, className, titleZh, titleEn, classicIcon, sportIcon, simpleIcon);
    }
}
