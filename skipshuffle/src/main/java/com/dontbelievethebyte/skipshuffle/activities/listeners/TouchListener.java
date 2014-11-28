/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.activities.listeners;

import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.utilities.preferences.PreferencesHelper;

public class TouchListener {

    private PreferencesHelper preferencesHelper;

    public TouchListener(PreferencesHelper preferencesHelper)
    {
        this.preferencesHelper = preferencesHelper;
    }

    public boolean handleTouch(View view, MotionEvent motionEvent)
    {
        if (MotionEvent.ACTION_DOWN == motionEvent.getAction())
            handleHapticFeedback(view);
        return false;
    }

    private void handleHapticFeedback(View view)
    {
        if (preferencesHelper.isHapticFeedback()) {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        }
    }
}
