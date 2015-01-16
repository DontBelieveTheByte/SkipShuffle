/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper;

import com.dontbelievethebyte.sk1pshuffle.ui.theme.UITypes;

public class TypeFaceMapper {
    public static String getTypeFace(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return "fonts/UbuntuMono-B.ttf";
            default:
                return "fonts/UbuntuMono-B.ttf";
        }
    }
}
