package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;

public class UIFactory {

    public static MainUI createMainUI(MainActivity mainActivity, Integer uiType)
    {
        switch (uiType){
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return new MainUI(mainActivity);
            default:
                return new MainUI(mainActivity);
        }
    }

    public static PlaylistUI createPlaylistUI(PlaylistActivity playlistActivity, Integer uiType)
    {
        switch (uiType){
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return new PlaylistUI(playlistActivity);
            default:
                return new PlaylistUI(playlistActivity);
        }
    }
}
