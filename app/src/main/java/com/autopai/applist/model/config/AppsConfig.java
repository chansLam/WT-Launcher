package com.autopai.applist.model.config;

/* loaded from: classes.dex */
public class AppsConfig {
    public static final java.lang.String AMAP_NAVI = "com.autonavi.amapauto";
    public static final java.lang.String APPS_AIR_CONDITIONER = "com.incall.apps.airconditioner";
    public static final java.lang.String APPS_CALL_CENTER = "com.incall.apps.callcenter";
    public static final java.lang.String APPS_CAR_3D_CONTROL = "com.tencent.wecarcontrol";
    public static final java.lang.String APPS_CAR_DVR_360 = "com.coagent.dvr";
    public static final java.lang.String APPS_CAR_SETTING = "com.incall.apps.vehiclesetting";
    public static final java.lang.String APPS_COMFORT_COCKPIT = "com.incall.apps.comfortcockpit";
    public static final java.lang.String APPS_ERYANET_LINK = "com.eryanet.vlink";
    public static final java.lang.String APPS_FILE_MANAGER = "com.incall.apps.filemanager";
    public static final java.lang.String APPS_INSTRUCTIONS = "com.incall.apps.instructions";
    public static final int APPS_LIST_COLUMNS = 5;
    public static final int APPS_LIST_ROWS = 2;
    public static final java.lang.String APPS_MAINTAIN = "cn.com.changan.maintain";
    public static final java.lang.String APPS_MUSIC = "com.wt.music";
    public static final java.lang.String APPS_NAVI = "com.estrongs.android.pop";
    public static final java.lang.String APPS_PHONE = "com.incall.apps.btphone";
    public static final java.lang.String APPS_RADIO = "com.shihab.diplay";
    public static final java.lang.String APPS_SOFT_MANAGER = "com.incall.apps.softmanager";
    public static final java.lang.String APPS_SYSTEM_SETTING = "com.incall.apps.setting";
    public static final java.lang.String APPS_TENCENT_APPLET = "com.tencent.wecarmas";
    public static final java.lang.String APPS_TENCENT_WECARSPEECH = "com.tencent.wecarspeech";
    public static final java.lang.String APPS_TENCENT_WECARSPEECH_MORE = "com.tencent.wecarspeech/com.tencent.wecarspeech.app.MoreActivity";
    public static final java.lang.String APPS_TENCENT_WECARSPEECH_MORE_CLASSNAME = "com.tencent.wecarspeech.app.MoreActivity";
    public static final java.lang.String APPS_USER_CENTER = "com.incall.apps.personalcenter";
    public static final int APP_DRAWABLE_PADDING = 32;
    public static final java.lang.String APP_ICONS_DB = "app_icons.db";
    public static final int APP_ICON_SIZE = 136;
    public static final int APP_TEXT_SIZE = 32;
    public static final int BADGE_OFFSET_X = 44;
    public static final int BADGE_OFFSET_Y = 47;
    public static final int BADGE_RADIO = 60;
    public static final int BUBBLE_TEXTVIEW_PADDING_TOP = 34;
    public static final java.lang.String DEFAULT_WALLPAPER_THUMBNAIL = "default_thumb2.jpg";
    public static final java.lang.String DEFAULT_WALLPAPER_THUMBNAIL_OLD = "default_thumb.jpg";
    public static final boolean FILTER_APP_LIST;
    public static final float ICON_BITMAP_SIZE = 138.0f;
    public static final int ITEM_HEIGHT = 264;
    public static final int ITEM_MARGIN_BOTTOM = 24;
    public static final int ITEM_MARGIN_LEFT = 56;
    public static final int ITEM_MARGIN_RIGHT = 32;
    public static final int ITEM_MARGIN_TOP = 24;
    public static final int ITEM_WIDTH = 264;
    public static final java.lang.String KEY_BADGE = "badge";
    public static final java.lang.String LAUNCHER_DB = "launcher.db";
    public static final java.lang.String MANAGED_USER_PREFERENCES_KEY = "com.autopai.apps.launcher2X.managedusers.prefs";
    public static final int MARGIN_BOTTOM = 84;
    public static final int MARGIN_LEFT = 80;
    public static final int MARGIN_RIGHT = 80;
    public static final int MARGIN_TOP = 60;
    public static final int MARKER_HEIGHT = 48;
    public static final int MARKER_ITEM_PADDING = 50;
    public static final int MARKER_LEFT_MARGIN = 0;
    public static final int MARKER_LONG_ITEM_LENGTH = 100;
    public static final int MARKER_SHORT_ITEM_LENGTH = 50;
    public static final int MARKER_STROKE_WIDTH = 4;
    public static final int MARKER_TOP_MARGIN = 0;
    public static final java.lang.String RESOURCE_CONFIG_PACKAGE_NAME = "com.android.launcherconfig";
    public static final int RETURN_IMG_MARGIN_LEFT = 36;
    public static final int RETURN_IMG_MARGIN_TOP = 12;
    public static final java.lang.String SHARED_PREFERENCES_KEY = "com.autopai.apps.launcher2X.prefs";
    public static final float SLIDE_RATIO_OF_SCREEN = 0.2f;
    public static final java.lang.String START_SPEECH = "com.tencent.wecarspeech.engine_init_broadcast";
    public static final java.lang.String TAG = "AppsConfig";
    public static final java.lang.String WALLPAPER_CROP_PREFERENCES_KEY = "com.autopai.apps.launcher2X.WallpaperCropActivity";
    public static final java.lang.String WALLPAPER_IMAGES_DB = "saved_wallpaper_images.db";
    public static final java.lang.String WIDGET_PREVIEWS_DB = "widgetpreviews.db";
    public static final java.lang.String WT_SPEECH_READY = "wt_speech_ready";
    private static final java.lang.String XML = ".xml";
    public static final java.util.ArrayList<java.lang.String> favoritesAppList;
    public static java.util.ArrayList<java.lang.String> listIcon;
    public static java.util.ArrayList<java.lang.String> listPackage;
    private static android.util.SparseArray<java.lang.String> mBackgrounds;
    public static java.lang.String mCarType;
    private static java.lang.String mClassicBackground;
    public static final java.util.LinkedHashMap<java.lang.String, java.lang.String> mClassicConfigAppMap;
    public static java.lang.String mConfigBackground;
    private static int mCurrentTheme;
    private static java.lang.String[] mDefaultMarker;
    public static final java.util.ArrayList<java.lang.String> mDisableAppsList;
    public static final java.util.ArrayList<java.lang.String> mDisableCarTypeList;
    private static android.util.SparseArray<java.lang.String[]> mMarkers;
    public static android.content.res.Resources mResourceConfig;
    private static java.lang.String mSimpleBackground;
    public static final java.util.LinkedHashMap<java.lang.String, java.lang.String> mSimpleConfigAppMap;
    private static java.lang.String mSportBackground;
    public static final java.util.LinkedHashMap<java.lang.String, java.lang.String> mSportConfigAppMap;
    public static final java.util.ArrayList<java.lang.String> packageEnNameAppList;
    public static final java.util.ArrayList<java.lang.String> packageNameAppList;
    private static android.util.SparseArray<java.util.LinkedHashMap<java.lang.String, java.lang.String>> sIcons;

