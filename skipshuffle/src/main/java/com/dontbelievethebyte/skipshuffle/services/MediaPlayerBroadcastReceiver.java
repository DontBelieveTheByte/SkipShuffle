package com.dontbelievethebyte.skipshuffle.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.callback.PlayerBroadcastReceiverCallback;

import java.util.ArrayList;

public class MediaPlayerBroadcastReceiver extends BroadcastReceiver{

    private long playlistID = 0;
    private int playlistPosition;
    private String playerState;
    private String currentSongTitle;
    private Context context;
    private ArrayList<PlayerBroadcastReceiverCallback> mediaBroadcastReceiverCallbacks;

    public MediaPlayerBroadcastReceiver(Context context)
    {
        this.context = context;
        mediaBroadcastReceiverCallbacks = new ArrayList<PlayerBroadcastReceiverCallback>();
    }

    public long getPlaylistID()
    {
        return playlistID;
    }

    public int getPlaylistPosition()
    {
        return playlistPosition;
    }

    public String getPlayerState()
    {
        return playerState;
    }

    public String getCurrentSongTitle()
    {
        if (currentSongTitle == null)
            return context.getString(R.string.meta_data_unknown_current_song_title);
        else
            return currentSongTitle;
    }

    public void broadcastToMediaPlayer(String command, Integer playlistPosition)
    {
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, command);

        if (SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE.equals(command) && playlistPosition != null)
                intent.putExtra(SkipShuflleMediaPlayerCommandsContract.CMD_SET_PLAYLIST_CURSOR_POSITION, playlistPosition);

        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        parseReceivedIntent(intent);

        if (null == playerState)
            playerState = SkipShuflleMediaPlayerCommandsContract.STATE_PAUSE;

        for(PlayerBroadcastReceiverCallback mediaBroadcastReceiverCallback : mediaBroadcastReceiverCallbacks) {
            mediaBroadcastReceiverCallback.mediaBroadcastReceiverCallback();
        }
    }

    public void registerCallback(PlayerBroadcastReceiverCallback mediaBroadcastReceiverCallback)
    {
        mediaBroadcastReceiverCallbacks.add(mediaBroadcastReceiverCallback);
    }

    private void parseReceivedIntent(Intent intent)
    {
        playlistID = intent.getLongExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_ID, 0);
        playerState = intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE);
        playlistPosition = intent.getIntExtra(SkipShuflleMediaPlayerCommandsContract.STATE_PLAYLIST_POSITION, 0);
        currentSongTitle = intent.getStringExtra(SkipShuflleMediaPlayerCommandsContract.STATE_CURRENT_SONG_TITLE);
    }
}
