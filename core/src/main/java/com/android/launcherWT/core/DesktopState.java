package com.android.launcherWT.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public abstract List<String> displayOrder();

    public abstract void setDisplayOrder(List<String> keys);

    public static DesktopState memory() {
        return new Memory();
    }

    private static final class Memory extends DesktopState {
        private final Set<String> hidden = new HashSet<String>();
        private final List<String> order = new ArrayList<String>();
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
                order.remove(key);
            } else {
                this.hidden.remove(key);
            }
        }

        @Override
        public List<String> displayOrder() {
            return new ArrayList<String>(order);
        }

        @Override
        public void setDisplayOrder(List<String> keys) {
            order.clear();
            if (keys == null) {
                return;
            }
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                if (key != null && !order.contains(key)) {
                    order.add(key);
                }
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
