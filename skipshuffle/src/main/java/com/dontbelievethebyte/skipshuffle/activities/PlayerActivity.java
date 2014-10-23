package com.dontbelievethebyte.skipshuffle.activities;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;

public class PlayerActivity extends BaseActivity {

    private PlayerUI ui;

    @Override
    protected void handleBackPressed()
    {

    }

    @Override
    protected int getViewStub() {
        return R.layout.media_player_footer;
    }

    @Override
    protected void onPause(){
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Register class specific callbacks.
//        ui.reboot();
    }

    @Override
    protected void setUI(Integer type)
    {
        super.setUI(type);


//        final SkipShuffleMediaPlayer mediaPlayer;
//        try {
//            mediaPlayer = mediaPlayerServiceConnection.getMediaPlayer();
//            ui = UIFactory.createMainUI(this, type);
//
//            //Useful for parent class.
//            playerUIInterface = ui;
//
//            ui.playBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mediaPlayer.getPlayerWrapper().isPlaying()) {
//                        ui.doPause();
//                    } else {
//                        ui.doPlay();
//                    }
//                }
//            });
//
//            ui.skipBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mediaPlayer.getPlayerWrapper().doSkip();
//                    ui.doSkip();
//                }
//            });
//
//            ui.prevBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mediaPlayer.getPlayerWrapper().doPrev();
//                    ui.doPrev();
//                }
//            });
//
//            ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mediaPlayer.getPlayerWrapper().doShuffle();
//                    ui.doShuffle();
//                }
//            });
//
//            ui.playlistBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent playlistActivity = new Intent(getApplicationContext(), PlaylistActivity.class);
//                    startActivity(playlistActivity);
//                }
//            });
//            ui.reboot();
//
//            //Set up navigation drawer for selecting playlist.
//            setNavigationDrawerContent();
//        } catch (NoMediaPlayerException noMediaPlayerException){
//            handleNoMediaPlayerException(noMediaPlayerException);
//        }
    }
}
