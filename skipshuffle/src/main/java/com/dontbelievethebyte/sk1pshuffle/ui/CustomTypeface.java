/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
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
