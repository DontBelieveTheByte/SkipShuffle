package com.dontbelievethebyte.skipshuffle.playlists;

import android.database.Cursor;

import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist implements PlaylistInterface {

    private Long playlistId;

    private Cursor cursor;

    private int cursorPosition = 0;

    private List<Long> tracksIds = new ArrayList<Long>();

    public RandomPlaylist(Cursor cursor)
    {
        this.cursor = cursor;
    }

    @Override
    public Track getFirst() throws PlaylistEmptyException
    {
        return null;
    }

    @Override
    public Track getLast() throws PlaylistEmptyException {
        return null;
    }

    @Override
    public Track getCurrent() throws PlaylistEmptyException
    {
        if (0 == tracksIds.size())
            throw new PlaylistEmptyException(playlistId);
        return null;
    }

    @Override
    public Track getAtPosition(int position) throws IndexOutOfBoundsException
    {
        if (position > tracksIds.size())
            position = tracksIds.size();

        return null;
    }

    @Override
    public Track getNext()
    {
        if (cursorPosition >= tracksIds.size())
            cursorPosition = 0;
        else
            cursorPosition++;

        return null;
    }

    @Override
    public Track getPrev()
    {
        if (cursorPosition <= 0)
            cursorPosition = 0;
        else
            cursorPosition--;

        return null;
    }

    @Override
    public long getId()
    {
        return playlistId;
    }

    @Override
    public int getPosition()
    {
        return cursorPosition;
    }

    @Override
    public void setPosition(int position)
    {
        if (position > tracksIds.size())
            cursorPosition = tracksIds.size();
        else if (position < 0)
            cursorPosition = 0;
        else
            cursorPosition = position;
    }

    @Override
    public void shuffle()
    {
        Collections.shuffle(tracksIds);
        cursorPosition = 0;
    }

    @Override
    public int getSize()
    {
        return tracksIds.size();
    }

    public List<Track> getAllTracks()
    {
        return null;
    }
}
