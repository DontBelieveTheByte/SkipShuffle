package com.dontbelievethebyte.skipshuffle.dialog;

import android.view.View;
import android.widget.ProgressBar;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;

public class MediaScannerProgressDialog {

    private ProgressBar progressBar;

    public MediaScannerProgressDialog(MainActivity mainActivity)
    {
        progressBar = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
    }

    public void show()
    {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hide()
    {
        progressBar.setVisibility(View.GONE);
    }
}
