package com.dontbelievethebyte.skipshuffle.services;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.database.DbHandler;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaScannerService extends IntentService {

    private AudioFileTypeValidator audioFileTypeValidator = new AudioFileTypeValidator();
    private DbHandler dbHandler;
    private RandomPlaylist playlist;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private PreferencesHelper preferencesHelper;

    private class Status {
        public String currentDirectory;
        public String currentFile;
        public boolean isLastFile;
    }

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
        ArrayList<File> directoryPaths = preferencesHelper.getMediaDirectories();
        for (File directory : directoryPaths) {
            recursiveMediaDirectoryScan(directory);
        }
    }

    private void recursiveMediaDirectoryScan(File dir)
    {
        try {
            playlist = new RandomPlaylist(
                    preferencesHelper.getLastPlaylist(),
                    dbHandler
            );
            playlist.setPosition(0);
        } catch (JSONException jsonException){
            Log.d(BaseActivity.TAG, "Json Error");
        }

        File[] files = dir.listFiles();

        List<String> validFiles = new ArrayList<String>();

        for (File file : files) {
            //Check if currentDirectory
            if (file.isDirectory())
                recursiveMediaDirectoryScan(file);
            else {
                //Fill an array list with valid files since when can't trust the last file position index to be a valid audio file.
                if (audioFileTypeValidator.validate(file.getAbsolutePath())) {
                    validFiles.add(file.getAbsolutePath());
                }
            }
        }
        if (validFiles.size() == 0) {
            Status status = new Status();
            status.isLastFile = true;
            broadcastIntentStatus(status);
        } else {
            for (int j = 0; j < validFiles.size(); j++) {
                SystemClock.sleep(3000);
                Status status = new Status();
                status.currentDirectory = dir.getAbsolutePath();
                status.currentFile = validFiles.get(j);
                status.isLastFile = (j == validFiles.size() - 1);
                broadcastIntentStatus(status);
//                Track track = new Track();
//                track.setPath(validFiles.get(j));
//                mediaMetadataRetriever.setDataSource(track.getPath());
//                track.setTitle(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
//                track.setArtist(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
//                track.setAlbum(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
//                track.setAlbum(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
//                dbHandler.addTrack(track);
//                playlist.addTrack(track);
            }

//            playlist.save();
              dbHandler.currate();
//            preferencesHelper.setLastPlaylist(1L);
//            preferencesHelper.setLastPlaylistPosition(0);
        }
    }

    private void broadcastIntentStatus(Status status)
    {
        Intent intent = new Intent(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_DIRECTORY_PROCESSING, status.currentDirectory);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING, status.currentFile);
        intent.putExtra(MediaScannerBroadcastMessageContract.IS_LAST_FILE_PROCESSING, status.isLastFile);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
