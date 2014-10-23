package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;

public class UIFactory {

    public static PlayerUI createBaseUI(PlayerActivity mainActivity, Integer uiType)
    {
        switch (uiType){
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return new PlayerUI(mainActivity);
            default:
                return new PlayerUI(mainActivity);
        }
    }

    public static PlayerUI createMainUI(PlayerActivity mainActivity, Integer uiType)
    {
        switch (uiType){
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return new PlayerUI(mainActivity);
            default:
                return new PlayerUI(mainActivity);
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
