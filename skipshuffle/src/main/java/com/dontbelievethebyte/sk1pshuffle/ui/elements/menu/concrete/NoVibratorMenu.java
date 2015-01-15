/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.menu.concrete;

import android.app.Activity;
import android.view.Menu;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.menu.callbacks.MenuItemSelectedCallback;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.menu.AbstractMenu;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.menu.CustomOptionsMenuInterface;

public class NoVibratorMenu extends AbstractMenu implements CustomOptionsMenuInterface {

    public NoVibratorMenu(Activity activity, Menu menu, MenuItemSelectedCallback menuItemSelectedCallback)
    {
        super(activity, menu, menuItemSelectedCallback);
    }

    @Override
    protected int getMenuResourceId()
    {
        return R.menu.main_no_vibrator;
    }

}
