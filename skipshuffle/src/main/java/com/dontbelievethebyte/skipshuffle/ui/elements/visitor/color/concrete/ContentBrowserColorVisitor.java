/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.MusicContentBrowser;
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
        if (uiElement instanceof MusicContentBrowser) {
            ListView drawerList = ((MusicContentBrowser) uiElement).getDrawerList();
            if (null != drawerList) {
                drawerList.setBackgroundResource(colors.navDrawerBackground);
                drawerList.setDivider(Colors.toColorDrawable(colors.listDivider));
            }
        }
    }
}
