/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.playlist.exception;


public class PlaylistBuildFailsException extends Exception{
    public PlaylistBuildFailsException(long playlistId){
        super("Playlist with id : " + Long.toString(playlistId) + " is empty");
    }
}
