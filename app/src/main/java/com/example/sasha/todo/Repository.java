package com.example.sasha.todo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Sasha on 07.02.2017.
 */
public class Repository {
    private Application application;
    private static final String API_URL = "https://oblakotodo.herokuapp.com/api";

    public Repository(Application application) {
        this.application = application;
    }

    /**
     * @TODO caching?!
     * @param callback
     */
    public void findProjectsAll (final ProjectsCallback callback) {
        final ArrayList<Project> projects = new ArrayList<Project>();
        application.ionLoadBuilder()
                .load(API_URL + "/projects")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (e == null) {

                            // hydration.. filling an object with data
                            // TODO move to separated methods hydrateProjectFromJson hydrateTodoFromJson
                            for(Iterator i = result.iterator(); i.hasNext();) {
                                JsonObject item = (JsonObject) i.next();
                                Project project = new Project(item.get("id").getAsInt());
                                project.title = item.get("title").getAsString();
                                for(Iterator j = item.get("todos").getAsJsonArray().iterator(); j.hasNext();) {
                                    JsonObject todoItem = (JsonObject) j.next();
                                    Todo todo = new Todo();
                                    todo.id = todoItem.get("id").getAsInt();
                                    todo.title = todoItem.get("text").getAsString();
                                    JsonElement isCompleted = todoItem.get("isCompleted");
                                    if (!isCompleted.isJsonNull()) {
                                        todo.isCompleted = isCompleted.getAsBoolean();
                                    }
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

    /**
     * @TODO use that method
     * @param todo
     */
    public void persistTodo (final Todo todo, final TodoCallback callback) {
        JsonObject json = new JsonObject();
        if (todo.id != null) {
            json.addProperty("id", todo.id);
        }
        json.addProperty("isCompleted", todo.isCompleted);
        json.addProperty("text", todo.title);
        if (todo.project != null) {
            json.addProperty("project_id", todo.project.id);
        }

        if(todo.id == null) {
            application.ionLoadBuilder()
                    .load("PUT", API_URL + "/create_todo")
                    .setJsonObjectBody(json)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            System.out.println(result);

                            if (e == null) {
                                Integer ID = result.get("id").getAsInt();
                                todo.id = ID;
                                JsonElement isCompleted = result.get("isCompleted");
                                if (!isCompleted.isJsonNull()) {
                                    todo.isCompleted = isCompleted.getAsBoolean();
                                }
                                todo.title = result.get("text").getAsString();

                                // TODO full todo.project

                                callback.execute(todo);
                            } else {
                                // TODO
                                System.out.println(e.getMessage());
                            }
                        }
                    });
            ;
            // TODO throw new Exception("Todo should be already exists")
        }

        // TODO
    }


    public interface ProjectsCallback {
        public void execute(ArrayList<Project> projects);
    }

    public interface TodoCallback {
        public void execute(Todo todo);
    }
}
