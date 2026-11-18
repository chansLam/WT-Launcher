package com.incall.apps.common.utils;

/* loaded from: classes.dex */
public class ThemeUtils {
    public static void setColorByTheme(android.content.Context context, android.view.View view) {
        view.setBackgroundResource(getColor(context));
    }

    public static void setColorTransparant(android.view.View view) {
        view.setBackgroundResource(0);
    }

    public static void setColorSelecterByTheme(android.content.Context context, android.view.View view) {
        int color = com.android.launcherWT.R.drawable.common_selecter_bg_classic;
        switch (getThemeType(context)) {
            case 1:
                color = com.android.launcherWT.R.drawable.common_selecter_bg_classic;
                break;
            case 2:
                color = com.android.launcherWT.R.drawable.common_selecter_bg_sport;
                break;
            case 3:
                color = com.android.launcherWT.R.drawable.common_selecter_bg_simple;
                break;
        }
        view.setBackgroundResource(color);
    }

    public static void setTextColorByTheme(android.content.Context context, android.widget.TextView textView) {
        textView.setTextColor(context.getResources().getColor(getColor(context)));
    }

    public static void setTextSelectorByTheme(android.content.Context context, android.widget.TextView textView) {
        int color = com.android.launcherWT.R.drawable.common_color_selecter_classic;
        switch (getThemeType(context)) {
            case 1:
                color = com.android.launcherWT.R.drawable.common_color_selecter_classic;
                break;
            case 2:
                color = com.android.launcherWT.R.drawable.common_color_selecter_sport;
                break;
            case 3:
                color = com.android.launcherWT.R.drawable.common_color_selecter_simple;
                break;
        }
        textView.setTextColor(context.getResources().getColor(color));
    }

    public static void setButtenStyleByTheme(android.content.Context context, android.widget.Button btn) {
        int color = com.android.launcherWT.R.drawable.common_color_selecter_btn_text_classic;
        int backgrand = com.android.launcherWT.R.drawable.common_btn_classic;
        switch (getThemeType(context)) {
            case 1:
                color = com.android.launcherWT.R.drawable.common_color_selecter_btn_text_classic;
                backgrand = com.android.launcherWT.R.drawable.common_btn_classic;
                break;
            case 2:
                color = com.android.launcherWT.R.drawable.common_color_selecter_btn_text_sport;
                backgrand = com.android.launcherWT.R.drawable.common_btn_sport;
                break;
            case 3:
                color = com.android.launcherWT.R.drawable.common_color_selecter_btn_text_simple;
                backgrand = com.android.launcherWT.R.drawable.common_btn_simple;
                break;
        }
        btn.setTextColor(context.getResources().getColorStateList(color));
        btn.setBackgroundResource(backgrand);
    }

    public static int getColor(android.content.Context context) {
        int color = com.android.launcherWT.R.color.common_flexible_tab_select;
        switch (getThemeType(context)) {
            case 1:
                int color2 = com.android.launcherWT.R.color.common_flexible_tab_select;
                return color2;
            case 2:
                int color3 = com.android.launcherWT.R.color.common_sport_tab_select;
                return color3;
            case 3:
                int color4 = com.android.launcherWT.R.color.common_simple_tab_select;
                return color4;
            default:
                return color;
        }
    }

    public static int getThemeType(android.content.Context context) {
        int theme = com.incall.apps.service.setting.SettingServiceManager.getInstance().getTheme();
        if (theme == 0) {
            return com.incall.apps.service.setting.SettingServiceManager.getInstance().getTheme(context);
        }
        return theme;
    }

    public static int getThemeColor(android.content.Context context) {
        int color = com.android.launcherWT.R.drawable.common_color_selecter_classic;
        switch (getThemeType(context)) {
            case 1:
                int color2 = com.android.launcherWT.R.drawable.common_color_selecter_classic;
                return color2;
            case 2:
                int color3 = com.android.launcherWT.R.drawable.common_color_selecter_sport;
                return color3;
            case 3:
                int color4 = com.android.launcherWT.R.drawable.common_color_selecter_simple;
                return color4;
            default:
                return color;
        }
    }

    public static void setSwitchCompat(android.content.Context context, android.widget.Switch mSwitch) {
        mSwitch.setTrackResource(com.android.launcherWT.R.color.transparent);
        switch (getThemeType(context)) {
            case 1:
                mSwitch.setBackground(context.getResources().getDrawable(com.android.launcherWT.R.drawable.classic_switch));
                break;
            case 2:
                mSwitch.setBackground(context.getResources().getDrawable(com.android.launcherWT.R.drawable.sport_bg_switch));
                break;
            case 3:
                mSwitch.setBackground(context.getResources().getDrawable(com.android.launcherWT.R.drawable.simple_bg_switch));
                break;
        }
        mSwitch.setThumbResource(com.android.launcherWT.R.color.transparent);
        mSwitch.refreshDrawableState();
    }
}
