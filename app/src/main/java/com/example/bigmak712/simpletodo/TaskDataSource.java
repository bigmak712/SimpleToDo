package com.example.bigmak712.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigmak712 on 5/11/17.
 */

public class TaskDataSource {

    // Database fields
    private SQLiteDatabase database;
    private TasksDatabaseHelper dbHelper;
    private String[] allColumns = {TasksDatabaseHelper.KEY_TASK_TITLE,
            TasksDatabaseHelper.KEY_TASK_NOTES, TasksDatabaseHelper.KEY_TASK_DUE_DATE};

    public TaskDataSource(Context context) {
        dbHelper = new TasksDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Task addTask(Task task) {

        ContentValues values = new ContentValues();
        values.put(TasksDatabaseHelper.KEY_TASK_TITLE, task.getTitle());
        values.put(TasksDatabaseHelper.KEY_TASK_NOTES, task.getNotes());
        values.put(TasksDatabaseHelper.KEY_TASK_DUE_DATE, task.getDate());

        long insertId = database.insert(TasksDatabaseHelper.DATABASE_TABLE, null, values);
        Cursor cursor = database.query(TasksDatabaseHelper.DATABASE_TABLE, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();

        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }

    public void deleteTask(Task task) {
        long id = task.getId();
        database.delete(TasksDatabaseHelper.DATABASE_TABLE, TasksDatabaseHelper.KEY_TASK_ID
        + " = " + id, null);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<Task>();

        Cursor cursor = database.query(TasksDatabaseHelper.DATABASE_TABLE, allColumns,
                null, null, null, null, null);

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Task t = cursorToTask(cursor);
            tasks.add(t);
        }

        cursor.close();
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {
        int iTitle = cursor.getColumnIndex(TasksDatabaseHelper.KEY_TASK_TITLE);
        int iNotes = cursor.getColumnIndex(TasksDatabaseHelper.KEY_TASK_NOTES);
        int iDate = cursor.getColumnIndex(TasksDatabaseHelper.KEY_TASK_DUE_DATE);

        Task t = new Task();
        t.setTitle(cursor.getString(iTitle));
        t.setNotes(cursor.getString(iNotes));
        t.setDate(cursor.getString(iDate));
        return t;
    }
}
