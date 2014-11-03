package com.dontbelievethebyte.skipshuffle.ui.click.listener;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public abstract class Custom implements View.OnClickListener {

    protected BaseActivity baseActivity;

    public Custom(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

}
