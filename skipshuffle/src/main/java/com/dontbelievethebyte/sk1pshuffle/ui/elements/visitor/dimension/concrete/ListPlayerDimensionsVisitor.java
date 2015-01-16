/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.DimensionsMapper;

public class ListPlayerDimensionsVisitor extends AbstractDimensionsVisitor {

    public ListPlayerDimensionsVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof ListPlayer) {
            ListPlayer listPlayer = (ListPlayer) uiElement;
            adjustListViewDivider(listPlayer);
        }
    }

    private void adjustListViewDivider(ListPlayer listPlayer)
    {
        ListView listView = listPlayer.listView;
        listView.setDividerHeight(DimensionsMapper.List.dividerHeight);
    }
}
