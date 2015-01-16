/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.listeners;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public abstract class AbstractDrawerClickListener implements ListView.OnItemClickListener {

    private DrawerLayout drawer;

    protected AbstractDrawerClickListener(DrawerLayout drawerLayout)
    {
        this.drawer = drawerLayout;
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

    protected abstract void browseSongs(Context context);

    protected abstract void browseArtists(Context context);

    protected abstract void browseAlbums(Context context);

    protected abstract void browseGenres(Context context);
}
