package com.dontbelievethebyte.skipshuffle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;

public class SongsAdapter extends AbstractCustomAdapter {

    public SongsAdapter(Context context, Cursor cursor)
    {
        super(context, cursor);
    }

    @Override
    public String getTitle()
    {
        return adapterTypes[0];
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return layoutInflater.inflate(
                R.layout.list_item_song,
                viewGroup,
                false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        setImage(view, R.id.track_image, false);
        setTextField(view, R.id.track_title, cursor, MediaStore.Audio.Media.TITLE);
        setTextField(view, R.id.track_artist, cursor, MediaStore.Audio.Media.ARTIST);
    }
}