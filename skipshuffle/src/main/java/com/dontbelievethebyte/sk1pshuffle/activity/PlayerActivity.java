/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.activity;

import android.support.v4.widget.DrawerLayout;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.UICompositionFactory;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.listeners.concrete.BaseActivityClickListener;

public class PlayerActivity extends BaseActivity {

    private boolean viewMode;

    @Override
    protected void onPause()
    {
        preferencesHelper.setListViewMode(viewMode);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        viewMode = preferencesHelper.getListViewMode();
    }

    @Override
    protected void setUI(Integer type)
    {
        try {
            ui = (viewMode) ?
                    UICompositionFactory.createListPlayer(this, type):
                    UICompositionFactory.createMainPlayer(this, type);
            ui.getPlayer().reboot();

            ui.getContentBrowserDrawer().setClickListener(
                    new BaseActivityClickListener(
                            (DrawerLayout) findViewById(R.id.drawer_layout)
                    )
            );
        } catch (NoMediaPlayerException e) {
            handleNoMediaPlayerException(e);
        }
    }

    @Override
    public void onViewModeChanged()
    {
        viewMode = !viewMode;
        setUI(preferencesHelper.getUIType());
    }
}
