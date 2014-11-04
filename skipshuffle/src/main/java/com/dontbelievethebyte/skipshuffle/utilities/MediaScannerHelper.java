package com.dontbelievethebyte.skipshuffle.utilities;


import android.content.DialogInterface;

import com.dontbelievethebyte.skipshuffle.activities.Activity;
import com.dontbelievethebyte.skipshuffle.ui.dialog.MediaScannerDialog;
import com.dontbelievethebyte.skipshuffle.ui.dialog.MediaScannerProgressDialog;

public class MediaScannerHelper {

    public static final String IS_SCANNING_MEDIA = "IS_SCANNING_MEDIA";

    private Activity baseActivity;
    private DialogInterface.OnClickListener positive;
    private MediaScannerClient mediaScannerClient;
    private MediaScannerProgressDialog mediaScannerProgressDialog;

    public MediaScannerHelper(Activity baseActivity)
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
