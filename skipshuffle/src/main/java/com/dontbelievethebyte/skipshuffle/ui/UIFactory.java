package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistSelectorActivity;

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

    public static FilePickerUI createFilePickerUI(FilePickerActivity filePickerActivity, Integer uiType)
    {
        switch (uiType){
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return new FilePickerUI(filePickerActivity);
            default:
                return new FilePickerUI(filePickerActivity);
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

    public static PlaylistSelectorUI createPlaylistSelectorUI(PlaylistSelectorActivity playlistSelectorActivityActivity, Integer uiType)
    {
        switch (uiType){
            case UITypes.MONO_LIGHT :
            case UITypes.MONO_DARK :
            case UITypes.NEON :
                return new PlaylistSelectorUI(playlistSelectorActivityActivity);
            default:
                return new PlaylistSelectorUI(playlistSelectorActivityActivity);
        }
    }
}
