package com.dontbelievethebyte.skipshuffle.ui.visitor;

import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.BaseUI;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.UIElement;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class DrawablesVisitor {

    Drawables drawables;

    public DrawablesVisitor(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public void visit(UIElement uiElement)
    {
        if (uiElement instanceof MusicPlayerDrawer)
            visitMusicPlayerDrawer((MusicPlayerDrawer) uiElement);
        else if (uiElement instanceof BaseUI)
            visitBaseUI((BaseUI) uiElement);
        else if (uiElement instanceof PlayerUI)
            visitPlayerUI((PlayerUI) uiElement);
    }

    private void visitMusicPlayerDrawer(MusicPlayerDrawer musicPlayerDrawer)
    {

    }

    private void visitBaseUI(BaseUI baseUI)
    {

    }

    private void visitPlayerUI(PlayerUI playerUI)
    {

    }
}
