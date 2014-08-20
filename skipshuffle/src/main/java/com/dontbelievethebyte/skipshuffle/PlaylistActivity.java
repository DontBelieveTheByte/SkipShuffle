package com.dontbelievethebyte.skipshuffle;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class PlaylistActivity extends BaseActivity implements MediaBroadcastReceiverCallback {

    private ListView listView;
    private PlaylistAdapter playlistAdapter;

    protected PlaylistUI ui;

    private PlaylistInterface playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ui = UIFactory.createPlaylistUI(PlaylistActivity.this, preferencesHelper.getUIType());


        //Register class specific callback from MediaBroadcastReceiverCallback interface.
        mediaPlayerBroadcastReceiver.registerCallback(this);

        playlist = new RandomPlaylist(
                preferencesHelper.getLastPlaylist(),
                new DbHandler(getApplicationContext())
        );
        playlist.setPosition(preferencesHelper.getLastPlaylistPosition());

        listView = (ListView) findViewById(R.id.current_playlist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE, position);
            }
        });

        playlistAdapter = new PlaylistAdapter(getApplicationContext(), playlist);
        listView.setAdapter(playlistAdapter);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);



        ui.playlistPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE, null);
            }
        });

        ui.playlistPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PREV, null);
            }
        });

        ui.playlistSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_SKIP, null);
            }
        });

        ui.playlistShuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST, null);
            }
        });

        //Register haptic feedback for all buttons.
        ui.playlistPlayBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.playlistPrevBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.playlistSkipBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.playlistShuffleBtn.setOnTouchListener(onTouchDownHapticFeedback);

    }

    @Override
    public void mediaBroadcastReceiverCallback(){
        if(mediaPlayerBroadcastReceiver.getPlayerState().intern() == SkipShuflleMediaPlayerCommandsContract.STATE_PLAY){
            ui.playlistPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.neon_play_states));
        } else {
            ui.playlistPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.neon_pause_states));
        }
        playlist.setPosition(mediaPlayerBroadcastReceiver.getPlaylistPosition());
        playlistAdapter.notifyDataSetChanged();
    }

}
