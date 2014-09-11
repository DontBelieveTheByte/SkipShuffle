package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;
import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistSelectorActivity;
import com.dontbelievethebyte.skipshuffle.ui.main.MainUI;
import com.dontbelievethebyte.skipshuffle.ui.main.MonoDarkMainUI;
import com.dontbelievethebyte.skipshuffle.ui.main.MonoLightMainUI;
import com.dontbelievethebyte.skipshuffle.ui.main.NeonMainUI;
import com.dontbelievethebyte.skipshuffle.ui.playlist.MonoDarkPlaylistUI;
import com.dontbelievethebyte.skipshuffle.ui.playlist.MonoLightPlaylistUI;
import com.dontbelievethebyte.skipshuffle.ui.playlist.NeonPlaylistUI;
import com.dontbelievethebyte.skipshuffle.ui.playlist.PlaylistUI;
import com.dontbelievethebyte.skipshuffle.ui.playlistselector.MonoDarkPlaylistSelectorUI;
import com.dontbelievethebyte.skipshuffle.ui.playlistselector.MonoLightPlaylistSelectorUI;
import com.dontbelievethebyte.skipshuffle.ui.playlistselector.NeonPlaylistSelectorUI;
import com.dontbelievethebyte.skipshuffle.ui.playlistselector.PlaylistSelectorUI;

public class UIFactory {
    public static final int MONO_LIGHT = 0;
    public static final int MONO_DARK = 1;
    public static final int NEON = 2;

    public static MainUI createMainUI(MainActivity mainActivity, Integer uiType)
    {
        switch (uiType){
            case MONO_LIGHT :
                return new MonoLightMainUI(mainActivity);
            case MONO_DARK :
                return new MonoDarkMainUI(mainActivity);
            case NEON :
                return new NeonMainUI(mainActivity);
            default:
                return new NeonMainUI(mainActivity);
        }
    }

    public static PlaylistUI createPlaylistUI(PlaylistActivity playlistActivity, Integer uiType)
    {
        switch (uiType){
            case MONO_LIGHT :
                return new MonoLightPlaylistUI(playlistActivity);
            case MONO_DARK :
                return new MonoDarkPlaylistUI(playlistActivity);
            case NEON :
                return new NeonPlaylistUI(playlistActivity);
            default:
                return new NeonPlaylistUI(playlistActivity);
        }
    }

    public static PlaylistSelectorUI createPlaylistSelectorUI(PlaylistSelectorActivity playlistSelectorActivityActivity, Integer uiType)
    {
        switch (uiType){
            case MONO_LIGHT :
                return new MonoLightPlaylistSelectorUI(playlistSelectorActivityActivity);
            case MONO_DARK :
                return new MonoDarkPlaylistSelectorUI(playlistSelectorActivityActivity);
            case NEON :
                return new NeonPlaylistSelectorUI(playlistSelectorActivityActivity);
            default:
                return new NeonPlaylistSelectorUI(playlistSelectorActivityActivity);
        }
    }

    public static int getSinglePlaylistItemLayout(Integer uiType)
    {
        switch (uiType) {
            case MONO_LIGHT :
                return R.layout.mono_light_playlist_item;
            case MONO_DARK :
                return R.layout.mono_dark_playlist_item;
            case NEON :
                return R.layout.neon_playlist_item;
            default:
                return R.layout.neon_playlist_item;
        }
    }

    public static int getPlayDrawable(Integer uiType)
    {
        switch (uiType) {
            case MONO_LIGHT :
                return R.drawable.mono_light_play_states;
            case MONO_DARK :
                return R.drawable.mono_dark_play_states;
            case NEON :
                return R.drawable.neon_play_states;
            default:
                return R.drawable.neon_play_states;
        }
    }

    public static int getPauseDrawable(Integer uiType)
    {
        switch (uiType) {
            case MONO_LIGHT :
                return R.drawable.mono_light_pause_states;
            case MONO_DARK :
                return R.drawable.mono_dark_pause_states;
            case NEON :
                return R.drawable.neon_pause_states;
            default:
                return R.drawable.neon_pause_states;
        }
    }

    public static int getNotificationLayout(Integer uiType)
    {
        switch (uiType){
            case MONO_LIGHT :
                return R.layout.mono_light_notification;
            case MONO_DARK :
                return R.layout.mono_dark_notification;
            case NEON :
                return R.layout.neon_notification;
            default:
                return R.layout.neon_notification;
        }
    }

    public static int getFilePickerLayout(Integer uiType)
    {
        switch (uiType){
            case MONO_LIGHT :
                return R.layout.mono_light_file_picker;
            case MONO_DARK :
                return R.layout.mono_dark_file_picker;
            case NEON :
                return R.layout.neon_file_picker;
            default:
                return R.layout.neon_file_picker;
        }
    }

    public static int getFilePickerSingleItemLayout(Integer uiType)
    {
        switch (uiType){
            case MONO_LIGHT :
                return R.layout.mono_light_file_picker_list_item;
            case MONO_DARK :
                return R.layout.mono_dark_file_picker_list_item;
            case NEON :
                return R.layout.neon_file_picker_list_item;
            default:
                return R.layout.neon_file_picker_list_item;
        }
    }
}
