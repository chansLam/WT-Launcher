package com.android.launcherWT.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.launcherWT.core.DesktopState;
import com.android.launcherWT.core.WallpaperMode;

import java.util.ArrayList;
import java.util.List;

public class DesktopDb extends DesktopState {
    static final String NAME = "desktop.db";
    private static final int VERSION = 2;
    private static final int WALLPAPER_ID = 1;

    private final Context context;
    private final Helper helper;

    public DesktopDb(Context context) {
        this.context = context;
        this.helper = new Helper(context);
    }

    @Override
    public boolean isHidden(String key) {
        try {
            return queryHidden(open(), key);
        } catch (SQLiteException first) {
            rebuild();
            return queryHidden(helper.getWritableDatabase(), key);
        }
    }

    @Override
    public void setHidden(String key, boolean hidden) {
        try {
            writeHidden(open(), key, hidden);
        } catch (SQLiteException first) {
            rebuild();
            writeHidden(helper.getWritableDatabase(), key, hidden);
        }
    }

    @Override
    public List<String> displayOrder() {
        try {
            return queryOrder(open());
        } catch (SQLiteException first) {
            rebuild();
            return queryOrder(helper.getWritableDatabase());
        }
    }

    @Override
    public void setDisplayOrder(List<String> keys) {
        try {
            writeOrder(open(), keys);
        } catch (SQLiteException first) {
            rebuild();
            writeOrder(helper.getWritableDatabase(), keys);
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

    private void writeHidden(SQLiteDatabase db, String key, boolean hidden) {
        if (hidden) {
            ContentValues values = new ContentValues();
            values.put("component_key", key);
            db.insertWithOnConflict("hidden", null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.delete("app_order", "component_key=?", new String[] {key});
        } else {
            db.delete("hidden", "component_key=?", new String[] {key});
        }
    }

    private List<String> queryOrder(SQLiteDatabase db) {
        List<String> keys = new ArrayList<String>();
        Cursor cursor = db.query(
                "app_order",
                new String[] {"component_key"},
                null,
                null,
                null,
                null,
                "position ASC");
        try {
            while (cursor.moveToNext()) {
                keys.add(cursor.getString(0));
            }
            return keys;
        } finally {
            cursor.close();
        }
    }

    private void writeOrder(SQLiteDatabase db, List<String> keys) {
        db.beginTransaction();
        try {
            db.delete("app_order", null, null);
            if (keys != null) {
                for (int i = 0; i < keys.size(); i++) {
                    String key = keys.get(i);
                    if (key == null) {
                        continue;
                    }
                    ContentValues values = new ContentValues();
                    values.put("component_key", key);
                    values.put("position", i);
                    db.insertWithOnConflict("app_order", null, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private boolean queryHidden(SQLiteDatabase db, String key) {
        Cursor cursor = db.query(
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

    private SQLiteDatabase open() {
        try {
            return helper.getWritableDatabase();
        } catch (SQLiteException first) {
            rebuild();
            return helper.getWritableDatabase();
        }
    }

    private void rebuild() {
        helper.close();
        context.deleteDatabase(NAME);
    }

    private WallpaperRow readWallpaper() {
        try {
            return queryWallpaper(open());
        } catch (SQLiteException first) {
            rebuild();
            return queryWallpaper(helper.getWritableDatabase());
        }
    }

    private WallpaperRow queryWallpaper(SQLiteDatabase db) {
        WallpaperRow row = new WallpaperRow();
        Cursor cursor = db.query(
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
                try {
                    row.mode = WallpaperMode.valueOf(mode);
                } catch (RuntimeException invalid) {
                    row.mode = WallpaperMode.DAILY;
                }
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
            db.execSQL(
                    "CREATE TABLE app_order(component_key TEXT PRIMARY KEY, position INTEGER NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 2) {
                db.execSQL(
                        "CREATE TABLE IF NOT EXISTS app_order(component_key TEXT PRIMARY KEY, position INTEGER NOT NULL)");
            }
        }
    }
}
