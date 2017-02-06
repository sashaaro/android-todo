package com.example.sasha.todo;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddActivity extends
        //AppCompatActivity
        Activity
{

    private ArrayList<Category> categories = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        TextView headTextView = (TextView) findViewById(R.id.textSeparator);
        ListView categoriesListView = (ListView) findViewById(R.id.categories_list);

        headTextView.setText("Задача");

        final Activity activity = this;
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish(); // back to previously activity
            }
        });

        // load categories

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.categories = categories;
        categoriesListView.setAdapter(categoriesAdapter);

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

    private class CategoriesAdapter  extends BaseAdapter
    {
        public ArrayList<Category> categories = new ArrayList<Category>();

        public Category selectedCategory;

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Object getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return categories.indexOf(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //

            return null;
        }
    }
}
