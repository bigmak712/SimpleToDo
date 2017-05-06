package com.example.bigmak712.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_CODE = 20;
    private final int SAVE_CODE = 30;
    private final int ADD_CODE = 40;
    private final int SUBMIT_CODE = 50;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    ArrayList<Task> tasks;
    CustomTaskAdapter tasksAdapter;

    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();

        /*
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        */

        tasksAdapter = new CustomTaskAdapter(this, tasks);
        lvItems.setAdapter(tasksAdapter);

        setupListViewListener();
    }

    // Adds input item to the list
    public void onAddItem(View view) {
        /*
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");

        writeItems();
        */

        Intent i = new Intent(MainActivity.this, AddItemActivity.class);
        startActivityForResult(i, ADD_CODE);
    }

    // Method for setting up listener
    public void setupListViewListener() {

        // Attach a LongClickListener to each item
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            // Hold the item for a while to remove it
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {

                // Removes the item

                // items.remove(pos);
                tasks.remove(pos);

                // Refreshes the adapter

                // adapter.notifyDataSetChanged();
                tasksAdapter.notifyDataSetChanged();

                writeItems();

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                String itemText = tasks.get(pos).getTitle();
                launchEditView(itemText, pos);
            }
        });
    }

    // Open a file and read the list of items
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            // items = new ArrayList<String>(FileUtils.readLines(todoFile));
            tasks = new ArrayList<Task>(FileUtils.readLines(todoFile));
        } catch (IOException e){
            // items = new ArrayList<String>();
            tasks = new ArrayList<Task>();
        }
    }

    // Save a file and write the list of items
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            // FileUtils.writeLines(todoFile, items);
            FileUtils.writeLines(todoFile, tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchEditView(String item, int pos) {

        // 1st parameter: context; 2nd parameter: class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("item", item);
        i.putExtra("position", pos);

        startActivityForResult(i, EDIT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if(requestCode == EDIT_CODE && resultCode == SAVE_CODE) {

            String etTask = i.getExtras().getString("edit");
            int pos = i.getIntExtra("position", 0);

            /*
            items.remove(pos);
            items.add(pos, etTask);
            itemsAdapter.notifyDataSetChanged();
            */

            tasks.get(pos).setTitle(etTask);
            tasksAdapter.notifyDataSetChanged();

            writeItems();
        }

        else if(requestCode == ADD_CODE && resultCode == SUBMIT_CODE) {
            String task = i.getExtras().getString("task");
            String notes = i.getExtras().getString("notes");
            String dueDate = i.getExtras().getString("dueDate");

            Task t = new Task(task, notes, dueDate);
            tasks.add(t);

            tasksAdapter.notifyDataSetChanged();
            writeItems();
        }
        else {
            System.err.println("Error when receiving data from EditView");
        }
    }
}
