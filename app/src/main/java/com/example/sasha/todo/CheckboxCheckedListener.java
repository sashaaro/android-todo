package com.example.sasha.todo;

import android.support.v7.widget.AppCompatCheckBox;
import android.widget.CompoundButton;

import com.google.gson.JsonObject;

/**
 * Created by Sasha on 07.02.2017.
 */
public class CheckboxCheckedListener implements CompoundButton.OnCheckedChangeListener
{
    private Repository repository;

    public CheckboxCheckedListener(Repository repository){
        this.repository = repository;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        AppCompatCheckBox checkBox = (AppCompatCheckBox)buttonView;
        Todo todo = (Todo) checkBox.getTag();

        todo.isCompleted = isChecked;
        this.repository.persistTodoStatus(todo);
    }
}
