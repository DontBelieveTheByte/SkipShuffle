package com.dontbelievethebyte.skipshuffle.ui.elements.menu;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.callbacks.MenuItemSelectedCallback;
import com.dontbelievethebyte.skipshuffle.exceptions.BackPressedNotHandledException;
import com.dontbelievethebyte.skipshuffle.exceptions.MenuOptionNotHandledException;
import com.dontbelievethebyte.skipshuffle.exceptions.NoHardwareMenuKeyException;
import com.dontbelievethebyte.skipshuffle.ui.elements.actionbar.CustomActionBarWrapper;

public abstract class AbstractMenu implements CustomOptionsMenuInterface {

    private MenuItemSelectedCallback menuItemSelectedCallback;
    protected Activity activity;
    protected boolean isOptionsMenuOpen = false;
    protected CustomActionBarWrapper customActionBarWrapper;

    public AbstractMenu(Activity activity, Menu menu, MenuItemSelectedCallback menuItemSelectedCallback)
    {
        this.activity = activity;
        this.menuItemSelectedCallback = menuItemSelectedCallback;
        MenuInflater menuInflater = activity.getMenuInflater();
        menuInflater.inflate(getMenuResourceId(), menu);
    }

    public void setCustomActionBarWrapper(CustomActionBarWrapper customActionBarWrapper)
    {
        this.customActionBarWrapper = customActionBarWrapper;
    }

    protected abstract int getMenuResourceId();

    @Override
    public boolean handleSelection(MenuItem menuItem) throws MenuOptionNotHandledException {
        switch (menuItem.getItemId()) {
            case R.id.refresh_media:
                return menuItemSelectedCallback.handleMenuRefreshMedia();
            case R.id.haptic_feedback_toggle:
                return menuItemSelectedCallback.handleMenuHapticFeedBack();
            case R.id.theme:
                return menuItemSelectedCallback.handleMenuThemeSelection();
            default:
                throw new MenuOptionNotHandledException();
        }
    }

    @Override
    public boolean isShowing()
    {
        return false;
    }

    @Override
    public boolean handleMenuKeyDown(int keyCode, KeyEvent event)
    {
        Log.d(BaseActivity.TAG, "PRESSED!!!");
        boolean returnValue = false;

        if (isOptionsMenuOpen) {
            if (customActionBarWrapper.isShowing()) {
                try {
                    customActionBarWrapper.showToggle();
                    returnValue = true;
                } catch (NoHardwareMenuKeyException noHardWareMenuKeyException) {
                    returnValue = true;
                }
            }
        } else {
            activity.closeOptionsMenu();
        }
        isOptionsMenuOpen = !isOptionsMenuOpen;
        return returnValue;
    }

    @Override
    public void showToggle()
    {

    }

    @Override
    public boolean handleBackPressed() throws BackPressedNotHandledException {
        return false;
    }
}
