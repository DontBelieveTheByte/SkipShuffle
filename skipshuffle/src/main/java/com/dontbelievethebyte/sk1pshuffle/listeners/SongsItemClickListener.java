/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.listeners;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.sk1pshuffle.playlist.PlaylistData;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;

import java.util.ArrayList;

public class SongsItemClickListener implements AdapterView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        Log.d(BaseActivity.TAG, "Position : " + Integer.toString(position));

    }
}
