package com.dontbelievethebyte.skipshuffle;


public class PlaylistEmptyException extends Exception{
    public PlaylistEmptyException(long playlistId){
        super("Playlist with id : " + Long.toString(playlistId) + " is empty");
    }
}
