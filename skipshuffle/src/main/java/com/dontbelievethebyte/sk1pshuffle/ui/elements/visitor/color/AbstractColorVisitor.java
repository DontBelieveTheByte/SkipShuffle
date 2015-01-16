/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color;

import android.app.Activity;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Colors;

public abstract class AbstractColorVisitor {

    public static interface Visitable {
        public void acceptColorVisitor(AbstractColorVisitor colorVisitor);
    }

    protected Activity activity;
    protected Colors colors;

    public AbstractColorVisitor(Activity activity)
    {
        this.activity = activity;
    }

    public abstract void visit(UIElementCompositeInterface uiElement);

    public void setColors(Colors colors)
    {
        this.colors = colors;
    }

}
