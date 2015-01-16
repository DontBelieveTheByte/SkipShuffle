/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.widget.SimpleCursorAdapter;

import com.dontbelievethebyte.sk1pshuffle.R;

public class ArtistsAdapter extends SimpleCursorAdapter {

    public ArtistsAdapter(Context context)
    {
        super(
                context,
                R.layout.list_item_artist,
                null,
                new String[] {MediaStore.Audio.Artists.ARTIST, MediaStore.Audio.Artists.NUMBER_OF_ALBUMS, MediaStore.Audio.Artists.NUMBER_OF_TRACKS},
                new int[] { R.id.artist_name, R.id.artist_number_of_albums, R.id.artist_number_of_tracks},
                0
        );
    }
}