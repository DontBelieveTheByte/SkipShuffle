/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DimensionsMapper;

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

    protected int computeActionBarHeight()
    {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data,
                    activity.getResources().getDisplayMetrics()
            );
        } else {
            return (int) (computedScreenHeight * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.top : DimensionsMapper.Player.Padding.Portrait.top));
        }
    }

    protected void setSquareImageButtonSize(int buttonId, double size)
    {
        ImageButton imageButton = (ImageButton) activity.findViewById(buttonId);

        LinearLayout.LayoutParams playButtonLayoutParams = createLinearLayoutParams();

        playButtonLayoutParams.height = (int) size;
        playButtonLayoutParams.width = (int) size;
        imageButton.setLayoutParams(playButtonLayoutParams);
    }
}
