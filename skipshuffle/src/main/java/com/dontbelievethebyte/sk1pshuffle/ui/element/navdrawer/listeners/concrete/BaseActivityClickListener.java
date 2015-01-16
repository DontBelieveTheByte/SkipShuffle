/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.listeners.concrete;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;

import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.activity.ContentBrowserActivity;
import com.dontbelievethebyte.sk1pshuffle.media.ContentTypes;
import com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.listeners.AbstractDrawerClickListener;

public class BaseActivityClickListener extends AbstractDrawerClickListener {

    public BaseActivityClickListener(DrawerLayout drawer)
    {
        super(drawer);
    }

    @Override
    protected void browseSongs(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.SONGS.ordinal()
        );
        Activity activity = (Activity) context;
        activity.startActivityForResult(
                intent,
                BaseActivity.CONTENT_BROWSER
        );
    }

    @Override
    protected void browseArtists(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.ARTISTS.ordinal()
        );
        context.startActivity(intent);
    }

    @Override
    protected void browseAlbums(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.ALBUMS.ordinal()
        );
        context.startActivity(intent);
    }

    @Override
    protected void browseGenres(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.GENRES.ordinal()
        );
        context.startActivity(intent);
    }
}
