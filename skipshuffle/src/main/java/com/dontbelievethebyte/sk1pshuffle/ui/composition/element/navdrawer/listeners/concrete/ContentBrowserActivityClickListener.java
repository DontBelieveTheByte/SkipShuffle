/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.listeners.concrete;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;

import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.listeners.AbstractDrawerClickListener;
import com.dontbelievethebyte.sk1pshuffle.utilities.LogUtil;

public class ContentBrowserActivityClickListener extends AbstractDrawerClickListener {


    public ContentBrowserActivityClickListener(DrawerLayout drawer)
    {
        super(drawer);
    }

    @Override
    protected void browseSongs(Context context)
    {
        LogUtil.writeDebug("DMADOSAMDO");
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
