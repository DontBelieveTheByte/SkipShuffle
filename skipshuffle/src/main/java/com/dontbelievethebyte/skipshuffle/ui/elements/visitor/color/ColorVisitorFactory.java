package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color;

import android.app.Activity;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.MusicContentBrowser;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.ContentBrowserColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.ListLayoutColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.ListPlayerColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.MainPlayerColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.MainPlayerLayoutColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.exception.NoSuchVisitorException;

public class ColorVisitorFactory {

    public static AbstractColorVisitor make(UIElementCompositeInterface uiElement, Activity activity) throws NoSuchVisitorException
    {
        if (uiElement instanceof MusicContentBrowser)
            return new ContentBrowserColorVisitor(activity);

        else if (uiElement instanceof ListLayout)
            return new ListLayoutColorVisitor(activity);

        else if (uiElement instanceof PlayerLayout)
            return new MainPlayerLayoutColorVisitor(activity);

        else if (uiElement instanceof ListPlayer)
            return new ListPlayerColorVisitor(activity);

        else if (uiElement instanceof MainPlayer)
            return new MainPlayerColorVisitor(activity);
        else
            throw new NoSuchVisitorException();
    }
}
