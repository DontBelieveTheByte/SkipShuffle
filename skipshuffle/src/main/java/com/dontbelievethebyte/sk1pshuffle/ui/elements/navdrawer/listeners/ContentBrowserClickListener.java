/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer.listeners;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;

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
        Log.d(BaseActivity.TAG , "$$$ BROWSING SONGS");
    }

    private void browseArtists(Context context)
    {
        Log.d(BaseActivity.TAG , "$$$ BROWSING ARTISTS");
    }

    private void browseAlbums(Context context)
    {
        Log.d(BaseActivity.TAG , "$$$ BROWSING ALBUMS");
    }

    private void browseGenres(Context context)
    {
        Log.d(BaseActivity.TAG , "$$$ BROWSING GENRES");
    }
}
