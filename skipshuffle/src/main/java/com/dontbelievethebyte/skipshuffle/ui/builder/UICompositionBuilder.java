package com.dontbelievethebyte.skipshuffle.ui.builder;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.AbstractLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.ContentBrowser;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIComposition;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class UICompositionBuilder {

    public AbstractPlayerUI player;
    public ContentBrowser musicPlayerDrawer;

    public BaseActivity baseActivity;
    public AbstractLayout contentArea;
    public Colors colors;
    public Drawables drawables;

    public UICompositionBuilder setActivity(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
        return this;
    }

    public UICompositionBuilder setNavigationDrawer(ContentBrowser musicPlayerDrawer)
    {
        this.musicPlayerDrawer = musicPlayerDrawer;
        return this;
    }

    public UICompositionBuilder setPlayer(AbstractPlayerUI playerUIInterface)
    {
        this.player = playerUIInterface;
        return this;
    }

    public UICompositionBuilder setContentArea(AbstractLayout contentArea)
    {
        this.contentArea = contentArea;
        return this;
    }

    public UICompositionBuilder setDrawables(Drawables drawables)
    {
        this.drawables = drawables;
        return this;
    }

    public UICompositionBuilder setColors(Colors colors)
    {
        this.colors = colors;
        return this;
    }

    public UIComposition build()
    {
        return new UIComposition(this);
    }
}