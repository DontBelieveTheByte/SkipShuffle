/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.AbstractLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;

public class MainPlayerLayoutDimensionsVisitor extends AbstractDimensionsVisitor {

    public MainPlayerLayoutDimensionsVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        setBottomLayoutSize((PlayerLayout)uiElement);

    }

    private void setBottomLayoutSize(AbstractLayout contentArea)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();

//        bottomLayout.setPadding(
//        /* left   */ (int) (computedScreenWidth * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.left : DimensionsMapper.Player.Padding.Portrait.left)),
//        /* top    */ computeActionBarHeight(),
//        /* right  */ (int) (computedScreenWidth * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.right : DimensionsMapper.Player.Padding.Portrait.right)),
//        /* bottom */ 0
//        );

        bottomLayout.setPadding(
        /* left   */ 0,
        /* top    */ computeActionBarHeight(),
        /* right  */ 0,
        /* bottom */ 0
        );
    }
}
