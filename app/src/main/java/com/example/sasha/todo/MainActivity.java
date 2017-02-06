package com.example.sasha.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
{

    private TodoAdapter todoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        todoListAdapter = new TodoAdapter(this);

        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list_item);
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

        Ion.with(this)
            .load("https://oblakotodo.herokuapp.com/api/projects")
            .asJsonArray()
            .setCallback(new FutureCallback<JsonArray>() {
                @Override
                public void onCompleted(Exception e, JsonArray result) {
                    if (e == null) {
                        ArrayList<Project> projects = new ArrayList<Project>();

                        for(Iterator i = result.iterator(); i.hasNext();) {
                            JsonObject item = (JsonObject) i.next();
                            Project project = new Project(item.get("title").getAsString());
                            project.id = item.get("id").getAsInt();
                            for(Iterator j = item.get("todos").getAsJsonArray().iterator(); j.hasNext();) {
                                JsonObject todoItem = (JsonObject) j.next();
                                Todo todo = new Todo();
                                todo.id = todoItem.get("id").getAsInt();
                                todo.title = todoItem.get("text").getAsString();
                                todo.isCompleted = todoItem.get("isCompleted").getAsBoolean();
                                project.todos.add(todo);
                            }

                            projects.add(project);
                        }
                        // TODO try catch NullPointerException
                        todoListAdapter.setList(projects);
                        // todoListAdapter.addSectionHeaderItem("Section #" + i);

                    } else {
                        // TODO
                        // Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            });
    }
}
