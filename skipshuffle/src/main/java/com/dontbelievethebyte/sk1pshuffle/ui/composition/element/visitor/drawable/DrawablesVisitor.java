/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.drawable;

import com.dontbelievethebyte.sk1pshuffle.ui.composition.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.AbstractPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.concrete.ListPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.concrete.MainPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Drawables;

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
