/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui;

import android.app.Activity;
import android.graphics.Typeface;

import com.dontbelievethebyte.sk1pshuffle.ui.mapper.TypeFaceMapper;

public class CustomTypeface {

    private int uiType;
    private Typeface typeface;
    private Activity activity;

    public CustomTypeface(Activity activity, int uiType)
    {
        this.activity = activity;
        this.uiType = uiType;
    }

    public Typeface getTypeFace()
    {
        if (null == typeface) {
            typeface = Typeface.createFromAsset(
                    activity.getAssets(),
                    TypeFaceMapper.getTypeFace(uiType)
            );
        }
        return typeface;
    }
}
