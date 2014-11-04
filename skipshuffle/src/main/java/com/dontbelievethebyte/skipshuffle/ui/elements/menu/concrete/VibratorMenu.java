package com.dontbelievethebyte.skipshuffle.ui.elements.menu.concrete;

import android.app.Activity;
import android.view.Menu;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.menu.callbacks.MenuItemSelectedCallback;
import com.dontbelievethebyte.skipshuffle.ui.elements.menu.AbstractMenu;

public class VibratorMenu extends AbstractMenu {

    public VibratorMenu(Activity activity, Menu menu, MenuItemSelectedCallback menuItemSelectedCallback)
    {
        super(activity, menu, menuItemSelectedCallback);
    }

    @Override
    protected int getMenuResourceId()
    {
        return R.menu.main;
    }
}
