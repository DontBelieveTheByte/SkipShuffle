package com.dontbelievethebyte.skipshuffle.listeners;

import android.widget.AdapterView;

import com.dontbelievethebyte.skipshuffle.activities.ListNavigatorActivity;

public abstract class AbstractListClick implements AdapterView.OnItemClickListener {

    protected ListNavigatorActivity listActivity;

    public AbstractListClick(ListNavigatorActivity listActivity)
    {
        this.listActivity = listActivity;
    }
}
