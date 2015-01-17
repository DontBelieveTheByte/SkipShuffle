/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor;

import android.app.Activity;

import com.dontbelievethebyte.sk1pshuffle.ui.composition.UIComposition;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.color.ColorVisitorFactory;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.dimension.DimensionsVisitorFactory;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.drawable.DrawablesVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.visitor.exception.NoSuchVisitorException;

import java.util.ArrayList;
import java.util.List;

public class VisitorManager {

    private UIComposition uiComposition;
    List<UIElementCompositeInterface> uiElements;

    public VisitorManager(UIComposition uiComposition)
    {
        this.uiComposition = uiComposition;
        uiElements = new ArrayList<>();
        uiElements.add(uiComposition.getActivity());
        uiElements.add(uiComposition.getContentBrowserDrawer());
        uiElements.add(uiComposition.getPlayer());
        if (null != uiComposition.getPlayer()) {
            uiElements.add(uiComposition.getPlayer().buttons);
        }
        uiElements.add(uiComposition.getCustomSeekBar());
    }

    public void visitElements()
    {
        colorVisit(uiElements);
        dimensionsVisit(uiElements);
        drawablesVisit(uiElements);
    }

    private void colorVisit(List<UIElementCompositeInterface> uiElements)
    {
        for (UIElementCompositeInterface element : uiElements) {
            try {
                AbstractColorVisitor colorVisitor = ColorVisitorFactory.make(
                        element,
                        (Activity) uiComposition.getActivity()
                );
                colorVisitor.setColors(uiComposition.getTheme().getColors());
                colorVisitor.visit(element);
            } catch (NoSuchVisitorException e) {
                continue;
            }
        }
    }

    private void dimensionsVisit(List<UIElementCompositeInterface> uiElements)
    {
        for (UIElementCompositeInterface element : uiElements) {
            try {
                AbstractDimensionsVisitor dimensionsVisitor = DimensionsVisitorFactory.make(
                        element,
                        (Activity) uiComposition.getActivity()
                );
                dimensionsVisitor.visit(element);
            } catch (NoSuchVisitorException e) {
                continue;
            }
        }
    }

    private void drawablesVisit(List<UIElementCompositeInterface> uiElements)
    {
        DrawablesVisitor drawablesVisitor = new DrawablesVisitor(uiComposition.getTheme().getDrawables());

        for (UIElementCompositeInterface element : uiElements) {
            drawablesVisitor.visit(element);
        }
    }
}
