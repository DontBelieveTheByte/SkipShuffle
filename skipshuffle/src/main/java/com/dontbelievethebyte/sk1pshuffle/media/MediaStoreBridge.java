/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.provider.MediaStore;

public class MediaStoreBridge {

    private Context context;
    private ContentResolver contentResolver;

    public MediaStoreBridge(Context context)
    {
        this.context = context;
        contentResolver = context.getContentResolver();
    }

    public CursorLoader getAlbums()
    {
        return  new CursorLoader(
                context,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Projections.albums,
                MediaStore.Audio.Media.IS_MUSIC,
                null, // SelectionArgs
                MediaStore.Audio.Albums.ALBUM //Sort order
        );
    }

    public CursorLoader getSongsFromAlbum(String id)
    {
        return  new CursorLoader(
                context,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Projections.album,
                null,
                null, // SelectionArgs
                MediaStore.Audio.Albums.ALBUM///Sort order
        );
    }

    public CursorLoader getGenres()
    {
        return  new CursorLoader(
                context,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Projections.genres,
                MediaStore.Audio.Media.IS_MUSIC,
                null, // SelectionArgs
                null//Sort order
        );
    }

    public CursorLoader getSongsFromGenre(String id) {
        Long genreToGetSongsFrom = Long.valueOf(id);
        return  new CursorLoader(
                context,
                MediaStore.Audio.Genres.Members.getContentUri("external", genreToGetSongsFrom), //URI
                Projections.genre,
                null,
                null, // SelectionArgs
                MediaStore.Audio.Genres.NAME//Sort order
        );
    }

    public CursorLoader getArtists()
    {
        return  new CursorLoader(
                context,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Projections.artists,
                MediaStore.Audio.Media.IS_MUSIC,
                null, // SelectionArgs
                null//Sort order
        );
    }

    public CursorLoader getAlbumsFromArtist(String id)
    {
        Long artistToGetAlbumsFrom = Long.valueOf(id);
        return  new CursorLoader(
                context,
                MediaStore.Audio.Artists.Albums.getContentUri("external", artistToGetAlbumsFrom), //URI
                Projections.artist,
                null,
                null, // SelectionArgs
                MediaStore.Audio.Albums.ALBUM//Sort order
         );
    }

    public CursorLoader getSongs()
    {
        return  new CursorLoader(
                context,
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Projections.songs,
                MediaStore.Audio.Media.IS_MUSIC,
                null, // SelectionArgs
                MediaStore.Audio.Media.TITLE //Sort order
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

    public String getSummary(String[] currentProjection, Cursor currentCursor)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String column : currentProjection){
            String stringColumnResult = currentCursor.getString(currentCursor.getColumnIndex(column));
            if(stringColumnResult.equals(MediaStore.UNKNOWN_STRING)) {
                stringColumnResult = "UNKNOWN sHIT";
            }
            stringBuilder.append(" *** ");
            stringBuilder.append(column);
            stringBuilder.append(" : ");
            stringBuilder.append(stringColumnResult);
        }
        return stringBuilder.toString();
    }
}
