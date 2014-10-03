package com.dontbelievethebyte.skipshuffle.activities.util;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.services.MediaScannerBroadcastMessageContract;
import com.dontbelievethebyte.skipshuffle.services.MediaScannerService;

public class MediaScannerDialog {

    private boolean isScanningMedia = false;
    private boolean isDialogShowing = false;
    private ProgressDialog progressDialog;
    private BroadcastReceiver mediaScannerReceiver;
    private BaseActivity baseActivity;

    public MediaScannerDialog(BaseActivity baseActivity)
    {
        this.progressDialog = new ProgressDialog(baseActivity);
        this.baseActivity = baseActivity;
        this.progressDialog.setTitle(
                baseActivity.getString(R.string.media_scan_start_title)
        );
        this.progressDialog.setMessage(
                baseActivity.getString(R.string.media_scan_start_message)
        );
        this.progressDialog.setCancelable(false);
        this.progressDialog.setIndeterminate(true);
    }

    public void doScan()
    {
        registerMediaScannerBroadcastReceiver();
        Intent mediaScannerIntent = new Intent(
                baseActivity,
                MediaScannerService.class
        );
        baseActivity.startService(mediaScannerIntent);
        isScanningMedia = true;
    }

    public boolean isScanningMedia()
    {
        return isScanningMedia;
    }

    public void registerMediaScannerBroadcastReceiver()
    {
        if (null == mediaScannerReceiver) {
            mediaScannerReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent)
                {
                    String currentDirectory = intent.getStringExtra(
                            MediaScannerBroadcastMessageContract.CURRENT_DIRECTORY_PROCESSING
                    );
                    String currentFile = intent.getStringExtra(
                            MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING
                    );
                    boolean isLast = intent.getBooleanExtra(
                            MediaScannerBroadcastMessageContract.IS_LAST_FILE_PROCESSING,
                            false
                    );
                    if (!isLast) {
                        show();
                        setTitle(currentDirectory);
                        setMessage(currentFile);
                    } else {
                        if (null == currentDirectory && null == currentFile) {
                            baseActivity.getToastHelper().showLongToast(
                                    baseActivity.getString(R.string.media_scan_directory_empty)
                            );
                        }
                        dismiss();
                        unregisterMediaScannerBroadcastReceiver();
                    }
                }
            };
        }
        LocalBroadcastManager.getInstance(baseActivity)
                .registerReceiver(
                        mediaScannerReceiver,
                        new IntentFilter(
                                MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING
                        )
                );
    }

    public void unregisterMediaScannerBroadcastReceiver()
    {
        if (mediaScannerReceiver != null) {
            LocalBroadcastManager.getInstance(baseActivity)
                                 .unregisterReceiver(mediaScannerReceiver);
        }
        dismiss();
        isScanningMedia = false;
    }

    public void dismiss()
    {
        if (isDialogShowing) {
            progressDialog.dismiss();
        }
        isDialogShowing = false;
    }

    private void setMessage(String message)
    {
        progressDialog.setMessage(message);
    }

    private void setTitle(String title)
    {
        progressDialog.setTitle(baseActivity.getString(
                        R.string.media_scan_dialog_title,
                        title
                )
        );
    }

    public void show()
    {
        if (!isDialogShowing) {
            progressDialog.show();
        }
        isDialogShowing = true;
    }
}
