package com.incall.apps.common;

/* loaded from: classes.dex */
public abstract class BaseThemeActivity extends com.incall.apps.common.BaseFragmentActivity {
    private int mCurrentTheme = -1;
    private boolean isActivityShow = false;
    private android.os.Handler handler = new android.os.Handler();
    private com.incall.apps.service.setting.IOnThemeChange themeChangeListener = new com.incall.apps.service.setting.IOnThemeChange.Stub() { // from class: com.incall.apps.common.BaseThemeActivity.1
        @Override // com.incall.apps.service.setting.IOnThemeChange
        public void onThemeChange(int theme) throws android.os.RemoteException {
            com.incall.apps.common.BaseThemeActivity.this.handler.post(new java.lang.Runnable() { // from class: com.incall.apps.common.BaseThemeActivity.1.1
                @Override // java.lang.Runnable
                public void run() {
                    com.incall.apps.common.BaseThemeActivity.this.updateThemeBackground();
                }
            });
        }
    };

    public abstract void themeChange(int i);

    @Override // com.incall.apps.common.BaseFragmentActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.SupportActivity, android.app.Activity
    protected void onCreate(android.os.Bundle arg0) {
        super.onCreate(arg0);
        com.incall.apps.service.setting.SettingServiceManager.getInstance().registerThemeChangeListener(this.themeChangeListener);
    }

    @Override // com.incall.apps.common.BaseFragmentActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        updateThemeBackground();
    }

    @Override // com.incall.apps.common.BaseFragmentActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        this.isActivityShow = true;
        super.onStart();
    }

    @Override // com.incall.apps.common.BaseFragmentActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        this.isActivityShow = false;
        super.onStop();
    }

    @Override // com.incall.apps.common.BaseFragmentActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        closeShowingDialog();
        com.incall.apps.service.setting.SettingServiceManager.getInstance().unRegisterThemeChangeListener(this.themeChangeListener);
    }

    public void updateThemeBackground() {
        int theme = com.incall.apps.common.utils.ThemeUtils.getThemeType(getApplicationContext());
        if (theme == this.mCurrentTheme) {
            android.util.Log.d(this.TAG, "updateThemeBackground getCurrentColors = theme = " + theme);
        }
        android.util.Log.i(this.TAG, "updateThemeBackground getCurrentColors  theme = " + theme + ",mCurrentTheme = " + this.mCurrentTheme);
        this.mCurrentTheme = theme;
        themeChange(theme);
        switch (this.mCurrentTheme) {
            case 1:
                setBackgroundRes(getWindow().getDecorView(), com.android.launcherWT.R.drawable.common_bg_theme_classic);
                break;
            case 2:
                setBackgroundRes(getWindow().getDecorView(), com.android.launcherWT.R.drawable.common_bg_theme_sport);
                break;
            case 3:
                setBackgroundRes(getWindow().getDecorView(), com.android.launcherWT.R.drawable.common_bg_theme_simple);
                break;
        }
    }

    public void setBackgroundRes(android.view.View view, int viewId) {
        view.setBackgroundResource(0);
        view.setBackgroundResource(viewId);
    }

    public int getCurrentTheme() {
        return this.mCurrentTheme;
    }
}
