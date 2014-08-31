package com.dontbelievethebyte.skipshuffle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class MediaPlayerBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "PLAYER_BROADCAST_RECEIVER";
    private long playlistID = 0;
    private int playlistPosition;
    private String playerState;
    private String currentSongTitle;
    private Context context;
    private ArrayList<MediaBroadcastReceiverCallback> mediaBroadcastReceiverCallbacks;

    public long getPlaylistID() {
        return playlistID;
    }
    public int getPlaylistPosition(){
        return playlistPosition;
    }
    public String getPlayerState() {return playerState;}
    public String getCurrentSongTitle(){
        if(currentSongTitle == null){
            return context.getString(R.string.meta_data_unknown_current_song_title);
        } else {
            return currentSongTitle;
        }
    }

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
        playlistID = intent.getLongExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_ID, 0);
        playerState = intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE);
        if(null == playerState){
            playerState = SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE;
        }
        playlistPosition = intent.getIntExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_POSITION, 0);
        currentSongTitle = intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.STATE_CURRENT_SONG_TITLE);

        for(MediaBroadcastReceiverCallback mediaBroadcastReceiverCallback : mediaBroadcastReceiverCallbacks) {
            mediaBroadcastReceiverCallback.mediaBroadcastReceiverCallback();
        }
    }

    public void registerCallback(MediaBroadcastReceiverCallback mediaBroadcastReceiverCallback){
        mediaBroadcastReceiverCallbacks.add(mediaBroadcastReceiverCallback);
    }
}