/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.widget.SimpleCursorAdapter;

import com.dontbelievethebyte.sk1pshuffle.R;

public class GenresAdapter extends SimpleCursorAdapter {

    public GenresAdapter(Context context)
    {
        super(
                context,
                R.layout.list_item_genre,
                null,
                new String[] {MediaStore.Audio.Genres.NAME},
                new int[] {R.id.genre_name},
                0
        );
    }

}