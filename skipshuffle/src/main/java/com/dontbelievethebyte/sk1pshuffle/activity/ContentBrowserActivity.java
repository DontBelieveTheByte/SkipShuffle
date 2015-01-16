/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.activity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.media.ContentTypes;
import com.dontbelievethebyte.sk1pshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.AlbumsAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.ArtistsAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.GenresAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.SongsAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.listener.AlbumsItemClickListener;
import com.dontbelievethebyte.sk1pshuffle.media.listener.ArtistsItemClickListener;
import com.dontbelievethebyte.sk1pshuffle.media.listener.GenresItemClickListener;
import com.dontbelievethebyte.sk1pshuffle.media.listener.SongsItemClickListener;
import com.dontbelievethebyte.sk1pshuffle.playlist.Interface.PlaylistBuilderInterface;
import com.dontbelievethebyte.sk1pshuffle.playlist.PlaylistData;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistBuildFailsException;
import com.dontbelievethebyte.sk1pshuffle.ui.element.UIComposition;
import com.dontbelievethebyte.sk1pshuffle.ui.element.UICompositionFactory;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PreferencesHelper;

public class ContentBrowserActivity extends ActionBarActivity implements ThemableActivityInterface,
                                                                         LoaderManager.LoaderCallbacks<Cursor>,
        PlaylistBuilderInterface {

    public final static String CONTENT_TYPE = "com.dontbelievethebyte.CONTENT_TYPE";
    public UIComposition ui;

    private ListView listView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUI();
        getSupportLoaderManager().initLoader(
                parseActivityIntent(),
                null,
                this
        );
    }

    private int parseActivityIntent()
    {
        Intent intent = getIntent();
        return intent.getIntExtra(ContentBrowserActivity.CONTENT_TYPE, 0);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int loaderId, Bundle args)
    {
        MediaStoreBridge mediaStoreBridge = new MediaStoreBridge(this);

        if (ContentTypes.SONGS.ordinal() == loaderId) {

            adapter = new SongsAdapter(this);
            listView.setOnItemClickListener(new SongsItemClickListener(this));
            return mediaStoreBridge.getSongs();
        } else if (ContentTypes.ARTISTS.ordinal() == loaderId) {

            adapter = new ArtistsAdapter(this);
            listView.setOnItemClickListener(new ArtistsItemClickListener());
            return mediaStoreBridge.getArtists();

        } else if (ContentTypes.ALBUMS.ordinal() == loaderId) {

            adapter = new AlbumsAdapter(this);
            listView.setOnItemClickListener(new AlbumsItemClickListener());
            mediaStoreBridge.getAlbums();

        } else if (ContentTypes.GENRES.ordinal() == loaderId) {

            adapter = new GenresAdapter(this);
            listView.setOnItemClickListener(new GenresItemClickListener());
            mediaStoreBridge.getGenres();
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor)
    {
        adapter.changeCursor(cursor);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader)
    {
        adapter.changeCursor(null);
    }

    @Override
    public void setPlaylist(PlaylistData playlistData)
    {

    }

    @Override
    public void handleBuildPlaylistFailsException(PlaylistBuildFailsException e)
    {

    }
    private void setUI()
    {
        PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext());
        ui = UICompositionFactory.createContentBrowser(
                this,
                preferencesHelper.getUIType()
        );
        listView = (ListView) findViewById(R.id.current_list);
    }
}
