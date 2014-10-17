package com.dontbelievethebyte.skipshuffle.media;


import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

public class MediaStoreBridge {
    private ContentResolver contentResolver;
    private String[] currentProjection;
    private Cursor currentCursor;

    public MediaStoreBridge(ContentResolver contentResolver)
    {
        this.contentResolver = contentResolver;
    }

    public String getSummary()
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

    public Cursor getAlbums()
    {
        currentProjection = Projections.albums;
        currentCursor = contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, //URI
                currentProjection, //Projection
                null, // Selection
                null, // SelectionArgs
                MediaStore.Audio.Albums.ALBUM//Sort order
        );
        return currentCursor;
    }

    public Cursor getSongsFromAlbum(String id)
    {
        currentProjection = Projections.album;
        currentCursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, //URI
                currentProjection, //Projection
                null, // Selection
                null, // SelectionArgs
                MediaStore.Audio.Albums.ALBUM//Sort order
        );
        return currentCursor;
    }

    public Cursor getGenres()
    {
        currentProjection = Projections.genres;
        currentCursor = contentResolver.query(
                MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI, //URI
                currentProjection, //Projection
                null, // Selection
                null, // SelectionArgs
                null//Sort order
        );
        return currentCursor;
    }

    public Cursor getSongsFromGenre(String id) {
        Long genreToGetSongsFrom = Long.valueOf(id);
        currentProjection = Projections.genre;
        currentCursor = contentResolver.query(
                MediaStore.Audio.Genres.Members.getContentUri("external", genreToGetSongsFrom), //URI
                currentProjection, //Projection
                null, // Selection
                null, // SelectionArgs
                MediaStore.Audio.Genres.NAME//Sort order
        );
        return currentCursor;
    }

    public Cursor getArtists()
    {
        currentProjection = Projections.artists;
        currentCursor = contentResolver.query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, //URI
                currentProjection, //Projection
                null, // Selection
                null, // SelectionArgs
                MediaStore.Audio.Artists.ARTIST//Sort order
        );
        return currentCursor;
    }

    public Cursor getAlbumsFromArtist(String id)
    {
        Long artistToGetAlbumsFrom = Long.valueOf(id);
        currentProjection = Projections.artist;
        currentCursor = contentResolver.query(
                MediaStore.Audio.Artists.Albums.getContentUri("external", artistToGetAlbumsFrom), //URI
                Projections.artist, //Projection
                null, // Selection
                null, // SelectionArgs
                MediaStore.Audio.Albums.ALBUM//Sort order
        );
        return currentCursor;
    }

    public Cursor getSongs()
    {
        currentProjection = Projections.songs;
        currentCursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currentProjection,
                MediaStore.Audio.Media.IS_MUSIC,
                null, // SelectionArgs
                MediaStore.Audio.Media.TITLE //Sort order
        );
        return currentCursor;
    }

    public Cursor getSong(String id)
    {
        currentProjection = Projections.song;
        currentCursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, //URI
                currentProjection, //Projection
                MediaStore.Audio.Media._ID + " = ? ", // Selection
                new String[]{id}, // SelectionArgs
                MediaStore.Audio.Media.TITLE //Sort order
        );
        return currentCursor;
    }
}
