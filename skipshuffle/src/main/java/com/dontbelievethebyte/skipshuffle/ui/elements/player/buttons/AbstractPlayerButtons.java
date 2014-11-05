package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons;

import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.DrawablesVisitor;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public abstract class AbstractPlayerButtons implements UIElementCompositeInterface, DrawablesVisitor.Visitable {

    public ImageButton playlist;
    public ImageButton prev;
    public ImageButton play;
    public ImageButton shuffle;
    public ImageButton skip;
    public Drawables drawables;
    public PlayerButtonsAnimations animations;

    protected ViewGroup bottomLayout;

    public AbstractPlayerButtons(ContentArea contentArea)
    {
        bottomLayout = contentArea.getBottomLayout();
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
