package com.dontbelievethebyte.skipshuffle.ui.builder;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.AbstractPlayer;
import com.dontbelievethebyte.skipshuffle.ui.BaseUI;
import com.dontbelievethebyte.skipshuffle.ui.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class UIBuilder {

    public AbstractPlayer player;
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

    public UIBuilder setPlayer(AbstractPlayer playerUIInterface)
    {
        this.player = playerUIInterface;
        return this;
    }

    public UIBuilder setLayout(ContentArea contentArea)
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

    public BaseUI build()
    {
        return new BaseUI(this);
    }
}