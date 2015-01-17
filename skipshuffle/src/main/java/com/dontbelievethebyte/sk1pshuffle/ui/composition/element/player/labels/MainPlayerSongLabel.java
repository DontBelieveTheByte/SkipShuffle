/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.labels;

import android.view.ViewGroup;
import android.widget.TextView;

import com.dontbelievethebyte.sk1pshuffle.ui.composition.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.CustomTypeface;

public class MainPlayerSongLabel implements UIElementCompositeInterface {

    private TextView label;

    public MainPlayerSongLabel(ViewGroup viewGroup, int labelId)
    {
        label = (TextView) viewGroup.findViewById(labelId);
    }

    public void setContent(String content)
    {
        if (null != label) {
            label.setText(content);
            label.setSelected(true);
        }
    }

    public void setTypeFace(CustomTypeface typeFace)
    {
        if (null != label && null != typeFace.getTypeFace())
            label.setTypeface(typeFace.getTypeFace());
    }

    public void setColor(int color)
    {
        if (null != label)
            label.setTextColor(color);
    }

}
