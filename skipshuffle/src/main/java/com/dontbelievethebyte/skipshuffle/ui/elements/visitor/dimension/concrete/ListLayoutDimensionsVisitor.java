package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete;

import android.app.Activity;
import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.AbstractLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;

public class ListLayoutDimensionsVisitor extends AbstractDimensionsVisitor {

    public ListLayoutDimensionsVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        setBottomLayoutSize((ListLayout) uiElement);
    }

    private void setBottomLayoutSize(AbstractLayout contentArea)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();

        bottomLayout.setPadding(
        /* left   */ 0,
        /* top    */ computeActionBarHeight(),
        /* right  */ 0,
        /* bottom */ 0
        );
    }
}
