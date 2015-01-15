/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.listeners;

import android.widget.AdapterView;

import com.dontbelievethebyte.sk1pshuffle.activities.MusicContentBrowserActivity;

public abstract class AbstractListClick implements AdapterView.OnItemClickListener {

    protected MusicContentBrowserActivity listActivity;

    public AbstractListClick(MusicContentBrowserActivity listActivity)
    {
        this.listActivity = listActivity;
    }
}
