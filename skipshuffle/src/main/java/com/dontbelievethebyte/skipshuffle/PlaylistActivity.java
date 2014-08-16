package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class PlaylistActivity extends Activity implements MediaBroadcastReceiverCallback {

    private ImageButton playlistPlayBtn;
    private ImageButton playlistPrevBtn;
    private ImageButton playlistSkipBtn;
    private ImageButton playlistShuffleBtn;
    private MediaPlayerBroadcastReceiver mediaPlayerBroadcastReceiver;
    private ListView listView;
    private PlaylistAdapter playlistAdapter;
    private String[] drawerMenuTitles;
    private DrawerLayout drawerLayout;
    private ListView drawerList;

    private static final String TAG = "SkipShufflePlaylist";

    private PlaylistInterface playlist;
    private PreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.drawer_layout);

        drawerMenuTitles = getResources().getStringArray(R.array.drawer_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        drawerList.setAdapter(new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        drawerMenuTitles
                )
        );
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        preferencesHelper = new PreferencesHelper(getApplicationContext());
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

        //Make sure we adjust the volume of the media player and not something else
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

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
    protected class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {

        Log.d(TAG, "POSITION : " + Integer.toString(position));

        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
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
