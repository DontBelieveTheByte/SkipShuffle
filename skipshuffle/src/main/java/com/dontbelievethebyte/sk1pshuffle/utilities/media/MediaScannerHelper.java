/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.utilities.media;

import android.content.DialogInterface;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.ui.dialog.MediaScannerDialog;
import com.dontbelievethebyte.sk1pshuffle.ui.dialog.MediaScannerProgressDialog;

public class MediaScannerHelper {

    public static final String IS_SCANNING_MEDIA = "IS_SCANNING_MEDIA";

    private BaseActivity baseActivity;
    private DialogInterface.OnClickListener positive;
    private MediaScannerClient mediaScannerClient;
    private MediaScannerProgressDialog mediaScannerProgressDialog;

    public MediaScannerHelper(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
        positive = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startMediaScan();
            }
        };
    }

    public boolean isScanningMedia()
    {
        return (null != mediaScannerClient && mediaScannerClient.isScanningMedia());
    }

    public void showMediaScannerDialog()
    {
        MediaScannerDialog mediaScannerDialog1 = new MediaScannerDialog(baseActivity);
        mediaScannerDialog1.setPositive(positive);
        mediaScannerDialog1.build();
        mediaScannerDialog1.show();
    }

    public void startMediaScan()
    {
        MediaScannerClient mediaScannerClient = new MediaScannerClient(baseActivity);
        mediaScannerClient.setMediaScannerHelper(this);
        mediaScannerClient.doScan();
        mediaScannerProgressDialog = new MediaScannerProgressDialog(baseActivity);
    }

    public void onStartMediaScan()
    {
        baseActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        mediaScannerProgressDialog.show();

                    }
                }
        );
    }

    public void onStopMediaScan()
    {
        baseActivity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        mediaScannerProgressDialog.hide();
                        mediaScannerClient = null;
                    }
                }
        );
    }
}
