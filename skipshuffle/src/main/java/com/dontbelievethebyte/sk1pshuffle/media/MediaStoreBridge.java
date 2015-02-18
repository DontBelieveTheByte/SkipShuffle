/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class MediaStoreBridge {

    private ContentResolver contentResolver;

    public MediaStoreBridge(Context context)
    {
        contentResolver = context.getContentResolver();
    }

    public Cursor getAlbums()
    {
        return  contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                Projections.albums,
                null,
                null, // SelectionArgs
                null //Sort order
        );
    }

    public Cursor getSongsFromAlbum(String id)
    {
        return  contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Projections.album,
                null,
                null, // SelectionArgs
                MediaStore.Audio.Albums.ALBUM///Sort order
        );
    }

    public Cursor getGenres()
    {
        return  contentResolver.query(
                MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,
                Projections.genres,
                null,
                null, // SelectionArgs
                null//Sort order
        );

    }

    public Cursor getSongsFromGenre(String id) {
        Long genreToGetSongsFrom = Long.valueOf(id);
        return  contentResolver.query(
                MediaStore.Audio.Genres.Members.getContentUri("external", genreToGetSongsFrom), //URI
                Projections.genre,
                null,
                null, // SelectionArgs
                MediaStore.Audio.Genres.NAME//Sort order
        );
    }

    public Cursor getArtists()
    {
        return  contentResolver.query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                Projections.artists,
                null,
                null, // SelectionArgs
                null//Sort order
        );
    }

    public Cursor getAlbumsFromArtist(String id)
    {
        Long artistToGetAlbumsFrom = Long.valueOf(id);
        return  contentResolver.query(
                MediaStore.Audio.Artists.Albums.getContentUri("external", artistToGetAlbumsFrom), //URI
                Projections.artist,
                null,
                null, // SelectionArgs
                MediaStore.Audio.Albums.ALBUM//Sort order
         );
    }

    public Cursor getAllSongs()
    {
        return  contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Projections.songs,
                MediaStore.Audio.Media.IS_MUSIC,
                null, // SelectionArgs
                MediaStore.Audio.Media.TITLE //Sort order
        );
    }

    public Cursor getSong(String id)
    {
        return  contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Projections.song,
                MediaStore.Audio.Media._ID + " = ? ", // Selection
                new String[]{id}, // SelectionArgs
                null //Sort order
        );
    }

}
