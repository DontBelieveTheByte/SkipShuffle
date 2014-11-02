package com.dontbelievethebyte.skipshuffle.ui.builder;

import android.graphics.Typeface;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.BaseUI;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUIInterface;

public class UIBuilder {

    private BaseActivity baseActivity;
    private MusicPlayerDrawer musicPlayerDrawer;
    private Colors colors;
    private Drawables drawables;
    private PlayerUIInterface playerUIInterface;
    private ContentArea contentArea;
    private Typeface typeface;

    public void setActivity(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    public void setNavigationDrawer(MusicPlayerDrawer musicPlayerDrawer)
    {
        this.musicPlayerDrawer = musicPlayerDrawer;
    }

    public void setPlayerUIInterface(PlayerUIInterface playerUIInterface)
    {
        this.playerUIInterface = playerUIInterface;
    }

    public void setContentArea(ContentArea contentArea)
    {
        this.contentArea = contentArea;
    }

    public void setDrawables(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public void setColors(Colors colors)
    {
        this.colors = colors;
    }

    public void setTypeFace(Typeface typeFace)
    {

    }

    public BaseUI build()
    {
        return new BaseUI(this);
    }

}
