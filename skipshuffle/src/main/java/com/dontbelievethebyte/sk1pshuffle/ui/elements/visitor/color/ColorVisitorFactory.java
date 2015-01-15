/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color;

import android.app.Activity;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete.ContentBrowserColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete.ListLayoutColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete.ListPlayerColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete.MainPlayerColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete.MainPlayerLayoutColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete.SeekBarColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.exception.NoSuchVisitorException;

public class ColorVisitorFactory {

    public static AbstractColorVisitor make(UIElementCompositeInterface uiElement, Activity activity) throws NoSuchVisitorException
    {
        if (uiElement instanceof ContentBrowserDrawer)
            return new ContentBrowserColorVisitor(activity);

        else if (uiElement instanceof ListLayout)
            return new ListLayoutColorVisitor(activity);

        else if (uiElement instanceof PlayerLayout)
            return new MainPlayerLayoutColorVisitor(activity);

        else if (uiElement instanceof ListPlayer)
            return new ListPlayerColorVisitor(activity);

        else if (uiElement instanceof MainPlayer)
            return new MainPlayerColorVisitor(activity);

        else if (uiElement instanceof CustomSeekBar)
            return new SeekBarColorVisitor(activity);
        else
            throw new NoSuchVisitorException();
    }
}
