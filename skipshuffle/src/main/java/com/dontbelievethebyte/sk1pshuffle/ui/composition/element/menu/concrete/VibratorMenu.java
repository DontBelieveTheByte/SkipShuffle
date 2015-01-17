/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.menu.concrete;

import android.app.Activity;
import android.view.Menu;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.menu.AbstractMenu;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.menu.callbacks.MenuItemSelectedCallback;

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
