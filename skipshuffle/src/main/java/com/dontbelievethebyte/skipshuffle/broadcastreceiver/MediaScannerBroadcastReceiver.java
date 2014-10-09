package com.dontbelievethebyte.skipshuffle.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dontbelievethebyte.skipshuffle.callback.MediaScannerBroadcastReceiverCallback;
import com.dontbelievethebyte.skipshuffle.services.MediaScannerBroadcastMessageContract;

import java.util.ArrayList;

public class MediaScannerBroadcastReceiver extends BroadcastReceiver {
    private ArrayList<MediaScannerBroadcastReceiverCallback> scannerBroadcastReceiverCallbacks;
    private String currentDirectory;
    private String currentFile;
    private boolean isLast = false;

    public String getCurrentDirectory()
    {
        return currentDirectory;
    }

    public String getCurrentFile()
    {
        return currentFile;
    }

    public boolean isLast()
    {
        return isLast;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        currentDirectory = intent.getStringExtra(
                MediaScannerBroadcastMessageContract.CURRENT_DIRECTORY_PROCESSING
        );
        currentFile = intent.getStringExtra(
                MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING
        );
        isLast = intent.getBooleanExtra(
                MediaScannerBroadcastMessageContract.IS_LAST_FILE_PROCESSING,
                false
        );

        for (MediaScannerBroadcastReceiverCallback scannerBroadcastReceiverCallback : scannerBroadcastReceiverCallbacks) {
            scannerBroadcastReceiverCallback.scannerBroadcastReceiverCallback();
        }
    }

    public void registerCallback(MediaScannerBroadcastReceiverCallback scannerBroadcastReceiverCallback)
    {
        scannerBroadcastReceiverCallbacks.add(scannerBroadcastReceiverCallback);
    }
}
