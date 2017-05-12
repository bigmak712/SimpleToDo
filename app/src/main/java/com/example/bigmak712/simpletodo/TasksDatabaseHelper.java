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
    public static final String DATABASE_TABLE = "tasks";
    private static final int DATABASE_VERSION = 1;

    // Task Table Columns
    public static final String KEY_TASK_ID = "_id";
    public static final String KEY_TASK_TITLE = "title";
    public static final String KEY_TASK_NOTES = "notes";
    public static final String KEY_TASK_DUE_DATE = "dueDate";

    public TasksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + DATABASE_TABLE +
                "( " +
                KEY_TASK_ID + " INTEGER PRIMARY KEY," +
                KEY_TASK_TITLE + " TEXT," +
                KEY_TASK_NOTES + " TEXT," +
                KEY_TASK_DUE_DATE + " TEXT " +
                ")";

        database.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }


    /*
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


    public void addTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TASK_TITLE, task.title);
            values.put(KEY_TASK_NOTES, task.notes);
            values.put(KEY_TASK_DUE_DATE, task.date);

            db.insertOrThrow(TABLE_TASKS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e){
            System.err.println("Error while trying to add a task");
        } finally {
            db.endTransaction();
        }
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();

        String TASKS_SELECT_QUERY = String.format("SELECT * FROM %s", TABLE_TASKS);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TASKS_SELECT_QUERY, null);
        try {
            if(cursor.moveToFirst()) {
                do {
                    Task newTask = new Task();
                    newTask.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TASK_TITLE)));
                    newTask.setNotes(cursor.getString(cursor.getColumnIndex(KEY_TASK_NOTES)));
                    newTask.setDate(cursor.getString(cursor.getColumnIndex(KEY_TASK_DUE_DATE)));

                    tasks.add(newTask);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            System.err.println("Error while trying to get tasks from database");
        } finally {
            if(cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return tasks;
    }

    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_TITLE, task.getTitle());
        values.put(KEY_TASK_NOTES, task.getNotes());
        values.put(KEY_TASK_DUE_DATE, task.getDate());

        return db.update(TABLE_TASKS, values, KEY_TASK_TITLE,
                new String[]{String.valueOf(task.getTitle())});
    }

    public void deleteAllTasks() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TASKS, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.err.println("Error while trying to delete all tasks");
        } finally {
            db.endTransaction();
        }
    }
    */
}
