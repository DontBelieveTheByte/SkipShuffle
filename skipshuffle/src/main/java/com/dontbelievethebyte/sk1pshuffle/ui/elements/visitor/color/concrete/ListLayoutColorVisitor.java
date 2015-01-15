/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.AbstractColorVisitor;

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
