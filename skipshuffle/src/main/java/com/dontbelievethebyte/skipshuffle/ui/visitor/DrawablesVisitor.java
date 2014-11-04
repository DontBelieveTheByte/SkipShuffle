package com.dontbelievethebyte.skipshuffle.ui.visitor;

import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.UIElement;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;
import com.dontbelievethebyte.skipshuffle.ui.structured.PlayerButtons;

public class DrawablesVisitor {

    public static interface Visitable {
        public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor);
    }

    Drawables drawables;

    public DrawablesVisitor(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public void visit(UIElement uiElement)
    {
        if (uiElement instanceof MusicPlayerDrawer)
            visitMusicPlayerDrawer((MusicPlayerDrawer) uiElement);
        else if (uiElement instanceof PlayerButtons)
            visitPlayerUI((PlayerButtons) uiElement);
    }

    private void visitMusicPlayerDrawer(MusicPlayerDrawer musicPlayerDrawer)
    {

    }


    private void visitPlayerUI(PlayerButtons buttons)
    {
        buttons.prev.setImageDrawable(buttons.drawables.getPrev());
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.shuffle.setImageDrawable(buttons.drawables.getShuffle());
        buttons.skip.setImageDrawable(buttons.drawables.getSkip());
        buttons.playlist.setImageDrawable(buttons.drawables.getPlaylist());
    }
}
