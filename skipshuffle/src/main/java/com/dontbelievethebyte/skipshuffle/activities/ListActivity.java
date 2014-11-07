package com.dontbelievethebyte.skipshuffle.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.adapters.AbstractCustomAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.AlbumsAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.ArtistsAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.GenresAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.SongsAdapter;
import com.dontbelievethebyte.skipshuffle.listeners.AlbumsClick;
import com.dontbelievethebyte.skipshuffle.listeners.ArtistsClick;
import com.dontbelievethebyte.skipshuffle.listeners.GenresClick;
import com.dontbelievethebyte.skipshuffle.listeners.SongsClick;
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

public class ListActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView listView;
    private int listType;
    private MediaStoreBridge mediaStoreBridge;
    private AbstractCustomAdapter adapter;
    private AdapterView.OnItemClickListener clickListener;

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
                resetContentList(new SongsAdapter(this));
                clickListener = new SongsClick();
                return mediaStoreBridge.getSongs();
            case MediaStoreBridge.Types.ALBUMS:
                resetContentList(new AlbumsAdapter(this));
                clickListener = new AlbumsClick();
                return mediaStoreBridge.getAlbums();
            case MediaStoreBridge.Types.ARTISTS:
                resetContentList(new ArtistsAdapter(this));
                clickListener = new ArtistsClick();
                return mediaStoreBridge.getArtists();
            case MediaStoreBridge.Types.GENRES:
                resetContentList(new GenresAdapter(this));
                clickListener = new GenresClick();
                return mediaStoreBridge.getGenres();
            default:
                return null;
        }
    }

    private void resetContentList(AbstractCustomAdapter newAdapter)
    {
        adapter = newAdapter;
        adapter.setDrawables(ui.player.buttons.drawables);
        customActionBar.setTitle(adapter.getTitle());
        listView.setOnItemClickListener(clickListener);
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
        loadType(listType);
    }

    @Override
    public void onMediaPlayerAvailable()
    {
        ui.player.reboot();
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

    private void initList()
    {
        listView = (ListView) findViewById(R.id.current_list);
        listView.setAdapter(null);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);

    }

    private void loadType(int contentType)
    {
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(
                contentType,
                null,
                this
        );
    }
}
