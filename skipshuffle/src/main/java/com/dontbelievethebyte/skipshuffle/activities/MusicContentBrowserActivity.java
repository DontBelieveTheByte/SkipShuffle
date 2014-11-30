/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

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
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.listeners.AlbumsClick;
import com.dontbelievethebyte.skipshuffle.listeners.ArtistsClick;
import com.dontbelievethebyte.skipshuffle.listeners.GenresClick;
import com.dontbelievethebyte.skipshuffle.listeners.SongsClick;
import com.dontbelievethebyte.skipshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.skipshuffle.ui.elements.UICompositionFactory;

public class MusicContentBrowserActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>{

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
                clickListener = new SongsClick(this);
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
//        try {
//            SkipShuffleMediaPlayer mp = getMediaPlayer();
//            mp.setPlaylist(new RandomPlaylist(cursor));
//        } catch (NoMediaPlayerException e) {
//            e.printStackTrace();
//        }
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
        initList();
        loadType(listType);
    }

    @Override
    public void onMediaPlayerAvailable()
    {
        ui.player.reboot();
    }

    public AbstractCustomAdapter getAdapter()
    {
        return adapter;
    }

    @Override
    protected void setUI(Integer type)
    {
        try {
            ui = UICompositionFactory.makeMusicContentBrowser(this, type);
            ui.player.reboot();
        } catch (NoMediaPlayerException e) {
            handleNoMediaPlayerException(e);
        }
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

    @Override
    public void onPlayerStateChanged()
    {

    }

    @Override
    public void onViewModeChanged()
    {

    }
}
