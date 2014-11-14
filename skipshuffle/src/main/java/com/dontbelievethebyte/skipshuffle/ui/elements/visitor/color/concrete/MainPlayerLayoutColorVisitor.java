package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.view.ViewGroup;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;

public class MainPlayerLayoutColorVisitor extends AbstractColorVisitor {

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof PlayerLayout)
            colorBackground((PlayerLayout) uiElement);
    }

    private void colorBackground(PlayerLayout playerLayout)
    {
        ViewGroup bottomLayout = playerLayout.getBottomLayout();
        bottomLayout.setBackgroundResource(colors.background);
    }
}
