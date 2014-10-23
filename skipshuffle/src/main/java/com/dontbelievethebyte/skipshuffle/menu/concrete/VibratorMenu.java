package com.dontbelievethebyte.skipshuffle.menu.concrete;

import android.view.Menu;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.menu.AbstractMenu;

public class VibratorMenu extends AbstractMenu {


    public VibratorMenu(BaseActivity baseActivity, Menu menu)
    {
        super(baseActivity, menu);
    }

    @Override
    protected int getMenuResourceId()
    {
        return R.menu.main;
    }
}
