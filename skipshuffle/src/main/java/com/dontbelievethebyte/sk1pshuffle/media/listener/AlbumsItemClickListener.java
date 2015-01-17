/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.listener;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dontbelievethebyte.sk1pshuffle.utilities.LogUtil;

public class AlbumsItemClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        Log.d(LogUtil.TAG, "Position : " + Integer.toString(position));

    }
}