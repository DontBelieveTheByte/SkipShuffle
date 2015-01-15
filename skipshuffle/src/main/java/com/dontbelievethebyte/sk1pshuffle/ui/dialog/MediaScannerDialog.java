/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import com.dontbelievethebyte.sk1pshuffle.R;

public class MediaScannerDialog {
    private Activity activity;
    private Dialog mediaScannerDialog;
    private DialogInterface.OnClickListener positive;

    public MediaScannerDialog(Activity activity)
    {
        this.activity = activity;
    }

    public void setPositive(DialogInterface.OnClickListener positive)
    {
        this.positive = positive;
    }

    public void build()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getString(R.string.dialog_media_scan_title));
        builder.setMessage(activity.getString(R.string.dialog_media_scan_text));

        builder.setPositiveButton(
                R.string.dialog_positive,
                positive
        );

        builder.setNegativeButton(
                R.string.dialog_negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }
        );

        mediaScannerDialog = builder.create();
    }

    public void show ()
    {
        mediaScannerDialog.show();
    }
}
