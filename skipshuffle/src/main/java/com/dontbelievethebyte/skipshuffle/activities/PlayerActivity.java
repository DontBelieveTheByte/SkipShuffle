package com.dontbelievethebyte.skipshuffle.activities;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.BaseUI;
import com.dontbelievethebyte.skipshuffle.ui.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class PlayerActivity extends BaseActivity {

    @Override
    protected void onPause()
    {
        //Give a break to GPU when hidden
        ui.player.buttons.play.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ui.player.reboot();
    }

    @Override
    protected void handleBackPressed()
    {

    }

    @Override
    protected void setUI(Integer type)
    {
        CustomTypeface customTypeface = new CustomTypeface(this, type);
        BaseUI.UIBuilder uiBuilder = new BaseUI.UIBuilder();
        uiBuilder.setUiType(preferencesHelper.getUIType());
        uiBuilder.setActivity(this);
        uiBuilder.setContentArea(new ContentArea(R.layout.main_activity));
        uiBuilder.setPlayerUIInterface(new PlayerUI(this, customTypeface));
        uiBuilder.setNavigationDrawer(buildNavigationDrawer());
        uiBuilder.setColors(new Colors(type));
        uiBuilder.setDrawables(new Drawables(this, type));
        uiBuilder.setCustomTypeFace(customTypeface);
        ui = uiBuilder.build();
//        try {
//            mediaPlayer = getMediaPlayer();
//
//            ui = UIFactory.createPlayerUI(this, type);
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
