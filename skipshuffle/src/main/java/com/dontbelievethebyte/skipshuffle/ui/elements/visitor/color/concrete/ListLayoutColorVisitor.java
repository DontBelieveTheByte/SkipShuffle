/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;

public class ListLayoutColorVisitor extends AbstractColorVisitor {

    public ListLayoutColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof ListLayout)
            colorBackground((ListLayout) uiElement);
    }

    private void colorBackground(ListLayout listLayout)
    {
        ViewGroup bottomLayout = listLayout.getBottomLayout();
        bottomLayout.setBackgroundResource(colors.background);
    }
}
