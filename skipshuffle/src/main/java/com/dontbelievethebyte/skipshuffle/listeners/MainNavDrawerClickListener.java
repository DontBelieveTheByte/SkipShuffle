package com.dontbelievethebyte.skipshuffle.listeners;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistSelectorActivity;

public class MainNavDrawerClickListener implements ListView.OnItemClickListener {

    private Context context;
    private DrawerLayout drawer;

    public MainNavDrawerClickListener(Context context, DrawerLayout drawer)
    {
        this.context = context;
        this.drawer = drawer;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id)
    {
        Intent intent = new Intent(
                context,
                PlaylistSelectorActivity.class
        );
        switch (position) {
            case PlaylistSelectorActivity.SONGS:
                intent.putExtra(
                        PlaylistSelectorActivity.TYPE,
                        PlaylistSelectorActivity.SONGS
                );
                break;
            case PlaylistSelectorActivity.ARTISTS:
                intent.putExtra(
                        PlaylistSelectorActivity.TYPE,
                        PlaylistSelectorActivity.ARTISTS
                );
                break;
            case PlaylistSelectorActivity.ALBUMS:
                intent.putExtra(
                        PlaylistSelectorActivity.TYPE,
                        PlaylistSelectorActivity.ALBUMS
                );
                break;
            case PlaylistSelectorActivity.GENRES:
                intent.putExtra(
                        PlaylistSelectorActivity.TYPE,
                        PlaylistSelectorActivity.GENRES
                );
                break;
            default:
                intent.putExtra(
                        PlaylistSelectorActivity.TYPE,
                        PlaylistSelectorActivity.SONGS
                );
        }
        drawer.closeDrawer(Gravity.START);
        context.startActivity(intent);
    }
}
