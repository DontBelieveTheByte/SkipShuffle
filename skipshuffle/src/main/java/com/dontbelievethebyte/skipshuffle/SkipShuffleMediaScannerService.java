package com.dontbelievethebyte.skipshuffle;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
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
        String directoryPath = intent.getStringExtra(BroadcastMessageInterface.DIRECTORIES_LIST);
        File directory = new File(directoryPath);
        recursiveFileList(directory);
        Log.d(TAG, "Handling intent : " + directory.getAbsolutePath());
    }

    private void recursiveFileList(File dir) {
        //Get list of all files and folders in directory
        File[] files = dir.listFiles();

        //For all files and folders in directory
        for (int i = 0; i < files.length; i++) {
            //Check if directory
            if (files[i].isDirectory())
                //Recursively call file list function on the new directory
                recursiveFileList(files[i]);
            else {
                //If not a directory, print the file path
                if (_audioFileTypeValidator.VALID == _audioFileTypeValidator.validate(files[i].getAbsolutePath())) {
                    broadcastIntentStatus(files[i].getAbsolutePath());
                    //Add to database here...
                    //mediaListReference.add(files[i]);
                    SystemClock.sleep(4000);
                }
            }
        }
    }

    private void broadcastIntentStatus(String status){
        Intent intent = new Intent(BroadcastMessageInterface.CURRENT_FILE_PROCESSING);
        intent.putExtra(BroadcastMessageInterface.CURRENT_FILE_PROCESSING, status);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
