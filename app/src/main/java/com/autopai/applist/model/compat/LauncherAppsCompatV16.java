package com.autopai.applist.model.compat;

/* loaded from: classes.dex */
public class LauncherAppsCompatV16 extends com.autopai.applist.model.compat.LauncherAppsCompat {
    private android.content.Context mContext;
    private android.content.pm.PackageManager mPm;
    private java.util.List<com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat> mCallbacks = new java.util.ArrayList();
    private com.autopai.applist.model.compat.LauncherAppsCompatV16.PackageMonitor mPackageMonitor = new com.autopai.applist.model.compat.LauncherAppsCompatV16.PackageMonitor();

    LauncherAppsCompatV16(android.content.Context context) {
        this.mPm = context.getPackageManager();
        this.mContext = context;
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> getActivityList(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        android.content.Intent mainIntent = new android.content.Intent("android.intent.action.MAIN", (android.net.Uri) null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        mainIntent.setPackage(packageName);
        java.util.List<android.content.pm.ResolveInfo> infos = this.mPm.queryIntentActivities(mainIntent, 0);
        java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> list = new java.util.ArrayList<>(infos.size());
        for (android.content.pm.ResolveInfo info : infos) {
            list.add(new com.autopai.applist.model.compat.LauncherActivityInfoCompatV16(this.mContext, info));
        }
        return list;
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public com.autopai.applist.model.compat.LauncherActivityInfoCompat resolveActivity(android.content.Intent intent, com.autopai.applist.model.compat.UserHandleCompat user) {
        android.content.pm.ResolveInfo info = this.mPm.resolveActivity(intent, 0);
        if (info != null) {
            return new com.autopai.applist.model.compat.LauncherActivityInfoCompatV16(this.mContext, info);
        }
        return null;
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public void startActivityForProfile(android.content.ComponentName component, com.autopai.applist.model.compat.UserHandleCompat user, android.graphics.Rect sourceBounds, android.os.Bundle opts) {
        android.content.Intent launchIntent = new android.content.Intent("android.intent.action.MAIN");
        launchIntent.addCategory("android.intent.category.LAUNCHER");
        launchIntent.setComponent(component);
        launchIntent.setSourceBounds(sourceBounds);
        launchIntent.addFlags(268435456);
        this.mContext.startActivity(launchIntent, opts);
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public void showAppDetailsForProfile(android.content.ComponentName component, com.autopai.applist.model.compat.UserHandleCompat user) {
        java.lang.String packageName = component.getPackageName();
        android.content.Intent intent = new android.content.Intent("android.settings.APPLICATION_DETAILS_SETTINGS", android.net.Uri.fromParts("package", packageName, null));
        intent.setFlags(276856832);
        this.mContext.startActivity(intent, null);
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public synchronized void addOnAppsChangedCallback(com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback) {
        if (callback != null) {
            if (!this.mCallbacks.contains(callback)) {
                this.mCallbacks.add(callback);
                if (this.mCallbacks.size() == 1) {
                    registerForPackageIntents();
                }
            }
        }
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public synchronized void removeOnAppsChangedCallback(com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback) {
        this.mCallbacks.remove(callback);
        if (this.mCallbacks.size() == 0) {
            unregisterForPackageIntents();
        }
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public boolean isPackageEnabledForProfile(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        return isAppEnabled(this.mPm, packageName, 0);
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public boolean isActivityEnabledForProfile(android.content.ComponentName component, com.autopai.applist.model.compat.UserHandleCompat user) {
        try {
            android.content.pm.ActivityInfo info = this.mPm.getActivityInfo(component, 0);
            if (info != null) {
                return info.isEnabled();
            }
            return false;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void unregisterForPackageIntents() {
        this.mContext.unregisterReceiver(this.mPackageMonitor);
    }

    private void registerForPackageIntents() {
        android.content.IntentFilter filter = new android.content.IntentFilter("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addAction("android.intent.action.PACKAGE_CHANGED");
        filter.addDataScheme("package");
        this.mContext.registerReceiver(this.mPackageMonitor, filter);
        android.content.IntentFilter filter2 = new android.content.IntentFilter();
        filter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        filter2.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
        this.mContext.registerReceiver(this.mPackageMonitor, filter2);
    }

    synchronized java.util.List<com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat> getCallbacks() {
        return new java.util.ArrayList(this.mCallbacks);
    }

    class PackageMonitor extends android.content.BroadcastReceiver {
        PackageMonitor() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            com.autopai.applist.model.compat.UserHandleCompat user = com.autopai.applist.model.compat.UserHandleCompat.myUserHandle();
            if ("android.intent.action.PACKAGE_CHANGED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_ADDED".equals(action)) {
                java.lang.String packageName = intent.getData().getSchemeSpecificPart();
                boolean replacing = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                if (packageName != null && packageName.length() != 0) {
                    if ("android.intent.action.PACKAGE_CHANGED".equals(action)) {
                        for (com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback : com.autopai.applist.model.compat.LauncherAppsCompatV16.this.getCallbacks()) {
                            callback.onPackageChanged(packageName, user);
                        }
                        return;
                    }
                    if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                        if (!replacing) {
                            for (com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback2 : com.autopai.applist.model.compat.LauncherAppsCompatV16.this.getCallbacks()) {
                                callback2.onPackageRemoved(packageName, user);
                            }
                            return;
                        }
                        return;
                    }
                    if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                        if (!replacing) {
                            for (com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback3 : com.autopai.applist.model.compat.LauncherAppsCompatV16.this.getCallbacks()) {
                                callback3.onPackageAdded(packageName, user);
                            }
                            return;
                        }
                        for (com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback4 : com.autopai.applist.model.compat.LauncherAppsCompatV16.this.getCallbacks()) {
                            callback4.onPackageChanged(packageName, user);
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(action)) {
                boolean replacing2 = intent.getBooleanExtra("android.intent.extra.REPLACING", com.autopai.common.utils.common.Utilities.ATLEAST_KITKAT ? false : true);
                java.lang.String[] packages = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                for (com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback5 : com.autopai.applist.model.compat.LauncherAppsCompatV16.this.getCallbacks()) {
                    callback5.onPackagesAvailable(packages, user, replacing2);
                }
                return;
            }
            if ("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(action)) {
                boolean replacing3 = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                java.lang.String[] packages2 = intent.getStringArrayExtra("android.intent.extra.changed_package_list");
                for (com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback6 : com.autopai.applist.model.compat.LauncherAppsCompatV16.this.getCallbacks()) {
                    callback6.onPackagesUnavailable(packages2, user, replacing3);
                }
            }
        }
    }
}
