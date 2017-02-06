package com.example.sasha.todo;

import java.util.ArrayList;

/**
 * Created by Sasha on 06.02.2017.
 */
public class Project {
    public Integer id;
    public String title;
    public ArrayList<Todo> todos = new ArrayList<Todo>();

    public Project(String title) {
        this.title = title;
    }

    public String toString() {
        return this.title;
    }
}
