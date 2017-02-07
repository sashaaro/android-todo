package com.example.sasha.todo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Sasha on 06.02.2017.
 */
class TodoAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Project> list = new ArrayList<Project>();
    private Application application;

    public TodoAdapter(Context context, Application application) {
        this.context = context;
        this.application = application;
    }

    public TodoAdapter setList(ArrayList<Project> list) {
        this.list = list;

        return this;
    }

    @Override
    public int getGroupCount() {
        return this.list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.list.get(groupPosition).todos.size();
    }

    @Override
    public Project getGroup(int groupPosition) {
        return this.list.get(groupPosition);
    }

    @Override
    public Todo getChild(int groupPosition, int childPosition) {
        return this.list.get(groupPosition).todos.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return this.list.get(groupPosition).id;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return this.list.get(groupPosition).todos.get(childPosition).id;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Project project = (Project)getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.head, null);
        }
        TextView lblListHeader = (TextView)convertView.findViewById(R.id.textSeparator);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(project.title);
        //lblListHeader.set

        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Todo todo = (Todo)getChild(groupPosition,childPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView)convertView.findViewById(R.id.lblListItem);
        CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.cbBox);
        checkBox.setChecked(todo.isCompleted);
        checkBox.setOnCheckedChangeListener(new CheckboxCheckedListener(todo, this.application));

        txtListChild.setText(todo.title);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
};