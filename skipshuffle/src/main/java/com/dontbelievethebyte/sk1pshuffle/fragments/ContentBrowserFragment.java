/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.activities.ContentBrowserActivity;
import com.dontbelievethebyte.sk1pshuffle.listeners.AlbumsClick;
import com.dontbelievethebyte.sk1pshuffle.listeners.ArtistsClick;
import com.dontbelievethebyte.sk1pshuffle.listeners.GenresClick;
import com.dontbelievethebyte.sk1pshuffle.listeners.SongsClick;
import com.dontbelievethebyte.sk1pshuffle.media.ContentTypes;
import com.dontbelievethebyte.sk1pshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.AlbumsAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.ArtistsAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.GenresAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.SongsAdapter;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;

public class ContentBrowserFragment extends Fragment implements UIElementCompositeInterface,
                                                                LoaderManager.LoaderCallbacks<Cursor> {
    ListView listView;
    SimpleCursorAdapter adapter;

    public ContentBrowserFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_content_browser, container, false);
        listView = (ListView) rootView.findViewById(R.id.current_list);
        getLoaderManager().initLoader(
                parseActivityIntent(),
                null,
                this
        );
        return rootView;
    }

    private int parseActivityIntent()
    {
        Intent intent = getActivity().getIntent();
        return intent.getIntExtra(ContentBrowserActivity.CONTENT_TYPE, 0);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int loaderId, Bundle args)
    {
        MediaStoreBridge mediaStoreBridge = new MediaStoreBridge(getActivity());

        if (ContentTypes.SONGS.ordinal() == loaderId) {

            adapter = new SongsAdapter(getActivity());
            listView.setOnItemClickListener(new SongsClick());
            return mediaStoreBridge.getSongs();

        } else if (ContentTypes.ARTISTS.ordinal() == loaderId) {

            adapter = new ArtistsAdapter(getActivity());
            listView.setOnItemClickListener(new ArtistsClick());
            return mediaStoreBridge.getArtists();

        } else if (ContentTypes.ALBUMS.ordinal() == loaderId) {

            adapter = new AlbumsAdapter(getActivity());
            listView.setOnItemClickListener(new AlbumsClick());
            mediaStoreBridge.getAlbums();

        } else if (ContentTypes.GENRES.ordinal() == loaderId) {

            adapter = new GenresAdapter(getActivity());
            listView.setOnItemClickListener(new GenresClick());
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
}