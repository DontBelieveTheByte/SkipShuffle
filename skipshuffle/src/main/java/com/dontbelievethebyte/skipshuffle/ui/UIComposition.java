package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.AbstractLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.ContentBrowser;
import com.dontbelievethebyte.skipshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.ColorVisitorFactory;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.DimensionsVisitorFactory;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.exception.NoSuchVisitorException;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.drawable.DrawablesVisitor;

import java.util.ArrayList;

public class UIComposition {

    public AbstractPlayerUI player;

    private BaseActivity baseActivity;
    private ContentBrowser musicPlayerDrawer;
    private AbstractLayout contentArea;
    private Colors colors;
    private Drawables drawables;

    public UIComposition(UICompositionBuilder builder)
    {
        baseActivity = builder.baseActivity;
        contentArea = builder.contentArea;
        colors = builder.colors;
        musicPlayerDrawer = builder.musicPlayerDrawer;
        player = builder.player;
        drawables = builder.drawables;
        visitElements();
    }

    private void visitElements()
    {
        ArrayList<UIElementCompositeInterface> uiElements = new ArrayList<UIElementCompositeInterface>();
        uiElements.add(musicPlayerDrawer);
        uiElements.add(player);
        uiElements.add(player.buttons);
        uiElements.add(contentArea);
        colorVisit(uiElements);
        dimensionsVisit(uiElements);
        drawablesVisit(uiElements);
    }

    private void colorVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        for (UIElementCompositeInterface element : uiElements) {
            try {
                AbstractColorVisitor colorVisitor = ColorVisitorFactory.make(element, baseActivity);
                colorVisitor.setColors(colors);
                colorVisitor.visit(element);
            } catch (NoSuchVisitorException e) {
                continue;
            }
        }
    }

    private void dimensionsVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        for (UIElementCompositeInterface element : uiElements) {
            try {
                AbstractDimensionsVisitor dimensionsVisitor = DimensionsVisitorFactory.make(element, baseActivity);
                dimensionsVisitor.visit(element);
            } catch (NoSuchVisitorException e) {
                continue;
            }
        }
    }

    private void drawablesVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        DrawablesVisitor drawablesVisitor = new DrawablesVisitor(drawables);

        for (UIElementCompositeInterface element : uiElements) {
            drawablesVisitor.visit(element);
        }
    }
}
