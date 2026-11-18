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

    public abstract String dailySource();

    public abstract void setDailySource(String source);

    public static final int DEFAULT_COLUMNS = 5;
    public static final int DEFAULT_ROWS = 3;
    public static final int DEFAULT_ICON_SIZE = 96;

    public abstract int gridColumns();

    public abstract void setGridColumns(int columns);

    public abstract int gridRows();

    public abstract void setGridRows(int rows);

    public abstract int iconSize();

    public abstract void setIconSize(int size);

    /** Drag onto an occupied slot pushes the rest along. */
    public static final int DRAG_INSERT = 0;
    /** Drag onto an occupied slot swaps the two icons. */
    public static final int DRAG_SWAP = 1;

    public int dragMode() {
        return DRAG_INSERT;
    }

    public void setDragMode(int mode) {
    }

    public abstract List<String> displayOrder();

    public abstract void setDisplayOrder(List<String> keys);

    public static DesktopState memory() {
        return new Memory();
    }

    private static final class Memory extends DesktopState {
        private final Set<String> hidden = new HashSet<String>();
        private final List<String> order = new ArrayList<String>();
        private WallpaperMode mode = WallpaperMode.DEFAULT;
        private String customPath;
        private String dailyStartdate;
        private String dailySource;
        private int columns = DEFAULT_COLUMNS;
        private int rows = DEFAULT_ROWS;
        private int iconSize = DEFAULT_ICON_SIZE;
        private int dragMode = DRAG_INSERT;

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
        public int gridColumns() {
            return columns;
        }

        @Override
        public void setGridColumns(int columns) {
            this.columns = columns;
        }

        @Override
        public int gridRows() {
            return rows;
        }

        @Override
        public void setGridRows(int rows) {
            this.rows = rows;
        }

        @Override
        public int iconSize() {
            return iconSize;
        }

        @Override
        public void setIconSize(int size) {
            this.iconSize = size;
        }

        @Override
        public int dragMode() {
            return dragMode;
        }

        @Override
        public void setDragMode(int mode) {
            this.dragMode = mode == DRAG_SWAP ? DRAG_SWAP : DRAG_INSERT;
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

        @Override
        public String dailySource() {
            return dailySource;
        }

        @Override
        public void setDailySource(String source) {
            this.dailySource = source;
        }
    }
}
