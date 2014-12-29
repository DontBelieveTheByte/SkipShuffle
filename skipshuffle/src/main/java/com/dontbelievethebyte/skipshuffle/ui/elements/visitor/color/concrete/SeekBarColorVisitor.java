/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.SeekBar;

import com.dontbelievethebyte.skipshuffle.R;
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
        colorProgress(customSeekBar.getSeekBar());
        colorThumb(customSeekBar.getSeekBar());
    }

    private void colorThumb(SeekBar seekBar)
    {
        Drawable thumbDrawable = activity.getResources().getDrawable(R.drawable.seekbar_compat_thumb);
        thumbDrawable.setColorFilter(
                activity.getResources().getColor(colors.seekBarThumb),
                PorterDuff.Mode.SRC_IN
        );
        seekBar.setThumb(thumbDrawable);
    }

    private void colorProgress(SeekBar seekBar)
    {
        Drawable progressDrawable = seekBar.getProgressDrawable();
        progressDrawable.setColorFilter(
                activity.getResources().getColor(colors.seekBarProgress),
                PorterDuff.Mode.SRC_IN
        );

        seekBar.setProgressDrawable(progressDrawable);
    }
}
