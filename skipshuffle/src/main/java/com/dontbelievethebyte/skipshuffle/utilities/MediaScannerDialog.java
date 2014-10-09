package com.dontbelievethebyte.skipshuffle.utilities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.broadcastreceivers.MediaScannerBroadcastReceiver;
import com.dontbelievethebyte.skipshuffle.callbacks.MediaScannerBroadcastReceiverCallback;
import com.dontbelievethebyte.skipshuffle.services.MediaScannerBroadcastMessageContract;
import com.dontbelievethebyte.skipshuffle.services.MediaScannerService;

import java.lang.ref.WeakReference;

public class MediaScannerDialog implements MediaScannerBroadcastReceiverCallback {

    private boolean isScanningMedia = false;
    private boolean isDialogShowing = false;
    private ProgressDialog progressDialog;
    private MediaScannerBroadcastReceiver mediaScannerReceiver;
    WeakReference<BaseActivity> weakActivity;

    public MediaScannerDialog(BaseActivity baseActivity)
    {
        weakActivity = new WeakReference<BaseActivity>(baseActivity);

        setUpProgressDialog();
    }

    public void doScan()
    {
        Intent mediaScannerIntent = new Intent(
                weakActivity.get(),
                MediaScannerService.class
        );
        weakActivity.get().startService(mediaScannerIntent);
        isScanningMedia = true;
    }

    public boolean isScanningMedia()
    {
        return isScanningMedia;
    }

    public void registerBroadcastReceiver()
    {
        if (null == mediaScannerReceiver) {
            mediaScannerReceiver = new MediaScannerBroadcastReceiver();
            mediaScannerReceiver.registerCallback(this);

            LocalBroadcastManager.getInstance(weakActivity.get())
                                 .registerReceiver(
                                         mediaScannerReceiver,
                                         new IntentFilter(MediaScannerBroadcastMessageContract.CURRENT_FILE_PROCESSING)
                                 );

        }
    }

    public void unregisterBroadcastReceiver()
    {
        if (mediaScannerReceiver != null) {
            LocalBroadcastManager.getInstance(weakActivity.get())
                                 .unregisterReceiver(mediaScannerReceiver);
        }
    }

    public void dismiss()
    {
        if (isDialogShowing) {
            progressDialog.dismiss();
            isDialogShowing = false;
            isScanningMedia = false;
        }
    }

    public void show()
    {
        if (!isDialogShowing) {
            progressDialog.show();
            isDialogShowing = true;
        }
    }

    @Override
    public void scannerBroadcastReceiverCallback()
    {
        if (mediaScannerReceiver.isLast())
            handleLastFileReceived();
        else
            handleFileReceived();
    }

    private void setUpProgressDialog()
    {
        progressDialog = new ProgressDialog(weakActivity.get());
        progressDialog.setTitle(
                weakActivity.get().getString(R.string.media_scan_start_title)
        );
        progressDialog.setMessage(
                weakActivity.get().getString(R.string.media_scan_start_message)
        );
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
    }

    private void handleFileReceived()
    {
        show();
        setTitle(mediaScannerReceiver.getCurrentDirectory());
        setMessage(mediaScannerReceiver.getCurrentFile());
    }

    private void handleLastFileReceived()
    {
        weakActivity.get().getToastHelper().showLongToast(
                weakActivity.get().getString(R.string.media_scan_directory_empty)
        );
        dismiss();
        unregisterBroadcastReceiver();
    }

    private void setMessage(String message)
    {
        progressDialog.setMessage(message);
    }

    private void setTitle(String title)
    {
        progressDialog.setTitle(
                weakActivity.get().getString(
                        R.string.media_scan_dialog_title,
                        title
                )
        );
    }
}
