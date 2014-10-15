package com.dontbelievethebyte.skipshuffle.services;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.persistance.DbHandler;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class MediaScannerService extends IntentService {

    private AudioFileTypeValidator audioFileTypeValidator = new AudioFileTypeValidator();
    private DbHandler dbHandler;
    private MediaScanStatus mediaScanStatus;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private PreferencesHelper preferencesHelper;
    private HashSet<File> mediaDirectories;

    public MediaScannerService()
    {
        super("SkipShuffleMediaScanner");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        dbHandler = new DbHandler(getApplicationContext());
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaScanStatus = new MediaScanStatus();
        mediaDirectories = new HashSet<File>();
        prepareDirectories();
        for (File directory: mediaDirectories) {
            scanMediaDirectoryScan(directory);
        }
        onLastFile();
    }

    private void addToPlayer(Track track)
    {
        addToSongs(track);
        addToAlbums(track);
        addToArtist(track);
        addToGenre(track);
    }

    private void addToAlbums(Track track)
    {

    }

    private void addToGenre(Track track)
    {

    }

    private void addToSongs(Track track)
    {
//        try {
//            playlist = new RandomPlaylist(
//                    preferencesHelper.getLastPlaylist(),
//                    dbHandler
//            );
//            playlist.addTrack(track);
//            playlist.setPosition(0);
//            playlist.save();
//        } catch (JSONException jsonException){
//            Log.d(BaseActivity.TAG, "Json Error");
//        }
    }

    private void addToArtist(Track track)
    {

    }

    private void broadcastIntentStatus(MediaScanStatus status)
    {
        Intent intent = new Intent(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_DIRECTORY_PROCESSING, status.getCurrentDirectory());
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING, status.getCurrentFile());
        intent.putExtra(MediaScannerBroadcastMessageContract.IS_LAST_FILE_PROCESSING, status.isLastFile());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private ArrayList<File> filterValidFiles(File[] files)
    {
        ArrayList<File> validFiles = new ArrayList<File>();
        for (File file : files) {
            if (!file.isDirectory() && audioFileTypeValidator.validate(file.getAbsolutePath())) {
                validFiles.add(file);
            }
        }
        return validFiles;
    }

    private Track createTrackFromFile(File audioFile)
    {
        Track track = new Track();
        mediaMetadataRetriever.setDataSource(audioFile.getAbsolutePath());
        track.setPath(audioFile.getAbsolutePath());
        track.setTitle(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        track.setArtist(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        track.setAlbum(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
        track.setAlbum(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
        Log.d(BaseActivity.TAG, "title is : "  + track.getTitle().toString());
//        dbHandler.addTrack(track);
        return track;
    }
    private void prepareDirectories()
    {
        ArrayList<File> directoryPaths = preferencesHelper.getMediaDirectories();
        for (File directory : directoryPaths) {
            if (directory.isDirectory()) {
                mediaDirectories.add(directory);
                prepareSubDirectories(directory);
            }
        }
    }

    private void prepareSubDirectories(File directory)
    {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                mediaDirectories.add(file);
                prepareSubDirectories(file);
            }
        }
    }

    private void scanMediaDirectoryScan(File directory)
    {
        ArrayList<File> validFiles = filterValidFiles(directory.listFiles());
        for (File file : validFiles) {
            SystemClock.sleep(2000);
            Track track = createTrackFromFile(file);
            addToPlayer(track);
            mediaScanStatus.setCurrentDirectory(directory.getPath());
            mediaScanStatus.setCurrentFile(track.getPath());
            mediaScanStatus.setLastFile(false);
            broadcastIntentStatus(mediaScanStatus);
        }
    }

    private void onLastFile()
    {
        preferencesHelper.setLastPlaylist(1L);
        preferencesHelper.setLastPlaylistPosition(0);
        mediaScanStatus.setCurrentFile(null);
        mediaScanStatus.setCurrentDirectory(null);
        mediaScanStatus.setLastFile(true);
        broadcastIntentStatus(mediaScanStatus);
    }

}
