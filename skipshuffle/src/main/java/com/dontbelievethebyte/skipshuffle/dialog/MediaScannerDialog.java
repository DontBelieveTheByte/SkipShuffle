package com.dontbelievethebyte.skipshuffle.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public class MediaScannerDialog {
    private BaseActivity baseActivity;
    private Dialog themeSelectionDialog;

    public MediaScannerDialog(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    public void build()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setTitle(baseActivity.getString(R.string.dialog_media_scan_title));
        builder.setMessage(baseActivity.getString(R.string.dialog_media_scan_text));

        builder.setPositiveButton(
                R.string.dialog_positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        baseActivity.sendBroadcast (
                                new Intent(Intent.ACTION_MEDIA_MOUNTED,
                                        Uri.parse("file://" + Environment.getExternalStorageDirectory()))
                        );
                    }
                }
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

        AlertDialog alert = builder.create();
        alert.show();

        themeSelectionDialog = builder.create();
    }

    public void show ()
    {
        themeSelectionDialog.show();
    }
}
