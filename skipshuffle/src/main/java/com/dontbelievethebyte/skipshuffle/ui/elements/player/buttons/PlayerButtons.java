package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons;

import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DrawablesVisitor;

public class PlayerButtons implements UIElementCompositeInterface, DrawablesVisitor.Visitable {

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
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
