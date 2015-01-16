/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.concrete;

import android.app.Activity;
import android.widget.SeekBar;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.element.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.MainPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.ColorMapper;

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
