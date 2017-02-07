package com.example.sasha.todo;

import android.widget.CompoundButton;

import com.google.gson.JsonObject;

/**
 * Created by Sasha on 07.02.2017.
 */
public class CheckboxCheckedListener implements CompoundButton.OnCheckedChangeListener
{
    private Todo todo;
    private Application application;

    public CheckboxCheckedListener(Todo todo, Application application){
        this.todo = todo;
        this.application = application;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        todo.isCompleted = isChecked;
        persistTodo();
    }

    private void persistTodo() {
        JsonObject json = new JsonObject();
        json.addProperty("id", this.todo.id);
        json.addProperty("isCompleted", this.todo.isCompleted);
        this.application.ionLoadBuilder().load("https://oblakotodo.herokuapp.com/api/todo_change_status")
                .setJsonObjectBody(json)
                .asJsonObject();
    }
}
