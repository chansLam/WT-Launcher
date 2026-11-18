package com.autopai.applist.model.compat;

/* loaded from: classes.dex */
public abstract class LauncherAppsCompat {
    public static final java.lang.String ACTION_MANAGED_PROFILE_ADDED = "android.intent.action.MANAGED_PROFILE_ADDED";
    public static final java.lang.String ACTION_MANAGED_PROFILE_REMOVED = "android.intent.action.MANAGED_PROFILE_REMOVED";
    private static com.autopai.applist.model.compat.LauncherAppsCompat sInstance;
    private static final java.lang.Object sInstanceLock = new java.lang.Object();

    public interface OnAppsChangedCallbackCompat {
        void onPackageAdded(java.lang.String str, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

        void onPackageChanged(java.lang.String str, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

        void onPackageRemoved(java.lang.String str, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

        void onPackagesAvailable(java.lang.String[] strArr, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat, boolean z);

        void onPackagesUnavailable(java.lang.String[] strArr, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat, boolean z);
    }

    public abstract void addOnAppsChangedCallback(com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat onAppsChangedCallbackCompat);

    public abstract java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> getActivityList(java.lang.String str, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract boolean isActivityEnabledForProfile(android.content.ComponentName componentName, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract boolean isPackageEnabledForProfile(java.lang.String str, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract void removeOnAppsChangedCallback(com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat onAppsChangedCallbackCompat);

    public abstract com.autopai.applist.model.compat.LauncherActivityInfoCompat resolveActivity(android.content.Intent intent, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract void showAppDetailsForProfile(android.content.ComponentName componentName, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat);

    public abstract void startActivityForProfile(android.content.ComponentName componentName, com.autopai.applist.model.compat.UserHandleCompat userHandleCompat, android.graphics.Rect rect, android.os.Bundle bundle);

    protected LauncherAppsCompat() {
    }

    public static com.autopai.applist.model.compat.LauncherAppsCompat getInstance(android.content.Context context) {
        com.autopai.applist.model.compat.LauncherAppsCompat launcherAppsCompat;
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                if (com.autopai.common.utils.common.Utilities.ATLEAST_LOLLIPOP) {
                    sInstance = new com.autopai.applist.model.compat.LauncherAppsCompatVL(context.getApplicationContext());
                } else {
                    sInstance = new com.autopai.applist.model.compat.LauncherAppsCompatV16(context.getApplicationContext());
                }
            }
            launcherAppsCompat = sInstance;
        }
        return launcherAppsCompat;
    }

    boolean isAppEnabled(android.content.pm.PackageManager pm, java.lang.String packageName, int flags) {
        try {
            android.content.pm.ApplicationInfo info = pm.getApplicationInfo(packageName, flags);
            if (info != null) {
                return info.enabled;
            }
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
