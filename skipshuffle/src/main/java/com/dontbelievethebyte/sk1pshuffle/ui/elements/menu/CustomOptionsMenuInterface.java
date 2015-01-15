/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.menu;

import android.view.KeyEvent;
import android.view.MenuItem;

import com.dontbelievethebyte.sk1pshuffle.exceptions.MenuOptionNotHandledException;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.actionbar.CustomActionBarWrapper;

public interface CustomOptionsMenuInterface {

    public boolean isShowing();
    public void showToggle();
    public void setCustomActionBarWrapper(CustomActionBarWrapper customActionBarWrapper);
    public boolean handleSelection(MenuItem menuItem) throws MenuOptionNotHandledException;
    public boolean handleMenuKeyDown(int keyCode, KeyEvent event);
    public void handleBackPressed();
}
