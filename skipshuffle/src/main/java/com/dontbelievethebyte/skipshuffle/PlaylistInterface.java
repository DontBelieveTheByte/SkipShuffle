package com.dontbelievethebyte.skipshuffle;

import java.util.List;

public interface PlaylistInterface {

    public Track getFirst();

    public Track getNext();

    public Track getPrev();

    public List<Integer> getList();

    public int getCursorPosition();

    public int setCursorPosition(int position);

    public int addTrack(Track track);

    public void removeTrack(Track track);

    public void shuffle();

    public void save();
}
