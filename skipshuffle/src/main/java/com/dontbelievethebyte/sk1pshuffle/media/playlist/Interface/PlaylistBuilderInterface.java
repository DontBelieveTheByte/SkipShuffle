/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.playlist.Interface;

import com.dontbelievethebyte.sk1pshuffle.media.playlist.PlaylistData;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.exception.PlaylistBuildFailsException;

public interface PlaylistBuilderInterface {
    public void setPlaylist(PlaylistData playlistData);
    public void handleBuildPlaylistFailsException(PlaylistBuildFailsException e);
}
