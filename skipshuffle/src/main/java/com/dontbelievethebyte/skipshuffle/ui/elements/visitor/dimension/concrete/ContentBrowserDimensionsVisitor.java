package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DimensionsMapper;

public class ContentBrowserDimensionsVisitor extends AbstractDimensionsVisitor {

    public ContentBrowserDimensionsVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        ListView drawerList = (ListView) activity.findViewById(R.id.drawer_list);
        adjustOuterDimensions(drawerList);
        adjustDividerHeight(drawerList);
    }

    private void adjustOuterDimensions(ListView drawerList)
    {
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) drawerList.getLayoutParams();
        params.width = (int) (computedScreenWidth * ( isLandScape ? DimensionsMapper.Drawer.Landscape.width : DimensionsMapper.Drawer.Portrait.width));
        drawerList.setLayoutParams(params);
    }

    private void adjustDividerHeight(ListView drawerList)
    {
        drawerList.setDividerHeight(DimensionsMapper.List.dividerHeight);
    }
}
