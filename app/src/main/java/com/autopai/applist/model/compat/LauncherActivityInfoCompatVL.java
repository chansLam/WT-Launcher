package com.autopai.applist.model.compat;

@android.annotation.TargetApi(21)
/* loaded from: classes.dex */
public class LauncherActivityInfoCompatVL extends com.autopai.applist.model.compat.LauncherActivityInfoCompat {
    private android.content.pm.LauncherActivityInfo mLauncherActivityInfo;

    LauncherActivityInfoCompatVL(android.content.pm.LauncherActivityInfo launcherActivityInfo) {
        this.mLauncherActivityInfo = launcherActivityInfo;
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public android.content.ComponentName getComponentName() {
        return this.mLauncherActivityInfo.getComponentName();
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public com.autopai.applist.model.compat.UserHandleCompat getUser() {
        return com.autopai.applist.model.compat.UserHandleCompat.fromUser(this.mLauncherActivityInfo.getUser());
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public java.lang.CharSequence getLabel() {
        return this.mLauncherActivityInfo.getLabel();
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public android.graphics.drawable.Drawable getIcon(int density) {
        return this.mLauncherActivityInfo.getIcon(density);
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public android.content.pm.ApplicationInfo getApplicationInfo() {
        return this.mLauncherActivityInfo.getApplicationInfo();
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public long getFirstInstallTime() {
        return this.mLauncherActivityInfo.getFirstInstallTime();
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public android.graphics.drawable.Drawable getBadgedIcon(int density) {
        return this.mLauncherActivityInfo.getBadgedIcon(density);
    }
}
