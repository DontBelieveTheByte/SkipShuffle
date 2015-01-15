/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.mapper.ColorMapper;
import com.dontbelievethebyte.sk1pshuffle.ui.structure.Colors;

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
