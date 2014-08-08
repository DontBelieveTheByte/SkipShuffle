package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.coderplus.filepicker.FilePickerActivity;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements Callback{

    private UI ui;
    private PreferencesHelper preferencesHelper;

    private MediaScannerDialog mediaScannerDialog;
    private BroadcastReceiver mediaScannerReceiver;
    public MediaPlayerBroadcastReceiver mediaPlayerBroadcastReceiver;

    private static final String TAG = "SkipShuffleMain";

    private static final String IS_SCANNING_MEDIA = "IS_SCANNING_MEDIA";

    protected static int REQUEST_PICK_FILE = 777;



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
                        String currentDirectory = intent.getStringExtra(MediaScannerBroadcastMessageContract.CURRENT_DIRECTORY_PROCESSING);
                        String currentFile = intent.getStringExtra(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING);
                        boolean isLast = intent.getBooleanExtra(MediaScannerBroadcastMessageContract.IS_LAST_FILE_PROCESSING, false);
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
            LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(mediaScannerReceiver, new IntentFilter(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING));
        }
        public void unregisterMediaScannerBroadcastReceiver() {
            if(mediaScannerReceiver != null){
                LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(mediaScannerReceiver);
                mediaScannerReceiver = null;
            }
            mediaScannerDialog.dismiss();
            isScanningMedia = false;
        }

        public void doScan(){
            registerMediaScannerBroadcastReceiver();
            Intent mediaScannerIntent = new Intent(MainActivity.this, MediaScannerService.class);
            startService(mediaScannerIntent);
            isScanningMedia = true;
        }
        protected void pickMediaDirectories() {
            Intent intent = new Intent(MainActivity.this, FilePickerActivity.class);
            intent.putExtra(FilePickerActivity.EXTRA_SELECT_MULTIPLE, true);
            intent.putExtra(FilePickerActivity.EXTRA_SELECT_DIRECTORIES_ONLY, true);
            intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
            Toast.makeText(getApplicationContext(), R.string.sel_target_directories, Toast.LENGTH_LONG).show();
            MainActivity.this.startActivityForResult(intent, REQUEST_PICK_FILE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_PICK_FILE) {
            if (data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
                // Get the file path
                List<File> filePickerActivityResult = (List<File>) data.getSerializableExtra(FilePickerActivity.EXTRA_FILE_PATH);
                if (filePickerActivityResult.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.no_directory, Toast.LENGTH_LONG).show();
                } else {
                    //Check this shit.
                    String[] mediaDirectoriesToScan = new String[filePickerActivityResult.size()];
                    int i = 0;
                    //Save to a class instance array in case the activity needs to restart.
                    for (Iterator<File> iterator = filePickerActivityResult.iterator(); iterator.hasNext(); ) {
                        File directory = iterator.next();
                        mediaDirectoriesToScan[i] = directory.getAbsolutePath();
                        i++;
                    }
                    if (null == mediaScannerDialog) {
                        mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(MainActivity.this));
                    }
                    preferencesHelper.setMediaDirectories(mediaDirectoriesToScan);
                    mediaScannerDialog.doScan();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up UI, this can be later instantiated user prefs to get an alternative UI.
        ui = UI.createUI(MainActivity.this);

        //Set up preferences
        preferencesHelper = new PreferencesHelper(getApplicationContext());

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
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(), SkipShuffleMediaPlayer.class));
        if(mediaScannerDialog != null){
            mediaScannerDialog.unregisterMediaScannerBroadcastReceiver();
            mediaScannerDialog.dismiss();
        }
        unregisterReceiver(mediaPlayerBroadcastReceiver);
    }

    @Override
    protected void onPause(){
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.dismiss();
            mediaScannerDialog.unregisterMediaScannerBroadcastReceiver();
        }
        unregisterReceiver(mediaPlayerBroadcastReceiver);
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
    public void callback() {
        String state = mediaPlayerBroadcastReceiver.getPlayerState();
        if(state == SkipShuflleMediaPlayerCommandsContract.STATE_PLAY){
            ui.doPlay();
        } else {
            ui.doPause();
        }
    }

    public View.OnTouchListener onTouchDownHapticFeedback = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()){
                if(preferencesHelper.isHapticFeedback()){
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                }
                if(mediaPlayerBroadcastReceiver == null || preferencesHelper.getCurrentPlaylist() == 0) {
                    if(null == mediaScannerDialog) {
                        mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(MainActivity.this));
                    }
                    mediaScannerDialog.pickMediaDirectories();
                    //Return true because we already handled the event and want to prevent bubbling.
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_media:
                if(null == mediaScannerDialog){
                    mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(MainActivity.this));
                }
                if(null == preferencesHelper.getMediaDirectories()){
                    mediaScannerDialog.pickMediaDirectories();
                } else {
                    mediaScannerDialog.doScan();
                }
                return true;
            case R.id.set_target_directories:
                if(null == mediaScannerDialog){
                    mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(MainActivity.this));
                }
                mediaScannerDialog.pickMediaDirectories();
                return true;
            case R.id.haptic_feedback_toggle:
                preferencesHelper.hapticFeedbackToggle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_SCANNING_MEDIA, true);
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.dismiss();
        }
        super.onSaveInstanceState(outState);
    }
}
