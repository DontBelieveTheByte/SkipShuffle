package com.dontbelievethebyte.skipshuffle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MediaPlayerBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = "PLAYER_BROADCAST_RECEIVER";
    private long playlistID;
    private int playlistPosition;
    private String playerState;
    private Context context;

    public long getPlaylistID() {
        return playlistID;
    }
    public int getPlaylistPosition(){
        return playlistPosition;
    }
    public String getPlayerState() {return playerState;}

    MediaPlayerBroadcastReceiver(Context context) {
        this.context = context;
    }

    public void broadcastToMediaPlayer(String command){
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, command);
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.PLAYLIST_ID)){
            playlistID = intent.getLongExtra(SkipShuflleMediaPlayerCommandsContract.PLAYLIST_ID, 1);
        }
        if(intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.PLAYLIST_CURSOR_POSITION)){
            playlistID = intent.getLongExtra(SkipShuflleMediaPlayerCommandsContract.PLAYLIST_ID, 0);
        }
        if(intent.hasExtra(SkipShuflleMediaPlayerCommandsContract.CMD_GET_PLAYER_STATE)){
            playerState = intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.CMD_GET_PLAYER_STATE);
        }
    }
}