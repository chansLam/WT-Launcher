package com.android.launcherWT.home;

import com.android.launcherWT.R;

final class ShortcutTargets {
    final String packageName;
    final String className;
    final String widgetPackage;
    final String widgetClass;
    final int labelRes;

    private ShortcutTargets(String packageName, String className, String widgetPackage,
            String widgetClass, int labelRes) {
        this.packageName = packageName;
        this.className = className;
        this.widgetPackage = widgetPackage;
        this.widgetClass = widgetClass;
        this.labelRes = labelRes;
    }

    static final ShortcutTargets NAVIGATION = new ShortcutTargets(
            "com.tencent.wecarnavi",
            "com.tencent.wecarnavi.MainActivity",
            "com.tencent.wecarnavi",
            "com.tencent.wecarnavi.appwidget.NaviAppWidget",
            R.string.shortcut_nav);

    static final ShortcutTargets MUSIC = new ShortcutTargets(
            "com.tencent.wecarmusicp",
            "com.tencent.wecarmusic.activity.MainActivity",
            "com.tencent.musicwidget",
            "com.tencent.musicwidget.MusicConnectAppWidgetProvider",
            R.string.shortcut_music);

    static final ShortcutTargets NEWS = new ShortcutTargets(
            "com.tencent.wecarnews",
            "com.tencent.wecarnews.UI.home.NewsMainTab",
            "com.tencent.wecarnews.swidget",
            "com.tencent.wecarnews.swidget.SWidget",
            R.string.shortcut_news);

    static final ShortcutTargets[] ALL = {NAVIGATION, MUSIC, NEWS};
}
