package com.dontbelievethebyte.skipshuffle.dialog;

import android.view.View;
import android.widget.ProgressBar;

public class MediaScannerProgressDialog {

    private ProgressBar progressBar;

    public MediaScannerProgressDialog(ProgressBar progressBar)
    {
        this.progressBar = progressBar;
    }

    public boolean isShowing()
    {
        int visibility = progressBar.getVisibility();
        return (visibility == View.VISIBLE);
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
