/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension;

import android.app.Activity;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.concrete.ContentBrowserDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.concrete.ListPlayerDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.concrete.MainPlayerDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.exception.NoSuchVisitorException;

public class DimensionsVisitorFactory {

    public static AbstractDimensionsVisitor make(UIElementCompositeInterface uiElement, Activity activity) throws NoSuchVisitorException
    {
        if (uiElement instanceof ContentBrowserDrawer)
            return new ContentBrowserDimensionsVisitor(activity);
        else if (uiElement instanceof MainPlayer)
            return new MainPlayerDimensionsVisitor(activity);
        else if (uiElement instanceof ListPlayer)
            return new ListPlayerDimensionsVisitor(activity);
        else
            throw new NoSuchVisitorException();
    }
}
