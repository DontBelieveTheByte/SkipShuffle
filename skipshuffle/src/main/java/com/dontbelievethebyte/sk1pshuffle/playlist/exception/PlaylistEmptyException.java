/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.playlist.exception;


public class PlaylistEmptyException extends Exception{
    public PlaylistEmptyException(long playlistId){
        super("Playlist with id : " + Long.toString(playlistId) + " is empty");
    }
}
