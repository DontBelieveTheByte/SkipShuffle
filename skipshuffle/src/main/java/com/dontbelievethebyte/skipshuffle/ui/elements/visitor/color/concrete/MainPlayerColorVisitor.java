package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;

public class MainPlayerColorVisitor extends AbstractColorVisitor {

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof MainPlayer) {
            MainPlayer mainPlayer = (MainPlayer) uiElement;
            TextView songLabel = mainPlayer.songLabel.getLabel();
            if (null != songLabel)
                songLabel.setTextColor(colors.songLabel);
        }
    }
}
