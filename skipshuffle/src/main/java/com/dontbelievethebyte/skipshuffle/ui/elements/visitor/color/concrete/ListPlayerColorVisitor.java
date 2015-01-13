/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.structure.Colors;

public class ListPlayerColorVisitor extends AbstractColorVisitor {

    public ListPlayerColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof ListPlayer) {
            ListPlayer listPlayer = (ListPlayer) uiElement;
            colorListView(listPlayer);
        }
    }

    private void colorListView(ListPlayer listPlayer)
    {
        ListView listView = listPlayer.listView;
        listView.setDivider(
                Colors.toColorDrawable(
                        activity,
                        colors.listDivider
                )
        );
        colorButtons(listPlayer);
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
}
