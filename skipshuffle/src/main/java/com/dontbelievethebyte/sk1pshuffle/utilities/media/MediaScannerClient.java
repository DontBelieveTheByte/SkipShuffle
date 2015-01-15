/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.utilities.media;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

public class MediaScannerClient implements MediaScannerConnection.MediaScannerConnectionClient {
    private MediaScannerConnection mediaScannerConnection;
    private boolean isScanningMedia = false;



    private MediaScannerHelper mediaScannerHelper;

    public MediaScannerClient(Activity activity)
    {
        mediaScannerConnection = new MediaScannerConnection(
                activity,
                this
        );
    }

    public void setMediaScannerHelper(MediaScannerHelper mediaScannerHelper)
    {
        this.mediaScannerHelper = mediaScannerHelper;
    }

    public boolean isScanningMedia()
    {
        return isScanningMedia;
    }

    public void doScan()
    {
        mediaScannerConnection.connect();
    }

    @Override
    public void onMediaScannerConnected()
    {
        isScanningMedia = true;
        mediaScannerConnection.scanFile(
                Environment.getDownloadCacheDirectory().getAbsolutePath(),
                "audio/*"
        );
        mediaScannerHelper.onStartMediaScan();
    }

    @Override
    public void onScanCompleted(String s, Uri uri)
    {
        isScanningMedia = false;
        mediaScannerHelper.onStopMediaScan();
    }
}
