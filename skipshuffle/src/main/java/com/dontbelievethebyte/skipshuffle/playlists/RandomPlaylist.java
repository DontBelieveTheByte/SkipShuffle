package com.dontbelievethebyte.skipshuffle.playlists;

import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.media.MediaStoreBridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist implements PlaylistInterface {

    private MediaStoreBridge mediaStoreBridge;
    private boolean shuffle;
    private int currentPosition = 0;
    private List<String> currentTracksIds;
    private List<String> trackIds;
    private List<String> shuffledTrackIds;


    public RandomPlaylist(List<String> trackIds, MediaStoreBridge mediaStoreBridge)
    {
        this.trackIds = trackIds;
        this.currentTracksIds = this.trackIds;
        this.mediaStoreBridge = mediaStoreBridge;
    }

    public RandomPlaylist(List trackIds, int initialPosition, MediaStoreBridge mediaStoreBridge)
    {
        this.currentTracksIds = trackIds;
        this.mediaStoreBridge = mediaStoreBridge;
        setPosition(initialPosition);
    }

    public List<String> getCurrentTracksIds()
    {
        return currentTracksIds;
    }

    @Override
    public Track getFirst() throws PlaylistEmptyException
    {
        return makeTrackFromId(currentTracksIds.get(0), 0);
    }

    @Override
    public Track getLast() throws PlaylistEmptyException
    {
        int last = currentTracksIds.size() - 1;
        return makeTrackFromId(currentTracksIds.get(last), last);
    }

    @Override
    public Track getCurrent() throws PlaylistEmptyException
    {
        if (0 == currentTracksIds.size())
            throw new PlaylistEmptyException(0);
        return makeTrackFromId(currentTracksIds.get(currentPosition), currentPosition);
    }

    @Override
    public Track getAtPosition(int position) throws IndexOutOfBoundsException
    {
        return makeTrackFromId(currentTracksIds.get(position), position);
    }

    @Override
    public Track getNext()
    {
        int next;
        if (currentPosition >= currentTracksIds.size())
            next = 0;
        else
            next = currentPosition + 1;

        return makeTrackFromId(currentTracksIds.get(next), next);
    }

    @Override
    public Track getPrev()
    {
        int prev;
        if (currentPosition <= 0)
            prev = 0;
        else
            prev = currentPosition - 1;

        return makeTrackFromId(currentTracksIds.get(prev), prev);
    }

    @Override
    public int getPosition()
    {
        return currentPosition;
    }

    @Override
    public void setPosition(int position)
    {
        if (position > currentPosition)
            currentPosition = position;
        else if (position < 0)
            currentPosition = 0;
        else
            currentPosition = position;
    }

    @Override
    public void shuffle()
    {
        shuffledTrackIds = new ArrayList<String>(trackIds);
        Collections.shuffle(shuffledTrackIds);
        currentTracksIds = shuffledTrackIds;
        setPosition(0);
    }

    @Override
    public int getSize()
    {
        return currentTracksIds.size();
    }

    private Track makeTrackFromId(String id, int trackPosition)
    {
        return new Track(mediaStoreBridge.getSong(id), trackPosition);
    }

    public boolean isShuffle()
    {
        return shuffle;
    }

    public void setShuffle(boolean shuffle)
    {
        this.shuffle = shuffle;
    }
}
