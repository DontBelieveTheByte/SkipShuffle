package com.dontbelievethebyte.skipshuffle.ui.structured;

import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.UIElement;
import com.dontbelievethebyte.skipshuffle.ui.visitor.ColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DrawablesVisitor;

public class PlayerButtons implements UIElement {

    public ImageButton playlist;
    public ImageButton prev;
    public ImageButton play;
    public ImageButton shuffle;
    public ImageButton skip;
    public Drawables drawables;
    public PlayerButtonsAnimations animations;

    public PlayerButtons(ContentArea contentArea)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();

        playlist = (ImageButton) bottomLayout.findViewById(R.id.playlistBtn);
        prev = (ImageButton) bottomLayout.findViewById(R.id.prevBtn);
        play = (ImageButton) bottomLayout.findViewById(R.id.playBtn);
        shuffle = (ImageButton) bottomLayout.findViewById(R.id.shuffleBtn);
        skip = (ImageButton) bottomLayout.findViewById(R.id.skipBtn);
    }

    @Override
    public void acceptColorVisitor(ColorVisitor colorVisitor)
    {

    }

    @Override
    public void acceptDimensionsVisitor(DimensionsVisitor dimensionsVisitor)
    {

    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
