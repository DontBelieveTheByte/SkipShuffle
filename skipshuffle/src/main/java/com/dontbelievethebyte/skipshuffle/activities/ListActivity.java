package com.dontbelievethebyte.skipshuffle.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.adapters.AbstractCustomAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.AlbumsAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.ArtistsAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.GenresAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.SongsAdapter;
import com.dontbelievethebyte.skipshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.SongLabel;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class ListActivity extends BaseActivity implements AdapterView.OnItemClickListener,
                                                          LoaderManager.LoaderCallbacks<Cursor>{

    private ListView listView;
    private int listType;
    private MediaStoreBridge mediaStoreBridge;
    private AbstractCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        parseIntent();
        mediaStoreBridge = new MediaStoreBridge(this);
    }

    private void parseIntent()
    {
        Intent intent = getIntent();
        listType = intent.getIntExtra(MediaStoreBridge.Types.TYPE, 0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderType, Bundle bundle)
    {
        switch (loaderType) {
            case MediaStoreBridge.Types.SONGS:
                resetAdapter(new SongsAdapter(this, null));
                return mediaStoreBridge.getSongs();
            case MediaStoreBridge.Types.ALBUMS:
                resetAdapter(new AlbumsAdapter(this, null));
                return mediaStoreBridge.getAlbums();
            case MediaStoreBridge.Types.ARTISTS:
                resetAdapter(new ArtistsAdapter(this, null));
                return mediaStoreBridge.getArtists();
            case MediaStoreBridge.Types.GENRES:
                resetAdapter(new GenresAdapter(this, null));
                return mediaStoreBridge.getGenres();
            default:
                return null;
        }
    }

    private void resetAdapter(AbstractCustomAdapter newAdapter)
    {
        adapter = newAdapter;
        adapter.setDrawables(ui.player.buttons.drawables);
        customActionBar.setTitle(adapter.getTitle());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        adapter.changeCursor(cursor);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {
        adapter.changeCursor(null);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        preferencesHelper.registerCallBack(this);
        initList();
    }

    @Override
    public void onMediaPlayerAvailable()
    {
        ui.player.reboot();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        Cursor c = adapter.getCursor();
        c.moveToPosition(position);
//        try {
//            SkipShuffleMediaPlayer mediaPlayer = getMediaPlayer();
//            if ( (playlist.getPosition() == position) && ((mediaPlayer.isPlaying())) ) {
//                ImageView imageView = (ImageView) view.findViewById(R.id.track_image);
//                imageView.setImageDrawable(
//                        getResources().getDrawable(
//                                DrawableMapper.getPause(preferencesHelper.getUIType())
//                        )
//                );
//                mediaPlayer.doPause();
//                ui.player.doPause();
//            } else {
//                mediaPlayer.doPlay(playlist.getPosition());
//                ui.player.doPlay();
//            }
//        } catch (NoMediaPlayerException noMediaPlayerException) {
//            handleNoMediaPlayerException(noMediaPlayerException);
//        } catch (PlaylistEmptyException playlistEmptyException) {
//            handlePlaylistEmptyException();
//        }
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
        initList();
    }

    private void initList()
    {
        listView = (ListView) findViewById(R.id.current_list);
        listView.setOnItemClickListener(this);
        listView.setAdapter(null);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(
                listType,
                null,
                this
        );
    }
}
