package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension;

import android.app.Activity;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.ContentBrowser;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete.ContentBrowserDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete.ListLayoutDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete.ListPlayerDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete.MainPlayerDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete.MainPlayerLayoutDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.exception.NoSuchVisitorException;

public class DimensionsVisitorFactory {

    public static AbstractDimensionsVisitor make(UIElementCompositeInterface uiElement, Activity activity) throws NoSuchVisitorException
    {
        if (uiElement instanceof ContentBrowser)
            return new ContentBrowserDimensionsVisitor(activity);
        else if (uiElement instanceof MainPlayer)
            return new MainPlayerDimensionsVisitor(activity);
        else if (uiElement instanceof ListPlayer)
            return new ListPlayerDimensionsVisitor(activity);
        else if (uiElement instanceof PlayerLayout)
            return new MainPlayerLayoutDimensionsVisitor(activity);
        else if (uiElement instanceof ListLayout)
            return new ListLayoutDimensionsVisitor(activity);
        else
            throw new NoSuchVisitorException();
    }
}
