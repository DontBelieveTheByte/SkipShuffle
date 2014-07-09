package com.dontbelievethebyte.skipshuffle;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SkipShuffleMediaScannerService extends IntentService {

    private static final String TAG = "SkipShuffleMediaScan";

    private AudioFileTypeValidator _audioFileTypeValidator = new AudioFileTypeValidator();

    private List<File> mediaListReference ;

    public SkipShuffleMediaScannerService(){
        super("SkipShuffleMediaScanner");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String[] directoryPaths = intent.getStringArrayExtra(BroadcastMessageInterface.DIRECTORIES_LIST);
        for (String directory : directoryPaths) {
            File dir = new File(directory);
            recursiveFileList(dir);
        }
    }

    private void recursiveFileList(File dir) {

        File[] files = dir.listFiles();

        List<String> validFiles = new ArrayList<String>();

        for (int i = 0; i < files.length; i++) {
            //Check if directory
            if (files[i].isDirectory())
                recursiveFileList(files[i]);
            else {
                //Fill an array list with valid files since when can't trust the last file position index to be a valid audio file.
                if (_audioFileTypeValidator.VALID == _audioFileTypeValidator.validate(files[i].getAbsolutePath())) {
                    validFiles.add(files[i].getAbsolutePath());
                }
            }
        }

        for (int j = 0; j < validFiles.size();j++){
            broadcastIntentStatus(dir.getAbsolutePath(), validFiles.get(j), (j == validFiles.size() - 1) ? true : false);
            //Add to database here.
            SystemClock.sleep(3000);
        }
    }

    private void broadcastIntentStatus(String directory, String status, boolean isLast){
        Intent intent = new Intent(BroadcastMessageInterface.CURRENT_FILE_PROCESSING);
        intent.putExtra(BroadcastMessageInterface.CURRENT_DIRECTORY_PROCESSING, directory);
        intent.putExtra(BroadcastMessageInterface.CURRENT_FILE_PROCESSING, status);
        intent.putExtra(BroadcastMessageInterface.IS_LAST_FILE_PROCESSING, isLast);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
