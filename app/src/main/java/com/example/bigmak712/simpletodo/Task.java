package com.example.bigmak712.simpletodo;

/**
 * Created by bigmak712 on 5/6/17.
 */

public class Task {
    private long id;
    private String title;
    private String notes;
    private String date;

    public Task() {
        this.title = "";
        this.notes = "";
        this.date = "";
    }

    public Task(String title, String notes, String date) {
        this.title = title;
        this.notes = notes;
        this.date = date;
    }

    public long getId() { return this.id; }

    public String getTitle() {
        return this.title;
    }

    public String getNotes() {
        return this.notes;
    }

    public String getDate() { return this.date; }

    public void setId(long id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setDate(String date) { this.date = date; }

    public void setNotes(String notes) { this.notes = notes; }
}
