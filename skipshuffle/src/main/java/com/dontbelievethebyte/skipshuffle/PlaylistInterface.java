package com.dontbelievethebyte.skipshuffle;

import java.util.List;

public interface PlaylistInterface {

    public Track getFirst();

    public Track getNext();

    public Track getPrev();

    public int getSize();

    public List<Long> getList();

    public long getCursorPosition();

    public void setCursorPosition(long position);

    public void addTrack(Track track);

    public void removeTrack(Track track);

    public void shuffle();

    public void save();
}
