package com.example.sasha.todo;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Sasha on 06.02.2017.
 */
class TodoAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Project> list = new ArrayList<Project>();
    private CheckboxCheckedListener checkboxCheckedListener;

    public TodoAdapter(Context context, CheckboxCheckedListener checkboxCheckedListener) {
        this.context = context;
        this.checkboxCheckedListener = checkboxCheckedListener;
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
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);

            final Todo todo = (Todo)getChild(groupPosition, childPosition);

            CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.cbBox);
            TextView txtListChild = (TextView)convertView.findViewById(R.id.lblListItem);

            checkBox.setTag(todo);
            checkBox.setChecked(todo.isCompleted);

            txtListChild.setText(todo.title);

            ChainOnCheckedChangeListener chainOnCheckedChangeListener = new ChainOnCheckedChangeListener(); // for multiple setting eventListeners
            chainOnCheckedChangeListener.registerListener(this.checkboxCheckedListener);

            TextCheckboxCheckedListener textCheckboxCheckedListener = new TextCheckboxCheckedListener(txtListChild);
            chainOnCheckedChangeListener.registerListener(textCheckboxCheckedListener);
            checkBox.setOnCheckedChangeListener(chainOnCheckedChangeListener);


            textCheckboxCheckedListener.onCheckedChanged(checkBox, checkBox.isChecked());// fire event
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class TextCheckboxCheckedListener  implements CompoundButton.OnCheckedChangeListener {

        private TextView txtListChild;

        public TextCheckboxCheckedListener(TextView txtListChild)
        {
            this.txtListChild = txtListChild;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            txtListChild.setPaintFlags(isChecked ? Paint.STRIKE_THRU_TEXT_FLAG : Paint.ANTI_ALIAS_FLAG);
        }
    }
};