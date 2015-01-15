/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;

import com.dontbelievethebyte.sk1pshuffle.R;

public class AlbumsAdapter extends AbstractCustomAdapter {

    public AlbumsAdapter(Context context)
    {
        super(context);
    }

    @Override
    public String getTitle()
    {
        return adapterTypes[2];
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return layoutInflater.inflate(
                R.layout.list_item_album,
                viewGroup,
                false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        setImage(view, R.id.album_image, false);
        setTextField(view, R.id.album_title, cursor, MediaStore.Audio.Media.TITLE);
        setTextField(view, R.id.album_number_of_songs, cursor, MediaStore.Audio.Media.ARTIST);
    }
}