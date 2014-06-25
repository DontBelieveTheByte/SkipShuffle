package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.coderplus.filepicker.FilePickerActivity;

import java.io.File;
import java.util.List;
import java.util.Iterator;

public class MainActivity extends Activity {

    private boolean isPlaylistSet = false;
    private Integer playState = 0;

    private List<File> mediaDirectoriesToScan ;

    private UI ui;


    private static final String TAG = "SkipShuffleMain";
    private static final String PLAY_STATE = "playState";
    private static final Integer PLAYING = 1;
    private static final Integer PAUSED = -1;

    protected static int REQUEST_PICK_FILE = 777;

    private class UI {
        public ImageButton playlistBtn = (ImageButton) findViewById(R.id.playBtn);
        public ImageButton prevBtn = (ImageButton) findViewById(R.id.prevBtn);
        public ImageButton playBtn = (ImageButton) findViewById(R.id.playlistBtn);
        public ImageButton shuffleBtn = (ImageButton) findViewById(R.id.shuffleBtn);
        public ImageButton skipBtn = (ImageButton) findViewById(R.id.skipBtn);

        public Animation ltr = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ltr);
        public Animation flipRightAnimation  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_right);
        public Animation flipDownAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_down);
        public Animation flipLeftAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_left);
        public Animation blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);

        public void doPlay() {
            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
            playBtn.startAnimation(ltr);
        }

        public void doPause() {
            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
            playBtn.startAnimation(blinkAnimation);
        }

        private void doSkip() {
            playBtn.clearAnimation();
            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
            playBtn.startAnimation(blinkAnimation);
            skipBtn.startAnimation(flipRightAnimation);
            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
            playBtn.startAnimation(ltr);
        }

        private void doPrev() {
            playBtn.clearAnimation();
            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
            playBtn.startAnimation(blinkAnimation);
            prevBtn.startAnimation(flipLeftAnimation);
            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
            playBtn.startAnimation(ltr);
        }

        private void doShuffle() {
            playBtn.clearAnimation();
            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
            playBtn.startAnimation(blinkAnimation);
            shuffleBtn.startAnimation(flipDownAnimation);
            playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
            playBtn.startAnimation(ltr);
        }

        private void reboot(){
            if(PLAYING == playState) {
                doPlay();
            } else if (PAUSED == playState) {
                doPause();
            }
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case 777:
                    if(data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
                        // Get the file path
                        mediaDirectoriesToScan = (List<File>)data.getSerializableExtra(FilePickerActivity.EXTRA_FILE_PATH);
                        if(mediaDirectoriesToScan.isEmpty()){
                            Toast.makeText(getApplicationContext(), R.string.no_directory, Toast.LENGTH_LONG).show();
                        } else {
                            registerMediaScannerBroadcastReceiver();
                            MediaScannerDialog mediaScannerDialog = new MediaScannerDialog();
                            for (Iterator<File> iterator = mediaDirectoriesToScan.iterator(); iterator.hasNext(); ) {
                                File directory = iterator.next();
                                Intent mediaScannerIntent = new Intent(this, SkipShuffleMediaScannerService.class);
                                mediaScannerIntent.putExtra(BroadcastMessageInterface.DIRECTORIES_LIST, directory.getAbsolutePath());
                                startService(mediaScannerIntent);
                            }
                        }
                    }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up UI, this can be later instantiated from a factory to produce a different UI.
        ui = new UI();

        //Start the mediaPlayer service.

        //startService(new Intent(getApplicationContext(), SkipShuffleMediaPlayer.class));

        //Register haptic feedback for all buttons.
        ui.playBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.skipBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.playlistBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.prevBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.shuffleBtn.setOnTouchListener(onTouchDownHapticFeedback);


        ui.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastMessage;

                if (playState == PLAYING) {
                    ui.doPause();
                    toastMessage = getResources().getString(R.string.pause);
                    playState = PAUSED;

                } else {
                    ui.doPlay();
                    toastMessage = getResources().getString(R.string.play);
                    playState = PLAYING;
                }

                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();

            }
        });

        ui.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ui.doSkip();
                Toast.makeText(getApplicationContext(), R.string.skip, Toast.LENGTH_SHORT).show();
            }
        });

        ui.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ui.doPrev();
                Toast.makeText(getApplicationContext(), R.string.prev, Toast.LENGTH_LONG).show();
            }
        });

        ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ui.doShuffle();
                Toast.makeText(getApplicationContext(), R.string.shuffle, Toast.LENGTH_LONG).show();
            }
        });

        ui.playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickMediaDirectories();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Stop the mediaPlayer service.

        stopService(new Intent(getApplicationContext(), SkipShuffleMediaPlayer.class));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        playState = savedInstanceState.getInt(PLAY_STATE);
        ui.reboot();
    }

    @Override
    protected void onPause(){
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
        unregisterMediaScannerBroadcastReceiver();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaDirectoriesToScan != null) {
            registerMediaScannerBroadcastReceiver();
        }
        ui.reboot();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(PLAY_STATE, playState);
        if(mediaDirectoriesToScan != null) {
            registerMediaScannerBroadcastReceiver();
        }
        super.onSaveInstanceState(outState);
    }

    protected void pickMediaDirectories() {
        Intent intent = new Intent(this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.EXTRA_SELECT_MULTIPLE, true);
        intent.putExtra(FilePickerActivity.EXTRA_SELECT_DIRECTORIES_ONLY, true);
        intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
        startActivityForResult(intent, REQUEST_PICK_FILE);
        Toast.makeText(getApplicationContext(), R.string.sel_target_directories, Toast.LENGTH_LONG).show();
    }

    private View.OnTouchListener onTouchDownHapticFeedback = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()){
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                if(isPlaylistSet == false) {
                    pickMediaDirectories();
                    //Return true because we already handled the event and want to prevent bubbling.
                    return true;
                }
            }
            return false;
        }
    };

    public void registerMediaScannerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mediaScannerReceiver, new IntentFilter(BroadcastMessageInterface.CURRENT_FILE_PROCESSING));
    }
    public void unregisterMediaScannerBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mediaScannerReceiver);
    }

    private BroadcastReceiver mediaScannerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String currentFile = intent.getStringExtra(BroadcastMessageInterface.CURRENT_FILE_PROCESSING);
            Log.d(TAG, "Currently processing : " + currentFile);
        }
    };

    private class MediaScannerDialog {

        private ProgressDialog pd = new ProgressDialog(MainActivity.this);

        MediaScannerDialog() {

                    pd.setTitle("Scanning audio files... ");
                    pd.setMessage("Please wait.");
                    pd.setCancelable(false);
                    pd.setIndeterminate(true);

                isPlaylistSet = true;
        }
        public void show() {
            pd.show();
        }

        public void setMessage(String message) {
            pd.setMessage(message);
        }
        private void setTitle(String title) {
            pd.setTitle(getString(R.string.media_scan_dialog_title, title));
        }
        public void dismiss() {
            pd.dismiss();
        }
    };
}
