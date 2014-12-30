/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.widget.SeekBar;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;

public class MainPlayerColorVisitor extends AbstractColorVisitor {

    public MainPlayerColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof MainPlayer) {
            MainPlayer mainPlayer = (MainPlayer) uiElement;
            MainPlayerSongLabel songLabel = mainPlayer.getSongLabel();
            songLabel.setColor(
                    activity.getResources().getColor(
                            colors.songLabel
                    )
            );
            colorButtons(mainPlayer);
        }
    }

    private void colorButtons(AbstractPlayerUI playerUI)
    {
        playerUI.buttons.play.setColorFilter(
                activity.getResources().getColor(
                        ColorMapper.getPauseButton(playerUI.type)
                )
        );

        playerUI.buttons.skip.setColorFilter(
                activity.getResources().getColor(
                        ColorMapper.getSkipButton(playerUI.type)
                )
        );

        playerUI.buttons.shuffle.setColorFilter(
                activity.getResources().getColor(
                        ColorMapper.getShuffleButton(playerUI.type)
                )
        );

        playerUI.buttons.playlist.setColorFilter(
                activity.getResources().getColor(
                        ColorMapper.getPlaylistButton(playerUI.type)
                )
        );

        playerUI.buttons.prev.setColorFilter(
                activity.getResources().getColor(
                        ColorMapper.getPrevButton(playerUI.type)
                )
        );
    }

    private void colorSeekbar(AbstractPlayerUI playerUI)
    {
        SeekBar seekBar = (SeekBar) activity.findViewById(R.id.seekBar);

    }
}
