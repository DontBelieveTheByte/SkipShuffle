package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.ContentBrowser;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;

public class MusicPlayerDrawerColorVisitor extends AbstractColorVisitor {

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof ContentBrowser) {
            ListView drawerList = ((ContentBrowser) uiElement).getDrawerList();
            if (null != drawerList) {
                drawerList.setBackgroundResource(colors.navDrawerBackground);
                drawerList.setDivider(Colors.toColorDrawable(colors.listDivider));
            }
        }
    }
}
