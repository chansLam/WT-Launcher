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
    private static final int VERSION = 4;
    private static final int WALLPAPER_ID = 1;
    private static final int LAYOUT_ID = 1;
    public static final int MIN_COLUMNS = 4;
    public static final int MAX_COLUMNS = 7;
    public static final int MIN_ROWS = 2;
    public static final int MAX_ROWS = 4;
    public static final int MIN_ICON = 64;
    public static final int MAX_ICON = 160;

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
    public int gridColumns() {
        return readLayout().columns;
    }

    @Override
    public void setGridColumns(int columns) {
        LayoutRow row = readLayout();
        writeLayout(clamp(columns, MIN_COLUMNS, MAX_COLUMNS), row.rows, row.iconSize);
    }

    @Override
    public int gridRows() {
        return readLayout().rows;
    }

    @Override
    public void setGridRows(int rows) {
        LayoutRow row = readLayout();
        writeLayout(row.columns, clamp(rows, MIN_ROWS, MAX_ROWS), row.iconSize);
    }

    @Override
    public int iconSize() {
        return readLayout().iconSize;
    }

    @Override
    public void setIconSize(int size) {
        LayoutRow row = readLayout();
        writeLayout(row.columns, row.rows, clamp(size, MIN_ICON, MAX_ICON));
    }

    private static int clamp(int value, int min, int max) {
        return value < min ? min : (value > max ? max : value);
    }

    private LayoutRow readLayout() {
        try {
            return queryLayout(open());
        } catch (SQLiteException first) {
            rebuild();
            return queryLayout(helper.getWritableDatabase());
        }
    }

    private LayoutRow queryLayout(SQLiteDatabase db) {
        LayoutRow row = new LayoutRow();
        Cursor cursor = db.query(
                "layout",
                new String[] {"columns", "rows", "icon_size"},
                "id=?",
                new String[] {Integer.toString(LAYOUT_ID)},
                null,
                null,
                null);
        try {
            if (!cursor.moveToFirst()) {
                return row;
            }
            row.columns = clamp(cursor.getInt(0), MIN_COLUMNS, MAX_COLUMNS);
            row.rows = clamp(cursor.getInt(1), MIN_ROWS, MAX_ROWS);
            row.iconSize = clamp(cursor.getInt(2), MIN_ICON, MAX_ICON);
            return row;
        } finally {
            cursor.close();
        }
    }

    private void writeLayout(int columns, int rows, int iconSize) {
        ContentValues values = new ContentValues();
        values.put("id", LAYOUT_ID);
        values.put("columns", columns);
        values.put("rows", rows);
        values.put("icon_size", iconSize);
        open().insertWithOnConflict("layout", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public WallpaperMode wallpaperMode() {
        return readWallpaper().mode;
    }

    @Override
    public void setWallpaperMode(WallpaperMode mode) {
        WallpaperRow row = readWallpaper();
        writeWallpaper(mode, row.customPath, row.dailyStartdate, row.dailySource);
    }

    @Override
    public String customPath() {
        return readWallpaper().customPath;
    }

    @Override
    public void setCustomPath(String path) {
        WallpaperRow row = readWallpaper();
        writeWallpaper(row.mode, path, row.dailyStartdate, row.dailySource);
    }

    @Override
    public String dailyStartdate() {
        return readWallpaper().dailyStartdate;
    }

    @Override
    public void setDailyStartdate(String date) {
        WallpaperRow row = readWallpaper();
        writeWallpaper(row.mode, row.customPath, date, row.dailySource);
    }

    @Override
    public String dailySource() {
        return readWallpaper().dailySource;
    }

    @Override
    public void setDailySource(String source) {
        WallpaperRow row = readWallpaper();
        writeWallpaper(row.mode, row.customPath, row.dailyStartdate, source);
    }

    private void writeHidden(SQLiteDatabase db, String key, boolean hidden) {
        if (hidden) {
            ContentValues values = new ContentValues();
            values.put("component_key", key);
            db.insertWithOnConflict("hidden", null, values, SQLiteDatabase.CONFLICT_REPLACE);
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
                new String[] {"mode", "custom_path", "daily_startdate", "daily_source"},
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
            row.dailySource = cursor.getString(3);
            return row;
        } finally {
            cursor.close();
        }
    }

    private void writeWallpaper(WallpaperMode mode, String customPath, String dailyStartdate, String dailySource) {
        ContentValues values = new ContentValues();
        values.put("id", WALLPAPER_ID);
        values.put("mode", mode.name());
        values.put("custom_path", customPath);
        values.put("daily_startdate", dailyStartdate);
        values.put("daily_source", dailySource);
        open().insertWithOnConflict("wallpaper", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private static final class LayoutRow {
        private int columns = DesktopState.DEFAULT_COLUMNS;
        private int rows = DesktopState.DEFAULT_ROWS;
        private int iconSize = DesktopState.DEFAULT_ICON_SIZE;
    }

    private static final class WallpaperRow {
        private WallpaperMode mode = WallpaperMode.DEFAULT;
        private String customPath;
        private String dailyStartdate;
        private String dailySource;
    }

    private static final class Helper extends SQLiteOpenHelper {
        Helper(Context context) {
            super(context, NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE hidden(component_key TEXT PRIMARY KEY)");
            db.execSQL(
                    "CREATE TABLE wallpaper(id INTEGER PRIMARY KEY, mode TEXT, custom_path TEXT, daily_startdate TEXT, daily_source TEXT)");
            db.execSQL(
                    "CREATE TABLE app_order(component_key TEXT PRIMARY KEY, position INTEGER NOT NULL)");
            db.execSQL(
                    "CREATE TABLE layout(id INTEGER PRIMARY KEY, columns INTEGER, rows INTEGER, icon_size INTEGER)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 2) {
                db.execSQL(
                        "CREATE TABLE IF NOT EXISTS app_order(component_key TEXT PRIMARY KEY, position INTEGER NOT NULL)");
            }
            if (oldVersion < 3) {
                db.execSQL("ALTER TABLE wallpaper ADD COLUMN daily_source TEXT");
            }
            if (oldVersion < 4) {
                db.execSQL(
                        "CREATE TABLE IF NOT EXISTS layout(id INTEGER PRIMARY KEY, columns INTEGER, rows INTEGER, icon_size INTEGER)");
            }
        }
    }
}
