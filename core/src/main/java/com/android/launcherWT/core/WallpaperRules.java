package com.android.launcherWT.core;

public final class WallpaperRules {
    private WallpaperRules() {
    }

    public static WallpaperMode afterCustomImage(boolean saved) {
        if (saved) {
            return WallpaperMode.CUSTOM;
        }
        return WallpaperMode.DAILY;
    }

    public static boolean shouldDownload(WallpaperMode mode, String cachedStartdate, String remoteStartdate) {
        if (mode != WallpaperMode.DAILY || remoteStartdate == null) {
            return false;
        }
        return !remoteStartdate.equals(cachedStartdate);
    }
}
