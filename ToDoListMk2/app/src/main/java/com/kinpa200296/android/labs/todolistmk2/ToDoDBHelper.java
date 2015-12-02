package com.kinpa200296.android.labs.todolistmk2;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ToDoDatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE = "ToDoList";

    public static final String COLUMN_ID = "ToDoId";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_DESCRIPTION = "Description";
    public static final String COLUMN_YEAR = "Year";
    public static final String COLUMN_MONTH = "Month";
    public static final String COLUMN_DAY = "Day";
    public static final String COLUMN_HOUR = "Hour";
    public static final String COLUMN_MINUTE = "Minute";
    public static final String COLUMN_IS_DONE = "IsDone";


    public ToDoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("+COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE
            + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, "
            + COLUMN_YEAR + " INTEGER, " + COLUMN_MONTH
            + " INTEGER, " + COLUMN_DAY + " INTEGER, "
            + COLUMN_HOUR + " INTEGER, " + COLUMN_MINUTE
            + " INTEGER, " + COLUMN_IS_DONE +  " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
