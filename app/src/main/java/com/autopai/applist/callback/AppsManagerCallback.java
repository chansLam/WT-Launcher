package com.autopai.applist.callback;

/* loaded from: classes.dex */
public interface AppsManagerCallback {
    void changeTheme();

    void localeChanged();

    void onAppDataChange(java.util.List<com.autopai.applist.model.infos.AppInfo> list);

    void setAppListAdapter(java.util.List<com.autopai.applist.model.infos.AppInfo> list);
}
