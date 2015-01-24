/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.playlist.builder;

import android.database.Cursor;

import com.dontbelievethebyte.sk1pshuffle.media.playlist.Interface.PlaylistDataBuilderInterface;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.PlaylistData;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.exception.PlaylistBuildFailsException;

public class SongsPlaylistDataBuilder implements PlaylistDataBuilderInterface {
    @Override
    public PlaylistData build(Cursor cursor)
    {
        return null;
    }

    @Override
    public void handleBuildPlaylistFailsException(PlaylistBuildFailsException e)
    {

    }
}
