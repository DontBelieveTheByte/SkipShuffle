package com.dontbelievethebyte.skipshuffle;

public class UIFactory {
    public static final int MONO_LIGHT = 0;
    public static final int MONO_DARK = 1;
    public static final int NEON = 3;
    public static final int MARIO = 4;

    public static MainUI createMainUI(MainActivity mainActivity, Integer uiType){
        switch (uiType){
            case 0 :
                return new MonoLightMainUI(mainActivity);
            case 1 :
                return new MonoDarkMainUI(mainActivity);
            case 2 :
                return new NeonMainUI(mainActivity);
            case 4 :
                return new MarioMainUI(mainActivity);
            default:
                return new NeonMainUI(mainActivity);
        }
    }

    public static UI createPlaylistUI(PlaylistActivity playlistActivity, Integer uiType){
        switch (uiType){
//            case 0 :
//                return new MonoLightUI(playlistActivity);
//            case 1 :
//                return new MonoDarkUI(playlistActivity);
//            case 2 :
//                return new NeonUI(playlistActivity);
//            case 4 :
//                return new MarioUI(playlistActivity);
            default:
                return new NeonPlaylistUI();
        }
    }
//
//    public static UI createNotificationUI(Context context, Integer uiType){
//        switch (uiType){
//            case 0 :
//                return new MonoLightUI(mainActivity);
//            case 1 :
//                return new MonoDarkUI(mainActivity);
//            case 2 :
//                return new NeonUI(mainActivity);
//            case 4 :
//                return new MarioUI(mainActivity);
//            default:
//                return new NeonUI(mainActivity);
//        }
//    }
}
