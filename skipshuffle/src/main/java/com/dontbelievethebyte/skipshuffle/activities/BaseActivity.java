package com.dontbelievethebyte.skipshuffle.activities;

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
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.util.MediaScannerDialog;
import com.dontbelievethebyte.skipshuffle.activities.util.NavDrawerClickListener;
import com.dontbelievethebyte.skipshuffle.callback.MediaBroadcastReceiverCallback;
import com.dontbelievethebyte.skipshuffle.callback.PreferenceChangedCallback;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.services.MediaPlayerBroadcastReceiver;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

import java.io.File;
import java.util.List;

public abstract class BaseActivity extends Activity implements MediaBroadcastReceiverCallback, PreferenceChangedCallback {

    protected static final String TAG = "SkipShuffle";
    protected static int REQUEST_PICK_FILE = 777;
    protected static final String IS_SCANNING_MEDIA = "IS_SCANNING_MEDIA";
    protected MediaScannerDialog mediaScannerDialog;
    protected BroadcastReceiver mediaScannerReceiver;
    protected PreferencesHelper preferencesHelper;
    protected String[] drawerMenuTitles;
    protected DrawerLayout drawerLayout;
    protected ListView drawerList;
    protected MediaPlayerBroadcastReceiver mediaPlayerBroadcastReceiver;

    abstract protected void setUI(Integer type);

    public MediaPlayerBroadcastReceiver getMediaPlayerBroadcastReceiver()
    {
        return mediaPlayerBroadcastReceiver;
    }

    public BroadcastReceiver getMediaScannerReceiver()
    {
        return mediaScannerReceiver;
    }

    public void setMediaScannerReceiver(BroadcastReceiver mediaScannerReceiver)
    {
        this.mediaScannerReceiver = mediaScannerReceiver;
    }

    protected View.OnTouchListener onTouchDownHapticFeedback = new View.OnTouchListener()
    {
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set up preferences
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        preferencesHelper.registerPrefsChangedListener();

        Log.d(TAG, "FIRST CALLED.");

        //Is mandatory to get the current state of the player.
        mediaPlayerBroadcastReceiver = new MediaPlayerBroadcastReceiver(getApplicationContext());

        registerReceiver(mediaPlayerBroadcastReceiver, new IntentFilter(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE));

        //Make sure we adjust the volume of the media player and not something else
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        MenuInflater menuInflater = getMenuInflater();
        if(vibrator.hasVibrator()){
            menuInflater.inflate(R.menu.main, menu);
        } else {
            menuInflater.inflate(R.menu.main_no_vibrator, menu);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK && requestCode == REQUEST_PICK_FILE) {
            if (data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
                // Get the file path
                List<File> filePickerActivityResult = (List<File>) data.getSerializableExtra(FilePickerActivity.EXTRA_FILE_PATH);
                if (!filePickerActivityResult.isEmpty()) {
                    //Check this shit.
                    String[] mediaDirectoriesToScan = new String[filePickerActivityResult.size()];
                    int i = 0;
                    //Save to a class instance array in case the activity needs to restart.
                    for (File directory : filePickerActivityResult){
                        mediaDirectoriesToScan[i] = directory.getAbsolutePath();
                        i++;
                    }
                    Log.d(TAG, "DIR : " + mediaDirectoriesToScan[0]);
                    preferencesHelper.setMediaDirectories(mediaDirectoriesToScan);
                }
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //@TODO CHECK FROM SERVICE IF UI STILL EXISTS.
        //stopService(new Intent(getApplicationContext(), SkipShuffleMediaPlayer.class));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        //Check if we're scanning media beforehand and.
        if(savedInstanceState.getBoolean(IS_SCANNING_MEDIA)){
//            mediaScannerDialog = new MediaScannerDialog(
//                    this,
//                    new ProgressDialog(BaseActivity.this)
//            );
//            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            Log.d(TAG, "media directories to scan wasn't NULL");
        }
    }

    @Override
    protected void onPause()
    {
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia()) {
            mediaScannerDialog.dismiss();
            mediaScannerDialog.unregisterMediaScannerBroadcastReceiver();
        }
        super.onPause();
    }

    @Override
    protected void onStop()
    {
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
    protected void onResume()
    {
        super.onResume();
        if(mediaScannerDialog != null && mediaScannerDialog.isScanningMedia()) {
            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            mediaScannerDialog.show();
        }
        registerReceiver(mediaPlayerBroadcastReceiver, new IntentFilter(SkipShuflleMediaPlayerCommandsContract.CMD_GET_PLAYER_STATE));
        preferencesHelper.registerPrefsChangedListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putBoolean(IS_SCANNING_MEDIA, true);
        if (mediaScannerDialog != null && mediaScannerDialog.isScanningMedia()) {
            mediaScannerDialog.dismiss();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.refresh_media:
                if (null == preferencesHelper.getMediaDirectories()) {
                    pickMediaDirectories();
                } else {
                    if (null == mediaScannerDialog) {
//                        mediaScannerDialog = new MediaScannerDialog(
//                                this,
//                                new ProgressDialog(this)
//                        );
                    }
                    mediaScannerDialog.doScan();
                }
                return true;
            case R.id.set_target_directories:
                pickMediaDirectories();
                return true;
            case R.id.haptic_feedback_toggle:
                preferencesHelper.hapticFeedbackToggle();
            case R.id.theme:
                showThemeSelectionDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void showThemeSelectionDialog()
    {
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
                                break;
                            case UIFactory.MONO_DARK:
                                preferencesHelper.setUIType(UIFactory.MONO_DARK);
                                break;
                            default: //Equivalent to UIFactory.NEON
                                preferencesHelper.setUIType(UIFactory.NEON);
                                break;
                        }
                        dialogInterface.dismiss();
                    }
                }
        );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void setUpDrawer()
    {
        drawerMenuTitles = getResources().getStringArray(R.array.drawer_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer1);
        drawerList.setAdapter(new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        drawerMenuTitles
                )
        );
        drawerList.setOnItemClickListener(new NavDrawerClickListener(this));
    }

    protected void pickMediaDirectories()
    {
        Intent intent = new Intent(BaseActivity.this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.EXTRA_SELECT_MULTIPLE, true);
        intent.putExtra(FilePickerActivity.EXTRA_SELECT_DIRECTORIES_ONLY, true);
        intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
        BaseActivity.this.startActivityForResult(intent, REQUEST_PICK_FILE);
        Toast.makeText(getApplicationContext(), R.string.media_scan_sel_target_directories, Toast.LENGTH_LONG).show();
    }

    public void preferenceChangedCallback(String prefsKey)
    {
        if (prefsKey.equals(getString(R.string.pref_media_directories))) {
            Log.d(TAG, "PREF DIRECTORY CHANGED!!@!#!!#%%%$$$");
            mediaScannerDialog = new MediaScannerDialog(
                    this,
                    new ProgressDialog(BaseActivity.this)
            );
            mediaScannerDialog.doScan();
        }
    }
}
