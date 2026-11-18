package com.autopai.applist.model.compat;

@android.annotation.TargetApi(21)
/* loaded from: classes.dex */
public class LauncherAppsCompatVL extends com.autopai.applist.model.compat.LauncherAppsCompat {
    private java.util.Map<com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat, com.autopai.applist.model.compat.LauncherAppsCompatVL.WrappedCallback> mCallbacks = new java.util.HashMap();
    private android.content.pm.LauncherApps mLauncherApps;

    LauncherAppsCompatVL(android.content.Context context) {
        this.mLauncherApps = (android.content.pm.LauncherApps) context.getSystemService("launcherapps");
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public java.util.List<com.autopai.applist.model.compat.LauncherActivityInfoCompat> getActivityList(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        java.util.List<android.content.pm.LauncherActivityInfo> list = this.mLauncherApps.getActivityList(packageName, user.getUser());
        if (list.size() == 0) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList<com.autopai.applist.model.compat.LauncherActivityInfoCompat> compatList = new java.util.ArrayList<>(list.size());
        for (android.content.pm.LauncherActivityInfo info : list) {
            compatList.add(new com.autopai.applist.model.compat.LauncherActivityInfoCompatVL(info));
        }
        return compatList;
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public com.autopai.applist.model.compat.LauncherActivityInfoCompat resolveActivity(android.content.Intent intent, com.autopai.applist.model.compat.UserHandleCompat user) {
        android.content.pm.LauncherActivityInfo activity = this.mLauncherApps.resolveActivity(intent, user.getUser());
        if (activity != null) {
            return new com.autopai.applist.model.compat.LauncherActivityInfoCompatVL(activity);
        }
        return null;
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public void startActivityForProfile(android.content.ComponentName component, com.autopai.applist.model.compat.UserHandleCompat user, android.graphics.Rect sourceBounds, android.os.Bundle opts) {
        this.mLauncherApps.startMainActivity(component, user.getUser(), sourceBounds, opts);
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public void showAppDetailsForProfile(android.content.ComponentName component, com.autopai.applist.model.compat.UserHandleCompat user) {
        this.mLauncherApps.startAppDetailsActivity(component, user.getUser(), null, null);
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public void addOnAppsChangedCallback(com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback) {
        com.autopai.applist.model.compat.LauncherAppsCompatVL.WrappedCallback wrappedCallback = new com.autopai.applist.model.compat.LauncherAppsCompatVL.WrappedCallback(callback);
        synchronized (this.mCallbacks) {
            this.mCallbacks.put(callback, wrappedCallback);
        }
        this.mLauncherApps.registerCallback(wrappedCallback);
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public void removeOnAppsChangedCallback(com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback) {
        com.autopai.applist.model.compat.LauncherAppsCompatVL.WrappedCallback wrappedCallback;
        synchronized (this.mCallbacks) {
            wrappedCallback = this.mCallbacks.remove(callback);
        }
        if (wrappedCallback != null) {
            this.mLauncherApps.unregisterCallback(wrappedCallback);
        }
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public boolean isPackageEnabledForProfile(java.lang.String packageName, com.autopai.applist.model.compat.UserHandleCompat user) {
        return this.mLauncherApps.isPackageEnabled(packageName, user.getUser());
    }

    @Override // com.autopai.applist.model.compat.LauncherAppsCompat
    public boolean isActivityEnabledForProfile(android.content.ComponentName component, com.autopai.applist.model.compat.UserHandleCompat user) {
        return this.mLauncherApps.isActivityEnabled(component, user.getUser());
    }

    private static class WrappedCallback extends android.content.pm.LauncherApps.Callback {
        private com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat mCallback;

        public WrappedCallback(com.autopai.applist.model.compat.LauncherAppsCompat.OnAppsChangedCallbackCompat callback) {
            this.mCallback = callback;
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageRemoved(java.lang.String packageName, android.os.UserHandle user) {
            this.mCallback.onPackageRemoved(packageName, com.autopai.applist.model.compat.UserHandleCompat.fromUser(user));
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageAdded(java.lang.String packageName, android.os.UserHandle user) {
            this.mCallback.onPackageAdded(packageName, com.autopai.applist.model.compat.UserHandleCompat.fromUser(user));
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackageChanged(java.lang.String packageName, android.os.UserHandle user) {
            this.mCallback.onPackageChanged(packageName, com.autopai.applist.model.compat.UserHandleCompat.fromUser(user));
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesAvailable(java.lang.String[] packageNames, android.os.UserHandle user, boolean replacing) {
            this.mCallback.onPackagesAvailable(packageNames, com.autopai.applist.model.compat.UserHandleCompat.fromUser(user), replacing);
        }

        @Override // android.content.pm.LauncherApps.Callback
        public void onPackagesUnavailable(java.lang.String[] packageNames, android.os.UserHandle user, boolean replacing) {
            this.mCallback.onPackagesUnavailable(packageNames, com.autopai.applist.model.compat.UserHandleCompat.fromUser(user), replacing);
        }
    }
}
