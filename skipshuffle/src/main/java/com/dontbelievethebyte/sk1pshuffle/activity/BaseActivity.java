/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.callbacks.PlayerStateChangedCallback;
import com.dontbelievethebyte.sk1pshuffle.service.connection.MediaPlayerServiceConnection;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.dialog.ThemeSelectionDialog;
import com.dontbelievethebyte.sk1pshuffle.ui.element.UIComposition;
import com.dontbelievethebyte.sk1pshuffle.ui.element.actionbar.CustomActionBarWrapper;
import com.dontbelievethebyte.sk1pshuffle.ui.element.menu.CustomOptionsMenuInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.element.menu.builder.OptionsMenuBuilder;
import com.dontbelievethebyte.sk1pshuffle.ui.element.menu.callbacks.MenuItemSelectedCallback;
import com.dontbelievethebyte.sk1pshuffle.ui.element.menu.exception.MenuOptionNotHandledException;
import com.dontbelievethebyte.sk1pshuffle.utilities.AppRater;
import com.dontbelievethebyte.sk1pshuffle.utilities.ToastHelper;
import com.dontbelievethebyte.sk1pshuffle.utilities.media.MediaScannerHelper;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PreferencesHelper;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.callbacks.PrefsCallbacksManager;

public abstract class BaseActivity extends ActionBarActivity implements PrefsCallbacksManager.ThemeChangedCallback,
                                                                        PrefsCallbacksManager.HapticFeedBackChangedCallback,
                                                                        MediaPlayerServiceConnection.MediaPlayerConnectedCallback,
                                                                        PrefsCallbacksManager.ViewModeChangedCallback,
                                                                        PlayerStateChangedCallback,
                                                                        ThemableActivityInterface {

    private class MenuCallBacks implements MenuItemSelectedCallback {
        @Override
        public boolean handleMenuRefreshMedia()
        {
            showMediaScannerDialog();
            return false;
        }

        @Override
        public boolean handleMenuHapticFeedBack()
        {
            preferencesHelper.toggleHapticFeedback();
            return true;
        }

        @Override
        public boolean handleMenuThemeSelection()
        {
            showThemeSelectionDialog();
            return false;
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
        AppRater appRater = new AppRater(
                this,
                new PreferencesHelper(getApplicationContext()),
                toastHelper
        );
        appRater.rateIfPossible();
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
        ui.getPlayer().buttons.play.clearAnimation();
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        try {
            return customOptionsMenu.handleSelection(item);
        } catch (MenuOptionNotHandledException menuOptionNotHandledException){
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent)
    {
        if (KeyEvent.KEYCODE_MENU  == keyCode)
            return customOptionsMenu.handleMenuKeyDown(keyCode, keyEvent);
        else if (KeyEvent.KEYCODE_MEDIA_PLAY == keyCode || KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE == keyCode)
            ui.getPlayer().buttons.play.performClick();
        else if (KeyEvent.KEYCODE_MEDIA_NEXT == keyCode)
            ui.getPlayer().buttons.skip.performClick();
        else if (KeyEvent.KEYCODE_MEDIA_PREVIOUS == keyCode)
            ui.getPlayer().buttons.prev.performClick();
        else if (KeyEvent.KEYCODE_MEDIA_STOP == keyCode)
            ui.getPlayer().buttons.shuffle.performClick();
        return super.onKeyDown(keyCode, keyEvent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ContentBrowserActivity.REQUEST_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    toastHelper.showLongToast("OKOKOK!");
                }
                break;
            }
        }
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
        long[] pattern = {
                0L,
                500L,
                110L,
                500L,
                110L,
                450L,
                110L,
                200L,
                110L,
                170L,
                40L,
                450L,
                110L,
                200L,
                110L,
                170L,
                40L,
                500L
        };
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator() && preferencesHelper.isHapticFeedback())
            vibrator.vibrate(pattern, -1);
        toastHelper.showShortToast(
                preferencesHelper.isHapticFeedback() ?
                        getString(R.string.haptic_feedback_on) :
                        getString(R.string.haptic_feedback_off)
        );
    }

    @Override
    public void onThemeChanged()
    {
        setUI(preferencesHelper.getUIType());
    }

    @Override
    public void onPlayerStateChanged()
    {
        if (null != ui.getPlayer())
            ui.getPlayer().reboot();
    }

    public void handleNoMediaPlayerException(NoMediaPlayerException noMediaPlayerException)
    {
        toastHelper.showLongToast(
                getString(R.string.no_media_player)
        );
    }

    public void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        toastHelper.showLongToast(
                getString(R.string.empty_playlist)
        );
    }
}
