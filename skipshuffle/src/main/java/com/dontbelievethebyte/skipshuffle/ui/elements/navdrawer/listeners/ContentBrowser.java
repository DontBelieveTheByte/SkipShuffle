package com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.listeners;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.MusicContentBrowserActivity;
import com.dontbelievethebyte.skipshuffle.media.MediaStoreBridge;

public class ContentBrowser implements ListView.OnItemClickListener {

    private Context context;
    private DrawerLayout drawer;

    public ContentBrowser(Context context, DrawerLayout drawer)
    {
        this.context = context;
        this.drawer = drawer;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id)
    {
        int type;
        switch (position) {
            case MediaStoreBridge.Types.SONGS:
            case MediaStoreBridge.Types.ARTISTS:
            case MediaStoreBridge.Types.ALBUMS:
            case MediaStoreBridge.Types.GENRES:
            case MediaStoreBridge.Types.PLAYLIST:
            type = position;
                break;
            default:
                type = MediaStoreBridge.Types.SONGS;
        }
        drawer.closeDrawer(Gravity.START);
        startPlaylistActivity(type);
    }

    private void startPlaylistActivity(int type)
    {
        Intent intent = new Intent(
                context,
                MusicContentBrowserActivity.class
        );
        intent.putExtra(
                MediaStoreBridge.Types.TYPE,
                type
        );
        context.startActivity(intent);
    }
}
