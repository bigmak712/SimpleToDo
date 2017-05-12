package com.example.bigmak712.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_CODE = 20;
    private final int SAVE_CODE = 30;
    //private final int ADD_CODE = 40;
    //private final int SUBMIT_CODE = 50;

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;

    ArrayList<Task> tasks;
    CustomTaskAdapter tasksAdapter;

    ListView lvItems;
    //TasksDatabaseHelper dbHelper;

    private TaskDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // Retrieve the items that are saved
        readItems();

        // Set up the list view with the items
        lvItems = (ListView)findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        */

        dataSource = new TaskDataSource(this);
        dataSource.open();

        tasks = new ArrayList<Task>(dataSource.getAllTasks());

        tasksAdapter = new CustomTaskAdapter(this, tasks);
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(tasksAdapter);

        setupListViewListener();

        //dbHelper = TasksDatabaseHelper.getInstance(this);

    }

    // Adds input item to the list
    public void onAddItem(View view) {

        // Get the text from the edit text
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String title = etNewItem.getText().toString();
        Task t = dataSource.addTask(new Task(title, "", ""));
        tasksAdapter.add(t);
        tasksAdapter.notifyDataSetChanged();

        /*
        // Add the new task and set the edit text to blank
        itemsAdapter.add(itemText);
        etNewItem.setText("");

        writeItems();
        */

        //Intent i = new Intent(MainActivity.this, AddItemActivity.class);
        //startActivityForResult(i, ADD_CODE);
    }

    // Method for setting up listener
    public void setupListViewListener() {

        // Attach a LongClickListener to each item
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            // Hold the item for a while to remove it
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {

                dataSource.deleteTask(tasks.get(pos));

                // Removes the item
                //items.remove(pos);
                tasks.remove(pos);

                // Refreshes the adapter
                //itemsAdapter.notifyDataSetChanged();
                tasksAdapter.notifyDataSetChanged();

                //writeItems();

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                //String itemText = tasks.get(pos).getTitle();
                String itemText = items.get(pos).toString();
                launchEditView(itemText, pos);
            }
        });
    }

    // Open a file and read the list of items
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            /*
            Scanner reader = new Scanner(todoFile);
            while(reader.hasNext()) {
                String title = reader.next();
                String notes = reader.next();
                String date = reader.next();
                tasks.add(new Task(title, notes, date));
            }
            */

            items = new ArrayList<String>(FileUtils.readLines(todoFile));
            //tasks = new ArrayList<Task>(FileUtils.readLines(todoFile));
        } catch (IOException e){
            items = new ArrayList<String>();
            //tasks = new ArrayList<Task>();
        }
    }

    // Save a file and write the list of items
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
            //FileUtils.writeLines(todoFile, tasks);
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


            items.remove(pos);
            items.add(pos, etTask);
            itemsAdapter.notifyDataSetChanged();

            /*
            tasks.get(pos).setTitle(etTask);
            dbHelper.updateTask(tasks.get(pos));
            tasksAdapter.notifyDataSetChanged();
            */

            writeItems();
        }
        /*
        else if(requestCode == ADD_CODE && resultCode == SUBMIT_CODE) {
            String task = i.getExtras().getString("task");
            String notes = i.getExtras().getString("notes");
            String dueDate = i.getExtras().getString("dueDate");

            Task t = new Task(task, notes, dueDate);
            tasks.add(t);

            tasksAdapter.notifyDataSetChanged();
            dbHelper.addTask(t);
        }*/
        else {
            System.err.println("Error when receiving data from EditView");
        }
    }
}
