/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.service.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;

public class OrientationBroadcastReceiver extends BroadcastReceiver {
    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;

    public OrientationBroadcastReceiver(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
    }

    public void register()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        skipShuffleMediaPlayer.registerReceiver(this, intentFilter);
    }

    public void unregister()
    {
        skipShuffleMediaPlayer.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_CONFIGURATION_CHANGED))
            skipShuffleMediaPlayer.onOrientationChanged();
    }
}
