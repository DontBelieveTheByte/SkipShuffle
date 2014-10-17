package com.dontbelievethebyte.skipshuffle.utilities;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public class MediaScannerClient implements MediaScannerConnection.MediaScannerConnectionClient {
    private Context context;
    private MediaScannerConnection mediaScannerConnection;

    public MediaScannerClient(Context context)
    {
        this.context = context;
        mediaScannerConnection = new MediaScannerConnection(
                this.context,
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
        Log.d(BaseActivity.TAG, "MEDIA SCANNER CONNECTED ****");
        mediaScannerConnection.scanFile(
                Environment.getDownloadCacheDirectory().getAbsolutePath(),
                "audio/*"
        );
    }

    @Override
    public void onScanCompleted(String s, Uri uri)
    {
        Log.d(BaseActivity.TAG, "MEDIA SCAN COMPLETE ****");
    }
}
