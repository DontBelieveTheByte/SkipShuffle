/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons;

import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dontbelievethebyte.sk1pshuffle.ui.composition.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.drawable.DrawablesVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Drawables;

public abstract class AbstractPlayerButtons implements UIElementCompositeInterface, DrawablesVisitor.Visitable {

    public ImageButton playlist;
    public ImageButton prev;
    public ImageButton play;
    public ImageButton shuffle;
    public ImageButton skip;
    public Drawables drawables;
    public PlayerButtonsAnimations animations;

    protected ViewGroup viewGroup;

    public AbstractPlayerButtons(ViewGroup viewGroup)
    {
        this.viewGroup = viewGroup;
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
