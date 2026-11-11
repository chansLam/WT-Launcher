package com.android.launcherWT.core;

import java.util.HashSet;
import java.util.Set;

public abstract class DesktopState {
    public abstract boolean isHidden(String key);

    public abstract void setHidden(String key, boolean hidden);

    public abstract WallpaperMode wallpaperMode();

    public abstract void setWallpaperMode(WallpaperMode mode);

    public abstract String customPath();

    public abstract void setCustomPath(String path);

    public abstract String dailyStartdate();

    public abstract void setDailyStartdate(String date);

    public static DesktopState memory() {
        return new Memory();
    }

    private static final class Memory extends DesktopState {
        private final Set<String> hidden = new HashSet<String>();
        private WallpaperMode mode = WallpaperMode.DAILY;
        private String customPath;
        private String dailyStartdate;

        @Override
        public boolean isHidden(String key) {
            return hidden.contains(key);
        }

        @Override
        public void setHidden(String key, boolean hidden) {
            if (hidden) {
                this.hidden.add(key);
            } else {
                this.hidden.remove(key);
            }
        }

        @Override
        public WallpaperMode wallpaperMode() {
            return mode;
        }

        @Override
        public void setWallpaperMode(WallpaperMode mode) {
            this.mode = mode;
        }

        @Override
        public String customPath() {
            return customPath;
        }

        @Override
        public void setCustomPath(String path) {
            this.customPath = path;
        }

        @Override
        public String dailyStartdate() {
            return dailyStartdate;
        }

        @Override
        public void setDailyStartdate(String date) {
            this.dailyStartdate = date;
        }
    }
}
