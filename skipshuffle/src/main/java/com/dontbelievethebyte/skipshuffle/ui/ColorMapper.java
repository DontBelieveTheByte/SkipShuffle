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

    public static int getListDividerColor(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.mono_light_list_divider;
            case UITypes.MONO_DARK :
                return R.color.mono_dark_list_divider;
            case UITypes.NEON :
                return R.color.neon_list_divider;
            default:
                return R.color.neon_list_divider;
        }
    }

    public static int getActionBarColor(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.mono_light_action_bar;
            case UITypes.MONO_DARK :
                return R.color.mono_dark_action_bar;
            case UITypes.NEON :
                return R.color.neon_action_bar;
            default:
                return R.color.neon_action_bar;
        }
    }
}
