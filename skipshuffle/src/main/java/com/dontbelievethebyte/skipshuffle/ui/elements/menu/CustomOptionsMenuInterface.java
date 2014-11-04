package com.dontbelievethebyte.skipshuffle.ui.elements.menu;


import android.view.KeyEvent;
import android.view.MenuItem;

import com.dontbelievethebyte.skipshuffle.exceptions.MenuOptionNotHandledException;
import com.dontbelievethebyte.skipshuffle.ui.elements.actionbar.CustomActionBarWrapper;

public interface CustomOptionsMenuInterface {

    public void setCustomActionBarWrapper(CustomActionBarWrapper customActionBarWrapper);
    public boolean handleSelection(MenuItem menuItem) throws MenuOptionNotHandledException;
    public boolean handleMenuKeyDown(int keyCode, KeyEvent event);
    public void handleBackPressed();
}
