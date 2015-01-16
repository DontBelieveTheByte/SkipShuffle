/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer;

import android.app.Activity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.ui.element.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.element.visitor.drawable.DrawablesVisitor;

public class ContentBrowserDrawer implements UIElementCompositeInterface,
                                            AbstractDimensionsVisitor.Visitable,
                                            DrawablesVisitor.Visitable {

    private ListView drawerList;

    public ContentBrowserDrawer(Activity activity, int drawerId)
    {
        drawerList = (ListView) activity.findViewById(drawerId);
    }

    public void setClickListener(ListView.OnItemClickListener navDrawerClickListener)
    {
        drawerList.setOnItemClickListener(navDrawerClickListener);
    }

    public void setTouchListener(View.OnTouchListener touchListener)
    {
        drawerList.setOnTouchListener(touchListener);
    }

    public ListView getDrawerList() {
        return drawerList;
    }

    public void setAdapter(BaseAdapter baseAdapter)
    {
        drawerList.setAdapter(baseAdapter);
    }


    @Override
    public void acceptDimensionsVisitor(AbstractDimensionsVisitor dimensionsVisitor)
    {
        dimensionsVisitor.visit(this);
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
