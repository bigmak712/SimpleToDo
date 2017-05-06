package com.example.bigmak712.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bigmak712 on 5/6/17.
 */

public class CustomTaskAdapter extends ArrayAdapter<Task>{
    public CustomTaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data for this position
        Task task = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_layout, parent, false);
        }

        // Lookup views for data
        TextView title = (TextView) convertView.findViewById(R.id.task_title);
        TextView notes = (TextView) convertView.findViewById(R.id.task_notes);
        TextView date = (TextView) convertView.findViewById(R.id.task_date);

        // Populate the data into the template view
        title.setText(task.title);
        notes.setText(task.notes);
        date.setText(task.date);

        // Return the completed view
        return convertView;
    }
}
