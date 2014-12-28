/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.widget.SeekBar;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;

public class SeekBarColorVisitor extends AbstractColorVisitor {

    public SeekBarColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof MainPlayer) {
            MainPlayer mainPlayer = (MainPlayer) uiElement;
            colorSeekbar(mainPlayer);
        }
    }

    private void colorSeekbar(MainPlayer playerUI)
    {
        SeekBar seekBar = playerUI.seekBar.getSeekBar();
        //        seekBar.getProgressDrawable().setColorFilter(
//                baseActivity.getResources().getColor(
//                        ColorMapper.getPlayButton(baseActivity.ui.player.type)
//                ),
//                PorterDuff.Mode.SRC_IN
//        );
//        Drawable thumb = baseActivity.getResources().getDrawable(R.drawable.remove_pressed_neon);
//        seekBar.setThumb(thumb);
//        Drawable progress = baseActivity.getResources().getDrawable(R.drawable.notification_background_mono_light);
//        seekBar.setProgressDrawable(progress);
//        Drawable background = baseActivity.getResources().getDrawable(R.drawable.notification_background_mono_dark);
//        seekBar.setBackgroundColor(
//                baseActivity.getResources().getColor(android.R.color.transparent)
//        );//        seekBar.setThumb(
//                baseActivity.ui.player.buttons.drawables.getRemove()
//        );

//        seekBar.getBackground().setColorFilter(
//                baseActivity.getResources().getColor(
//                        ColorMapper.getPlayButton(baseActivity.ui.player.type)
//                ),
//                PorterDuff.Mode.SRC_IN
//        );
    }
}
