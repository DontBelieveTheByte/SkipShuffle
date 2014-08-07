package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class PlaylistActivity extends Activity {

    private ImageButton playlistPlay;
    private ImageButton playlistPrev;
    private ImageButton playlistSkip;
    private ImageButton playlistShuffle;
    private MediaPlayerBroadcastReceiver mediaPlayerBroadcastReceiver;

    private static final String TAG = "SkipShufflePlaylist";

    private PlaylistInterface playlist;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        populate();

        playlistPlay = (ImageButton) findViewById(R.id.playlist_layout_play);
        playlistShuffle = (ImageButton) findViewById(R.id.playlist_layout_shuffle);
        playlistSkip = (ImageButton) findViewById(R.id.playlist_layout_skip);
        playlistPrev = (ImageButton) findViewById(R.id.playlist_layout_prev);

        mediaPlayerBroadcastReceiver = new MediaPlayerBroadcastReceiver(getApplicationContext());

        playlistPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PLAY);
            }
        });

        playlistPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_PREV);
            }
        });

        playlistSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_SKIP);
            }
        });

        playlistShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void populate(){
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        playlist = new RandomPlaylist(
                1L,
                new DbHandler(getApplicationContext())
        );

        final ListView listView = (ListView) findViewById(R.id.current_playlist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d("POS", ": " + position);
            }
        });
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(getApplicationContext(), playlist);
        listView.setAdapter(playlistAdapter);
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
