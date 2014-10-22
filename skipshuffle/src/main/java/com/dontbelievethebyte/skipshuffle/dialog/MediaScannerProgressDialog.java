package com.dontbelievethebyte.skipshuffle.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import com.dontbelievethebyte.skipshuffle.R;

public class MediaScannerProgressDialog {

    private Activity activity;
    private ProgressBar progressBar;
    private CharSequence oldTitle;

    public MediaScannerProgressDialog(Activity activity)
    {
        this.activity = activity;
        this.progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        oldTitle = activity.getTitle();
    }

    public void show()
    {
        progressBar.setVisibility(View.VISIBLE);
        activity.setTitle(
                activity.getString(R.string.media_scan_start_title)
        );
    }

    public void hide()
    {
        progressBar.setVisibility(View.GONE);
        activity.setTitle(oldTitle);
    }
}
