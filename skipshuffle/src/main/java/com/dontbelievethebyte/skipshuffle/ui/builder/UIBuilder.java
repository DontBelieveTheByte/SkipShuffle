package com.dontbelievethebyte.skipshuffle.ui.builder;

import android.app.Activity;

import com.dontbelievethebyte.skipshuffle.ui.BaseUI;
import com.dontbelievethebyte.skipshuffle.ui.Colors;
import com.dontbelievethebyte.skipshuffle.ui.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.NavigationDrawerProxy;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUIInterface;
import com.dontbelievethebyte.skipshuffle.ui.Drawables;

public class UIBuilder {

    private Activity activity;
    private NavigationDrawerProxy navigationDrawerProxy;
    private Colors colors;
    private Drawables drawables;
    private PlayerUIInterface playerUIInterface;
    private ContentArea contentArea;

    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }

    public void setNavigationDrawerProxy(NavigationDrawerProxy navigationDrawerProxy)
    {
        this.navigationDrawerProxy = navigationDrawerProxy;
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

    public BaseUI build()
    {
        return new BaseUI(this);
    }

}
