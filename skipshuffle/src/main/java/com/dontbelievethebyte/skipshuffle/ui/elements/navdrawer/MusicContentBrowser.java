/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.drawable.DrawablesVisitor;

public class MusicContentBrowser implements UIElementCompositeInterface,
                                            AbstractDimensionsVisitor.Visitable,
                                            DrawablesVisitor.Visitable {

    private ListView drawerList;

    public MusicContentBrowser(BaseActivity baseActivity, int drawerId)
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
