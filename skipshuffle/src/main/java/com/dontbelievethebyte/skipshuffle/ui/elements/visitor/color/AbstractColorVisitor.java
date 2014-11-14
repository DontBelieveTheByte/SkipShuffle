package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;

public abstract class AbstractColorVisitor {

    public static interface Visitable {
        public void acceptColorVisitor(AbstractColorVisitor colorVisitor);
    }

    protected Colors colors;

    public abstract void visit(UIElementCompositeInterface uiElement);

    public void setColors(Colors colors)
    {
        this.colors = colors;
    }

}
