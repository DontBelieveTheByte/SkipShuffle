package com.dontbelievethebyte.skipshuffle.actionbar;

import android.support.v7.app.ActionBar;
import android.view.ViewConfiguration;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public class CustomActionBar {

    private BaseActivity baseActivity;
    private ActionBar actionBar;

    public CustomActionBar(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
        actionBar = baseActivity.getSupportActionBar();
    }

    public void setUp()
    {
        if (ViewConfiguration.get(baseActivity).hasPermanentMenuKey())
            actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
