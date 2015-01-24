/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.playlist.Interface;

import android.database.Cursor;

import com.dontbelievethebyte.sk1pshuffle.media.playlist.PlaylistData;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.exception.PlaylistBuildFailsException;

public interface PlaylistDataBuilderInterface {
    public PlaylistData build(Cursor cursor);
    public void handleBuildPlaylistFailsException(PlaylistBuildFailsException e);
}
