package com.dontbelievethebyte.skipshuffle.ui.elements.actionbar;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewConfiguration;

import com.dontbelievethebyte.skipshuffle.exceptions.NoHardwareMenuKeyException;

public class CustomActionBarWrapper {

    private ActionBarActivity activity;
    private ActionBar actionBar;
    private boolean hasHardWareMenuKey;
    private boolean isOptionsMenuOpen;

    public CustomActionBarWrapper(ActionBarActivity activity)
    {
        this.activity = activity;
        actionBar = activity.getSupportActionBar();
        hasHardWareMenuKey = ViewConfiguration.get(activity).hasPermanentMenuKey();
    }

    public void setUp()
    {
        if (ViewConfiguration.get(activity).hasPermanentMenuKey())
            actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void showToggle() throws NoHardwareMenuKeyException
    {
        if (actionBar.isShowing()) {
            if (hasHardWareMenuKey) {
                actionBar.hide();
                isOptionsMenuOpen = false;
            } else
                throw new NoHardwareMenuKeyException();
        } else {
            actionBar.show();
            isOptionsMenuOpen = true;
        }
    }

    public boolean isShowing()
    {
        return actionBar.isShowing();
    }

    public boolean handleOnKeyDownWidthPermanentMenuKey()
    {
        if (actionBar.isShowing()) {
            if (isOptionsMenuOpen) {
                activity.closeOptionsMenu();
                actionBar.hide();
                isOptionsMenuOpen = false;
            } else {
                activity.openOptionsMenu();
                isOptionsMenuOpen = true;
            }
        } else {
            actionBar.show();
        }
        return true;
    }
}
