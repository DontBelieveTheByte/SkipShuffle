package com.dontbelievethebyte.skipshuffle;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SkipShuffleDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "skipshuffle.db";
    private static final String TABLE_TRACKS = "tracks";
    private static final String TABLE_PLAYLIST = "playlist";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TRACK_ID = "track_id";
    public static final String COLUMN_LAST_PLAY_TIMESTAMP = "play_time";
    public static final String COLUMN_PATH = "path";
    public static final String COLUMN_CHECKSUM_ID = "checksum";


    public SkipShuffleDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TRACKS_TABLE = "CREATE TABLE " +
                TABLE_TRACKS + "(" +
                COLUMN_CHECKSUM_ID + " VARCHAR(32) PRIMARY KEY," +
                COLUMN_PATH + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TRACKS_TABLE);

        String CREATE_PLAYLIST_TABLE = "CREATE TABLE " +
                TABLE_PLAYLIST + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TRACK_ID + " INTEGER," +
                COLUMN_LAST_PLAY_TIMESTAMP + " DATETIME" +
                ")";
        sqLiteDatabase.execSQL(CREATE_PLAYLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYLIST);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TRACKS);
        onCreate(sqLiteDatabase);
    }

    public void addTrack(Track track) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHECKSUM_ID, track.getChecksum());
        values.put(COLUMN_PATH, track.getPath());

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.insert(TABLE_TRACKS, null, values);
        sqLiteDatabase.close();
    }

    public Track getTrack(String checksum){

        return new Track();
    }

    public void removeAllTracks(){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_TRACKS, null, null);
        sqLiteDatabase.delete(TABLE_PLAYLIST, null, null);
        sqLiteDatabase.close();
    }

    public void addToPlaylist(){

    }

    public void getLastPlayed(){
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        sqLiteDatabase.query(TABLE_PLAYLIST, COLUMN_CHECKSUM_ID, "WHERE ",null );
    }


}
