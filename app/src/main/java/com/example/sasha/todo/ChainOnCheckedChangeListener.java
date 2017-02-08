package com.example.sasha.todo;

import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Created by Sasha on 08.02.2017.
 */
public class ChainOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
    private ArrayList<CompoundButton.OnCheckedChangeListener> registeredListeners = new ArrayList<CompoundButton.OnCheckedChangeListener>();

    public void registerListener (CompoundButton.OnCheckedChangeListener listener) {
        registeredListeners.add(listener);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        for(CompoundButton.OnCheckedChangeListener listener:registeredListeners) {
            listener.onCheckedChanged(buttonView, isChecked);
        }
    }
}
