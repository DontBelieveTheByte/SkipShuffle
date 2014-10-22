package com.dontbelievethebyte.skipshuffle.utilities;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public class MediaScannerClient implements MediaScannerConnection.MediaScannerConnectionClient {
    private BaseActivity baseActivity;
    private MediaScannerConnection mediaScannerConnection;
    private boolean isScanningMedia = false;

    public boolean isScanningMedia()
    {
        return isScanningMedia;
    }

    public MediaScannerClient(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
        mediaScannerConnection = new MediaScannerConnection(
                this.baseActivity,
                this
        );
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
        baseActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        baseActivity.onStartMediaScan();
                    }
                }
        );
    }

    @Override
    public void onScanCompleted(String s, Uri uri)
    {
        isScanningMedia = false;
        baseActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        baseActivity.onStopMediaScan();
                    }
                }
        );
    }
}
