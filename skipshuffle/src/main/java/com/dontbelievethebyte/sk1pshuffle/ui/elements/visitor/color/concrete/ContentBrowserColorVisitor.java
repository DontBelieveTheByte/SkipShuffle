/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.structure.Colors;

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
