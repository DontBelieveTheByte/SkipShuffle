package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.ui.visitor.ColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DrawablesVisitor;

public interface UIElement
{
    public void acceptColorVisitor(ColorVisitor colorVisitor);
    public void acceptDimensionsVisitor(DimensionsVisitor dimensionsVisitor);
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor);
}
