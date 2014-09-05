package com.dontbelievethebyte.skipshuffle.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.database.DbHandler;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.ui.playlist.PlaylistUI;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

import org.json.JSONException;

public class PlaylistActivity extends BaseActivity {

    private ListView listView;
    private PlaylistAdapter playlistAdapter;

    protected PlaylistUI ui;

    private PlaylistInterface playlist;

    private DbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Register class specific callback from MediaBroadcastReceiverCallback interface.
        mediaPlayerBroadcastReceiver.registerCallback(this);

        setUI(preferencesHelper.getUIType());

        dbHandler = new DbHandler(getApplicationContext());

        preferencesHelper.registerCallBack(this);

        loadPlaylist(preferencesHelper.getLastPlaylist());
    }

    @Override
    public void onStop(){
        preferencesHelper.unRegisterPrefsChangedListener();
    }

    @Override
    public void onResume(){
        preferencesHelper.registerPrefsChangedListener();
        preferencesHelper.registerCallBack(this);
        super.onResume();
    }

    @Override
    public void mediaBroadcastReceiverCallback(){
        Log.d(TAG, "STATE IS : " + mediaPlayerBroadcastReceiver.getPlayerState());
        if (SkipShuflleMediaPlayerCommandsContract.STATE_PLAY.equals(
                mediaPlayerBroadcastReceiver.getPlayerState())
        ) {
            ui.playlistPlayBtn.setImageDrawable(
                getResources().getDrawable(
                    UIFactory.getPlayDrawable(preferencesHelper.getUIType())
                )
            );
        } else {
            ui.playlistPlayBtn.setImageDrawable(
                getResources().getDrawable(
                    UIFactory.getPlayDrawable(preferencesHelper.getUIType())
                )
            );
        }
        playlist.setPosition(mediaPlayerBroadcastReceiver.getPlaylistPosition());
        playlistAdapter.notifyDataSetChanged();
    }

    protected void loadPlaylist(long playlistId){
        try {
            playlist = new RandomPlaylist(
                    playlistId,
                    dbHandler
            );
            playlist.setPosition(preferencesHelper.getLastPlaylistPosition());
            playlistAdapter = new PlaylistAdapter(
                    getApplicationContext(),
                    preferencesHelper,
                    playlist
            );

            listView = (ListView) findViewById(R.id.current_playlist);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                            SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE, position);
                }
            });
            listView.setAdapter(playlistAdapter);
            TextView emptyText = (TextView)findViewById(android.R.id.empty);
            listView.setEmptyView(emptyText);
        } catch (JSONException jsonException){
            Toast.makeText(
                    getApplicationContext(),
                    String.format(getString(R.string.playlist_load_error), preferencesHelper.getLastPlaylist()),
                    Toast.LENGTH_LONG
            ).show();
            preferencesHelper.setLastPlaylist(1);
            preferencesHelper.setLastPlaylistPosition(0);
        }
    }

    @Override
    protected void setUI(Integer type) {
        ui = UIFactory.createPlaylistUI(this, type);
        ui.playlistPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE,
                        null
                );
            }
        });

        ui.playlistPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PREV,
                        null
                );
            }
        });

        ui.playlistSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SKIP,
                        null
                );
            }
        });

        ui.playlistShuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST,
                        null
                );
            }
        });

        //Register haptic feedback for all buttons.
        ui.playlistPlayBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.playlistPrevBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.playlistSkipBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.playlistShuffleBtn.setOnTouchListener(onTouchDownHapticFeedback);
    }

    @Override
    public void preferenceChangedCallback(String prefsKey) {
        super.preferenceChangedCallback(prefsKey);
        if (getString(R.string.pref_current_playlist_id).equals(prefsKey)) {
            loadPlaylist(preferencesHelper.getLastPlaylist());
        }
    }
}
