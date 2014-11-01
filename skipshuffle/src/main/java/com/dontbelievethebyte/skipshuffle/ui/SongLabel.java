package com.dontbelievethebyte.skipshuffle.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

public class SongLabel {

    private TextView label;

    public SongLabel(Activity activity, int labelId)
    {
        label = (TextView) activity.findViewById(labelId);
    }

    public void setContent(String content)
    {
        label.setText(content);
        label.setSelected(true);
    }

    public void setTypeFace(Typeface typeFace)
    {
        label.setTypeface(typeFace);
    }
}
