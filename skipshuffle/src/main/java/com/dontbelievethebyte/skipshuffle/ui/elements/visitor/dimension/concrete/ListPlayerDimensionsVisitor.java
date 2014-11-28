/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DimensionsMapper;

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
