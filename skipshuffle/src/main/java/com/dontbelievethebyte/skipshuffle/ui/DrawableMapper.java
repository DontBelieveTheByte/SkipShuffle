package com.dontbelievethebyte.skipshuffle.ui;


import com.dontbelievethebyte.skipshuffle.R;

public class DrawableMapper {
    public static int getCancelDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_cancel_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_cancel_states;
            case UITypes.NEON :
                return R.drawable.neon_cancel_states;
            default:
                return R.drawable.neon_cancel_states;
        }
    }

    public static int getCheckboxDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_checkbox_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_checkbox_states;
            case UITypes.NEON :
                return R.drawable.neon_checkbox_states;
            default:
                return R.drawable.neon_checkbox_states;
        }
    }

    public static int getOkDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_ok_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_ok_states;
            case UITypes.NEON :
                return R.drawable.neon_ok_states;
            default:
                return R.drawable.neon_ok_states;
        }
    }

    public static int getPlayDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_play_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_play_states;
            case UITypes.NEON :
                return R.drawable.neon_play_states;
            default:
                return R.drawable.neon_play_states;
        }
    }

    public static int getPlayPressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_play_btn_pressed;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_play_btn_pressed;
            case UITypes.NEON :
                return R.drawable.neon_play_btn_pressed;
            default:
                return R.drawable.neon_play_btn_pressed;
        }
    }

    public static int getPauseDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_pause_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_pause_states;
            case UITypes.NEON :
                return R.drawable.neon_pause_states;
            default:
                return R.drawable.neon_pause_states;
        }
    }

    public static int getPausePressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_pause_btn_pressed;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_pause_btn_pressed;
            case UITypes.NEON :
                return R.drawable.neon_pause_btn_pressed;
            default:
                return R.drawable.neon_pause_btn_pressed;
        }
    }

    public static int getPrevDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_prev_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_prev_states;
            case UITypes.NEON :
                return R.drawable.neon_prev_states;
            default:
                return R.drawable.neon_prev_states;
        }
    }

    public static int getPrevPressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_prev_btn_pressed;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_prev_btn_pressed;
            case UITypes.NEON :
                return R.drawable.neon_prev_btn_pressed;
            default:
                return R.drawable.neon_prev_btn_pressed;
        }
    }

    public static int getSkipDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_skip_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_skip_states;
            case UITypes.NEON :
                return R.drawable.neon_skip_states;
            default:
                return R.drawable.neon_skip_states;
        }
    }

    public static int getSkipPressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_next_btn_pressed;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_next_btn_pressed;
            case UITypes.NEON :
                return R.drawable.neon_next_btn_pressed;
            default:
                return R.drawable.neon_next_btn_pressed;
        }
    }

    public static int getShuffleDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_shuffle_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_shuffle_states;
            case UITypes.NEON :
                return R.drawable.neon_shuffle_states;
            default:
                return R.drawable.neon_shuffle_states;
        }
    }

    public static int getShufflePressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_shuffle_btn_pressed;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_shuffle_btn_pressed;
            case UITypes.NEON :
                return R.drawable.neon_shuffle_btn_pressed;
            default:
                return R.drawable.neon_shuffle_btn_pressed;
        }
    }

    public static int getPlaylistDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_playlist_states;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_playlist_states;
            case UITypes.NEON :
                return R.drawable.neon_playlist_states;
            default:
                return R.drawable.neon_playlist_states;
        }
    }

    public static int getFolderDrawable(Integer uiType)
    {
        switch (uiType){
            case UITypes.MONO_LIGHT :
                return R.drawable.mono_light_folder;
            case UITypes.MONO_DARK :
                return R.drawable.mono_dark_folder;
            case UITypes.NEON :
                return R.drawable.neon_folder;
            default:
                return R.drawable.neon_folder;
        }
    }
}
