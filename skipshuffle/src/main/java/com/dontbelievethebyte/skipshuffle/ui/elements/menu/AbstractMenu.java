/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.menu;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.exceptions.MenuOptionNotHandledException;
import com.dontbelievethebyte.skipshuffle.ui.elements.actionbar.CustomActionBarWrapper;
import com.dontbelievethebyte.skipshuffle.ui.elements.menu.callbacks.MenuItemSelectedCallback;

public abstract class AbstractMenu implements CustomOptionsMenuInterface {

    private MenuItemSelectedCallback menuItemSelectedCallback;
    protected Activity activity;
    protected CustomActionBarWrapper customActionBarWrapper;
    private MenuStateMachine menuStateMachine;

    public AbstractMenu(Activity activity, Menu menu, MenuItemSelectedCallback menuItemSelectedCallback)
    {
        this.activity = activity;
        this.menuItemSelectedCallback = menuItemSelectedCallback;
        MenuInflater menuInflater = activity.getMenuInflater();
        menuInflater.inflate(getMenuResourceId(), menu);
        menuStateMachine = new MenuStateMachine();
    }

    private class MenuStateMachine
    {
        private boolean isOptionsMenuOpen = false;
        private boolean hasHardWareMenuKey;

        public MenuStateMachine()
        {
            hasHardWareMenuKey = ViewConfiguration.get(activity).hasPermanentMenuKey();
        }

        public void tick()
        {
            if (isOptionsMenuOpen) {
                activity.closeOptionsMenu();
                isOptionsMenuOpen = false;
            } else if (customActionBarWrapper.isShowing()) {
                if (hasHardWareMenuKey){
                    customActionBarWrapper.showToggle();
                }
            } else if (!customActionBarWrapper.isShowing()){//@TODO Fix this.
                if (hasHardWareMenuKey){
                    customActionBarWrapper.showToggle();
                }
            } else {
                activity.openOptionsMenu();
            }
        }

        public boolean isOptionsMenuOpen()
        {
            return isOptionsMenuOpen;
        }
    }

    public void setCustomActionBarWrapper(CustomActionBarWrapper customActionBarWrapper)
    {
        this.customActionBarWrapper = customActionBarWrapper;
    }

    protected abstract int getMenuResourceId();

    @Override
    public boolean handleSelection(MenuItem menuItem) throws MenuOptionNotHandledException
    {
        switch (menuItem.getItemId()) {
            case R.id.refresh_media:
                return menuItemSelectedCallback.handleMenuRefreshMedia();
//            case R.id.haptic_feedback_toggle:
//                return menuItemSelectedCallback.handleMenuHapticFeedBack();
            case R.id.theme:
                return menuItemSelectedCallback.handleMenuThemeSelection();
            default:
                throw new MenuOptionNotHandledException();
        }
    }

    @Override
    public boolean handleMenuKeyDown(int keyCode, KeyEvent event)
    {
        menuStateMachine.tick();
        return true;
    }

    @Override
    public void handleBackPressed()
    {
        menuStateMachine.tick();
    }

    @Override
    public boolean isShowing()
    {
        return menuStateMachine.isOptionsMenuOpen();
    }

    @Override
    public void showToggle()
    {
        menuStateMachine.tick();
    }
}
