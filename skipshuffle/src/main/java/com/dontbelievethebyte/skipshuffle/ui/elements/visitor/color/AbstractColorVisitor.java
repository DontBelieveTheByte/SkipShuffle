package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color;

import android.app.Activity;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;

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
