package com.dontbelievethebyte.skipshuffle.activities;

import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.builder.UIBuilder;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class PlayerActivity extends BaseActivity {

    private PlayerUI ui;

    @Override
    protected void onPause()
    {
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ui.reboot();
    }

    @Override
    protected void setUI(Integer type)
    {
        UIBuilder uiBuilder = new UIBuilder();
        uiBuilder.setActivity(this);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer());
        uiBuilder.setColors(new Colors(type));
        uiBuilder.setDrawables(new Drawables());
//        uiBuilder.setContentArea();
//        uiBuilder.setPlayerUIInterface();
        uiBuilder.build();
//        try {
//            mediaPlayer = getMediaPlayer();
//
//            ui = UIFactory.createPlayerUI(this, type);
//
//            //Useful for parent class.
//            playerUIInterface = ui;
//
//            ui.play.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mediaPlayer.isPlaying()) {
//                        ui.doPause();
//                    } else {
//                        ui.doPlay();
//                    }
//                }
//            });
//
//            ui.skip.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        mediaPlayer.doSkip();
//                        ui.doSkip();
//                    } catch (PlaylistEmptyException playlistEmptyException) {
//                        preferencesHelper.handlePlaylistEmptyException(playlistEmptyException);
//                    }
//                }
//            });
//
//            ui.prev.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        mediaPlayer.doPrev();
//                        ui.doPrev();
//                    } catch (PlaylistEmptyException playlistEmptyException) {
//                        preferencesHelper.handlePlaylistEmptyException(playlistEmptyException);
//                    }
//                }
//            });
//
//            ui.shuffle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        mediaPlayer.doShuffle();
//                        ui.doShuffle();
//                    } catch (PlaylistEmptyException playlistEmptyException) {
//                        preferencesHelper.handlePlaylistEmptyException(playlistEmptyException);
//                    }
//                }
//            });
//
//            ui.playlist.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent playlistActivity = new Intent(getApplicationContext(), PlaylistActivity.class);
//                    startActivity(playlistActivity);
//                }
//            });
//            ui.reboot();
//
//            //Set up navigation drawer for selecting playlist.
//            buildNavigationDrawer();
//        } catch (NoMediaPlayerException noMediaPlayerException){
//            Log.d(BaseActivity.TAG, "NO MEDIA PLAYER FOUNDS NOW");
//            handleNoMediaPlayerException(noMediaPlayerException);
//        }
    }
}
