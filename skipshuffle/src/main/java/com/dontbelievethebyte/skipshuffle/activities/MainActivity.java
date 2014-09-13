package com.dontbelievethebyte.skipshuffle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.services.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;
import com.dontbelievethebyte.skipshuffle.ui.MainUI;

public class MainActivity extends BaseActivity {

    private MainUI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setUI(preferencesHelper.getUIType());

        //Start the mediaPlayer service.
        startService(
                new Intent(
                        getApplicationContext(),
                        SkipShuffleMediaPlayer.class
                )
        );

        //Set up navigation drawer for selecting playlist.
        setUpDrawer();
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
        preferencesHelper.registerCallBack(this);
        mediaPlayerBroadcastReceiver.registerCallback(this);
        ui.reboot();
    }

    @Override
    public void mediaBroadcastReceiverCallback() {
        ui.reboot();
    }

    @Override
    protected void setUI(Integer type) {
        ui = UIFactory.createMainUI(this, type);
        //Register haptic feedback for all buttons.
        ui.playBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.skipBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.playlistBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.prevBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.shuffleBtn.setOnTouchListener(onTouchDownHapticFeedback);

        ui.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SkipShuflleMediaPlayerCommandsContract.STATE_PLAY.equals(mediaPlayerBroadcastReceiver.getPlayerState())) {
                    ui.doPause();
                } else {
                    ui.doPlay();
                }
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE,
                        null
                );
            }
        });

        ui.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SKIP,
                        null
                );
                ui.doSkip();
            }
        });

        ui.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PREV,
                        null
                );
                ui.doPrev();
            }
        });

        ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST,
                        null
                );
                ui.doShuffle();
            }
        });

        ui.playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playlistActivity = new Intent(getApplicationContext(), PlaylistActivity.class);
                startActivity(playlistActivity);
            }
        });
        ui.reboot();
        setUpDrawer();
    }

}
