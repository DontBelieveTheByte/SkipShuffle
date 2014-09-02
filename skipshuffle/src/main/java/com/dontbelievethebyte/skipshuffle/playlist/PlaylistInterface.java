package com.dontbelievethebyte.skipshuffle.playlist;

import java.util.List;

public interface PlaylistInterface {

    public Track getFirst () throws PlaylistEmptyException;

    public Track getCurrent() throws PlaylistEmptyException;

    public Track getAtPosition(int position) throws IndexOutOfBoundsException;

    public Track getNext();

    public Track getPrev();

    public long getId();

    public int getSize();

    public List<Long> getList();

    public int getPosition();

    public void setPosition(int position);

    public void addTrack(Track track);

    public void removeTrack(Track track);

    public void shuffle();

    public void save();

    public Long getPlaylistId();

    public void setPlaylistId(Long playlistId);
}
