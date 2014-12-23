/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.mapper;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.mapper.types.UITypes;

public class DrawableMapper {

    public static int getPlay(Integer uiType)
    {
        switch (uiType) {
            default:
                return R.drawable.play_states_neon;
        }
    }

    public static int getPlayPressed(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.play_btn_pressed_neon;
            default:
                return R.drawable.play_btn_pressed_neon;
        }
    }

    public static int getPause(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.pause_states_neon;
            default:
                return R.drawable.pause_states_neon;
        }
    }

    public static int getPausePressed(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.pause_btn_pressed_neon;
            default:
                return R.drawable.pause_btn_pressed_neon;
        }
    }

    public static int getPrev(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.prev_states_neon;
            default:
                return R.drawable.prev_states_neon;
        }
    }

    public static int getPrevPressed(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.prev_btn_pressed_neon;
            default:
                return R.drawable.prev_btn_pressed_neon;
        }
    }

    public static int getRemove(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.remove_states_neon;
            default:
                return R.drawable.remove_states_neon;
        }
    }

    public static int getSkip(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.skip_states_neon;
            default:
                return R.drawable.skip_states_neon;
        }
    }

    public static int getSkipPressed(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.next_btn_pressed_neon;
            default:
                return R.drawable.next_btn_pressed_neon;
        }
    }

    public static int getShuffle(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.shuffle_states_neon;
            default:
                return R.drawable.shuffle_states_neon;
        }
    }

    public static int getShufflePressed(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.shuffle_btn_pressed_neon;
            default:
                return R.drawable.shuffle_btn_pressed_neon;
        }
    }

    public static int getPlaylist(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return R.drawable.playlist_states_neon;
            default:
                return R.drawable.playlist_states_neon;
        }
    }

    public static int getNotificationBackground(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.drawable.notification_background_mono_light;
            case UITypes.MONO_DARK :
                return R.drawable.notification_background_mono_dark;
            case UITypes.NEON :
                return R.drawable.notification_background_neon;
            case UITypes.XMAS :
                return R.drawable.notification_background_xmas;
            default:
                return R.drawable.notification_background_neon;
        }
    }
}
