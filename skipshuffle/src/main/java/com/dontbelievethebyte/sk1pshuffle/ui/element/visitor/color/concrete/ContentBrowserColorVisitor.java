/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.ui.element.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Colors;

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
