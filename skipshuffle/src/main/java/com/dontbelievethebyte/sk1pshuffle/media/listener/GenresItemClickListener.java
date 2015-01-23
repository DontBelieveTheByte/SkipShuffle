/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.listener;

import android.view.View;
import android.widget.AdapterView;

import com.dontbelievethebyte.sk1pshuffle.utilities.LogUtil;

public class GenresItemClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        LogUtil.writeDebug("Position : " + Integer.toString(position));

    }
}
