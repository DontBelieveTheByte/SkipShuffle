/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.service.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;

import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuflleMediaPlayerCommandsContract;

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
        intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        skipShuffleMediaPlayer.registerReceiver(this, intentFilter);
    }

    public void unregister()
    {
        skipShuffleMediaPlayer.unregisterReceiver(this);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try {
            if (intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND)) {
                handleCommand(intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND));
            } else if (Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
                handleHeadsetIntent(intent);
            } else if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
                KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                if (KeyEvent.KEYCODE_HEADSETHOOK == keyEvent.getKeyCode() && KeyEvent.ACTION_UP == keyEvent.getAction()) {
                    handleCommand(skipShuffleMediaPlayer.isPlaying() ?
                            SkipShuflleMediaPlayerCommandsContract.PAUSE:
                            SkipShuflleMediaPlayerCommandsContract.PLAY
                    );
                }
            }
        } catch (PlaylistEmptyException playListEmptyException) {
            skipShuffleMediaPlayer.handlePlaylistEmptyException(playListEmptyException);
        }
    }

    private void handleCommand(String command) throws PlaylistEmptyException
    {
        if (command.equals(SkipShuflleMediaPlayerCommandsContract.PLAY))
                skipShuffleMediaPlayer.doPlay();
        else if (command.equals(SkipShuflleMediaPlayerCommandsContract.PAUSE))
            skipShuffleMediaPlayer.doPause();
        else if (command.equals(SkipShuflleMediaPlayerCommandsContract.PREV))
            skipShuffleMediaPlayer.doPrev();
        else if (command.equals(SkipShuflleMediaPlayerCommandsContract.SKIP))
            skipShuffleMediaPlayer.doSkip();
        else if (command.equals(SkipShuflleMediaPlayerCommandsContract.SHUFFLE))
            skipShuffleMediaPlayer.shuffleToggle();
    }

    private void handleHeadsetIntent(Intent intent)
    {
        isInitialStickyBroadcast();
        boolean isHeadphonesPlugged = (intent.getIntExtra("state", 0) > 0) ;//Transform state to boolean
        boolean isValidDespiteInitialStickBroadCast = isHeadphonesPlugged && !isInitialStickyBroadcast();//Filter out sticky broadcast on service start.
        skipShuffleMediaPlayer.onHeadsetStateChanged(isValidDespiteInitialStickBroadCast);
    }
}
