package com.dontbelievethebyte.skipshuffle.ui;

import android.view.ViewGroup;
import android.widget.TextView;

public class SongLabel {

    private TextView label;

    public SongLabel(ContentArea contentArea, int labelId)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();

        label = (TextView) bottomLayout.findViewById(labelId);
    }

    public void setContent(String content)
    {
        label.setText(content);
        label.setSelected(true);
    }

    public void setTypeFace(CustomTypeface typeFace)
    {
        if (null != typeFace.getTypeFace())
            label.setTypeface(typeFace.getTypeFace());
    }

    public TextView getLabel()
    {
        return label;
    }
}
