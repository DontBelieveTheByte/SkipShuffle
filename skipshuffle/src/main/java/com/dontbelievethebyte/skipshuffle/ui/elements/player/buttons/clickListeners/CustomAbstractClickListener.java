/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners;

import android.view.HapticFeedbackConstants;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.PreferencesHelper;

public abstract class CustomAbstractClickListener implements View.OnClickListener {

    protected BaseActivity activity;
    private PreferencesHelper preferencesHelper;

    public CustomAbstractClickListener(BaseActivity activity)
    {
        this.activity = activity;
        this.preferencesHelper = activity.getPreferencesHelper();
    }

    protected void handleHapticFeedback(View view)
    {
        if (preferencesHelper.isHapticFeedback())
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
    }
}
