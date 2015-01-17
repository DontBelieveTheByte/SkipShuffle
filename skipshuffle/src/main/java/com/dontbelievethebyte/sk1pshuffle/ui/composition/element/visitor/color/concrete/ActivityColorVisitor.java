/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.color.concrete;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.color.AbstractColorVisitor;

public class ActivityColorVisitor extends AbstractColorVisitor {

    public ActivityColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof Activity)
            colorBackground((Activity) uiElement);
    }

    private void colorBackground(Activity activity)
    {
        ViewGroup bottomLayout = (ViewGroup) activity.findViewById(R.id.bottom);
        bottomLayout.setBackgroundResource(colors.background);
    }
}
