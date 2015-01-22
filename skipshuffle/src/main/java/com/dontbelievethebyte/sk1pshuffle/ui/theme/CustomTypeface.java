/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.theme;

import android.content.Context;
import android.graphics.Typeface;

import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.TypeFaceMapper;

public class CustomTypeface {

    private Typeface typeface;

    public CustomTypeface(Context context, UITypes uiType)
    {
        typeface = Typeface.createFromAsset(
                context.getAssets(),
                TypeFaceMapper.getTypeFace(uiType)
        );
    }

    public Typeface getTypeFace()
    {
        return typeface;
    }
}
