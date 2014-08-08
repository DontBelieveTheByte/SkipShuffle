package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

public class PlaylistActivity extends Activity implements MediaBroadcastReceiverCallback {

    private ImageButton playlistPlayBtn;
    private ImageButton playlistPrevBtn;
    private ImageButton playlistSkipBtn;
    private ImageButton playlistShuffleBtn;
    private MediaPlayerBroadcastReceiver mediaPlayerBroadcastReceiver;
    private ListView listView;
    PlaylistAdapter playlistAdapter;

    private static final String TAG = "SkipShufflePlaylist";

    private PlaylistInterface playlist;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        preferencesHelper = new PreferencesHelper(getApplicationContext());
        playlist = new RandomPlaylist(
                preferencesHelper.getLastPlaylist(),
                new DbHandler(getApplicationContext())
        );

        listView = (ListView) findViewById(R.id.current_playlist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE, position);
            }
        });

        playlistAdapter = new PlaylistAdapter(getApplicationContext(), playlist);
        listView.setAdapter(playlistAdapter);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void mediaBroadcastReceiverCallback(){
        if(mediaPlayerBroadcastReceiver.getPlayerState().intern() == SkipShuflleMediaPlayerCommandsContract.STATE_PLAY){
            playlistPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
        } else {
            playlistPlayBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
        }
        playlist.setPosition(mediaPlayerBroadcastReceiver.getPlaylistPosition());
        playlistAdapter.notifyDataSetChanged();
    }

    public View.OnTouchListener onTouchDownHapticFeedback = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()){
                if(preferencesHelper.isHapticFeedback()){
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
                }
            }
            return false;
        }
    };
}
