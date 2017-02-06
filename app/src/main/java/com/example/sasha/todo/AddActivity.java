package com.example.sasha.todo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends
        //AppCompatActivity
        Activity
{

    private ArrayList<Project> projects = new ArrayList<Project>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        TextView headTextView = (TextView) findViewById(R.id.todoSeparator);
        //ListView projectsListView = (ListView) findViewById(R.id.projects_list);
        Spinner projectsSpinnerView = (Spinner) findViewById(R.id.projects_spinner);

        headTextView.setText("Задача");

        final Activity activity = this;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish(); // back to previously activity
            }
        });

        ArrayList<Project> projects = new ArrayList<Project>();

        projects.add(new Project("Первый"));
        projects.add(new Project("Второй"));
        projects.add(new Project("Третьий проект"));

        ArrayAdapter projectLisAdapter = new ArrayAdapter<Project>(this, android.R.layout.simple_spinner_item, projects);
        //SpinnerAdapter projectLisAdapter = new Spinn
        //projectsListView.setAdapter(projectLisAdapter);

        projectsSpinnerView.setAdapter(projectLisAdapter);

        ImageButton saveButton = (ImageButton) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // save
                // activity.finish();
                // Snackbar.make(view, "Successfully save", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}
