package com.example.sasha.todo;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

// TODO rename class more suitable
public class AddActivity extends
        //AppCompatActivity
        Activity
{

    private ArrayList<Project> projects = new ArrayList<Project>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        final Application application = (Application) this.getApplication();
        final Repository repository = new Repository(application);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);

        final LinearLayout formLayout = (LinearLayout) findViewById(R.id.include_form);
        final EditText todoTextView = (EditText) formLayout.findViewById(R.id.todo_text);
        final Spinner projectsSpinnerView = (Spinner) findViewById(R.id.projects_spinner);

        //headTextView.setText("Задача");

        final Activity activity = this;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish(); // back to previously activityR
            }
        });

        ArrayList<Project> projects = new ArrayList<Project>();

        final ArrayAdapter projectLisAdapter = new ArrayAdapter<Project>(this, android.R.layout.simple_spinner_item);
        projectsSpinnerView.setAdapter(projectLisAdapter);

        // TODO disable save button

        repository.findProjectsAll(new Repository.ProjectsCallback() {
            @Override
            public void execute(ArrayList<Project> projects) {
                // TODO try catch NullPointerException
                projectLisAdapter.addAll(projects);
                projectLisAdapter.notifyDataSetChanged();
                // TODO enable save button
            }
        });

        ImageButton saveButton = (ImageButton) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = new Todo();

                todo.title = (String) todoTextView.getText().toString();
                Project selectedProject = (Project) projectsSpinnerView.getSelectedItem();
                todo.project = selectedProject;

                repository.persistTodo(todo, new Repository.TodoCallback() {
                    @Override
                    public void execute(Todo todo, JsonObject errors) {
                        if (todo.id != null) { // successfully save
                            activity.finish(); // back to previously activity

                            // TODO update todoList. notification

                        } else if (errors.isJsonObject()) {
                            JsonElement textErrorElement = errors.get("text");
                            if (textErrorElement.isJsonArray()) {
                                String error = textErrorElement.getAsJsonArray().get(0).getAsString();
                                todoTextView.setError(error);
                            }

                            // TODO check project
                        } else {
                            // wtf?!
                        }

                        // Snackbar.make(view, "Созранено", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                });
            }
        });
    }
}
