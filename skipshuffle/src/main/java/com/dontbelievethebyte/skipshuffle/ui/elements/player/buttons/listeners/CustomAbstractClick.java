package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public abstract class CustomAbstractClick implements View.OnClickListener {

    protected BaseActivity baseActivity;

    public CustomAbstractClick(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

}
