package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete;

import android.app.Activity;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;

public class ListPlayerDimensionsVisitor extends AbstractDimensionsVisitor {

    public ListPlayerDimensionsVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        //Empty for now
    }
}
