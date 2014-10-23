package com.dontbelievethebyte.skipshuffle.menu;


import android.view.KeyEvent;
import android.view.MenuItem;

import com.dontbelievethebyte.skipshuffle.actionbar.CustomActionBarWrapper;
import com.dontbelievethebyte.skipshuffle.exceptions.BackPressedNotHandledException;
import com.dontbelievethebyte.skipshuffle.exceptions.MenuOptionNotHandledException;

public interface CustomOptionsMenuInterface {

    public boolean isShowing();
    public void showToggle();
    public void setCustomActionBarWrapper(CustomActionBarWrapper customActionBarWrapper);
    public boolean handleSelection(MenuItem menuItem) throws MenuOptionNotHandledException;
    public boolean handleMenuKeyDown(int keyCode, KeyEvent event);
    public boolean handleBackPressed() throws BackPressedNotHandledException;
}
