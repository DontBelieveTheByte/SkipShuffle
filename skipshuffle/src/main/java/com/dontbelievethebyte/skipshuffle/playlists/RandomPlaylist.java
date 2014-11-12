package com.dontbelievethebyte.skipshuffle.playlists;

import android.database.Cursor;

import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPlaylist implements PlaylistInterface {

    private Long playlistId;

    private boolean shuffle;

    private Cursor cursor;

    private int cursorPosition = 0;

    private List<Integer> shuffleIndex;

    public RandomPlaylist(Cursor cursor)
    {
        this.cursor = cursor;
    }

    @Override
    public Track getFirst() throws PlaylistEmptyException
    {
        cursor.moveToFirst();
        return new Track(cursor);
    }

    @Override
    public Track getLast() throws PlaylistEmptyException
    {
        cursor.moveToLast();
        return new Track(cursor);
    }

    @Override
    public Track getCurrent() throws PlaylistEmptyException
    {
        if (0 == cursor.getCount())
            throw new PlaylistEmptyException(playlistId);
        return new Track(cursor);
    }

    @Override
    public Track getAtPosition(int position) throws IndexOutOfBoundsException
    {
        if (position > cursor.getCount())
            position = cursor.getCount();

        cursor.moveToPosition(position);
        return new Track(cursor);
    }

    @Override
    public Track getNext()
    {
        if (cursorPosition >= cursor.getCount())
            cursorPosition = 0;
        else
            cursorPosition++;

        cursor.moveToPosition(cursorPosition);
        return new Track(cursor);
    }

    @Override
    public Track getPrev()
    {
        if (cursorPosition <= 0)
            cursorPosition = 0;
        else
            cursorPosition--;
        cursor.moveToPosition(cursorPosition);
        return new Track(cursor);
    }

    @Override
    public int getPosition()
    {
        return cursorPosition;
    }

    @Override
    public void setPosition(int position)
    {
        if (position > cursor.getCount())
            cursorPosition = cursor.getCount();
        else if (position < 0)
            cursorPosition = 0;
        else
            cursorPosition = position;
    }

    @Override
    public void shuffle()
    {
        shuffleIndex = new ArrayList<Integer>();
        for (int i = 0; i<cursor.getCount(); i++ )
        {
            shuffleIndex.add(i);
        }
        Collections.shuffle(shuffleIndex);
    }

    @Override
    public int getSize()
    {
        return cursor.getCount();
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