    static {
        FILTER_APP_LIST = !showTest();
        listIcon = new java.util.ArrayList<>();
        listPackage = new java.util.ArrayList<>();
        mClassicConfigAppMap = new java.util.LinkedHashMap() { // from class: com.autopai.applist.model.config.AppsConfig.1
            {
                put(com.autopai.applist.model.config.AppsConfig.APPS_NAVI, "classic_icon_map");
                put(com.autopai.applist.model.config.AppsConfig.APPS_MUSIC, "icon_music");
                put(com.autopai.applist.model.config.AppsConfig.APPS_RADIO, "classic_icon_radio");
                put(com.autopai.applist.model.config.AppsConfig.APPS_ERYANET_LINK, "classic_icon_eryanet_link");
                put(com.autopai.applist.model.config.AppsConfig.APPS_AIR_CONDITIONER, "classic_icon_airconditioner");
                put(com.autopai.applist.model.config.AppsConfig.AMAP_NAVI, "icon_amap");
                put(com.autopai.applist.model.config.AppsConfig.APPS_PHONE, "classic_icon_phone");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CAR_DVR_360, "classic_icon_car_dvr_360");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CAR_SETTING, "classic_icon_vehicle_setting");
                put(com.autopai.applist.model.config.AppsConfig.APPS_SYSTEM_SETTING, "classic_icon_system_settings");
                put(com.autopai.applist.model.config.AppsConfig.APPS_COMFORT_COCKPIT, "classic_icon_comfort_cockpit");
                put(com.autopai.applist.model.config.AppsConfig.APPS_SOFT_MANAGER, "classic_icon_soft_manager");
                put("com.incall.apps.filemanager", "classic_icon_file_manager");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CALL_CENTER, "ic_apps_icon_call_center");
                put(com.autopai.applist.model.config.AppsConfig.APPS_TENCENT_WECARSPEECH_MORE, "classic_icon_speech");
            }
        };
        mSportConfigAppMap = new java.util.LinkedHashMap() { // from class: com.autopai.applist.model.config.AppsConfig.2
            {
                put(com.autopai.applist.model.config.AppsConfig.APPS_NAVI, "sport_icon_map");
                put(com.autopai.applist.model.config.AppsConfig.APPS_MUSIC, "icon_music");
                put(com.autopai.applist.model.config.AppsConfig.APPS_RADIO, "sport_icon_radio");
                put(com.autopai.applist.model.config.AppsConfig.APPS_ERYANET_LINK, "sport_icon_eryanet_link");
                put(com.autopai.applist.model.config.AppsConfig.APPS_AIR_CONDITIONER, "sport_icon_airconditioner");
                put(com.autopai.applist.model.config.AppsConfig.AMAP_NAVI, "icon_amap");
                put(com.autopai.applist.model.config.AppsConfig.APPS_PHONE, "sport_icon_phone");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CAR_DVR_360, "sport_icon_car_dvr_360");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CAR_SETTING, "sport_icon_vehicle_setting");
                put(com.autopai.applist.model.config.AppsConfig.APPS_SYSTEM_SETTING, "sport_icon_system_settings");
                put(com.autopai.applist.model.config.AppsConfig.APPS_COMFORT_COCKPIT, "sport_icon_comfort_cockpit");
                put(com.autopai.applist.model.config.AppsConfig.APPS_SOFT_MANAGER, "sport_icon_soft_manager");
                put("com.incall.apps.filemanager", "sport_icon_file_manager");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CALL_CENTER, "ic_apps_icon_call_center");
                put(com.autopai.applist.model.config.AppsConfig.APPS_TENCENT_WECARSPEECH_MORE, "sport_icon_speech");
            }
        };
        mSimpleConfigAppMap = new java.util.LinkedHashMap() { // from class: com.autopai.applist.model.config.AppsConfig.3
            {
                put(com.autopai.applist.model.config.AppsConfig.APPS_NAVI, "simple_icon_map");
                put(com.autopai.applist.model.config.AppsConfig.APPS_MUSIC, "icon_music");
                put(com.autopai.applist.model.config.AppsConfig.APPS_RADIO, "simple_icon_radio");
                put(com.autopai.applist.model.config.AppsConfig.APPS_ERYANET_LINK, "simple_icon_eryanet_link");
                put(com.autopai.applist.model.config.AppsConfig.APPS_AIR_CONDITIONER, "simple_icon_airconditioner");
                put(com.autopai.applist.model.config.AppsConfig.AMAP_NAVI, "icon_amap");
                put(com.autopai.applist.model.config.AppsConfig.APPS_PHONE, "simple_icon_phone");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CAR_DVR_360, "simple_icon_car_dvr_360");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CAR_SETTING, "simple_icon_vehicle_setting");
                put(com.autopai.applist.model.config.AppsConfig.APPS_SYSTEM_SETTING, "simple_icon_system_settings");
                put(com.autopai.applist.model.config.AppsConfig.APPS_COMFORT_COCKPIT, "simple_icon_comfort_cockpit");
                put(com.autopai.applist.model.config.AppsConfig.APPS_SOFT_MANAGER, "simple_icon_soft_manager");
                put("com.incall.apps.filemanager", "simple_icon_file_manager");
                put(com.autopai.applist.model.config.AppsConfig.APPS_CALL_CENTER, "ic_apps_icon_call_center");
                put(com.autopai.applist.model.config.AppsConfig.APPS_TENCENT_WECARSPEECH_MORE, "simple_icon_speech");
            }
        };
        packageNameAppList = new java.util.ArrayList<java.lang.String>() { // from class: com.autopai.applist.model.config.AppsConfig.4
            {
                add("腾讯地图");
                add("QQ音乐");
                add("腾讯车载电台");
                add("智慧互联");
                add("空调");
                add("高德地图");
                add("电话");
                add("环视记录仪");
                add("车辆设置");
                add("系统设置");
                add("舒适氛围");
                add("系统升级");
                add("文件管理");
                add("一点通");
                add("语音设置");
            }
        };
        packageEnNameAppList = new java.util.ArrayList<java.lang.String>() { // from class: com.autopai.applist.model.config.AppsConfig.5
            {
                add("Tencent Map");
                add("QQ Music");
                add("Tencent Radio");
                add("CarAuto");
                add("Air Conditioner");
                add("amap auto");
                add("Phone");
                add("Circle view record");
                add("Vehicle Setting");
                add("Setting");
                add("ComfortCockpit");
                add("FOTA");
                add("File");
                add("CallCenter");
                add("Voice Assistant");
            }
        };
        favoritesAppList = new java.util.ArrayList<>(mClassicConfigAppMap.keySet());
        mCurrentTheme = 0;
        mClassicBackground = "classic_bg";
        mSportBackground = "sport_bg";
        mSimpleBackground = "simple_bg";
        mConfigBackground = "ic_common_bg";
        mMarkers = new android.util.SparseArray<>();
        mBackgrounds = new android.util.SparseArray<>();
        sIcons = new android.util.SparseArray<>();
        mDefaultMarker = new java.lang.String[]{"#FFFFFFFF", "#6FFFFFFF"};
        mMarkers.put(0, mDefaultMarker);
        mBackgrounds.put(0, mClassicBackground);
        mBackgrounds.put(1, mSportBackground);
        mBackgrounds.put(2, mSimpleBackground);
        sIcons.put(0, mClassicConfigAppMap);
        sIcons.put(1, mSportConfigAppMap);
        sIcons.put(2, mSimpleConfigAppMap);
        mCarType = "CHANGAN_S311_CARTYPE_10";
        mDisableCarTypeList = new java.util.ArrayList<java.lang.String>() { // from class: com.autopai.applist.model.config.AppsConfig.6
            {
                add("CHANGAN_S311_CARTYPE_1");
                add("CHANGAN_S311_CARTYPE_2");
                add("CHANGAN_S311_CARTYPE_3");
                add("CHANGAN_S311_CARTYPE_4");
                add("CHANGAN_S311_CARTYPE_5");
                add("CHANGAN_S311_CARTYPE_6");
                add("CHANGAN_S311_CARTYPE_7");
                add("CHANGAN_S311_CARTYPE_8");
                add("CHANGAN_S311_CARTYPE_9");
                add("CHANGAN_S311_CARTYPE_11");
                add("CHANGAN_S311_CARTYPE_12");
                add("CHANGAN_S311_CARTYPE_13");
                add("CHANGAN_S311_CARTYPE_14");
            }
        };
        mDisableAppsList = new java.util.ArrayList<java.lang.String>() { // from class: com.autopai.applist.model.config.AppsConfig.7
            {
                add(com.autopai.applist.model.config.AppsConfig.APPS_CAR_DVR_360);
                add(com.autopai.applist.model.config.AppsConfig.APPS_COMFORT_COCKPIT);
            }
        };
    }

    public static void setChangeUI(android.content.res.Resources resources) {
        android.util.Log.i(TAG, "loadFromConfig: listPackage.size()= " + listPackage.size());
        if (listPackage.size() > 0 && listPackage.size() == listIcon.size()) {
            mResourceConfig = resources;
            favoritesAppList.clear();
            favoritesAppList.addAll(listPackage);
            return;
        }
        mResourceConfig = null;
    }

    public static java.util.LinkedHashMap<java.lang.String, java.lang.String> getIcons() {
        java.util.LinkedHashMap<java.lang.String, java.lang.String> appsIconResIdMap = sIcons.get(mCurrentTheme);
        if (appsIconResIdMap == null) {
            return mClassicConfigAppMap;
        }
        return appsIconResIdMap;
    }

    public static java.lang.String getBackground() {
        java.lang.String resourceIds = mBackgrounds.get(mCurrentTheme);
        if (resourceIds == null) {
            return mClassicBackground;
        }
        return resourceIds;
    }

    public static void setBackground(java.lang.String background) {
        mConfigBackground = background;
    }

    public static java.lang.String[] getMarkerType() {
        java.lang.String[] resourceIds = mMarkers.get(mCurrentTheme);
        return resourceIds != null ? resourceIds : mDefaultMarker;
    }

    public static void setCurrentTheme(int mCurrentTheme2) {
        mCurrentTheme = mCurrentTheme2;
    }

    public static int getCurrentTheme() {
        return mCurrentTheme;
    }

    private static boolean showTest() {
        android.util.Log.i("hurley", "status=" + readProp("sys.thirdapk.caninstall"));
        return "1".equals(readProp("sys.thirdapk.caninstall"));
    }

    private static java.lang.String readProp(java.lang.String prop) {
        try {
            java.lang.Process process = java.lang.Runtime.getRuntime().exec("getprop " + prop);
            java.io.InputStreamReader ir = new java.io.InputStreamReader(process.getInputStream());
            java.io.BufferedReader input = new java.io.BufferedReader(ir);
            return input.readLine();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
