package com.dontbelievethebyte.skipshuffle.ui.builder;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.UIComposition;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class UIBuilder {

    public AbstractPlayerUI player;
    public MusicPlayerDrawer musicPlayerDrawer;

    public BaseActivity baseActivity;
    public ContentArea contentArea;
    public Colors colors;
    public Drawables drawables;
    public CustomTypeface customTypeface;

    public UIBuilder setActivity(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
        return this;
    }

    public UIBuilder setNavigationDrawer(MusicPlayerDrawer musicPlayerDrawer)
    {
        this.musicPlayerDrawer = musicPlayerDrawer;
        return this;
    }

    public UIBuilder setPlayer(AbstractPlayerUI playerUIInterface)
    {
        this.player = playerUIInterface;
        return this;
    }

    public UIBuilder setContentArea(ContentArea contentArea)
    {
        this.contentArea = contentArea;
        return this;
    }

    public UIBuilder setDrawables(Drawables drawables)
    {
        this.drawables = drawables;
        return this;
    }

    public UIBuilder setColors(Colors colors)
    {
        this.colors = colors;
        return this;
    }

    public UIBuilder setCustomTypeFace(CustomTypeface customTypeface)
    {
        this.customTypeface = customTypeface;
        return this;
    }

    public UIComposition build()
    {
        return new UIComposition(this);
    }
}