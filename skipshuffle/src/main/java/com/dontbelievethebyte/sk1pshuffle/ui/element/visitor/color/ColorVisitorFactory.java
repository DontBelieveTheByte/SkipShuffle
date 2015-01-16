/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color;

import android.app.Activity;

import com.dontbelievethebyte.sk1pshuffle.ui.element.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.MainPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.concrete.ActivityColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.concrete.ContentBrowserColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.concrete.ListPlayerColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.concrete.MainPlayerColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.concrete.SeekBarColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.exception.NoSuchVisitorException;

public class ColorVisitorFactory {

    public static AbstractColorVisitor make(UIElementCompositeInterface uiElement, Activity activity) throws NoSuchVisitorException
    {
        if (uiElement instanceof ContentBrowserDrawer)
            return new ContentBrowserColorVisitor(activity);

        else if (uiElement instanceof Activity)
            return new ActivityColorVisitor(activity);

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
