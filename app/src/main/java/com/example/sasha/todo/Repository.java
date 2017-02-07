package com.example.sasha.todo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sasha on 07.02.2017.
 */

public class Repository {
    private Application application;

    public Repository(Application application) {
        this.application = application;
    }

    public void findProjectsAll (final ProjectsCallback callback) {
        final ArrayList<Project> projects = new ArrayList<Project>();
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

                            callback.execute(projects);
                        } else {
                            // TODO
                            // Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    }
                });
    }

    public void persistTodo (Todo todo) {
        // TODO
    }

    public interface ProjectsCallback {
        public void execute(ArrayList<Project> projects);
    }
}
