/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.widget.SimpleCursorAdapter;

import com.dontbelievethebyte.sk1pshuffle.R;

public class AlbumsAdapter extends SimpleCursorAdapter {

    public AlbumsAdapter(Context context)
    {
        super(
                context,
                R.layout.list_item_album,
                null,
                new String[] {MediaStore.Audio.Albums.ALBUM},
                new int[] { R.id.album_name},
                0
        );
    }
}