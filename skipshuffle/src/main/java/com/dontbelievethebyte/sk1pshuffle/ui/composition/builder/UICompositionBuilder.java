/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.builder;

import com.dontbelievethebyte.sk1pshuffle.activity.ThemableActivityInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.UIComposition;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.Theme;

public class UICompositionBuilder {

    public AbstractPlayerUI player;
    public ContentBrowserDrawer musicPlayerDrawer;
    public ThemableActivityInterface themableActivity;
    public CustomSeekBar customSeekBar;
    public Theme theme;

    public UICompositionBuilder setActivity(ThemableActivityInterface themableActivity)
    {
        this.themableActivity = themableActivity;
        return this;
    }

    public UICompositionBuilder setTheme(Theme theme)
    {
        this.theme = theme;
        return this;
    }

    public UICompositionBuilder setNavigationDrawer(ContentBrowserDrawer musicPlayerDrawer)
    {
        this.musicPlayerDrawer = musicPlayerDrawer;
        return this;
    }

    public UICompositionBuilder setPlayer(AbstractPlayerUI playerUIInterface)
    {
        this.player = playerUIInterface;
        return this;
    }

    public UICompositionBuilder setSeekbar(CustomSeekBar customSeekBar)
    {
        this.customSeekBar = customSeekBar;
        return this;
    }

    public UIComposition build()
    {
        return new UIComposition(this);
    }
}