/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.listeners;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;

public class SongsClick implements AdapterView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        Log.d(BaseActivity.TAG, "Position : " + Integer.toString(position));
    }
}
