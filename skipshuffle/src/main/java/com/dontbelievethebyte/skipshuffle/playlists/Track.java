/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.playlists;

import android.database.Cursor;
import android.provider.MediaStore;

public class Track {
    private long id;
    private String path;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String duration;
    private int position;

    public Track(){}

    public Track(Cursor cursor, int position)
    {
        cursor.moveToFirst();
        setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
        setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
        setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
        setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
        setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
        setPosition(position);
        cursor.close();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getAlbum()
    {
        return album;
    }

    public void setAlbum(String album)
    {
        this.album = album;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getDuration()
    {
        return duration;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }
}
