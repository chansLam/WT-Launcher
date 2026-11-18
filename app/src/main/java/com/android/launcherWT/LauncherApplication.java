package com.android.launcherWT;

import android.app.Application;

/** Matches the original shell application; onCreate stayed empty there too. */
public class LauncherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
