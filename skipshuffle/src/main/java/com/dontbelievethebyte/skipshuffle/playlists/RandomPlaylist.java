package com.dontbelievethebyte.skipshuffle.playlists;

import android.database.Cursor;
import android.provider.MediaStore;

import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.media.MediaStoreBridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist implements PlaylistInterface {

    private MediaStoreBridge mediaStoreBridge;
    private boolean shuffle;
    private int currentPosition = 0;
    private List<String> tracksIds;
    private List<String> shuffledIds;

    public RandomPlaylist(List trackIds, MediaStoreBridge mediaStoreBridge)
    {
        this.tracksIds = trackIds;
        this.mediaStoreBridge = mediaStoreBridge;
    }

    public RandomPlaylist(List trackIds, int initialPosition, MediaStoreBridge mediaStoreBridge)
    {
        this.tracksIds = trackIds;
        this.mediaStoreBridge = mediaStoreBridge;
        setPosition(initialPosition);
    }

    public List<String> getTracksIds()
    {
        return tracksIds;
    }

    @Override
    public Track getFirst() throws PlaylistEmptyException
    {
        return makeTrackFromId(tracksIds.get(0));
    }

    @Override
    public Track getLast() throws PlaylistEmptyException
    {
        return makeTrackFromId(tracksIds.get(tracksIds.size()-1));
    }

    @Override
    public Track getCurrent() throws PlaylistEmptyException
    {
        if (0 == tracksIds.size())
            throw new PlaylistEmptyException(0);
        return makeTrackFromId(tracksIds.get(currentPosition));
    }

    @Override
    public Track getAtPosition(int position) throws IndexOutOfBoundsException
    {
        return makeTrackFromId(tracksIds.get(position));
    }

    @Override
    public Track getNext()
    {
        if (currentPosition >= tracksIds.size())
            currentPosition = 0;
        else
            currentPosition++;

        return makeTrackFromId(tracksIds.get(currentPosition));
    }

    @Override
    public Track getPrev()
    {
        if (currentPosition <= 0)
            currentPosition = 0;
        else
            currentPosition--;

        return makeTrackFromId(tracksIds.get(currentPosition));
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
        shuffledIds = new ArrayList<String>();
        for (String id : tracksIds)
        {
            shuffledIds.add(id);
        }
        Collections.shuffle(shuffledIds);
    }

    @Override
    public int getSize()
    {
        return tracksIds.size();
    }

    private Track makeTrackFromId(String id)
    {
        Track track = new Track();
        Cursor cursor = mediaStoreBridge.getSong(id);
        cursor.moveToFirst();
        track.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
        track.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
        track.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
        track.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
        track.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
        cursor.close();
        return track;
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
