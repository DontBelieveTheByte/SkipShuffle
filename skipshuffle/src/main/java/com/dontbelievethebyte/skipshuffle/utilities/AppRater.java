/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.utilities;

import android.app.Activity;

import com.dontbelievethebyte.skipshuffle.ui.dialog.AppRaterDialog;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.PreferencesHelper;

public class AppRater {

    private PreferencesHelper preferencesHelper;
    private Activity activity;
    private ToastHelper toastHelper;

    private final static int APP_LAUNCHES_UNTIL_PROMPT = 10;


    public AppRater(Activity activity, PreferencesHelper preferencesHelper, ToastHelper toastHelper)
    {
        this.preferencesHelper = preferencesHelper;
        this.activity = activity;
        this.toastHelper = toastHelper;
        preferencesHelper.increaseNumberTimesAppWasOpened();
    }

    public void rateIfPossible()
    {
        boolean isTimeToRate = preferencesHelper.getNumberTimesAppWasOpened() % APP_LAUNCHES_UNTIL_PROMPT == 0;

        if (preferencesHelper.canRateApp() && isTimeToRate) {
            AppRaterDialog appRaterDialog = new AppRaterDialog(
                    activity,
                    preferencesHelper,
                    toastHelper
            );
            appRaterDialog.build();
            appRaterDialog.show();
        }
    }
}
