package com.android.launcherWT.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.launcherWT.core.DesktopState;
import com.android.launcherWT.core.WallpaperMode;

public class DesktopDb extends DesktopState {
    static final String NAME = "desktop.db";
    private static final int VERSION = 1;
    private static final int WALLPAPER_ID = 1;

    private final Context context;
    private final Helper helper;

    public DesktopDb(Context context) {
        this.context = context;
        this.helper = new Helper(context);
    }

    @Override
    public boolean isHidden(String key) {
        Cursor cursor = open().query(
                "hidden",
                new String[] {"component_key"},
                "component_key=?",
                new String[] {key},
                null,
                null,
                null);
        try {
            return cursor.moveToFirst();
        } finally {
            cursor.close();
        }
    }

    @Override
    public void setHidden(String key, boolean hidden) {
        SQLiteDatabase db = open();
        if (hidden) {
            ContentValues values = new ContentValues();
            values.put("component_key", key);
            db.insertWithOnConflict("hidden", null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } else {
            db.delete("hidden", "component_key=?", new String[] {key});
        }
    }

    @Override
    public WallpaperMode wallpaperMode() {
        return readWallpaper().mode;
    }

    @Override
    public void setWallpaperMode(WallpaperMode mode) {
        WallpaperRow row = readWallpaper();
        writeWallpaper(mode, row.customPath, row.dailyStartdate);
    }

    @Override
    public String customPath() {
        return readWallpaper().customPath;
    }

    @Override
    public void setCustomPath(String path) {
        WallpaperRow row = readWallpaper();
        writeWallpaper(row.mode, path, row.dailyStartdate);
    }

    @Override
    public String dailyStartdate() {
        return readWallpaper().dailyStartdate;
    }

    @Override
    public void setDailyStartdate(String date) {
        WallpaperRow row = readWallpaper();
        writeWallpaper(row.mode, row.customPath, date);
    }

    private SQLiteDatabase open() {
        try {
            return helper.getWritableDatabase();
        } catch (SQLiteException first) {
            helper.close();
            context.deleteDatabase(NAME);
            return helper.getWritableDatabase();
        }
    }

    private WallpaperRow readWallpaper() {
        WallpaperRow row = new WallpaperRow();
        Cursor cursor = open().query(
                "wallpaper",
                new String[] {"mode", "custom_path", "daily_startdate"},
                "id=?",
                new String[] {Integer.toString(WALLPAPER_ID)},
                null,
                null,
                null);
        try {
            if (!cursor.moveToFirst()) {
                return row;
            }
            String mode = cursor.getString(0);
            if (mode != null) {
                row.mode = WallpaperMode.valueOf(mode);
            }
            row.customPath = cursor.getString(1);
            row.dailyStartdate = cursor.getString(2);
            return row;
        } finally {
            cursor.close();
        }
    }

    private void writeWallpaper(WallpaperMode mode, String customPath, String dailyStartdate) {
        ContentValues values = new ContentValues();
        values.put("id", WALLPAPER_ID);
        values.put("mode", mode.name());
        values.put("custom_path", customPath);
        values.put("daily_startdate", dailyStartdate);
        open().insertWithOnConflict("wallpaper", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private static final class WallpaperRow {
        private WallpaperMode mode = WallpaperMode.DAILY;
        private String customPath;
        private String dailyStartdate;
    }

    private static final class Helper extends SQLiteOpenHelper {
        Helper(Context context) {
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE hidden(component_key TEXT PRIMARY KEY)");
            db.execSQL(
                    "CREATE TABLE wallpaper(id INTEGER PRIMARY KEY, mode TEXT, custom_path TEXT, daily_startdate TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
