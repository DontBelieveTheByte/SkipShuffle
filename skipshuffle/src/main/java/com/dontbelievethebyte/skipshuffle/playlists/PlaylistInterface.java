package com.dontbelievethebyte.skipshuffle.playlists;

import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;

public interface PlaylistInterface {

    public Track getFirst () throws PlaylistEmptyException;

    public Track getLast () throws PlaylistEmptyException;

    public Track getCurrent() throws PlaylistEmptyException;

    public Track getAtPosition(int position) throws IndexOutOfBoundsException;

    public Track getNext();

    public Track getPrev();

    public int getSize();

    public int getPosition();

    public void setPosition(int position);

    public void shuffle();
}
