/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element;

import com.dontbelievethebyte.sk1pshuffle.activity.ThemableActivityInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.VisitorManager;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.Theme;

public class UIComposition {

    private AbstractPlayerUI player;
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
        makeVisit();
    }

    private void makeVisit()
    {
        VisitorManager visitorManager = new VisitorManager(this);
        visitorManager.visitElements();
    }

    public AbstractPlayerUI getPlayer()
    {
        return player;
    }

    public ThemableActivityInterface getActivity()
    {
        return activity;
    }

    public ContentBrowserDrawer getContentBrowserDrawer()
    {
        return contentBrowserDrawer;
    }

    public CustomSeekBar getCustomSeekBar()
    {
        return customSeekBar;
    }

    public Theme getTheme()
    {
        return theme;
    }
}
