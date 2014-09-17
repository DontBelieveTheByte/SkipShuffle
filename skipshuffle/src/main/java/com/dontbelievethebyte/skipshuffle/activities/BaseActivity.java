package com.dontbelievethebyte.skipshuffle.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.adapters.NavigationDrawerAdapter;
import com.dontbelievethebyte.skipshuffle.activities.util.MediaScannerDialog;
import com.dontbelievethebyte.skipshuffle.activities.util.NavDrawerClickListener;
import com.dontbelievethebyte.skipshuffle.callback.MediaBroadcastReceiverCallback;
import com.dontbelievethebyte.skipshuffle.callback.PreferenceChangedCallback;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.services.MediaPlayerBroadcastReceiver;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUIInterface;
import com.dontbelievethebyte.skipshuffle.ui.UITypes;

public abstract class BaseActivity extends ActionBarActivity implements MediaBroadcastReceiverCallback, PreferenceChangedCallback {

    protected static final String TAG = "SkipShuffle";
    protected static final String IS_SCANNING_MEDIA = "IS_SCANNING_MEDIA";
    protected MediaScannerDialog mediaScannerDialog;

    protected PreferencesHelper preferencesHelper;
    protected MediaPlayerBroadcastReceiver mediaPlayerBroadcastReceiver;
    protected PlayerUIInterface playerUIInterface;

    private static final int FILE_PICKER_REQUEST_CODE = 9002;
    private boolean isOptionsMenuOpen = false;

    abstract protected void setUI(Integer type);

    public MediaPlayerBroadcastReceiver getMediaPlayerBroadcastReceiver()
    {
        return mediaPlayerBroadcastReceiver;
    }

    protected View.OnTouchListener onTouchDownHapticFeedback = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()){
                if (preferencesHelper.isHapticFeedback()) {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                }
                if (mediaPlayerBroadcastReceiver == null || preferencesHelper.getLastPlaylist() == 0) {
                    pickMediaDirectories();
                    //Return true because we already handled the event and want to prevent bubbling.
                    return true;
                }
            }
            return false;
        }
    };

    public PreferencesHelper getPreferencesHelper()
    {
        return preferencesHelper;
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaScannerDialog = new MediaScannerDialog(
                    this,
                    new ProgressDialog(BaseActivity.this)
            );
            mediaScannerDialog.doScan();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set up preferences and listener but callback are implemented by child classes.
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        preferencesHelper.registerPrefsChangedListener();

        //Is mandatory to get the current state of the player.
        mediaPlayerBroadcastReceiver = new MediaPlayerBroadcastReceiver(getApplicationContext());

        //Make sure we adjust the volume of the media player and not something else
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onDestroy()
    {
        if (mediaScannerDialog != null && mediaScannerDialog.isScanningMedia()) {
            mediaScannerDialog.dismiss();
            mediaScannerDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        MenuInflater menuInflater = getMenuInflater();
        if (vibrator.hasVibrator()) {
            menuInflater.inflate(R.menu.main, menu);
        } else {
            menuInflater.inflate(R.menu.main_no_vibrator, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        //Check if we're scanning media beforehand and.
        if (savedInstanceState.getBoolean(IS_SCANNING_MEDIA)) {
            mediaScannerDialog = new MediaScannerDialog(
                    this,
                    new ProgressDialog(BaseActivity.this)
            );
            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            Log.d(TAG, "media directories to scan wasn't NULL");
        }
    }

    @Override
    protected void onPause()
    {
        if (mediaScannerDialog != null && mediaScannerDialog.isScanningMedia()) {
            mediaScannerDialog.unregisterMediaScannerBroadcastReceiver();
        }
        unregisterReceiver(mediaPlayerBroadcastReceiver);
        preferencesHelper.unRegisterPrefsChangedListener();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        if (mediaScannerDialog != null && mediaScannerDialog.isScanningMedia()) {
            mediaScannerDialog.registerMediaScannerBroadcastReceiver();
            mediaScannerDialog.show();
        }
        registerReceiver(
                mediaPlayerBroadcastReceiver,
                new IntentFilter(SkipShuflleMediaPlayerCommandsContract.CMD_GET_PLAYER_STATE)
        );
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
                    showMediaScanDialog();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && ViewConfiguration.get(this).hasPermanentMenuKey()) {
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar.isShowing()) {
                if (isOptionsMenuOpen) {
                    closeOptionsMenu();
                    isOptionsMenuOpen = false;
                    actionBar.hide();
                } else {
                    openOptionsMenu();
                    isOptionsMenuOpen = true;
                }
            } else {
                actionBar.show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void showThemeSelectionDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_theme_title));
        builder.setSingleChoiceItems(
                R.array.dialog_theme_items,
                preferencesHelper.getUIType(),
                null
        );
        builder.setPositiveButton(
                R.string.dialog_positive,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int indexPosition) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        switch (lw.getCheckedItemPosition()){
                            case UITypes.MONO_LIGHT:
                                preferencesHelper.setUIType(UITypes.MONO_LIGHT);
                                break;
                            case UITypes.MONO_DARK:
                                preferencesHelper.setUIType(UITypes.MONO_DARK);
                                break;
                            default: //Equivalent to UIFactory.NEON
                                preferencesHelper.setUIType(UITypes.NEON);
                                break;
                        }
                        dialog.dismiss();
                    }
                }
        );
        builder.setNegativeButton(
                R.string.dialog_negative,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int indexPosition) {
                        dialog.dismiss();
                    }
                }
        );
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void showMediaScanDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_media_scan_title));
        builder.setMessage(getString(R.string.dialog_media_scan_text));

        builder.setPositiveButton(
                R.string.dialog_positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        mediaScannerDialog = new MediaScannerDialog(
                                BaseActivity.this,
                                new ProgressDialog(BaseActivity.this)
                        );
                        mediaScannerDialog.doScan();
                    }
                }
        );

        builder.setNegativeButton(
                R.string.dialog_negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Do nothing
                        dialog.dismiss();
                    }
                }
        );

        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void setUpDrawer()
    {
        ListView drawerList = (ListView) findViewById(R.id.left_drawer1);

        drawerList.setAdapter(
            new NavigationDrawerAdapter(
                    this,
                    R.layout.drawer_list_item,
                    getResources().getStringArray(R.array.drawer_menu),
                    preferencesHelper,
                    playerUIInterface.getTypeFace()
            )
        );
        drawerList.setOnTouchListener(onTouchDownHapticFeedback);
        drawerList.setOnItemClickListener(
                new NavDrawerClickListener(
                        this,
                        (DrawerLayout) findViewById(R.id.drawer_layout)
                )
        );
    }

    protected void pickMediaDirectories()
    {
        if (!(this instanceof FilePickerActivity)) {
            Intent intent = new Intent(BaseActivity.this, FilePickerActivity.class);
            BaseActivity.this.startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.media_scan_sel_target_directories,
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void preferenceChangedCallback(String prefsKey)
    {
        if (getString(R.string.pref_current_ui_type).equals(prefsKey)) {
            setUI(preferencesHelper.getUIType());
        }
    }
}
