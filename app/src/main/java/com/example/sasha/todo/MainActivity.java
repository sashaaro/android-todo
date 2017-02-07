package com.example.sasha.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Application application = (Application) this.getApplication();
        final Repository repository = new Repository(application);
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

        RelativeLayout todoListLayout = (RelativeLayout) findViewById(R.id.include_todo_list);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) todoListLayout.findViewById(R.id.swiperefresh);

        final ExpandableListView listView = (ExpandableListView) swipeRefreshLayout.findViewById(R.id.todo_list);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                repository.findProjectsAll(new Repository.ProjectsCallback() {
                    @Override
                    public void execute(ArrayList<Project> projects) {
                        // TODO try catch NullPointerException

                        todoListAdapter
                                .setList(projects)
                                .notifyDataSetChanged();

                        swipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(listView, "Обновлено", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                });
            }
        });

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
