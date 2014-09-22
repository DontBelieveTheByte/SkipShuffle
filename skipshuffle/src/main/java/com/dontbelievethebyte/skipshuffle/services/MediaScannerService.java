package com.dontbelievethebyte.skipshuffle.services;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.database.DbHandler;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaScannerService extends IntentService {

    private static final String TAG = "SkipShuffleMediaScan";

    private AudioFileTypeValidator audioFileTypeValidator = new AudioFileTypeValidator();
    private DbHandler dbHandler;
    private RandomPlaylist playlist;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private PreferencesHelper preferencesHelper;

    public MediaScannerService()
    {
        super("SkipShuffleMediaScanner");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        ArrayList<String> directoryPaths = preferencesHelper.getMediaDirectories();
        dbHandler = new DbHandler(getApplicationContext());
        mediaMetadataRetriever = new MediaMetadataRetriever();
        for (String directory : directoryPaths) {
            File dir = new File(directory);
            recursiveMediaDirectoryScan(dir);
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
            Toast.makeText(
                    getApplicationContext(),
                    String.format(getString(R.string.playlist_load_error), preferencesHelper.getLastPlaylist()),
                    Toast.LENGTH_LONG
            ).show();
        }

        File[] files = dir.listFiles();

        List<String> validFiles = new ArrayList<String>();

        for (int i = 0; i < files.length; i++) {
            //Check if directory
            if (files[i].isDirectory())
                recursiveMediaDirectoryScan(files[i]);
            else {
                //Fill an array list with valid files since when can't trust the last file position index to be a valid audio file.
                if (audioFileTypeValidator.validate(files[i].getAbsolutePath())) {
                    validFiles.add(files[i].getAbsolutePath());
                }
            }
        }
        if (validFiles.size() == 0) {
            Toast.makeText(getApplicationContext(), R.string.media_scan_directory_empty, Toast.LENGTH_LONG).show();
            broadcastIntentStatus(
                    null,
                    null,
                    true
            );
        } else {
            for (int j = 0; j < validFiles.size();j++){
                broadcastIntentStatus(dir.getAbsolutePath(), validFiles.get(j), (j == validFiles.size() - 1));
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

    private void broadcastIntentStatus(String directory, String status, boolean isLast)
    {
        Intent intent = new Intent(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_DIRECTORY_PROCESSING, directory);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING, status);
        intent.putExtra(MediaScannerBroadcastMessageContract.IS_LAST_FILE_PROCESSING, isLast);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
