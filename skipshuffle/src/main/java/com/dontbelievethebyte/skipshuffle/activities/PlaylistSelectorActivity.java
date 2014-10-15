package com.dontbelievethebyte.skipshuffle.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.adapters.PlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.persistance.DbHandler;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.ui.PlaylistUI;

public class PlaylistSelectorActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static String TYPE = "type";
    public static final int SONGS = 0;
    public static final int ARTISTS = 1;
    public static final int ALBUMS = 2;
    public static final int GENRES = 3;

        protected PlaylistUI ui;

        private PlaylistAdapter playlistAdapter;
        private PlaylistInterface playlist;
        private DbHandler dbHandler;
        private ListView listView;

        @Override
        protected void handleBackPressed()
        {

        }

        protected void loadPlaylist(PlaylistInterface playlist)
        {
            playlist.setPosition(preferencesHelper.getLastPlaylistPosition());
            playlistAdapter = new PlaylistAdapter(
                    this,
                    playlist
            );

            listView = (ListView) findViewById(R.id.current_list);
            listView.setOnItemClickListener(this);
            listView.setAdapter(playlistAdapter);
            TextView emptyText = (TextView)findViewById(android.R.id.empty);
            listView.setEmptyView(emptyText);
        }

        @Override
        public void onResume()
        {
            super.onResume();
            preferencesHelper.registerCallBack(this);
            dbHandler = new DbHandler(getApplicationContext());
            loadPlaylist(mediaPlayer.getPlaylist());
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
        {
            if ( (playlist.getPosition() == position) && ((mediaPlayer.getPlayerWrapper().isPlaying())) ) {
                ImageView imageView = (ImageView) view.findViewById(R.id.track_image);
                imageView.setImageDrawable(
                        getResources().getDrawable(
                                DrawableMapper.getPause(preferencesHelper.getUIType())
                        )
                );
                mediaPlayer.getPlayerWrapper().doPause();
                ui.doPause();
            } else {
                mediaPlayer.getPlayerWrapper().doJump(playlist.getPosition());
                ui.doPlay();
            }
        }

        @Override
        public void preferenceChangedCallback(String prefsKey)
        {
            super.preferenceChangedCallback(prefsKey);
            if (getString(R.string.pref_current_ui_type).equals(prefsKey)) {
                setUI(preferencesHelper.getUIType());
            }
        }

        @Override
        protected void setUI(Integer type)
        {
//            ui = UIFactory.createPlaylistUI(this, type);
            ui.playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.getPlayerWrapper().doPlay();
                }
            });

            ui.prevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.getPlayerWrapper().doPrev();
                }
            });

            ui.skipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.getPlayerWrapper().doSkip();
                }
            });

            ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.getPlayerWrapper().doShuffle();
                }
            });


            setNavigationDrawerContent();
        }
    }
