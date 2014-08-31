package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public abstract class BaseActivity extends Activity {

    protected static final String TAG = "SkipShuffle";

    protected static int REQUEST_PICK_FILE = 777;

    protected static final String IS_SCANNING_MEDIA = "IS_SCANNING_MEDIA";

    protected MediaScannerDialog mediaScannerDialog;

    protected BroadcastReceiver mediaScannerReceiver;

    protected PreferencesHelper preferencesHelper;

    protected String[] drawerMenuTitles;
    protected DrawerLayout drawerLayout;
    protected ListView drawerList;

    public MediaPlayerBroadcastReceiver mediaPlayerBroadcastReceiver;

    protected class MediaScannerDialog {

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
            LocalBroadcastManager.getInstance(BaseActivity.this).registerReceiver(mediaScannerReceiver, new IntentFilter(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING));
        }
        public void unregisterMediaScannerBroadcastReceiver() {
            if(mediaScannerReceiver != null){
                LocalBroadcastManager.getInstance(BaseActivity.this).unregisterReceiver(mediaScannerReceiver);
                mediaScannerReceiver = null;
            }
            mediaScannerDialog.dismiss();
            isScanningMedia = false;
        }

        public void doScan(){
            registerMediaScannerBroadcastReceiver();
            Intent mediaScannerIntent = new Intent(BaseActivity.this, MediaScannerService.class);
            startService(mediaScannerIntent);
            isScanningMedia = true;
        }

    };

    protected View.OnTouchListener onTouchDownHapticFeedback = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()){
                if(preferencesHelper.isHapticFeedback()){
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                }
                if(mediaPlayerBroadcastReceiver == null || preferencesHelper.getLastPlaylist() == 0) {
                    pickMediaDirectories();
                    //Return true because we already handled the event and want to prevent bubbling.
                    return true;
                }
            }
            return false;
        }
    };

    abstract protected void setUI(Integer type);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up preferences
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        preferencesHelper.registerPrefsChangedListener();

        //Is mandatory to get the current state of the player.
        mediaPlayerBroadcastReceiver = new MediaPlayerBroadcastReceiver(getApplicationContext());

        registerReceiver(mediaPlayerBroadcastReceiver, new IntentFilter(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE));

        //Make sure we adjust the volume of the media player and not something else
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_PICK_FILE) {
            if (data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
                // Get the file path
                List<File> filePickerActivityResult = (List<File>) data.getSerializableExtra(FilePickerActivity.EXTRA_FILE_PATH);
                if (!filePickerActivityResult.isEmpty()) {
                    //Check this shit.
                    String[] mediaDirectoriesToScan = new String[filePickerActivityResult.size()];
                    int i = 0;
                    //Save to a class instance array in case the activity needs to restart.
                    for (Iterator<File> iterator = filePickerActivityResult.iterator(); iterator.hasNext(); ) {
                        File directory = iterator.next();
                        mediaDirectoriesToScan[i] = directory.getAbsolutePath();
                        i++;
                    }
                    preferencesHelper.setMediaDirectories(mediaDirectoriesToScan);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(), SkipShuffleMediaPlayer.class));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Check if we're scanning media beforehand and.
        if(savedInstanceState.getBoolean(IS_SCANNING_MEDIA)){
            mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(BaseActivity.this));
            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            Log.d(TAG, "media directories to scan wasn't NULL");
        }
    }

    @Override
    protected void onPause(){
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.dismiss();
            mediaScannerDialog.unregisterMediaScannerBroadcastReceiver();
        }
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();

        //There's no 1:1 ratio of onCreate/onDestroy, correct place unregister receivers is here.
        preferencesHelper.unRegisterPrefsChangedListener();
        unregisterReceiver(mediaPlayerBroadcastReceiver);

        if(mediaScannerDialog != null){
            mediaScannerDialog.unregisterMediaScannerBroadcastReceiver();
            mediaScannerDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            mediaScannerDialog.show();
        }
        registerReceiver(mediaPlayerBroadcastReceiver, new IntentFilter(SkipShuflleMediaPlayerCommandsContract.CMD_GET_PLAYER_STATE));
        preferencesHelper.registerPrefsChangedListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(IS_SCANNING_MEDIA, true);
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia) {
            mediaScannerDialog.dismiss();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_media:
                if(null == preferencesHelper.getMediaDirectories()){
                    pickMediaDirectories();
                } else {
                    if(null == mediaScannerDialog){
                        mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(BaseActivity.this));
                    }
                    mediaScannerDialog.doScan();
                }
                return true;
            case R.id.set_target_directories:
                if(null == mediaScannerDialog){
                    mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(BaseActivity.this));
                }
                pickMediaDirectories();
                return true;
            case R.id.haptic_feedback_toggle:
                preferencesHelper.hapticFeedbackToggle();
                return true;
            case R.id.theme:
                showThemeSelectionDialog();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void showThemeSelectionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setSingleChoiceItems(
                R.array.dialog_theme_items,
                preferencesHelper.getUIType(),
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int indexPosition) {
                        switch (indexPosition){
                            case UIFactory.MONO_LIGHT:
                                preferencesHelper.setUIType(UIFactory.MONO_LIGHT);
                                setUI(UIFactory.MONO_LIGHT);
                                return;
                            case UIFactory.MONO_DARK:
                                setUI(UIFactory.MONO_LIGHT);
                                preferencesHelper.setUIType(UIFactory.MONO_DARK);
                                return;
                            case UIFactory.NEON:
                                setUI(UIFactory.MONO_LIGHT);
                                preferencesHelper.setUIType(UIFactory.NEON);
                                return;
                        }
                    }
                }
        );
        builder.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int indexPosition) {
                switch (indexPosition){
                    case UIFactory.MONO_LIGHT:
                        preferencesHelper.setUIType(UIFactory.MONO_LIGHT);
                        setUI(UIFactory.MONO_LIGHT);
                        return;
                    case UIFactory.MONO_DARK:
                        setUI(UIFactory.MONO_LIGHT);
                        preferencesHelper.setUIType(UIFactory.MONO_DARK);
                        return;
                    case UIFactory.NEON:
                        setUI(UIFactory.MONO_LIGHT);
                        preferencesHelper.setUIType(UIFactory.NEON);
                        return;
                }
            }
        } );

        builder.setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int indexPosition) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    protected class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {

        }
    }

    protected void setUpDrawer(){
        drawerMenuTitles = getResources().getStringArray(R.array.drawer_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer1);
        drawerList.setAdapter(new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        drawerMenuTitles
                )
        );
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    protected void pickMediaDirectories() {
        Intent intent = new Intent(BaseActivity.this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.EXTRA_SELECT_MULTIPLE, true);
        intent.putExtra(FilePickerActivity.EXTRA_SELECT_DIRECTORIES_ONLY, true);
        intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
        BaseActivity.this.startActivityForResult(intent, REQUEST_PICK_FILE);
        Toast.makeText(getApplicationContext(), R.string.media_scan_sel_target_directories, Toast.LENGTH_LONG).show();
    }

    public void preferenceChangedCallback(String prefsKey) {
        if(prefsKey == getString(R.string.pref_media_directories)){
            mediaScannerDialog = new MediaScannerDialog(new ProgressDialog(BaseActivity.this));
            mediaScannerDialog.doScan();
        }
    }
}
