package com.dontbelievethebyte.skipshuffle.ui;


import com.dontbelievethebyte.skipshuffle.R;

public class DrawableMapper {
    public static int getCancelDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.cancel_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.cancel_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.cancel_states_neon;
            default:
                return R.drawable.cancel_states_neon;
        }
    }

    public static int getCheckboxDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.checkbox_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.checkbox_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.checkbox_states_neon;
            default:
                return R.drawable.checkbox_states_neon;
        }
    }

    public static int getOkDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.ok_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.ok_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.ok_states_neon;
            default:
                return R.drawable.ok_states_neon;
        }
    }

    public static int getPlayDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.play_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.play_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.play_states_neon;
            default:
                return R.drawable.play_states_neon;
        }
    }

    public static int getPlayPressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.play_btn_pressed_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.play_btn_pressed_mono_dark;
            case UITypes.NEON :
                return R.drawable.play_btn_pressed_neon;
            default:
                return R.drawable.play_btn_pressed_neon;
        }
    }

    public static int getPauseDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.pause_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.pause_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.pause_states_neon;
            default:
                return R.drawable.pause_states_neon;
        }
    }

    public static int getPausePressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.pause_btn_pressed_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.pause_btn_pressed_mono_dark;
            case UITypes.NEON :
                return R.drawable.pause_btn_pressed_neon;
            default:
                return R.drawable.pause_btn_pressed_neon;
        }
    }

    public static int getPrevDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.prev_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.prev_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.prev_states_neon;
            default:
                return R.drawable.prev_states_neon;
        }
    }

    public static int getPrevPressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.prev_btn_pressed_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.prev_btn_pressed_mono_dark;
            case UITypes.NEON :
                return R.drawable.prev_btn_pressed_neon;
            default:
                return R.drawable.prev_btn_pressed_neon;
        }
    }

    public static int getSkipDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.skip_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.skip_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.skip_states_neon;
            default:
                return R.drawable.skip_states_neon;
        }
    }

    public static int getSkipPressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.next_btn_pressed_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.next_btn_pressed_mono_dark;
            case UITypes.NEON :
                return R.drawable.next_btn_pressed_neon;
            default:
                return R.drawable.next_btn_pressed_neon;
        }
    }

    public static int getShuffleDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.shuffle_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.shuffle_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.shuffle_states_neon;
            default:
                return R.drawable.shuffle_states_neon;
        }
    }

    public static int getShufflePressedDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.shuffle_btn_pressed_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.shuffle_btn_pressed_mono_dark;
            case UITypes.NEON :
                return R.drawable.shuffle_btn_pressed_neon;
            default:
                return R.drawable.shuffle_btn_pressed_neon;
        }
    }

    public static int getPlaylistDrawable(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.playlist_states_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.playlist_states_mono_dark;
            case UITypes.NEON :
                return R.drawable.playlist_states_neon;
            default:
                return R.drawable.playlist_states_neon;
        }
    }

    public static int getFolderDrawable(Integer uiType)
    {
        switch (uiType){
            case UITypes.MONO_LIGHT :
                return R.drawable.folder_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.folder_mono_dark;
            case UITypes.NEON :
                return R.drawable.folder_neon;
            default:
                return R.drawable.folder_neon;
        }
    }
}
