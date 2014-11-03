package com.dontbelievethebyte.skipshuffle.ui.click.listener;

import android.content.Intent;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;

public class Playlist extends Custom {

    public Playlist(BaseActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view) {
        Intent playlistActivity = new Intent(baseActivity, PlaylistActivity.class);
        baseActivity.startActivity(playlistActivity);
    }
}
