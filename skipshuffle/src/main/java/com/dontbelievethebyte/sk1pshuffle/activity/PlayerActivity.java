/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.activity;

import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.element.UICompositionFactory;

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
