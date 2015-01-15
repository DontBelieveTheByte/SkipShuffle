/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.drawable.DrawablesVisitor;

public class ContentBrowserDrawer implements UIElementCompositeInterface,
                                            AbstractDimensionsVisitor.Visitable,
                                            DrawablesVisitor.Visitable {

    private ListView drawerList;

    public ContentBrowserDrawer(BaseActivity baseActivity, int drawerId)
    {
        drawerList = (ListView) baseActivity.findViewById(drawerId);
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
