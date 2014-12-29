/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.dialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.playlist.TrackPrinter;

public class SongInfoDialog {

    private Activity activity;
    private Dialog dialog;

    public SongInfoDialog(Activity activity)
    {
        this.activity = activity;
    }

    public void build(Track track)
    {
        if (null != track) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(activity.getString(R.string.dialog_song_info_title));
            builder.setMessage(getReport(track));
            builder.setIcon(activity.getApplicationInfo().icon);
            builder.setCancelable(true);
            dialog = builder.create();
        }
    }

    private String getReport(Track track)
    {
        TrackPrinter trackPrinter = new TrackPrinter(activity);
        return trackPrinter.printFullReport(track);
    }

    public void show ()
    {
        dialog.show();
    }
}
