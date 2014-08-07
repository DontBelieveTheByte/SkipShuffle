package com.dontbelievethebyte.skipshuffle;

import java.util.List;

public interface PlaylistInterface {

    public Track getFirst();

    public Track getCurrent();

    public Track getAtPosition(int position);

    public Track getNext();

    public Track getPrev();

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
