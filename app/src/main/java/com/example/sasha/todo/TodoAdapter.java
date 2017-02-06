package com.example.sasha.todo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by Sasha on 06.02.2017.
 */
class TodoAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private CompoundButton.OnCheckedChangeListener listener;
    private LayoutInflater mInflater;

    public TodoAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(ArrayList<Project> list) {
        for(Iterator i = list.iterator(); i.hasNext();) {
            Project project = (Project) i.next();
            this.addSectionHeaderItem(project.title);
            for(Iterator j = project.todos.iterator(); j.hasNext();) {
                Todo todo = (Todo)j.next();
                this.addItem(todo.title);
            }
        }
        //notifyDataSetChanged();
    }

    protected void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }



    protected void addSectionHeaderItem(final String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setListener(CompoundButton.OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);

                    holder.checkBox = (CheckBox) convertView.findViewById(R.id.cbBox);
                    holder.checkBox.setChecked(true); // TODO
                    //holder.checkBox.setTag(position);
                    holder.checkBox.setTag(new Todo());
                    holder.checkBox.setOnCheckedChangeListener(this.listener);

                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.head, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    break;
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }

}