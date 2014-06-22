package com.dontbelievethebyte.skipshuffle;

import android.util.Log;

import java.io.File;
import java.util.List;

public class MediaScanner {

    private static final String TAG = "SkipShuffleMediaScan";

    private AudioFileTypeValidator _audioFileTypeValidator;

    private List<File> mediaListReference ;

    public MediaScanner(AudioFileTypeValidator audioFileTypeValidator) {
        _audioFileTypeValidator = audioFileTypeValidator;
    }
    public void scanMedia (List<File> files, List<File> inPlaceMediaList) {

        mediaListReference = inPlaceMediaList;
        //List<File> foundDirectories;

        for(File file:files){
            recursiveFileList(file);
        }

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

                    mediaListReference.add(files[i]);
//                    Log.v(TAG, "IS AUDIO" + files[i].getAbsolutePath());
    //            } else {
  //                  Log.v(TAG, "NOT AUDIO" + files[i].getAbsolutePath());
                }
            }
        }
    }

}
