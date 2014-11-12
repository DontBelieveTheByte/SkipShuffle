package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons;

import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.content.AbstractContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.DrawablesVisitor;

public class MainPlayerButtons extends AbstractPlayerButtons implements DrawablesVisitor.Visitable {

    public MainPlayerButtons(AbstractContentArea contentArea)
    {
        super(contentArea);

        playlist = (ImageButton) bottomLayout.findViewById(R.id.playlistBtn);
        prev = (ImageButton) bottomLayout.findViewById(R.id.prevBtn);
        play = (ImageButton) bottomLayout.findViewById(R.id.playBtn);
        shuffle = (ImageButton) bottomLayout.findViewById(R.id.shuffleBtn);
        skip = (ImageButton) bottomLayout.findViewById(R.id.skipBtn);
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
