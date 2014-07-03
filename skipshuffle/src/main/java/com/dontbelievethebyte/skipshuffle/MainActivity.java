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
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class MainActivity extends Activity {

    private boolean isPlaylistSet = false;
    private Integer playState = 0;

    private UI ui;

    private MediaScannerDialog mediaScannerDialog;
    private BroadcastReceiver mediaScannerReceiver ;

    private static final String TAG = "SkipShuffleMain";
    private static final String IS_SCANNING_MEDIA = "isScanningMedia";
    private static final String PLAY_STATE = "playState";
    private static final Integer PLAYING = 1;
    private static final Integer PAUSED = -1;

    protected static int REQUEST_PICK_FILE = 777;

    private class UI {
        public ImageButton playlistBtn = (ImageButton) findViewById(R.id.playlistBtn);
        public ImageButton prevBtn = (ImageButton) findViewById(R.id.prevBtn);
        public ImageButton playBtn = (ImageButton) findViewById(R.id.playBtn);
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

    private class MediaScannerDialog {

        private ProgressDialog pd;

        public boolean isScanningMedia = false;

        private boolean isDialogShowing = false;

        MediaScannerDialog(ProgressDialog progressDialog) {
            pd = progressDialog;
            pd.setTitle("Scanning audio files... ");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
        }
        public void show() {
            if(!isDialogShowing){
                pd.show();
            }
            isDialogShowing = true;
        }

        public void setMessage(String message) {
            pd.setMessage(message);
        }
        private void setTitle(String title) {
            pd.setTitle(getString(R.string.media_scan_dialog_title, title));
        }
        public void dismiss() {
            if(isDialogShowing){
                pd.dismiss();
            }
            isDialogShowing = false;
        }

        public void registerMediaScannerBroadcastReceiver() {
            if (null == mediaScannerReceiver) {
                mediaScannerReceiver=  new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String currentDirectory = intent.getStringExtra(BroadcastMessageInterface.CURRENT_DIRECTORY_PROCESSING);
                        String currentFile = intent.getStringExtra(BroadcastMessageInterface.CURRENT_FILE_PROCESSING);
                        boolean isLast = intent.getBooleanExtra(BroadcastMessageInterface.IS_LAST_FILE_PROCESSING, false);
                        mediaScannerDialog.show();
                        mediaScannerDialog.setTitle(currentDirectory);
                        mediaScannerDialog.setMessage(currentFile);
                        if(isLast){
                            mediaScannerDialog.dismiss();
                            unregisterMediaScannerBroadcastReceiver();
                        }
                    }
                };
            }
            LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(mediaScannerReceiver, new IntentFilter(BroadcastMessageInterface.CURRENT_FILE_PROCESSING));
        }
        public void unregisterMediaScannerBroadcastReceiver() {
            if(mediaScannerReceiver != null){
                LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(mediaScannerReceiver);
                mediaScannerReceiver = null;
            }
            mediaScannerDialog.dismiss();
            isScanningMedia = false;
        }

        public void doScan(String[] directories){
            registerMediaScannerBroadcastReceiver();
            Intent mediaScannerIntent = new Intent(MainActivity.this, SkipShuffleMediaScannerService.class);
            mediaScannerIntent.putExtra(BroadcastMessageInterface.DIRECTORIES_LIST, directories);
            startService(mediaScannerIntent);
            isScanningMedia = true;
        }
        protected void pickMediaDirectories() {
            Intent intent = new Intent(getApplicationContext(), FilePickerActivity.class);
            intent.putExtra(FilePickerActivity.EXTRA_SELECT_MULTIPLE, true);
            intent.putExtra(FilePickerActivity.EXTRA_SELECT_DIRECTORIES_ONLY, true);
            intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
            startActivityForResult(intent, REQUEST_PICK_FILE);
            Toast.makeText(getApplicationContext(), R.string.sel_target_directories, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case 777:
                    if(data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
                        // Get the file path
                        List<File> filePickerActivityResult = (List<File>)data.getSerializableExtra(FilePickerActivity.EXTRA_FILE_PATH);
                        if(filePickerActivityResult.isEmpty()){
                            Toast.makeText(getApplicationContext(), R.string.no_directory, Toast.LENGTH_LONG).show();
                        } else {
                            String[] mediaDirectoriesToScan= new String[filePickerActivityResult.size()];
                            int i = 0;
                            //Save to a class instance array in case the activity needs to restart.
                            for (Iterator<File> iterator = filePickerActivityResult.iterator(); iterator.hasNext(); ) {
                                File directory = iterator.next();
                                mediaDirectoriesToScan[i] = directory.getAbsolutePath();
                                i++;
                            }
                            if(null == mediaScannerDialog) {
                                mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(MainActivity.this));
                            }
                            mediaScannerDialog.doScan(mediaDirectoriesToScan);
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
                Intent playlistActivity = new Intent(getApplicationContext(), PlaylistActivity.class);
                startActivity(playlistActivity);
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
    protected void onPause(){
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.dismiss();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.show();
        }
        ui.reboot();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        playState = savedInstanceState.getInt(PLAY_STATE);
        boolean onGoingScanState = savedInstanceState.getBoolean(IS_SCANNING_MEDIA);
        if(onGoingScanState){
            mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(MainActivity.this));
            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            Log.d(TAG, "media directories to scan wasn't NULL");
        }
        ui.reboot();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(PLAY_STATE, playState);
        outState.putBoolean(IS_SCANNING_MEDIA, true);
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.dismiss();
        }
        super.onSaveInstanceState(outState);
    }

    private View.OnTouchListener onTouchDownHapticFeedback = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()){
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
//                if(isPlaylistSet == false) {
//                    if(null == mediaScannerDialog) {
//                        mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(MainActivity.this));
//                    }
//                    mediaScannerDialog.pickMediaDirectories();
//                    //Return true because we already handled the event and want to prevent bubbling.
//                    return true;
//                }
            }
            return false;
        }
    };
}
