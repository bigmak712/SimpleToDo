package com.example.bigmak712.simpletodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bigmak712 on 5/6/17.
 */

public class TasksDatabaseHelper extends SQLiteOpenHelper{

    // Database Info
    private static final String DATABASE_NAME = "tasksDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_TASKS = "tasks";

    // Task Table Columns
    private static final String KEY_TASK_TITLE = "title";
    private static final String KEY_TASK_NOTES = "notes";
    private static final String KEY_TASK_DUE_DATE = "dueDate";

    private static TasksDatabaseHelper sInstance;

    // Ensures that only one TasksDatabaseHelper will exist at any time
    public static synchronized TasksDatabaseHelper getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new TasksDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }
    private TasksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured
    // Configure database settings
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the 1st time
    // If a database already exists on disk w/ the same DATABASE_NAME, this method won't be called
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS +
                "(" +
                    KEY_TASK_TITLE + " TEXT," +
                    KEY_TASK_NOTES + " TEXT," +
                    KEY_TASK_DUE_DATE + " TEXT" +
                ")";

        db.execSQL(CREATE_TASKS_TABLE);
    }

    // Called when the database needs to be upgraded
    // Only called if a database already exists on disk w/ the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the existing database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
            onCreate(db);
        }
    }




}
