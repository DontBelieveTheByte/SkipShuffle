package com.dontbelievethebyte.skipshuffle.actionbar;

import android.support.v7.app.ActionBar;
import android.view.ViewConfiguration;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoHardwareMenuKeyException;

public class CustomActionBarWrapper {

    private BaseActivity baseActivity;
    private ActionBar actionBar;
    boolean hasHardWareMenuKey;

    public CustomActionBarWrapper(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
        actionBar = baseActivity.getSupportActionBar();
        hasHardWareMenuKey = ViewConfiguration.get(baseActivity).hasPermanentMenuKey();
    }

    public void setUp()
    {
        if (ViewConfiguration.get(baseActivity).hasPermanentMenuKey())
            actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void showToggle() throws NoHardwareMenuKeyException
    {
        if (actionBar.isShowing()) {
            if (hasHardWareMenuKey)
                actionBar.hide();
            else
                throw new NoHardwareMenuKeyException();
        } else {
            actionBar.show();
        }
    }

    public boolean isShowing()
    {
        return actionBar.isShowing();
    }
}
