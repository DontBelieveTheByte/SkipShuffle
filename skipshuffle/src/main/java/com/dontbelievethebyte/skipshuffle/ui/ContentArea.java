package com.dontbelievethebyte.skipshuffle.ui;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.visitor.ColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DrawablesVisitor;

public class ContentArea implements UIElement {
    private ViewGroup bottomLayout;

    public ContentArea(Activity activity, int layoutId)
    {
        activity.setContentView(layoutId);
        bottomLayout = (ViewGroup) activity.findViewById(R.id.bottom);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }

    @Override
    public void acceptColorVisitor(ColorVisitor colorVisitor)
    {
        colorVisitor.visit(this);
    }

    @Override
    public void acceptDimensionsVisitor(DimensionsVisitor dimensionsVisitor)
    {
        dimensionsVisitor.visit(this);
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
