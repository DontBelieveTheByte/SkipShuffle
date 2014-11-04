package com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.listeners;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;

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
            case PlaylistActivity.Types.SONGS:
                type = PlaylistActivity.Types.SONGS;
                break;
            case PlaylistActivity.Types.ARTISTS:
                type = PlaylistActivity.Types.ARTISTS;
                break;
            case PlaylistActivity.Types.ALBUMS:
                type = PlaylistActivity.Types.ALBUMS;
                break;
            case PlaylistActivity.Types.GENRES:
                type = PlaylistActivity.Types.GENRES;
                break;
            case PlaylistActivity.Types.PLAYLIST:
                type = PlaylistActivity.Types.PLAYLIST;
                break;
            default:
                type = PlaylistActivity.Types.SONGS;
        }
        drawer.closeDrawer(Gravity.START);
        startPlaylistActivity(type);
    }

    private void startPlaylistActivity(int type)
    {
        Intent intent = new Intent(
                context,
                PlaylistActivity.class
        );
        intent.putExtra(
                PlaylistActivity.Types.TYPE,
                type
        );
        context.startActivity(intent);
    }
}
