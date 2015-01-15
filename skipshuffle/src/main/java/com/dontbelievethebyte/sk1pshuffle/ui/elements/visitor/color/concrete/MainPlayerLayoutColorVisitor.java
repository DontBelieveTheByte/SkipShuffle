/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.AbstractColorVisitor;

public class MainPlayerLayoutColorVisitor extends AbstractColorVisitor {

    public MainPlayerLayoutColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof PlayerLayout)
            colorBackground((PlayerLayout) uiElement);
    }

    private void colorBackground(PlayerLayout playerLayout)
    {
        ViewGroup bottomLayout = playerLayout.getBottomLayout();
        bottomLayout.setBackgroundResource(colors.background);
    }
}
