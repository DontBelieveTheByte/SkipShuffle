package com.dontbelievethebyte.skipshuffle.menu;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;

import com.dontbelievethebyte.skipshuffle.R;

public class OptionsMenuCreator {

    private Activity activity;

    public OptionsMenuCreator(Activity activity)
    {
        this.activity = activity;
    }

    public void buildOptionsMenuFromContext(Menu menu)
    {

        int menuResourceId = (hasVibratorCapability()) ?
                R.menu.main :
                R.menu.main_no_vibrator;

        MenuInflater menuInflater = activity.getMenuInflater();

        menuInflater.inflate(menuResourceId, menu);
    }

    private boolean hasVibratorCapability()
    {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        return vibrator.hasVibrator();
    }
}
