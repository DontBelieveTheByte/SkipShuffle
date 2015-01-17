/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.concrete;

import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.AbstractPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.drawable.DrawablesVisitor;

public class ListPlayerButtons extends AbstractPlayerButtons {

    public ListPlayerButtons(ViewGroup viewGroup)
    {
        super(viewGroup);
        playlist = (ImageButton) this.viewGroup.findViewById(R.id.playlistBtn);
        prev = (ImageButton) this.viewGroup.findViewById(R.id.prevBtn);
        play = (ImageButton) this.viewGroup.findViewById(R.id.playBtn);
        shuffle = (ImageButton) this.viewGroup.findViewById(R.id.shuffleBtn);
        skip = (ImageButton) this.viewGroup.findViewById(R.id.skipBtn);
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
