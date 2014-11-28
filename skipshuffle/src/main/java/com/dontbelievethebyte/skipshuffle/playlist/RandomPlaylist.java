/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.playlist;

import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.media.MediaStoreBridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist {

    private MediaStoreBridge mediaStoreBridge;
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

    public Track getFirst() throws PlaylistEmptyException
    {
        return makeTrackFromId(currentTracksIds.get(0), 0);
    }

    public Track getLast() throws PlaylistEmptyException
    {
        int last = currentTracksIds.size() - 1;
        return makeTrackFromId(currentTracksIds.get(last), last);
    }

    public Track getCurrent() throws PlaylistEmptyException
    {
        if (0 == currentTracksIds.size())
            throw new PlaylistEmptyException(0);
        return makeTrackFromId(currentTracksIds.get(currentPosition), currentPosition);
    }

    public Track getAtPosition(int position) throws IndexOutOfBoundsException
    {
        return makeTrackFromId(currentTracksIds.get(position), position);
    }

    public Track getNext()
    {
        int next;
        if (currentPosition >= currentTracksIds.size())
            next = 0;
        else
            next = currentPosition + 1;

        return makeTrackFromId(currentTracksIds.get(next), next);
    }

    public Track getPrev()
    {
        int prev;
        if (currentPosition <= 0)
            prev = 0;
        else
            prev = currentPosition - 1;

        return makeTrackFromId(currentTracksIds.get(prev), prev);
    }

    public int getPosition()
    {
        return currentPosition;
    }

    public void setPosition(int position)
    {
        if (position > currentTracksIds.size() - 1)
            currentPosition = 0;
        else if (position < 0)
            currentPosition = 0;
        else
            currentPosition = position;
    }

    public void shuffle()
    {
        currentTracksIds = shuffledTrackIds = new ArrayList<String>(trackIds);
        Collections.shuffle(currentTracksIds);
        setPosition(0);
    }

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
        return null != shuffledTrackIds && currentTracksIds == shuffledTrackIds;
    }

    public void setShuffle(boolean shuffle)
    {
        if (shuffle)
            if (null == shuffledTrackIds)
                shuffle();
            else
                currentTracksIds = shuffledTrackIds;
        else
            currentTracksIds = trackIds;
    }
}
