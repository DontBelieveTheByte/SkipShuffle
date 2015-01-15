/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;

public abstract class AbstractDimensionsVisitor {

    public static interface Visitable {
        public void acceptDimensionsVisitor(AbstractDimensionsVisitor dimensionsVisitor);
    }

    protected boolean isLandScape;
    protected int computedScreenHeight;
    protected int computedScreenWidth;
    protected Activity activity;

    public AbstractDimensionsVisitor(Activity activity)
    {
        this.activity = activity;
        initBasicDimensions();
    }

    private void initBasicDimensions()
    {
        isLandScape = Configuration.ORIENTATION_LANDSCAPE == activity.getResources().getConfiguration().orientation;
        computedScreenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        computedScreenWidth = activity.getResources().getDisplayMetrics().widthPixels;
    }

    public abstract void visit(UIElementCompositeInterface uiElement);

    protected LinearLayout.LayoutParams createLinearLayoutParams()
    {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    protected RelativeLayout.LayoutParams createRelativeLayoutParams()
    {
        return new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

}
