/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.listeners.concrete;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.listeners.AbstractDrawerClickListener;

public class ContentBrowserActivityClickListener extends AbstractDrawerClickListener {


    public ContentBrowserActivityClickListener(DrawerLayout drawer)
    {
        super(drawer);
    }

    @Override
    protected void browseSongs(Context context)
    {
        Log.d(BaseActivity.TAG, "DMADOSAMDO");
    }

    @Override
    protected void browseArtists(Context context)
    {

    }

    @Override
    protected void browseAlbums(Context context)
    {

    }

    @Override
    protected void browseGenres(Context context)
    {

    }
}
