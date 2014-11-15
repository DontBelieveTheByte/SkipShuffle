package com.dontbelievethebyte.skipshuffle.activities;

import com.dontbelievethebyte.skipshuffle.preferences.callbacks.PrefsCallbacksManager;
import com.dontbelievethebyte.skipshuffle.ui.elements.UICompositionFactory;

public class PlayerActivity extends BaseActivity implements PrefsCallbacksManager.ViewModeChangedCallback {

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
        ui = (viewMode) ?
                UICompositionFactory.makeListPlayer(this, type):
                UICompositionFactory.makeMainPlayer(this, type);
        ui.player.reboot();
    }

    @Override
    public void onViewModeChanged()
    {
        viewMode = !viewMode;
        setUI(preferencesHelper.getUIType());
    }
}
