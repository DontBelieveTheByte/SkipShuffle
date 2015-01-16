/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.remote.widget.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.callbacks.PlayerStateChangedCallback;

public class WidgetUpdater implements PlayerStateChangedCallback {

    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;

    public WidgetUpdater(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
    }

    public void updateWidgets(PlayerState playerState)
    {

        Intent intent = new Intent(
                skipShuffleMediaPlayer,
                SkipShuffleWidget.class
        );

        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");

        int ids[] = AppWidgetManager.getInstance(
                skipShuffleMediaPlayer.getApplication()).getAppWidgetIds(
                new ComponentName(skipShuffleMediaPlayer.getApplication(), SkipShuffleWidget.class)
        );

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        intent.putExtra(
                WidgetContract.TITLE,
                playerState.getTitle()
        );
        intent.putExtra(
                WidgetContract.ARTIST,
                playerState.getArtist()
        );
        intent.putExtra(
                WidgetContract.IS_PLAYING,
                playerState.isPlaying()
        );
        intent.putExtra(
                WidgetContract.IS_SHUFFLE,
                playerState.isShuffle());
        intent.putExtra(
                WidgetContract.UI_TYPE,
                skipShuffleMediaPlayer.getPreferencesHelper().getUIType()
        );
        skipShuffleMediaPlayer.sendBroadcast(intent);
    }

    @Override
    public void onPlayerStateChanged()
    {
        updateWidgets(new PlayerState(skipShuffleMediaPlayer));
    }
}
