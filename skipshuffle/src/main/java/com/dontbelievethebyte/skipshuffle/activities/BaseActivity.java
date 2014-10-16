package com.dontbelievethebyte.skipshuffle.activities;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.actionbar.CustomActionBarWrapper;
import com.dontbelievethebyte.skipshuffle.adapters.NavigationDrawerAdapter;
import com.dontbelievethebyte.skipshuffle.callbacks.HapticFeedBackChangedCallback;
import com.dontbelievethebyte.skipshuffle.callbacks.ThemeChangedCallback;
import com.dontbelievethebyte.skipshuffle.dialog.ThemeSelectionDialog;
import com.dontbelievethebyte.skipshuffle.exceptions.NoHardwareMenuKeyException;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.listeners.NavDrawerClickListener;
import com.dontbelievethebyte.skipshuffle.listeners.TouchHandler;
import com.dontbelievethebyte.skipshuffle.menu.OptionsMenuCreator;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.services.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUIInterface;
import com.dontbelievethebyte.skipshuffle.utilities.MediaScannerDialog;
import com.dontbelievethebyte.skipshuffle.utilities.ToastHelper;

import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;

public abstract class BaseActivity extends ActionBarActivity
        implements ThemeChangedCallback, HapticFeedBackChangedCallback, View.OnTouchListener {

    public static final String TAG = "SkipShuffle";

    protected static final String IS_SCANNING_MEDIA = "IS_SCANNING_MEDIA";
    protected ListView.OnItemClickListener navDrawerItemClickListener;
    protected ArrayAdapter<?> navDrawerListAdapter;
    protected MediaScannerDialog mediaScannerDialog;
    protected PlayerUIInterface playerUIInterface;
    protected PreferencesHelper preferencesHelper;
    protected CustomActionBarWrapper customActionBar;
    protected boolean isBoundToMediaPlayer;

    public SkipShuffleMediaPlayer getMediaPlayer() throws NoMediaPlayerException
    {
        if (null == mediaPlayer)
            throw new NoMediaPlayerException();
        else
            return mediaPlayer;
    }

    protected SkipShuffleMediaPlayer mediaPlayer;

    protected ToastHelper toastHelper;
    private static final int FILE_PICKER_REQUEST_CODE = 9002;

    private boolean isOptionsMenuOpen = false;
    protected abstract void setUI(Integer type);
    protected abstract void handleBackPressed();

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mediaPlayerServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service)
        {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            SkipShuffleMediaPlayer.MediaPlayerBinder binder = (SkipShuffleMediaPlayer.MediaPlayerBinder) service;
            mediaPlayer = binder.getService();
            isBoundToMediaPlayer = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            isBoundToMediaPlayer = false;
        }
    };

    public PreferencesHelper getPreferencesHelper()
    {
        return preferencesHelper;
    }

    public ToastHelper getToastHelper()
    {
        return toastHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        startMediaPlayerService();

        //Make sure we adjust the volume of the media player and not something else
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        setUpActionBar();

        toastHelper = new ToastHelper(getApplicationContext());
    }

    private void startMediaPlayerService()
    {
        startService(
                new Intent(
                        getApplicationContext(),
                        SkipShuffleMediaPlayer.class
                )
        );
    }

    protected void setUpActionBar()
    {
        customActionBar = new CustomActionBarWrapper(this);
        customActionBar.setUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        OptionsMenuCreator optionsMenuCreator = new OptionsMenuCreator(this);
        optionsMenuCreator.buildOptionsMenuFromContext(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        //Check if we're scanning media beforehand and.
        if (savedInstanceState.getBoolean(IS_SCANNING_MEDIA)) {
            mediaScannerDialog = new MediaScannerDialog(this);
            mediaScannerDialog.registerBroadcastReceiver();
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaScannerDialog = new MediaScannerDialog(this);
            mediaScannerDialog.registerBroadcastReceiver();
            mediaScannerDialog.doScan();
        }
    }

    @Override
    public void onBackPressed()
    {
        try {
            if (!customActionBar.isShowing())
                handleBackPressed();

            customActionBar.showToggle();
        } catch (NoHardwareMenuKeyException noHardwareMenuKeyException) {
            handleBackPressed();
        }
    }

    @Override
    protected void onPause()
    {
        if (mediaScannerDialog != null && mediaScannerDialog.isScanningMedia()) {
            mediaScannerDialog.unregisterBroadcastReceiver();
            mediaScannerDialog.dismiss();
        }
        preferencesHelper.unRegisterPrefsChangedListener();
        unbindService(mediaPlayerServiceConnection);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        preferencesHelper = new PreferencesHelper(this);

        //Set up preferences and listener but callback are implemented by child classes.
        preferencesHelper.registerPrefsChangedListener();
        preferencesHelper.registerCallBack(this);

        setUI(preferencesHelper.getUIType());
        if (mediaScannerDialog != null && mediaScannerDialog.isScanningMedia()) {
            mediaScannerDialog.registerBroadcastReceiver();
            mediaScannerDialog.show();
        }
        playerUIInterface.setSongLabel("TEST FOR NOW");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        if (mediaScannerDialog != null) {
            outState.putBoolean(IS_SCANNING_MEDIA, mediaScannerDialog.isScanningMedia());
            mediaScannerDialog.dismiss();
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, SkipShuffleMediaPlayer.class);
        bindService(
                intent,
                mediaPlayerServiceConnection,
                Context.BIND_AUTO_CREATE
        );
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        TouchHandler touchHandler = new TouchHandler(this);
        return touchHandler.handleTouch(view, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){
            case R.id.refresh_media:
//                if (null == preferencesHelper.getMediaDirectories()) {
//                    pickMediaDirectories();
//                } else {
//                    showMediaScanDialog();
//                }
                return true;
            case R.id.set_target_directories:
                pickMediaDirectories();
                return true;
            case R.id.haptic_feedback_toggle:
                preferencesHelper.setHapticFeedback(!preferencesHelper.isHapticFeedback());
                return true;
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
        ThemeSelectionDialog themeSelectionDialog = new ThemeSelectionDialog(this);
        themeSelectionDialog.build();
        themeSelectionDialog.show();
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
                                BaseActivity.this
                        );
                        mediaScannerDialog.registerBroadcastReceiver();
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
                        dialog.dismiss();
                    }
                }
        );

        AlertDialog alert = builder.create();
        alert.show();
    }



    protected void setNavigationDrawerContent()
    {
        if (!(this instanceof FilePickerActivity)) {

            MusicPlayerDrawer musicPlayerDrawer = new MusicPlayerDrawer(this, R.id.drawer_list);
            musicPlayerDrawer.setClickListener(
                    new NavDrawerClickListener(
                            this,
                            (DrawerLayout) findViewById(R.id.drawer_layout)
                    )
            );
            musicPlayerDrawer.setTouchListener(this);
            musicPlayerDrawer.setAdapter(
                    new NavigationDrawerAdapter(
                            this,
                            R.layout.drawer_list_item,
                            getResources().getStringArray(R.array.drawer_menu),
                            getPreferencesHelper(),
                            playerUIInterface.getTypeFace()
                    )
            );
        }
    }

    protected void pickMediaDirectories()
    {
        if (!(this instanceof FilePickerActivity)) {
            Intent intent = new Intent(BaseActivity.this, FilePickerActivity.class);
            BaseActivity.this.startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
        }
    }

    @Override
    public void onHapticFeedBackChanged(boolean isHapticFeedback)
    {
        toastHelper.showShortToast(
                isHapticFeedback ?
                        getString(R.string.haptic_feedback_off) :
                        getString(R.string.haptic_feedback_on)
        );
    }

    @Override
    public void onThemeChanged(int uiType)
    {
        setUI(uiType);
    }
}
