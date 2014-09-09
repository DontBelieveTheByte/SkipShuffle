package com.dontbelievethebyte.skipshuffle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.services.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;
import com.dontbelievethebyte.skipshuffle.ui.main.MainUI;

public class MainActivity extends BaseActivity {

    private MainUI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setUI(preferencesHelper.getUIType());

        //Register class specific callbacks.
        mediaPlayerBroadcastReceiver.registerCallback(this);
        preferencesHelper.registerCallBack(this);

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
        ui.reboot();
        preferencesHelper.registerCallBack(this);
        mediaPlayerBroadcastReceiver.registerCallback(this);
    }

    @Override
    public void mediaBroadcastReceiverCallback() {
        String state = mediaPlayerBroadcastReceiver.getPlayerState();
        if (SkipShuflleMediaPlayerCommandsContract.STATE_PLAY.equals(state)) {
            ui.doPlay();
        } else {
            ui.doPause();
        }
        ui.setSongTitle(mediaPlayerBroadcastReceiver.getCurrentSongTitle());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
    }

    @Override
    public void preferenceChangedCallback(String prefsKey) {
        super.preferenceChangedCallback(prefsKey);
        if (getString(R.string.pref_current_ui_type).equals(prefsKey)) {
            setUI(preferencesHelper.getUIType());
        }
    }
}