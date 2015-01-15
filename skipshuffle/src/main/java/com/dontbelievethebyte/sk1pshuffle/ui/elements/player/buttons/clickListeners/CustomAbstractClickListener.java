/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.clickListeners;

import android.view.HapticFeedbackConstants;
import android.view.View;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PreferencesHelper;

public abstract class CustomAbstractClickListener implements View.OnClickListener,
                                                             View.OnLongClickListener{

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
