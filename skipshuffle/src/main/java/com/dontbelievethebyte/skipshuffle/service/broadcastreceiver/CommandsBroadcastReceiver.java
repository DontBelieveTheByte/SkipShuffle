package com.dontbelievethebyte.skipshuffle.service.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;

public class CommandsBroadcastReceiver extends BroadcastReceiver {
    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;

    public CommandsBroadcastReceiver(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {

    }

    public void register()
    {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        intentFilter.addAction(Intent.ACTION_MEDIA_BUTTON);
        skipShuffleMediaPlayer.registerReceiver(this, intentFilter);
    }

    public void unregister()
    {
        skipShuffleMediaPlayer.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND)) {
            handleCommand(
                    intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND),
                    intent
            );
        } else if (Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
            handleHeadsetIntent(intent, isInitialStickyBroadcast());
        }
    }

    private void handleCommand(String command, Intent intent)
    {
        Integer newCursorPosition = null;

        if (intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.CMD_SET_PLAYLIST_CURSOR_POSITION))
            newCursorPosition = intent.getIntExtra(
                            SkipShuflleMediaPlayerCommandsContract.CMD_SET_PLAYLIST_CURSOR_POSITION,
                            0
            );

        skipShuffleMediaPlayer.onCommand(command, newCursorPosition);
    }

    private void handleHeadsetIntent(Intent intent, boolean isInitialStickBroadCast)
    {
        boolean isHeadphonesPlugged = (intent.getIntExtra("state", 0) > 0) ;//Transform state to boolean
        boolean isValidDespiteInitialStickBroadCast = isHeadphonesPlugged && !isInitialStickBroadCast;//Filter out sticky broadcast on service start.
        skipShuffleMediaPlayer.onHeadsetStateChanged(isValidDespiteInitialStickBroadCast);
    }
}
