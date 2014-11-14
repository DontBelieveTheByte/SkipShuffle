package com.dontbelievethebyte.skipshuffle.activities;

import com.dontbelievethebyte.skipshuffle.ui.elements.UICompositionFactory;

public class PlayerActivity extends BaseActivity {

    @Override
    protected void setUI(Integer type)
    {
        ui = (preferencesHelper.getListViewMode()) ?
                UICompositionFactory.makeMainPlayer(this, type):
                UICompositionFactory.makeListPlayer(this, type);
        ui.player.reboot();
    }
}
