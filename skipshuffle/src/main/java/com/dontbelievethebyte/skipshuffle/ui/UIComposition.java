package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.activities.Activity;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.AbstractPlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.ColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.DimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.DrawablesVisitor;

import java.util.ArrayList;

public class UIComposition {

    public AbstractPlayerUI player;

    private Activity baseActivity;
    private MusicPlayerDrawer musicPlayerDrawer;
    private ContentArea contentArea;
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
        ColorVisitor colorVisitor = new ColorVisitor(colors);

        for (UIElementCompositeInterface element : uiElements) {
            colorVisitor.visit(element);
        }
    }

    private void dimensionsVisit(ArrayList<UIElementCompositeInterface> uiElements)
    {
        DimensionsVisitor dimensionsVisitor = new DimensionsVisitor(baseActivity);

        for (UIElementCompositeInterface element : uiElements) {
            dimensionsVisitor.visit(element);
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
