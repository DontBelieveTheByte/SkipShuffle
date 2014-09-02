package com.dontbelievethebyte.skipshuffle.activities.util;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class NavDrawerClickListener implements ListView.OnItemClickListener {

    private static final int DRAWER_SONGS = 0;
    private static final int DRAWER_ARTISTS = 1;
    private static final int DRAWER_ALBUMS = 2;
    private static final int DRAWER_GENRES = 3;
    private Context context;

    public NavDrawerClickListener(Context context){
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        switch (position) {
            case DRAWER_SONGS:
            case DRAWER_ARTISTS:
            case DRAWER_ALBUMS:
            case DRAWER_GENRES:
            default:
            Toast.makeText(context, "CLICKED DRAWER ITEM : " + position, Toast.LENGTH_SHORT).show();
        }
    }
}
