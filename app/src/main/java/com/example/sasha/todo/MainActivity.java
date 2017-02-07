package com.example.sasha.todo;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Application application = (Application) this.getApplication();
        Repository repository = new Repository(application);
        final TodoAdapter todoListAdapter = new TodoAdapter(this, application);

        repository.findProjectsAll(new Repository.ProjectsCallback() {
            @Override
            public void execute(ArrayList<Project> projects) {
                // TODO try catch NullPointerException

                todoListAdapter
                    .setList(projects)
                    .notifyDataSetChanged();
            }
        });

        setContentView(R.layout.activity_main);

        RelativeLayout todoListLayout = (RelativeLayout) findViewById(R.id.include_todo_list11);

        ExpandableListView listView = (ExpandableListView) todoListLayout.findViewById(R.id.todo_list);

        listView.setAdapter(todoListAdapter);

        ImageButton addButton = (ImageButton) findViewById(R.id.add_button);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent myIntent = new Intent(view.getContext(), AddActivity.class);
            startActivity(myIntent);
            }
        };

        addButton.setOnClickListener(clickListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(clickListener);
    }
}
