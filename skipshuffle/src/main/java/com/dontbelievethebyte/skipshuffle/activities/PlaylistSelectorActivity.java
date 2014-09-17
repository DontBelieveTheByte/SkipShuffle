package com.dontbelievethebyte.skipshuffle.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.database.DbHandler;
import com.dontbelievethebyte.skipshuffle.activities.adapters.PlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;
import com.dontbelievethebyte.skipshuffle.ui.PlaylistSelectorUI;

import org.json.JSONException;

public class PlaylistSelectorActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static String TYPE = "type";
    public static final int SONGS = 0;
    public static final int ARTISTS = 1;
    public static final int ALBUMS = 2;
    public static final int GENRES = 3;

    private PlaylistSelectorUI ui;
    private PlaylistAdapter playlistAdapter;
    private PlaylistInterface playlist;
    private DbHandler dbHandler;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Register class specific callback from MediaBroadcastReceiverCallback interface.
        preferencesHelper.registerCallBack(this);

        setUI(preferencesHelper.getUIType());

        dbHandler = new DbHandler(getApplicationContext());

        loadType(preferencesHelper.getLastPlaylist());
        setUpDrawer();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        preferencesHelper.registerCallBack(this);
        mediaPlayerBroadcastReceiver.registerCallback(this);
    }

    @Override
    public void mediaBroadcastReceiverCallback()
    {
        if (SkipShuflleMediaPlayerCommandsContract.STATE_PLAY.equals(
                mediaPlayerBroadcastReceiver.getPlayerState())
                ) {
            ui.doPlay();
        } else {
            ui.doPause();
        }
        playlist.setPosition(mediaPlayerBroadcastReceiver.getPlaylistPosition());
        playlistAdapter.notifyDataSetChanged();
        listView.smoothScrollToPositionFromTop(playlist.getPosition(), 0);
    }

    protected void loadType(long playlistId)
    {
        try {
            playlist = new RandomPlaylist(
                    playlistId,
                    dbHandler
            );
            playlist.setPosition(preferencesHelper.getLastPlaylistPosition());
            playlistAdapter = new PlaylistAdapter(
                    getApplicationContext(),
                    preferencesHelper,
                    mediaPlayerBroadcastReceiver,
                    playlist
            );

            listView = (ListView) findViewById(R.id.current_list);
            listView.setOnItemClickListener(this);
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
    protected void setUI(Integer type)
    {
        ui = UIFactory.createPlaylistSelectorUI(this, type);
        ui.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE,
                        null
                );
            }
        });

        ui.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PREV,
                        null
                );
            }
        });

        ui.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SKIP,
                        null
                );
            }
        });

        ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST,
                        null
                );
            }
        });

        //Register haptic feedback for all buttons.
        ui.playBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.prevBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.skipBtn.setOnTouchListener(onTouchDownHapticFeedback);
        ui.shuffleBtn.setOnTouchListener(onTouchDownHapticFeedback);
        setUpDrawer();
    }

    @Override
    public void preferenceChangedCallback(String prefsKey)
    {
        super.preferenceChangedCallback(prefsKey);
        if (getString(R.string.pref_current_playlist_id).equals(prefsKey)) {
            loadType(preferencesHelper.getLastPlaylist());
        } else if (getString(R.string.pref_current_ui_type).equals(prefsKey)) {
            setUI(preferencesHelper.getUIType());
            loadType(preferencesHelper.getLastPlaylist());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        if (playlist.getPosition() == position &&
                mediaPlayerBroadcastReceiver.getPlayerState().equals(SkipShuflleMediaPlayerCommandsContract.STATE_PLAY)
                ) {
            mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                    SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE,
                    null
            );
            ImageView imageView = (ImageView) view.findViewById(R.id.track_image);
            imageView.setImageDrawable(
                    getResources().getDrawable(
                            DrawableMapper.getPause(preferencesHelper.getUIType())
                    )
            );
        } else {
            mediaPlayerBroadcastReceiver.broadcastToMediaPlayer(
                    SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE,
                    position
            );
        }
        ui.doPause();
    }
}
