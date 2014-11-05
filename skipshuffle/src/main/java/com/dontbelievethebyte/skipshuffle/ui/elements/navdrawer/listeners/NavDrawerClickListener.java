package com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.listeners;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.ListActivity;

public class NavDrawerClickListener implements ListView.OnItemClickListener {

    private Context context;
    private DrawerLayout drawer;

    public NavDrawerClickListener(Context context, DrawerLayout drawer)
    {
        this.context = context;
        this.drawer = drawer;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id)
    {
        int type;
        switch (position) {
            case ListActivity.Types.SONGS:
                type = ListActivity.Types.SONGS;
                break;
            case ListActivity.Types.ARTISTS:
                type = ListActivity.Types.ARTISTS;
                break;
            case ListActivity.Types.ALBUMS:
                type = ListActivity.Types.ALBUMS;
                break;
            case ListActivity.Types.GENRES:
                type = ListActivity.Types.GENRES;
                break;
            case ListActivity.Types.PLAYLIST:
                type = ListActivity.Types.PLAYLIST;
                break;
            default:
                type = ListActivity.Types.SONGS;
        }
        drawer.closeDrawer(Gravity.START);
        startPlaylistActivity(type);
    }

    private void startPlaylistActivity(int type)
    {
        Intent intent = new Intent(
                context,
                ListActivity.class
        );
        intent.putExtra(
                ListActivity.Types.TYPE,
                type
        );
        context.startActivity(intent);
    }
}
