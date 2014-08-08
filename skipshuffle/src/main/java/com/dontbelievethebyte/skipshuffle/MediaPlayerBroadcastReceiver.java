package com.dontbelievethebyte.skipshuffle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;

public class MediaPlayerBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "PLAYER_BROADCAST_RECEIVER";
    private int playlistID;
    private int playlistPosition;
    private String playerState;
    private Context context;
    private ArrayList<MediaBroadcastReceiverCallback> mediaBroadcastReceiverCallbacks;

    public int getPlaylistID() {
        return playlistID;
    }
    public int getPlaylistPosition(){
        return playlistPosition;
    }
    public String getPlayerState() {return playerState;}

    MediaPlayerBroadcastReceiver(Context context) {
        this.context = context;
        mediaBroadcastReceiverCallbacks = new ArrayList<MediaBroadcastReceiverCallback>();
    }

    public void broadcastToMediaPlayer(String command, Integer playlistPosition){
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, command);
        if(command == SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE && playlistPosition != null){
            intent.putExtra(SkipShuflleMediaPlayerCommandsContract.CMD_SET_PLAYLIST_CURSOR_POSITION, playlistPosition);
        }
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        playlistID = intent.getIntExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_ID, 1);
        playerState = intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE);
        if(null == playerState){
            playerState = SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE;
        }
        playlistPosition = intent.getIntExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_POSITION, 0);

        for(MediaBroadcastReceiverCallback mediaBroadcastReceiverCallback : mediaBroadcastReceiverCallbacks) {
            mediaBroadcastReceiverCallback.mediaBroadcastReceiverCallback();
        }
    }

    public void registerCallback(MediaBroadcastReceiverCallback mediaBroadcastReceiverCallback){
        mediaBroadcastReceiverCallbacks.add(mediaBroadcastReceiverCallback);
    }
}