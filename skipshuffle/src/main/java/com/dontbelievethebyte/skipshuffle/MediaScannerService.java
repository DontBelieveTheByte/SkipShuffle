package com.dontbelievethebyte.skipshuffle;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaScannerService extends IntentService {

    private static final String TAG = "SkipShuffleMediaScan";

    private AudioFileTypeValidator _audioFileTypeValidator = new AudioFileTypeValidator();

    private DbHandler _DbHandler;

    private RandomPlaylist playlist;

    private MediaMetadataRetriever mediaMetadataRetriever;

    public MediaScannerService(){
        super("SkipShuffleMediaScanner");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        PreferencesHelper preferencesHelper = new PreferencesHelper(getApplicationContext());
        String[] directoryPaths = preferencesHelper.getMediaDirectories();
        _DbHandler = new DbHandler(getApplicationContext());
        mediaMetadataRetriever = new MediaMetadataRetriever();
        for (String directory : directoryPaths) {
            File dir = new File(directory.toString());
            recursiveMediaDirectoryScan(dir);
        }
    }

    private void recursiveMediaDirectoryScan(File dir) {

        if(null == playlist){
            playlist = new RandomPlaylist(1, _DbHandler);
        }

        File[] files = dir.listFiles();

        List<String> validFiles = new ArrayList<String>();

        for (int i = 0; i < files.length; i++) {
            //Check if directory
            if (files[i].isDirectory())
                recursiveMediaDirectoryScan(files[i]);
            else {
                //Fill an array list with valid files since when can't trust the last file position index to be a valid audio file.
                if (_audioFileTypeValidator.VALID == _audioFileTypeValidator.validate(files[i].getAbsolutePath())) {
                    validFiles.add(files[i].getAbsolutePath());
                }
            }
        }
        for (int j = 0; j < validFiles.size();j++){
            broadcastIntentStatus(dir.getAbsolutePath(), validFiles.get(j), (j == validFiles.size() - 1) ? true : false);
            Track track = new Track();
            track.setPath(validFiles.get(j));
            mediaMetadataRetriever.setDataSource(track.getPath());
            track.setTitle(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            track.setArtist(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            track.setAlbum(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            track.setAlbum(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
            _DbHandler.addTrack(track);
            playlist.addTrack(track);
        }
        playlist.save();
        Toast.makeText(getApplicationContext(), R.string.media_scan_done, Toast.LENGTH_SHORT);
    }

    private void broadcastIntentStatus(String directory, String status, boolean isLast){
        Intent intent = new Intent(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_DIRECTORY_PROCESSING, directory);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING, status);
        intent.putExtra(MediaScannerBroadcastMessageContract.IS_LAST_FILE_PROCESSING, isLast);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
