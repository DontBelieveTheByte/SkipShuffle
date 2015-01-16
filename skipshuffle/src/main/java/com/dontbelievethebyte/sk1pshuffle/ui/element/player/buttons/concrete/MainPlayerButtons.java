/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.concrete;

import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.AbstractPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.drawable.DrawablesVisitor;

public class MainPlayerButtons extends AbstractPlayerButtons implements DrawablesVisitor.Visitable {

    public MainPlayerButtons(ViewGroup viewGroup)
    {
        super(viewGroup);

        playlist = (ImageButton) viewGroup.findViewById(R.id.playlistBtn);
        prev = (ImageButton) viewGroup.findViewById(R.id.prevBtn);
        play = (ImageButton) viewGroup.findViewById(R.id.playBtn);
        shuffle = (ImageButton) viewGroup.findViewById(R.id.shuffleBtn);
        skip = (ImageButton) viewGroup.findViewById(R.id.skipBtn);
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
