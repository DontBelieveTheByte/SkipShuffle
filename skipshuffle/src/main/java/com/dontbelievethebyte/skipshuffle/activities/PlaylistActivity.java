package com.dontbelievethebyte.skipshuffle.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.adapters.PlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.ui.PlaylistUI;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public class PlaylistActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    protected PlaylistUI ui;

    private PlaylistAdapter playlistAdapter;
    private PlaylistInterface playlist;
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
//        loadPlaylist(mediaPlayer.getPlaylist());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = getMediaPlayer();
            if ( (playlist.getPosition() == position) && ((mediaPlayer.isPlaying())) ) {
                ImageView imageView = (ImageView) view.findViewById(R.id.track_image);
                imageView.setImageDrawable(
                        getResources().getDrawable(
                                DrawableMapper.getPause(preferencesHelper.getUIType())
                        )
                );
                mediaPlayer.doPause();
                ui.doPause();
            } else {
                mediaPlayer.doPlay(playlist.getPosition());
                ui.doPlay();
            }
        } catch (NoMediaPlayerException noMediaPlayerException) {
            handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            handlePlaylistEmptyException();
        }
    }

    public void handlePlaylistEmptyException()
    {
        preferencesHelper.setLastPlaylist(0);
        preferencesHelper.setLastPlaylistPosition(0);
    }

    @Override
    protected void setUI(Integer type)
    {
        try {
            final SkipShuffleMediaPlayer mediaPlayer = getMediaPlayer();
            ui = UIFactory.createPlaylistUI(this, type);
            ui.playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.doPlay();
                    } catch (PlaylistEmptyException playlistEmptyException) {
                        handlePlaylistEmptyException();
                    }
                }
            });

            ui.prevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.doPrev();
                    } catch (PlaylistEmptyException playlistEmptyException) {
                        handlePlaylistEmptyException();
                    }
                }
            });

            ui.skipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.doSkip();
                    } catch (PlaylistEmptyException playlistEmptyException) {
                        handlePlaylistEmptyException();
                    }
                }
            });

            ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.doShuffle();
                    } catch (PlaylistEmptyException playlistEmptyException) {
                        handlePlaylistEmptyException();
                    }
                }
            });

            setNavigationDrawerContent();
        } catch (NoMediaPlayerException noMediaPlayerException) {
            handleNoMediaPlayerException(noMediaPlayerException);
        }
    }
}
