/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.playlist;

import com.dontbelievethebyte.sk1pshuffle.media.MediaStoreBridge;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.track.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist {


    private PlaylistData playlistData;
    private MediaStoreBridge mediaStoreBridge;

    public RandomPlaylist(PlaylistData playlistData, MediaStoreBridge mediaStoreBridge)
    {
        this.playlistData = playlistData;
        shuffle();
        this.mediaStoreBridge = mediaStoreBridge;
    }

    public PlaylistData getPlaylistData()
    {
        return playlistData;
    }

    public Track getCurrent() throws PlaylistEmptyException
    {
        List<String> currentTrackIds = (playlistData.isShuffleOn) ? playlistData.shuffledTrackIds : playlistData.trackIds;
        if (0 == currentTrackIds.size())
            throw new PlaylistEmptyException(0);
        return makeTrackFromId(
                currentTrackIds.get(playlistData.currentPosition),
                playlistData.currentPosition
        );
    }

    public Track getAtPosition(int position) throws IndexOutOfBoundsException
    {
        return makeTrackFromId(
                (playlistData.isShuffleOn) ? playlistData.shuffledTrackIds.get(position) : playlistData.trackIds.get(position),
                position
        );
    }

    public int getCurrentPosition()
    {
        return playlistData.currentPosition;
    }

    public void setPosition(int position)
    {
        List<String> currentTrackIds = (playlistData.isShuffleOn) ? playlistData.shuffledTrackIds : playlistData.trackIds;
        if (position > currentTrackIds.size() - 1 || position < 0)
            playlistData.currentPosition = 0;
        else
            playlistData.currentPosition = position;
    }

    public void shuffle()
    {
        if (null == playlistData.shuffledTrackIds)
            playlistData.shuffledTrackIds = new ArrayList<>(playlistData.trackIds);
        Collections.shuffle(playlistData.shuffledTrackIds);
    }

    public int getSize()
    {
        return (playlistData.isShuffleOn) ? playlistData.shuffledTrackIds.size() : playlistData.trackIds.size();
    }

    private Track makeTrackFromId(String id, int trackPosition)
    {
        return new Track(mediaStoreBridge.getSong(id), trackPosition);
    }

    public boolean isShuffle()
    {
        return playlistData.isShuffleOn;
    }

    public void setShuffle(boolean shuffle)
    {
        playlistData.isShuffleOn = shuffle;
        setPosition(0);
    }
}
