/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.activities;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.listeners.TouchListener;
import com.dontbelievethebyte.skipshuffle.exceptions.MenuOptionNotHandledException;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.service.callbacks.PlayerStateChangedCallback;
import com.dontbelievethebyte.skipshuffle.service.connection.MediaPlayerServiceConnection;
import com.dontbelievethebyte.skipshuffle.ui.dialog.ThemeSelectionDialog;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIComposition;
import com.dontbelievethebyte.skipshuffle.ui.elements.actionbar.CustomActionBarWrapper;
import com.dontbelievethebyte.skipshuffle.ui.elements.menu.CustomOptionsMenuInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.menu.builder.OptionsMenuBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.menu.callbacks.MenuItemSelectedCallback;
import com.dontbelievethebyte.skipshuffle.utilities.ToastHelper;
import com.dontbelievethebyte.skipshuffle.utilities.media.MediaScannerHelper;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.callbacks.PrefsCallbacksManager;

public abstract class BaseActivity extends ActionBarActivity implements PrefsCallbacksManager.ThemeChangedCallback,
                                                                        PrefsCallbacksManager.HapticFeedBackChangedCallback,
                                                                        MediaPlayerServiceConnection.MediaPlayerConnectedCallback,
                                                                        PrefsCallbacksManager.ViewModeChangedCallback,
                                                                        PlayerStateChangedCallback,
                                                                        View.OnTouchListener {

    public static final String TAG = "SkipShuffle";

    private class MenuCallBacks implements MenuItemSelectedCallback {
        @Override
        public boolean handleMenuRefreshMedia()
        {
            showMediaScannerDialog();
            return true;
        }

        @Override
        public boolean handleMenuHapticFeedBack()
        {
            preferencesHelper.setHapticFeedback(!preferencesHelper.isHapticFeedback());
            return true;
        }

        @Override
        public boolean handleMenuThemeSelection()
        {
            showThemeSelectionDialog();
            return true;
        }
    }

    public UIComposition ui;

    protected PreferencesHelper preferencesHelper;
    protected CustomActionBarWrapper customActionBar;
    protected ToastHelper toastHelper;
    protected CustomOptionsMenuInterface customOptionsMenu;

    private MediaScannerHelper mediaScannerHelper;
    private MediaPlayerServiceConnection mediaPlayerServiceConnection;

    protected abstract void setUI(Integer type);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        startMediaPlayerService();

        //Make sure we adjust the volume of the media player and not something else
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        setUpActionBar();

        toastHelper = new ToastHelper(getApplicationContext());
        mediaScannerHelper = new MediaScannerHelper(this);
    }

    public PreferencesHelper getPreferencesHelper()
    {
        return preferencesHelper;
    }

    private void startMediaPlayerService()
    {
        startService(
                new Intent(
                        getApplicationContext(),
                        SkipShuffleMediaPlayer.class
                )
        );
        mediaPlayerServiceConnection = new MediaPlayerServiceConnection();
        mediaPlayerServiceConnection.registerConnectedCallback(this);
    }

    public SkipShuffleMediaPlayer getMediaPlayer() throws NoMediaPlayerException
    {
        return mediaPlayerServiceConnection.getMediaPlayer();
    }

    private void setUpActionBar()
    {
        customActionBar = new CustomActionBarWrapper(this);
        customActionBar.setUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        OptionsMenuBuilder optionsMenuCreator = new OptionsMenuBuilder(this);
        optionsMenuCreator.setCustomActionBarWrapper(customActionBar);
        optionsMenuCreator.setMenuItemSelectedCallback(new MenuCallBacks());
        optionsMenuCreator.setCustomActionBarWrapper(customActionBar);
        customOptionsMenu = optionsMenuCreator.build(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        //Check if we're scanning media beforehand and.
        if (savedInstanceState.getBoolean(MediaScannerHelper.IS_SCANNING_MEDIA))
            mediaScannerHelper.startMediaScan();
    }

    @Override
    public void onBackPressed()
    {
        if (customOptionsMenu.isShowing()){
            customOptionsMenu.handleBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause()
    {
        preferencesHelper.unRegisterPrefsChangedListener();
        unbindService(mediaPlayerServiceConnection);
        ui.player.buttons.play.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpPreferencesHelper();
        setUpMediaPlayerServiceBinding();
    }

    @Override
    public void onMediaPlayerAvailable()
    {
        mediaPlayerServiceConnection.registerPlayerStateChanged(this);
        setUI(preferencesHelper.getUIType());
    }

    private void setUpPreferencesHelper()
    {
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        PrefsCallbacksManager prefsCallbacksManager = new PrefsCallbacksManager(this);
        prefsCallbacksManager.registerHapticFeedBackChanged(this);
        prefsCallbacksManager.registerViewModeChanged(this);
        prefsCallbacksManager.registerThemeChanged(this);
        preferencesHelper.setCallbacksManager(prefsCallbacksManager);
        preferencesHelper.registerPrefsChangedListener();
    }

    private void setUpMediaPlayerServiceBinding()
    {
        Intent intent = new Intent(this, SkipShuffleMediaPlayer.class);
        bindService(
                intent,
                mediaPlayerServiceConnection,
                Context.BIND_AUTO_CREATE
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putBoolean(MediaScannerHelper.IS_SCANNING_MEDIA, mediaScannerHelper.isScanningMedia());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        TouchListener touchHandler = new TouchListener(preferencesHelper);
        return touchHandler.handleTouch(view, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        try {
            return customOptionsMenu.handleSelection(item);
        } catch (MenuOptionNotHandledException menuOptionNotHandledException){
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU )
            return customOptionsMenu.handleMenuKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    public void showThemeSelectionDialog()
    {
        ThemeSelectionDialog themeSelectionDialog = new ThemeSelectionDialog(this);
        themeSelectionDialog.build(preferencesHelper);
        themeSelectionDialog.show();
    }

    public void showMediaScannerDialog()
    {
        mediaScannerHelper.showMediaScannerDialog();
    }

    @Override
    public void onHapticFeedBackChanged()
    {
        toastHelper.showShortToast(
                preferencesHelper.isHapticFeedback() ?
                        getString(R.string.haptic_feedback_off) :
                        getString(R.string.haptic_feedback_on)
        );
    }

    @Override
    public void onThemeChanged()
    {
        setUI(preferencesHelper.getUIType());
//        try {
//            SkipShuffleMediaPlayer skipShuffleMediaPlayer = getMediaPlayer();
//            skipShuffleMediaPlayer.onThemeChanged();
//        } catch (NoMediaPlayerException e) {
//            handleNoMediaPlayerException(e);
//        }
    }

    @Override
    public void onPlayerStateChanged()
    {
        if (null != ui.player)
            ui.player.reboot();
    }

    public void handleNoMediaPlayerException(NoMediaPlayerException noMediaPlayerException)
    {
        toastHelper.showLongToast(
                getString(R.string.no_media_player)
        );
    }

    public void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {

    }
}
