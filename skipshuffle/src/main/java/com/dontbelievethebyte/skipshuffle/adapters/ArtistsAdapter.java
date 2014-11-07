package com.dontbelievethebyte.skipshuffle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;

public class ArtistsAdapter extends AbstractCustomAdapter {

    public ArtistsAdapter(Context context, Cursor cursor)
    {
        super(context, cursor);
    }

    @Override
    public String getTitle()
    {
        return adapterTypes[1];
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup)
    {
        return layoutInflater.inflate(
                R.layout.list_item_artist,
                viewGroup,
                false
        );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        setImage(view, R.id.artist_image, false);
        setTextField(view, R.id.artist_name, cursor, MediaStore.Audio.Media.TITLE);
        setTextField(view, R.id.artist_number_of_albums, cursor, MediaStore.Audio.Media.ARTIST);
    }
}