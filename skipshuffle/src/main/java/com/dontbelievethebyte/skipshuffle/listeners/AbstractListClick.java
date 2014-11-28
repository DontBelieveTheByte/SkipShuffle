/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.listeners;

import android.widget.AdapterView;

import com.dontbelievethebyte.skipshuffle.activities.MusicContentBrowserActivity;

public abstract class AbstractListClick implements AdapterView.OnItemClickListener {

    protected MusicContentBrowserActivity listActivity;

    public AbstractListClick(MusicContentBrowserActivity listActivity)
    {
        this.listActivity = listActivity;
    }
}
