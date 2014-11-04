package com.dontbelievethebyte.skipshuffle.ui.elements.menu.concrete;


import android.view.Menu;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.ui.elements.menu.AbstractMenu;
import com.dontbelievethebyte.skipshuffle.ui.elements.menu.CustomOptionsMenuInterface;

public class NoVibratorMenu extends AbstractMenu implements CustomOptionsMenuInterface {

    public NoVibratorMenu(BaseActivity baseActivity, Menu menu)
    {
        super(baseActivity, menu);
    }


    @Override
    protected int getMenuResourceId()
    {
        return R.menu.main_no_vibrator;
    }
}
