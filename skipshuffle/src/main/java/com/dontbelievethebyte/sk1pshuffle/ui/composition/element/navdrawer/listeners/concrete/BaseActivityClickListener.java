/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.listeners.concrete;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;

import com.dontbelievethebyte.sk1pshuffle.activity.ContentBrowserActivity;
import com.dontbelievethebyte.sk1pshuffle.media.ContentTypes;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.listeners.AbstractDrawerClickListener;

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
        launchChildActivity(intent, (Activity) context);
    }

    @Override
    protected void browseArtists(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.ARTISTS.ordinal()
        );
        launchChildActivity(intent, (Activity) context);
    }

    @Override
    protected void browseAlbums(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.ALBUMS.ordinal()
        );
        launchChildActivity(intent, (Activity) context);
    }

    @Override
    protected void browseGenres(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.GENRES.ordinal()
        );
        launchChildActivity(intent, (Activity) context);
    }

    private void launchChildActivity(Intent intent, Activity activity)
    {
        activity.startActivityForResult(
                intent,
                ContentBrowserActivity.REQUEST_CODE
        );
    }
}
