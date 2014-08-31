package com.dontbelievethebyte.skipshuffle;

public class UIFactory {
    public static final int MONO_LIGHT = 0;
    public static final int MONO_DARK = 1;
    public static final int NEON = 2;

    public static MainUI createMainUI(MainActivity mainActivity, Integer uiType){
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

    public static PlaylistUI createPlaylistUI(PlaylistActivity playlistActivity, Integer uiType){
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
    public static int getSinglePlaylistItemLayout(Integer uiType){
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

    public static int getPlayDrawable(Integer uiType){
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

    public static int getPauseDrawable(Integer uiType){
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

    public static int getNotificationLayout(Integer uiType){
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
}
