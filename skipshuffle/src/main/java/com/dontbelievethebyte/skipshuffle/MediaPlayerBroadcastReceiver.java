package com.dontbelievethebyte.skipshuffle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MediaPlayerBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = "PLAYER_BROADCAST_RECEIVER";
    private int playlistID;
    private int playlistPosition;

    public int getPlaylistID() {
        return playlistID;
    }
    public int getPlaylistPosition(){
        return playlistPosition;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.PLAYLIST_ID)){
            playlistID = intent.getIntExtra(SkipShuflleMediaPlayerCommandsContract.PLAYLIST_ID, 0);
            Log.v(TAG, Integer.toString(playlistID));
        }
        if(intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.PLAYLIST_CURSOR_POSITION)){
            playlistID = intent.getIntExtra(SkipShuflleMediaPlayerCommandsContract.PLAYLIST_ID, 0);
            Log.v(TAG, Integer.toString(playlistPosition));
        }
    }
}