/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.exceptions;


public class PlaylistEmptyException extends Exception{
    public PlaylistEmptyException(long playlistId){
        super("Playlist with id : " + Long.toString(playlistId) + " is empty");
    }
}
