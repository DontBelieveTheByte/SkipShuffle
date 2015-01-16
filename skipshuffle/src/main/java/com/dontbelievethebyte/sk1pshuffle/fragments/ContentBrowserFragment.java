/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.media.ContentTypes;
import com.dontbelievethebyte.sk1pshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;

public class ContentBrowserFragment extends Fragment implements UIElementCompositeInterface,
                                                                LoaderManager.LoaderCallbacks<Cursor> {


    ListView listView;
    SimpleCursorAdapter adapter;

    public ContentBrowserFragment()
    {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        // Create an empty adapter we will use to display the loaded data.

//        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_content_browser, container, false);
        listView = (ListView) rootView.findViewById(R.id.current_list);
        getLoaderManager().initLoader(
                0,
                null,
                this
        );
        return rootView;
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int loaderId, Bundle args)
    {
        MediaStoreBridge mediaStoreBridge = new MediaStoreBridge(getActivity());

        if (ContentTypes.SONGS.ordinal() == loaderId) {
            return mediaStoreBridge.getSongs();
        } else if (ContentTypes.ARTISTS.ordinal() == loaderId) {
            return mediaStoreBridge.getArtists();
        } else if (ContentTypes.ALBUMS.ordinal() == loaderId) {
            mediaStoreBridge.getAlbums();
        } else if (ContentTypes.GENRES.ordinal() == loaderId) {
            mediaStoreBridge.getGenres();
        }
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor)
    {
        adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_2,
                null,
                new String[] {MediaStore.Audio.Media.TITLE},
                new int[] { android.R.id.text1},
                0
        );
        adapter.changeCursor(cursor);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader)
    {
        adapter.changeCursor(null);
    }
}