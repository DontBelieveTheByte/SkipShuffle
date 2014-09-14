package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.R;

public class ColorMapper {
    public static int getBackgroundColor(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.background_mono_light;
            case UITypes.MONO_DARK :
                return R.color.background_mono_dark;
            case UITypes.NEON :
                return R.color.background_neon;
            default:
                return R.color.background_neon;
        }
    }

    public static int getSonglabelColor(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.song_label_mono_light;
            case UITypes.MONO_DARK :
                return R.color.song_label_mono_dark;
            case UITypes.NEON :
                return R.color.song_label_neon;
            default:
                return R.color.song_label_neon;
        }
    }

    public static int getListDividerColor(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.list_divider_mono_light;
            case UITypes.MONO_DARK :
                return R.color.list_divider_mono_dark;
            case UITypes.NEON :
                return R.color.list_divider_neon;
            default:
                return R.color.list_divider_neon;
        }
    }

    public static int getNavDrawerTextColor(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.list_divider_mono_light;
            case UITypes.MONO_DARK :
                return R.color.list_divider_mono_dark;
            case UITypes.NEON :
                return R.color.list_divider_neon;
            default:
                return R.color.list_divider_neon;
        }
    }
}
