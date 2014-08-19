package com.dontbelievethebyte.skipshuffle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends BaseActivity implements MediaBroadcastReceiverCallback {

    private UI ui;

    private static final String TAG = "SkipShuffleMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up UI, this can be later instantiated user prefs to get an alternative UI.
        ui = UI.createUI(MainActivity.this);

        //Start the mediaPlayer service.
        startService(new Intent(getApplicationContext(), SkipShuffleMediaPlayer.class));

        //Is mandatory to get the current state of the player.
        mediaPlayerBroadcastReceiver = new MediaPlayerBroadcastReceiver(getApplicationContext());
        mediaPlayerBroadcastReceiver.registerCallback(this);
        registerReceiver(mediaPlayerBroadcastReceiver, new IntentFilter(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE));

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
    protected void onPause(){
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            mediaScannerDialog.show();
        }
        registerReceiver(mediaPlayerBroadcastReceiver, new IntentFilter(SkipShuflleMediaPlayerCommandsContract.CMD_GET_PLAYER_STATE));
        ui.reboot();
    }

    @Override
    public void mediaBroadcastReceiverCallback() {
        String state = mediaPlayerBroadcastReceiver.getPlayerState();
        if(state == SkipShuflleMediaPlayerCommandsContract.STATE_PLAY){
            ui.doPlay();
        } else {
            ui.doPause();
        }
        ui.setSongTitle("derpderperpep");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Check if we're scanning media beforehand and.
        if(savedInstanceState.getBoolean(IS_SCANNING_MEDIA)){
            mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(MainActivity.this));
            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            Log.d(TAG, "media directories to scan wasn't NULL");
        }
        ui.reboot();
    }


}
