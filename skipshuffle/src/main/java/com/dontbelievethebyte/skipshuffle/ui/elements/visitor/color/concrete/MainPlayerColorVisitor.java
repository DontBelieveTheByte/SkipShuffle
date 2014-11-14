package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;

public class MainPlayerColorVisitor extends AbstractColorVisitor {

    public MainPlayerColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof MainPlayer) {
            MainPlayer mainPlayer = (MainPlayer) uiElement;
            MainPlayerSongLabel songLabel = mainPlayer.getSongLabel();
            songLabel.setColor(
                    activity.getResources().getColor(
                            colors.songLabel
                    )
            );
        }
    }
}
