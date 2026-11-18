package com.autopai.applist.model.compat;

/* loaded from: classes.dex */
public class LauncherActivityInfoCompatV16 extends com.autopai.applist.model.compat.LauncherActivityInfoCompat {
    private final android.content.pm.ActivityInfo mActivityInfo;
    private final android.content.ComponentName mComponentName;
    private final android.content.pm.PackageManager mPm;
    private final android.content.pm.ResolveInfo mResolveInfo;

    LauncherActivityInfoCompatV16(android.content.Context context, android.content.pm.ResolveInfo info) {
        this.mResolveInfo = info;
        this.mActivityInfo = info.activityInfo;
        this.mComponentName = new android.content.ComponentName(this.mActivityInfo.packageName, this.mActivityInfo.name);
        this.mPm = context.getPackageManager();
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public com.autopai.applist.model.compat.UserHandleCompat getUser() {
        return com.autopai.applist.model.compat.UserHandleCompat.myUserHandle();
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public java.lang.CharSequence getLabel() {
        return this.mResolveInfo.loadLabel(this.mPm);
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public android.graphics.drawable.Drawable getIcon(int density) {
        int iconRes = this.mResolveInfo.getIconResource();
        android.graphics.drawable.Drawable icon = null;
        if (density != 0 && iconRes != 0) {
            try {
                android.content.res.Resources resources = this.mPm.getResourcesForApplication(this.mActivityInfo.applicationInfo);
                icon = resources.getDrawableForDensity(iconRes, density);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            } catch (android.content.res.Resources.NotFoundException e2) {
            }
        }
        if (icon == null) {
            icon = this.mResolveInfo.loadIcon(this.mPm);
        }
        if (icon == null) {
            android.content.res.Resources resources2 = android.content.res.Resources.getSystem();
            return resources2.getDrawableForDensity(android.R.mipmap.sym_def_app_icon, density);
        }
        return icon;
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public android.content.pm.ApplicationInfo getApplicationInfo() {
        return this.mActivityInfo.applicationInfo;
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public long getFirstInstallTime() {
        try {
            android.content.pm.PackageInfo info = this.mPm.getPackageInfo(this.mActivityInfo.packageName, 0);
            if (info != null) {
                return info.firstInstallTime;
            }
            return 0L;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return 0L;
        }
    }

    public java.lang.String getName() {
        return this.mActivityInfo.name;
    }

    @Override // com.autopai.applist.model.compat.LauncherActivityInfoCompat
    public android.graphics.drawable.Drawable getBadgedIcon(int density) {
        return getIcon(density);
    }
}
