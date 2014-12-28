/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.SeekBar;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;

public class SeekBarColorVisitor extends AbstractColorVisitor {

    public SeekBarColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof CustomSeekBar) {
            CustomSeekBar customSeekBar = (CustomSeekBar) uiElement;
            colorSeekbar(customSeekBar);
        }
    }

    private void colorSeekbar(CustomSeekBar customSeekBar)
    {
        Log.d(BaseActivity.TAG, "WE RAIN");
        SeekBar seekBar = customSeekBar.getSeekBar();

        Drawable progressDrawable = seekBar.getProgressDrawable();
        progressDrawable.setColorFilter(
                colors.playlistArtist,
                PorterDuff.Mode.SRC_IN
        );

        seekBar.setProgressDrawable(progressDrawable);

        Drawable backgroundDrawable = seekBar.getBackground();
        backgroundDrawable.setColorFilter(
                colors.playlistButton,
                PorterDuff.Mode.SRC_IN
        );
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentapiVersion > Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            seekBar.setBackground(backgroundDrawable);
        } else{
            seekBar.setBackgroundDrawable(backgroundDrawable);
        }

    }
}
