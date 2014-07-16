package com.dontbelievethebyte.skipshuffle;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SkipShuffleMediaScannerService extends IntentService {

    private static final String TAG = "SkipShuffleMediaScan";

    private AudioFileTypeValidator _audioFileTypeValidator = new AudioFileTypeValidator();

    private PlaylistInterface _playlist ;

    private SkipShuffleDbHandler _skipShuffleDbHandler;


    public SkipShuffleMediaScannerService(){
        super("SkipShuffleMediaScanner");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String[] directoryPaths = intent.getStringArrayExtra(MediaScannerBroadcastMessageContract.DIRECTORIES_LIST);
        _skipShuffleDbHandler = new SkipShuffleDbHandler(getApplicationContext());
        for (String directory : directoryPaths) {
            File dir = new File(directory);
            recursiveMediaDirectoryScan(dir);
        }
    }

    private void recursiveMediaDirectoryScan(File dir) {

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
        RandomPlaylist randomPlaylist = new RandomPlaylist(_skipShuffleDbHandler);
        for (int j = 0; j < validFiles.size();j++){
            broadcastIntentStatus(dir.getAbsolutePath(), validFiles.get(j), (j == validFiles.size() - 1) ? true : false);
            Track track = new Track();
            track.setPath(validFiles.get(j));
            _skipShuffleDbHandler.addTrack(track);
        }
    }

    private void broadcastIntentStatus(String directory, String status, boolean isLast){
        Intent intent = new Intent(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_DIRECTORY_PROCESSING, directory);
        intent.putExtra(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING, status);
        intent.putExtra(MediaScannerBroadcastMessageContract.IS_LAST_FILE_PROCESSING, isLast);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
