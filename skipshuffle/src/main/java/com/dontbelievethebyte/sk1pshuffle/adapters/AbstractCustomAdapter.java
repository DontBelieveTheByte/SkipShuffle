/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.structure.Drawables;

public abstract class AbstractCustomAdapter extends CursorAdapter {

    private Drawables drawables;

    protected LayoutInflater layoutInflater;
    protected String[] adapterTypes;

    public AbstractCustomAdapter(Context context)
    {
        super(context, null, FLAG_REGISTER_CONTENT_OBSERVER);
        layoutInflater = LayoutInflater.from(context);
        adapterTypes = context.getResources().getStringArray(R.array.adapter_types);
    }

    public void setDrawables(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public abstract String getTitle();

    protected ImageView setImage(View view, int resourceId, boolean isPlay)
    {
        ImageView imageLabel = (ImageView) view.findViewById(resourceId);
        ViewGroup.LayoutParams params = imageLabel.getLayoutParams();
//        imageLabel.setImageDrawable(
//                isPlay ? drawables.getPlay() : drawables.getPause()
//        );
//        imageLabel.setColorFilter(
//                mediaPlayer.isPlaying() ?
//                        ColorMapper.getPlayButton(mediaPlayer.getPreferencesHelper().getUIType()) :
//                        ColorMapper.getPauseButton(mediaPlayer.getPreferencesHelper().getUIType())
//
//        );

        imageLabel.setLayoutParams(params);
        return imageLabel;
    }

    protected TextView setTextField(View view, int resourceId, Cursor cursor, String colIndex)
    {
        TextView titleLabel = (TextView) view.findViewById(resourceId);
        String title = cursor.getString(cursor.getColumnIndex(colIndex));
        titleLabel.setText(title);
        return titleLabel;
    }
}
