package com.dontbelievethebyte.skipshuffle.service.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;

public class CommandsBroadcastReceiver extends BroadcastReceiver {
    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;

    public CommandsBroadcastReceiver(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
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
            Log.d(BaseActivity.TAG, "UNPLUGGGGEDDDDD!@@!@@!$$$");
            handleHeadsetIntent(intent);
        }
    }

    private void handleCommand(String command, Intent intent)
    {
        skipShuffleMediaPlayer.onCommand(command);
    }

    private void handleHeadsetIntent(Intent intent)
    {
        isInitialStickyBroadcast();
        boolean isHeadphonesPlugged = (intent.getIntExtra("state", 0) > 0) ;//Transform state to boolean
        boolean isValidDespiteInitialStickBroadCast = isHeadphonesPlugged && !isInitialStickyBroadcast();//Filter out sticky broadcast on service start.
        skipShuffleMediaPlayer.onHeadsetStateChanged(isValidDespiteInitialStickBroadCast);
    }
}
