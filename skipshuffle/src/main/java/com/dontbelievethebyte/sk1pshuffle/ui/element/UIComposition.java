/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element;

import android.app.Activity;

import com.dontbelievethebyte.sk1pshuffle.activity.ThemableActivityInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.ColorVisitorFactory;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.dimension.DimensionsVisitorFactory;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.drawable.DrawablesVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.exception.NoSuchVisitorException;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.Theme;

import java.util.ArrayList;

public class UIComposition {

    public AbstractPlayerUI player;

    private ThemableActivityInterface activity;
    private ContentBrowserDrawer contentBrowserDrawer;
    private CustomSeekBar customSeekBar;
    private Theme theme;

    public UIComposition(UICompositionBuilder builder)
    {
        activity = builder.themableActivity;
        theme = builder.theme;
        contentBrowserDrawer = builder.musicPlayerDrawer;
        player = builder.player;
        customSeekBar = builder.customSeekBar;
        visitElements();
    }

    private void visitElements()
    {
        ArrayList<UIElementCompositeInterface> uiElements = new ArrayList<UIElementCompositeInterface>();
        uiElements.add(activity);
        uiElements.add(contentBrowserDrawer);
        uiElements.add(player);
        uiElements.add(player.buttons);
        uiElements.add(customSeekBar);
        colorVisit(uiElements);
        dimensionsVisit(uiElements);
        drawablesVisit(uiElements);
    }

    private void colorVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        for (UIElementCompositeInterface element : uiElements) {
            try {
                AbstractColorVisitor colorVisitor = ColorVisitorFactory.make(
                        element,
                        (Activity) activity
                );
                colorVisitor.setColors(theme.getColors());
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
                AbstractDimensionsVisitor dimensionsVisitor = DimensionsVisitorFactory.make(
                        element,
                        (Activity) activity
                );
                dimensionsVisitor.visit(element);
            } catch (NoSuchVisitorException e) {
                continue;
            }
        }
    }

    private void drawablesVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        DrawablesVisitor drawablesVisitor = new DrawablesVisitor(theme.getDrawables());

        for (UIElementCompositeInterface element : uiElements) {
            drawablesVisitor.visit(element);
        }
    }
}
