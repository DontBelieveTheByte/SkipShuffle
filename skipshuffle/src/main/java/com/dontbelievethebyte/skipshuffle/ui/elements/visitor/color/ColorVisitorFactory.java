package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.ContentBrowser;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.ListLayoutColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.ListPlayerColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.MainPlayerColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.MainPlayerLayoutColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete.MusicPlayerDrawerColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.exception.NoSuchVisitorException;

public class ColorVisitorFactory {

    public static AbstractColorVisitor make(UIElementCompositeInterface uiElement) throws NoSuchVisitorException
    {
        if (uiElement instanceof ContentBrowser)
            return new MusicPlayerDrawerColorVisitor();

        else if (uiElement instanceof ListLayout)
            return new ListLayoutColorVisitor();

        else if (uiElement instanceof PlayerLayout)
            return new MainPlayerLayoutColorVisitor();

        else if (uiElement instanceof ListPlayer)
            return new ListPlayerColorVisitor();

        else if (uiElement instanceof MainPlayer)
            return new MainPlayerColorVisitor();
        else
            throw new NoSuchVisitorException();
    }
}
