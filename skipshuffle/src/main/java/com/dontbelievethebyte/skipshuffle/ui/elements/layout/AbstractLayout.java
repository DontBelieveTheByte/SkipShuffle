package com.dontbelievethebyte.skipshuffle.ui.elements.layout;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;

public abstract class AbstractLayout implements UIElementCompositeInterface {
    protected ViewGroup bottomLayout;

    public AbstractLayout(Activity activity, int layoutId)
    {
        activity.setContentView(layoutId);
        bottomLayout = (ViewGroup) activity.findViewById(R.id.bottom);
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }
}
