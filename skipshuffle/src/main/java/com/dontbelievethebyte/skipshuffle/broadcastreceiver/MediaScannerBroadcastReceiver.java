package com.dontbelievethebyte.skipshuffle.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dontbelievethebyte.skipshuffle.callback.ScannerBroadcastReceiverCallback;

import java.util.ArrayList;

public class MediaScannerBroadcastReceiver extends BroadcastReceiver {
    private ArrayList<ScannerBroadcastReceiverCallback> scannerBroadcastReceiverCallbacks;
    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public void registerCallback(ScannerBroadcastReceiverCallback scannerBroadcastReceiverCallback)
    {
        scannerBroadcastReceiverCallbacks.add(scannerBroadcastReceiverCallback);
    }
}
