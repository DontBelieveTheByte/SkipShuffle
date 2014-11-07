package com.dontbelievethebyte.skipshuffle.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public abstract class AbstractCustomAdapter extends CursorAdapter {

    private Drawables drawables;

    public AbstractCustomAdapter(Context context, Cursor cursor)
    {
        super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    public void setDrawables(Drawables drawables) {
        this.drawables = drawables;
    }

    protected ImageView setImage(View view, int resourceId, boolean isPlay)
    {
        ImageView imageLabel = (ImageView) view.findViewById(resourceId);
        ViewGroup.LayoutParams params = imageLabel.getLayoutParams();
        imageLabel.setImageDrawable(
                isPlay ? drawables.getPlay() : drawables.getPause()
        );
        imageLabel.setLayoutParams(params);
        return imageLabel;
    }

    protected TextView setTitle(View view, int resourceId, Cursor cursor, String colIndex)
    {
        TextView titleLabel = (TextView) view.findViewById(resourceId);
        String title = cursor.getString(cursor.getColumnIndex(colIndex));
        titleLabel.setText(title);
        return titleLabel;
    }

    protected TextView setArtist(View view, int resourceId, Cursor cursor, String colIndex)
    {
        TextView artistLabel = (TextView) view.findViewById(resourceId);
        String title = cursor.getString(cursor.getColumnIndex(colIndex));
        artistLabel.setText(title);
        return artistLabel;
    }
}
