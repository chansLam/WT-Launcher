package com.wt.statistics.persistentdata;

/* loaded from: classes.dex */
public class DataDBHelper extends android.database.sqlite.SQLiteOpenHelper {
    public static final java.lang.String ACTIVITY_PAGE = "activity_page";
    private static final java.lang.String CREATE_EVENT_TABLE = "CREATE TABLE events (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp STRING NOT NULL, event_json STRING NOT NULL);";
    private static final java.lang.String CREATE_EXCEPTION_TABLE = "CREATE TABLE exception (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp STRING NOT NULL, excep_type STRING NOT NULL, excep_trace STRING NOT NULL, excep_anrinfo STRING, excep_cpuinfo STRING);";
    private static final java.lang.String CREATE_PAGE_TABLE = "CREATE TABLE page (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp STRING NOT NULL, page_type STRING NOT NULL, page_json STRING NOT NULL);";
    public static final java.lang.String DB_NAME = "statistics.db";
    private static final int DB_VERSION = 1;
    public static final java.lang.String EVENT_ID = "event_id";
    public static final java.lang.String EVENT_JSON = "event_json";
    public static final java.lang.String EVENT_LABEL = "event_label";
    public static final java.lang.String EVENT_TABLE_NAME = "events";
    public static final java.lang.String EVENT_TIME = "event_time";
    public static final java.lang.String EXCEP_ANRINFO = "excep_anrinfo";
    public static final java.lang.String EXCEP_CPUINFO = "excep_cpuinfo";
    public static final java.lang.String EXCEP_TABLE_NAME = "exception";
    public static final java.lang.String EXCEP_TRACE = "excep_trace";
    public static final java.lang.String EXCEP_TYPE = "excep_type";
    public static final java.lang.String FRAGMENT_PAGE = "fragment_page";
    public static final java.lang.String ID = "_id";
    private static final java.lang.String LOGTAG = "DataDBHelper";
    public static final java.lang.String PAGE_JSON = "page_json";
    public static final java.lang.String PAGE_TABLE_NAME = "page";
    public static final java.lang.String PAGE_TYPE = "page_type";
    public static final java.lang.String TIMESTAMP = "timestamp";

    public DataDBHelper(android.content.Context context) {
        super(context, DB_NAME, (android.database.sqlite.SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        com.wt.statistics.utils.SLog.i(LOGTAG, "onCreate start");
        db.execSQL(CREATE_EVENT_TABLE);
        db.execSQL(CREATE_PAGE_TABLE);
        db.execSQL(CREATE_EXCEPTION_TABLE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        com.wt.statistics.utils.SLog.i(LOGTAG, "onUpgrade start");
        db.execSQL("DROP TABLE IF EXISTS events");
        db.execSQL("DROP TABLE IF EXISTS page");
        db.execSQL("DROP TABLE IF EXISTS CREATE TABLE exception (_id INTEGER PRIMARY KEY AUTOINCREMENT, timestamp STRING NOT NULL, excep_type STRING NOT NULL, excep_trace STRING NOT NULL, excep_anrinfo STRING, excep_cpuinfo STRING);");
        onCreate(db);
    }
}
