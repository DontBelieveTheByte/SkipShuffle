package com.dontbelievethebyte.skipshuffle.ui.mapper;

import com.dontbelievethebyte.skipshuffle.ui.UITypes;

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
