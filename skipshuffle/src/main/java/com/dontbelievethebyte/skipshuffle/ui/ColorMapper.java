package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.R;

public class ColorMapper {
    public static int getBackgroundColor(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.mono_light_background;
            case UITypes.MONO_DARK :
                return R.color.mono_dark_background;
            case UITypes.NEON :
                return R.color.neon_background;
            default:
                return R.color.neon_background;
        }
    }

    public static int getSonglabelColor(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.mono_light_song_label;
            case UITypes.MONO_DARK :
                return R.color.mono_dark_song_label;
            case UITypes.NEON :
                return R.color.neon_song_label;
            default:
                return R.color.neon_song_label;
        }
    }
}
