package com.dontbelievethebyte.skipshuffle.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.SongLabel;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class ListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static class Types {

        public static final String TYPE = "Type";
        public static final int SONGS = 0;
        public static final int ARTISTS = 1;
        public static final int ALBUMS = 2;
        public static final int GENRES = 3;
        public static final int PLAYLIST = 4;
    }

    private PlaylistAdapter playlistAdapter;
    private PlaylistInterface playlist;
    private ListView listView;
    private int listType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        parseIntent();
        setListTitle();
    }

    private void parseIntent()
    {
        Intent intent = getIntent();
        listType = intent.getIntExtra(Types.TYPE, 0);
    }

    private void setListTitle()
    {
        String actionBarTitle = "";
        switch (listType) {
            case Types.SONGS:
                actionBarTitle = "Songs";
            case Types.ARTISTS:
                actionBarTitle = "Artists";
            case Types.ALBUMS:
                actionBarTitle = "Albums";
            case Types.GENRES:
                actionBarTitle = "Genres";
            case Types.PLAYLIST:
                actionBarTitle = "Playlists";
            default:
        }
        customActionBar.setTitle(actionBarTitle);
    }

    protected void loadList(PlaylistInterface playlist)
    {
        playlist.setPosition(preferencesHelper.getLastPlaylistPosition());
        playlistAdapter = new PlaylistAdapter(
                this,
                ui.player.buttons.drawables,
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
    }

    @Override
    public void onMediaPlayerAvailable()
    {
        ui.player.reboot();
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
                ui.player.doPause();
            } else {
                mediaPlayer.doPlay(playlist.getPosition());
                ui.player.doPlay();
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
        ContentArea contentArea = new ContentArea(this, R.layout.playlist_activity);
        CustomTypeface customTypeface = new CustomTypeface(this, type);
        Drawables drawables = new Drawables(this, type);

        ListPlayerButtons buttons = new ListPlayerButtons(contentArea);
        buttons.animations = new PlayerButtonsAnimations(this);
        buttons.drawables = drawables;

        SongLabel songLabel = new SongLabel(contentArea, R.id.song_label);
        songLabel.setTypeFace(customTypeface);

        ListPlayer player = new ListPlayer(
                this,
                buttons,
                songLabel
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(this);
        uiBuilder.setContentArea(contentArea);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer(customTypeface));
        uiBuilder.setColors(new Colors(type));
        uiBuilder.setDrawables(drawables);
        uiBuilder.setPlayer(player);
        ui = uiBuilder.build();
        ui.player.reboot();
    }
}
