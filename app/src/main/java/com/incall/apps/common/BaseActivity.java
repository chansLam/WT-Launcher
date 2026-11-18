package com.incall.apps.common;

/* loaded from: classes.dex */
public class BaseActivity extends android.app.Activity {
    private java.lang.String TAG = getClass().getName();
    private int mCurrentTheme = -1;

    public void showToast(java.lang.String msg) {
        showToast(msg, 0, 5000);
    }

    public void showToast(java.lang.String msg, int duration) {
        showToast(msg, 0, duration);
    }

    public void showToastWithIcon(java.lang.String msg, int drwable, int duration) {
        showToast(msg, drwable, duration);
    }

    public void showToastWithIcon(java.lang.String msg, int drwable) {
        showToast(msg, drwable, 5000);
    }

    public void showLoading(java.lang.String msg) {
        showToast(msg, com.android.launcherWT.R.drawable.common_loading, -1);
    }

    public void showConfirmWithOneBtn(java.lang.String msg, java.lang.String btnTitle, android.view.View.OnClickListener listener) {
        showConfirmWithTwoBtn(msg, btnTitle, listener, null, null);
    }

    public void closeShowingDialog() {
        com.incall.apps.common.utils.DialogUtils.getInstance().closeShowingDialog();
    }

    private void showToast(java.lang.String msg, int icon, int duration) {
        com.incall.apps.common.utils.DialogUtils.getInstance().showToast(getApplicationContext(), msg, icon, duration);
    }

    public void showConfirmWithTwoBtn(java.lang.String msg, java.lang.String leftBtnTitle, android.view.View.OnClickListener leftBtnListener, java.lang.String rightBtnTitle, android.view.View.OnClickListener rightBtnListener) {
        com.incall.apps.common.utils.DialogUtils.getInstance().showTipConfirmWithTwoBtn(getApplicationContext(), false, msg, leftBtnTitle, leftBtnListener, rightBtnTitle, rightBtnListener);
    }

    public void setChildViewEnabled(boolean enable, android.view.ViewGroup viewGroup) {
        viewGroup.setEnabled(enable);
        for (int j = 0; j < viewGroup.getChildCount(); j++) {
            android.view.View child = viewGroup.getChildAt(j);
            try {
                setChildViewEnabled(enable, (android.view.ViewGroup) child);
            } catch (java.lang.Exception e) {
                viewGroup.getChildAt(j).setEnabled(enable);
            }
        }
    }

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle arg0) {
        android.util.Log.d(this.TAG, "onCreate versionName = " + getVersionName());
        super.onCreate(arg0);
    }

    @Override // android.app.Activity
    protected void onStart() {
        android.util.Log.d(this.TAG, "onStart");
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onResume() {
        android.util.Log.d(this.TAG, "onResume");
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onPause() {
        android.util.Log.d(this.TAG, "onPause");
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onStop() {
        android.util.Log.d(this.TAG, "onStop");
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        android.util.Log.d(this.TAG, "onDestory");
        super.onDestroy();
        closeShowingDialog();
    }

    public void updateThemeBackground() {
        int theme = getThemeType();
        if (theme == this.mCurrentTheme) {
            android.util.Log.d(this.TAG, "updateThemeBackground mCurrentTheme = theme = " + theme);
        }
        android.util.Log.i(this.TAG, "updateThemeBackground theme = " + theme + ",mCurrentTheme = " + this.mCurrentTheme);
        this.mCurrentTheme = theme;
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

    private java.lang.String getVersionName() {
        try {
            android.content.pm.PackageInfo packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            java.lang.String version = packageInfo.versionName;
            return version;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(this.TAG, "getVersionName e = " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    private int getThemeType() {
        return com.incall.apps.service.setting.SettingServiceManager.getInstance().getTheme();
    }
}
