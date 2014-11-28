/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.drawable;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.AbstractPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.concrete.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.concrete.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.structure.Drawables;

public class DrawablesVisitor {

    public static interface Visitable {
        public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor);
    }

    Drawables drawables;

    public DrawablesVisitor(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof MainPlayerButtons)
            visitPlayerUI((MainPlayerButtons) uiElement);
        if (uiElement instanceof ListPlayerButtons)
            visitPlayerUI((ListPlayerButtons) uiElement);
    }

    private void visitPlayerUI(AbstractPlayerButtons buttons)
    {
        buttons.prev.setImageDrawable(buttons.drawables.getPrev());
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.shuffle.setImageDrawable(buttons.drawables.getShuffle());
        buttons.skip.setImageDrawable(buttons.drawables.getSkip());
        buttons.playlist.setImageDrawable(buttons.drawables.getPlaylist());
    }
}
