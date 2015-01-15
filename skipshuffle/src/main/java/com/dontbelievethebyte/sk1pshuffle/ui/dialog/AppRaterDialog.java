/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.utilities.ToastHelper;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PreferencesHelper;

public class AppRaterDialog {

    private Activity activity;
    private Dialog dialog;
    private PreferencesHelper preferencesHelper;
    private ToastHelper toastHelper;

    public AppRaterDialog(Activity activity, PreferencesHelper preferencesHelper, ToastHelper toastHelper)
    {
        this.activity = activity;
        this.preferencesHelper = preferencesHelper;
        this.toastHelper = toastHelper;
    }

    public void build()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(activity.getString(R.string.dialog_app_rating_title));
        builder.setMessage(activity.getString(R.string.dialog_app_rating_text));
        builder.setIcon(activity.getApplicationInfo().icon);
        builder.setCancelable(false);
        builder.setPositiveButton(
                activity.getString(R.string.dialog_app_rating_positive),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        preferencesHelper.setCanRateApp(false);
                        try {
                            activity.startActivity(
                                    new Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=" + activity.getPackageName())
                                    )
                            );

                        } catch (ActivityNotFoundException e) {
                            toastHelper.showLongToast(activity.getString(R.string.no_app_store));
                        }

                        dialog.dismiss();
                    }
                }
        );

        builder.setNeutralButton(
                activity.getString(R.string.dialog_app_rating_neutral),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }
        );

        builder.setNegativeButton(
                activity.getString(R.string.dialog_app_rating_negative),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preferencesHelper.setCanRateApp(false);
                        dialog.dismiss();
                    }
                }
        );
        dialog = builder.create();
    }

    public void show ()
    {
        dialog.show();
    }
}
