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

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    // Adds input item to the list
    public void onAddItem(View view) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");

        writeItems();
    }

    // Method for setting up listener
    public void setupListViewListener() {

        // Attach a LongClickListener to each item
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            // Hold the item for a while to remove it
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {

                // Removes the item
                items.remove(pos);

                // Refreshes the adapter
                itemsAdapter.notifyDataSetChanged();

                writeItems();

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
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
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    // Save a file and write the list of items
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
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
            writeItems();
        }
        else {
            System.err.println("Error when receiving data from EditView");
        }
    }
}
