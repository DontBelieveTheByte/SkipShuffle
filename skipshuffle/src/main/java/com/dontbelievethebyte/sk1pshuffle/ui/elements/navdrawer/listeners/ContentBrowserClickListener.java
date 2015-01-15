/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer.listeners;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.activities.ContentBrowserActivity;
import com.dontbelievethebyte.sk1pshuffle.media.ContentTypes;

public class ContentBrowserClickListener implements ListView.OnItemClickListener {

    private DrawerLayout drawer;

    public ContentBrowserClickListener(DrawerLayout drawer)
    {
        this.drawer = drawer;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id)
    {
        switch (position) {
            case 0:
                browseSongs(view.getContext());
                break;
            case 1:
                browseArtists(view.getContext());
                break;
            case 2:
                browseAlbums(view.getContext());
                break;
            case 3:
                browseGenres(view.getContext());
                break;
            default:
        }
        drawer.closeDrawer(Gravity.START);
    }

    private void browseSongs(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.SONGS.ordinal()
        );
        context.startActivity(intent);
    }

    private void browseArtists(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.ARTISTS.ordinal()
        );
        context.startActivity(intent);
    }

    private void browseAlbums(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.ALBUMS.ordinal()
        );
        context.startActivity(intent);
    }

    private void browseGenres(Context context)
    {
        Intent intent = new Intent(context, ContentBrowserActivity.class);
        intent.putExtra(
                ContentBrowserActivity.CONTENT_TYPE,
                ContentTypes.GENRES.ordinal()
        );
        context.startActivity(intent);
    }
}
