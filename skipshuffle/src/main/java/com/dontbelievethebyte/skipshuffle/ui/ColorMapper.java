package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.R;

public class ColorMapper {
    public static int getBackground(Integer uiType)
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

    public static int getEmptyListText(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.list_empty_text_mono_light;
            case UITypes.MONO_DARK :
                return R.color.list_empty_text_mono_dark;
            case UITypes.NEON :
                return R.color.list_empty_text_neon;
            default:
                return R.color.list_empty_text_neon;
        }
    }

    public static int getListDivider(Integer uiType)
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

    public static int getNavDrawerBackground(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.nav_drawer_background_mono_light;
            case UITypes.MONO_DARK :
                return R.color.nav_drawer_background_mono_dark;
            case UITypes.NEON :
                return R.color.nav_drawer_background_neon;
            default:
                return R.color.nav_drawer_background_neon;
        }
    }

    public static int getNavHeaderText(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.nav_drawer_header_text_mono_light;
            case UITypes.MONO_DARK :
                return R.color.nav_drawer_header_text_mono_dark;
            case UITypes.NEON :
                return R.color.nav_drawer_header_text_neon;
            default:
                return R.color.nav_drawer_header_text_neon;
        }
    }

    public static int getNavDrawerText(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.nav_drawer_text_mono_light;
            case UITypes.MONO_DARK :
                return R.color.nav_drawer_text_mono_dark;
            case UITypes.NEON :
                return R.color.nav_drawer_text_neon;
            default:
                return R.color.nav_drawer_text_neon;
        }
    }

    public static int getSongLabel(Integer uiType)
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
}
