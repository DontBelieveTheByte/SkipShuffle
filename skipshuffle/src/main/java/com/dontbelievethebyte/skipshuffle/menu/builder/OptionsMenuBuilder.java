package com.dontbelievethebyte.skipshuffle.menu.builder;

import android.content.Context;
import android.os.Vibrator;
import android.view.Menu;

import com.dontbelievethebyte.skipshuffle.actionbar.CustomActionBarWrapper;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.menu.CustomOptionsMenuInterface;
import com.dontbelievethebyte.skipshuffle.menu.concrete.NoVibratorMenu;
import com.dontbelievethebyte.skipshuffle.menu.concrete.VibratorMenu;

public class OptionsMenuBuilder {

    private BaseActivity baseActivity;
    private CustomActionBarWrapper customActionBarWrapper;

    public OptionsMenuBuilder(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    public void setCustomActionBarWrapper(CustomActionBarWrapper customActionBarWrapper)
    {
        this.customActionBarWrapper = customActionBarWrapper;
    }

    public CustomOptionsMenuInterface build(Menu menu)
    {

        CustomOptionsMenuInterface customOptionsMenuInterface;

        customOptionsMenuInterface = (hasVibratorCapability()) ?
                new VibratorMenu(baseActivity, menu) :
                new NoVibratorMenu(baseActivity, menu);

        customOptionsMenuInterface.setCustomActionBarWrapper(customActionBarWrapper);
        return customOptionsMenuInterface;
    }

    private boolean hasVibratorCapability()
    {
        Vibrator vibrator = (Vibrator) baseActivity.getSystemService(Context.VIBRATOR_SERVICE);
        return vibrator.hasVibrator();
    }
}
