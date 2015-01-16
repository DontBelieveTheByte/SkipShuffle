/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.widget.SimpleCursorAdapter;

import com.dontbelievethebyte.sk1pshuffle.R;

public class SongsAdapter extends SimpleCursorAdapter {

    public SongsAdapter(Context context)
    {
        super(
                context,
                R.layout.list_item_song,
                null,
                new String[] {MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST},
                new int[] { R.id.track_title, R.id.track_artist},
                0
        );
    }
}