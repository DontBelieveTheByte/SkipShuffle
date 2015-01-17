/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.actionbar;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewConfiguration;

public class CustomActionBarWrapper {

    private ActionBarActivity activity;
    private ActionBar actionBar;

    public CustomActionBarWrapper(ActionBarActivity activity)
    {
        this.activity = activity;
        actionBar = activity.getSupportActionBar();
    }

    public void setUp()
    {
        if (ViewConfiguration.get(activity).hasPermanentMenuKey())
            actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void showToggle()
    {
        if (actionBar.isShowing())
            actionBar.hide();
        else
            actionBar.show();
    }

    public void setTitle(String title)
    {
        if (null != title)
            actionBar.setTitle(title);
    }

    public boolean isShowing()
    {
        return actionBar.isShowing();
    }
}
