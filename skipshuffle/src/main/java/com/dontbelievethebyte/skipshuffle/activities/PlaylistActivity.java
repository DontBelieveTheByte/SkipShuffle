package com.dontbelievethebyte.skipshuffle.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.adapters.PlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.database.DbHandler;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.ui.PlaylistUI;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

import org.json.JSONException;

public class PlaylistActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    protected PlaylistUI ui;

    private PlaylistAdapter playlistAdapter;
    private PlaylistInterface playlist;
    private DbHandler dbHandler;
    private ListView listView;

    @Override
    public void onResume()
    {
        super.onResume();
        preferencesHelper.registerCallBack(this);
        mediaPlayerBroadcastReceiver.registerCallback(this);
        dbHandler = new DbHandler(getApplicationContext());
        loadPlaylist(preferencesHelper.getLastPlaylist());
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

    protected void loadPlaylist(long playlistId)
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
            toastHelper.showLongToast(
                    String.format(
                            getString(R.string.playlist_load_error),
                            preferencesHelper.getLastPlaylist()
                    )
            );
            preferencesHelper.setLastPlaylist(1);
            preferencesHelper.setLastPlaylistPosition(0);
        }
    }

    @Override
    protected void setUI(Integer type)
    {
        ui = UIFactory.createPlaylistUI(this, type);
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
            loadPlaylist(preferencesHelper.getLastPlaylist());
        }
        else if (getString(R.string.pref_current_ui_type).equals(prefsKey)) {
            loadPlaylist(preferencesHelper.getLastPlaylist());
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
