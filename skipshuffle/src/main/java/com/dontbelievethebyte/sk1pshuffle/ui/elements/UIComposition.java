/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.layout.AbstractLayout;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.ColorVisitorFactory;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.DimensionsVisitorFactory;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.exception.NoSuchVisitorException;
import com.dontbelievethebyte.sk1pshuffle.ui.structure.Colors;
import com.dontbelievethebyte.sk1pshuffle.ui.structure.Drawables;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.drawable.DrawablesVisitor;

import java.util.ArrayList;

public class UIComposition {

    public AbstractPlayerUI player;

    private BaseActivity baseActivity;
    private ContentBrowserDrawer contentBrowserDrawer;
    private AbstractLayout contentArea;
    private Colors colors;
    private Drawables drawables;
    private CustomSeekBar customSeekBar;

    public UIComposition(UICompositionBuilder builder)
    {
        baseActivity = builder.baseActivity;
        contentArea = builder.contentArea;
        colors = builder.colors;
        contentBrowserDrawer = builder.musicPlayerDrawer;
        player = builder.player;
        drawables = builder.drawables;
        customSeekBar = builder.customSeekBar;
        visitElements();
    }

    private void visitElements()
    {
        ArrayList<UIElementCompositeInterface> uiElements = new ArrayList<UIElementCompositeInterface>();
        uiElements.add(baseActivity);
        uiElements.add(contentBrowserDrawer);
        uiElements.add(player);
        uiElements.add(player.buttons);
        uiElements.add(contentArea);
        uiElements.add(customSeekBar);
        colorVisit(uiElements);
        dimensionsVisit(uiElements);
        drawablesVisit(uiElements);
    }

    private void colorVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        for (UIElementCompositeInterface element : uiElements) {
            try {
                AbstractColorVisitor colorVisitor = ColorVisitorFactory.make(element, baseActivity);
                colorVisitor.setColors(colors);
                colorVisitor.visit(element);
            } catch (NoSuchVisitorException e) {
                continue;
            }
        }
    }

    private void dimensionsVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        for (UIElementCompositeInterface element : uiElements) {
            try {
                AbstractDimensionsVisitor dimensionsVisitor = DimensionsVisitorFactory.make(element, baseActivity);
                dimensionsVisitor.visit(element);
            } catch (NoSuchVisitorException e) {
                continue;
            }
        }
    }

    private void drawablesVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        DrawablesVisitor drawablesVisitor = new DrawablesVisitor(drawables);

        for (UIElementCompositeInterface element : uiElements) {
            drawablesVisitor.visit(element);
        }
    }
}
