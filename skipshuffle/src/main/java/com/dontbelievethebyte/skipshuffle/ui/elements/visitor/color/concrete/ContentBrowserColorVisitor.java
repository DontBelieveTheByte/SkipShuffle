/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.structure.Colors;

public class ContentBrowserColorVisitor extends AbstractColorVisitor {

    public ContentBrowserColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof ContentBrowserDrawer) {
            ListView drawerList = ((ContentBrowserDrawer) uiElement).getDrawerList();
            if (null != drawerList) {
                drawerList.setBackgroundResource(colors.navDrawerBackground);
                drawerList.setDivider(
                        Colors.toColorDrawable(activity, colors.listDivider)
                );
            }
        }
    }
}
