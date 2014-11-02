package com.dontbelievethebyte.skipshuffle.navdrawer;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.ui.UIElement;
import com.dontbelievethebyte.skipshuffle.ui.visitor.ColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DrawablesVisitor;

public class MusicPlayerDrawer implements UIElement {

    private ListView drawerList;

    public MusicPlayerDrawer(BaseActivity baseActivity, int drawerId)
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
    public void acceptColorVisitor(ColorVisitor colorVisitor)
    {
        colorVisitor.visit(this);
    }

    @Override
    public void acceptDimensionsVisitor(DimensionsVisitor dimensionsVisitor)
    {
        dimensionsVisitor.visit(this);
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
