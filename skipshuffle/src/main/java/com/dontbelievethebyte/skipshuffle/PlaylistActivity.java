package com.dontbelievethebyte.skipshuffle;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class PlaylistActivity extends BaseActivity implements MediaBroadcastReceiverCallback {

    private ImageButton playlistPlayBtn;
    private ImageButton playlistPrevBtn;
    private ImageButton playlistSkipBtn;
    private ImageButton playlistShuffleBtn;

    private ListView listView;
    private PlaylistAdapter playlistAdapter;

    private static final String TAG = "SkipShufflePlaylist";

    private PlaylistInterface playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist);

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

        playlistPlayBtn = (ImageButton) findViewById(R.id.playlist_layout_play);
        playlistShuffleBtn = (ImageButton) findViewById(R.id.playlist_layout_shuffle);
        playlistSkipBtn = (ImageButton) findViewById(R.id.playlist_layout_skip);
        playlistPrevBtn = (ImageButton) findViewById(R.id.playlist_layout_prev);

        mediaPlayerBroadcastReceiver = new MediaPlayerBroadcastReceiver(getApplicationContext());
        mediaPlayerBroadcastReceiver.registerCallback(this);
        registerReceiver(mediaPlayerBroadcastReceiver, new IntentFilter(SkipShuflleMediaPlayerCommandsContract.CURRENT_STATE));


        playlistPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE, null);
            }
        });

        playlistPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PREV, null);
            }
        });

        playlistSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_SKIP, null);
            }
        });

        playlistShuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST, null);
            }
        });

        //Register haptic feedback for all buttons.
        playlistPlayBtn.setOnTouchListener(onTouchDownHapticFeedback);
        playlistPrevBtn.setOnTouchListener(onTouchDownHapticFeedback);
        playlistSkipBtn.setOnTouchListener(onTouchDownHapticFeedback);
        playlistShuffleBtn.setOnTouchListener(onTouchDownHapticFeedback);

    }

    @Override
    public void mediaBroadcastReceiverCallback(){
        if(mediaPlayerBroadcastReceiver.getPlayerState().intern() == SkipShuflleMediaPlayerCommandsContract.STATE_PLAY){
            playlistPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.neon_play_states));
        } else {
            playlistPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.neon_pause_states));
        }
        playlist.setPosition(mediaPlayerBroadcastReceiver.getPlaylistPosition());
        playlistAdapter.notifyDataSetChanged();
    }

}
