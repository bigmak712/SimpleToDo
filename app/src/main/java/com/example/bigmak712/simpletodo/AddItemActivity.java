package com.example.bigmak712.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private final int SUBMIT_CODE = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void onSubmit(View view) {

        EditText etTask = (EditText) findViewById(R.id.etTask);
        EditText etNotes = (EditText) findViewById(R.id.etNotes);
        EditText etDueDate = (EditText) findViewById(R.id.etDueDate);

        Intent i = new Intent();

        i.putExtra("task", etTask.getText().toString());
        i.putExtra("notes", etNotes.getText().toString());
        i.putExtra("dueDate", etDueDate.getText().toString());

        setResult(SUBMIT_CODE, i);
        finish();
    }
}
