package com.dontbelievethebyte.skipshuffle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.ui.main.MainUI;
import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.services.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public class MainActivity extends BaseActivity {

    private MainUI ui;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Register class specific callback from MediaBroadcastReceiverCallback interface.
        mediaPlayerBroadcastReceiver.registerCallback(this);

        setUI(preferencesHelper.getUIType());

        //Start the mediaPlayer service.
        startService(
                new Intent(
                        getApplicationContext(),
                        SkipShuffleMediaPlayer.class
                )
        );

        Log.d(TAG, "RAN SECONDDDDD.");

        preferencesHelper.registerCallBack(this);

        //Set up navigation drawer for selecting playlist.
        setUpDrawer();
    }

    @Override
    protected void onPause(){
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
//        preferencesHelper.unRegisterPrefsChangedListener();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ui.reboot();
//        preferencesHelper.registerPrefsChangedListener();
//        preferencesHelper.registerCallBack(this);
    }

    @Override
    public void mediaBroadcastReceiverCallback() {
        String state = mediaPlayerBroadcastReceiver.getPlayerState();
        if (state.intern() == SkipShuflleMediaPlayerCommandsContract.STATE_PLAY) {
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
        ui = null;
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
                if (mediaPlayerBroadcastReceiver.getPlayerState() == SkipShuflleMediaPlayerCommandsContract.STATE_PLAY) {
                    ui.doPause();
                } else {
                    ui.doPlay();
                }
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE, null);
            }
        });

        ui.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_SKIP, null);
                ui.doSkip();
            }
        });

        ui.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PREV, null);
                ui.doPrev();
            }
        });

        ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST, null);
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
    }

    @Override
    public void preferenceChangedCallback(String prefsKey) {
        super.preferenceChangedCallback(prefsKey);
        if (prefsKey == getString(R.string.pref_current_ui_type)) {
            Log.d(TAG, "UI CHANGE CALLBACK LAUCHED");
            setUI(preferencesHelper.getUIType());
        }
    }
}
