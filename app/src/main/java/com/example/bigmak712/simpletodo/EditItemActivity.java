package com.example.bigmak712.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private final int SAVE_CODE = 30;

    EditText etTask;
    String item;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        item = getIntent().getStringExtra("item");
        pos = getIntent().getIntExtra("position", 0);

        etTask = (EditText)findViewById(R.id.etEditItem);
        etTask.setText(item);
        etTask.setSelection(item.length());
    }

    public void onSave(View view) {

        EditText etTask = (EditText)findViewById(R.id.etEditItem);
        Intent i = new Intent();
        i.putExtra("edit", etTask.getText().toString());
        i.putExtra("position", pos);

        setResult(SAVE_CODE, i);
        finish();
    }
}
