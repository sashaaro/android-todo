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

    private TodoAdapter todoListAdapter;
    private ArrayList<Project> projects = new ArrayList<Project>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Application application = (Application) this.getApplication();

        todoListAdapter = new TodoAdapter(this, this.projects);
        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Todo todo = (Todo) buttonView.getTag();
                System.out.println(todo);
                System.out.println(buttonView);
                if (todo.isCompleted == isChecked) {
                    //Todo todo = new Todo(); // TODO get from checkbox

                    JsonObject json = new JsonObject();
                    json.addProperty("id", todo.id);
                    json.addProperty("isCompleted", todo.isCompleted);

                    application.ionLoadBuilder().load("https://oblakotodo.herokuapp.com/api/todo_change_status")
                                        .setJsonObjectBody(json)
                                        .asJsonObject()
                                        /*.setCallback(new FutureCallback<JsonObject>() {
                                            @Override
                                            public void onCompleted(Exception e, JsonObject result) {
                                                if (e != null) {
                                                    // TODO
                                                }
                                            }
                                        })*/
                    ;
                };

            }
        };
        //todoListAdapter.setListener(listener);

        //adapter = new SimpleExpandableListAdapter(this);

        setContentView(R.layout.activity_main);
        //ListView listView = (ListView) findViewById(R.id.list_item);
        //listView.setAdapter(todoListAdapter);
        RelativeLayout todoListLayout = (RelativeLayout) findViewById(R.id.include_todo_list11);

        ExpandableListView listView = (ExpandableListView) todoListLayout.findViewById(R.id.todo_list);

        listView.setAdapter(this.todoListAdapter);

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

        final ArrayList<Project> projects = this.projects;

        application.ionLoadBuilder()
            .load("https://oblakotodo.herokuapp.com/api/projects")
            .asJsonArray()
            .setCallback(new FutureCallback<JsonArray>() {
                @Override
                public void onCompleted(Exception e, JsonArray result) {
                    if (e == null) {

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
                        System.out.println(projects);
                        System.out.println(projects.size());
                        todoListAdapter.notifyDataSetChanged();
                        // todoListAdapter.addSectionHeaderItem("Section #" + i);

                    } else {
                        // TODO
                        // Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
            });
    }
}
