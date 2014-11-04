package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.Activity;

public abstract class CustomAbstractClick implements View.OnClickListener {

    protected Activity activity;

    public CustomAbstractClick(Activity activity)
    {
        this.activity = activity;
    }

}
