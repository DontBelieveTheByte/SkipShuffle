package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.builder.UIBuilder;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;
import com.dontbelievethebyte.skipshuffle.ui.visitor.ColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DrawablesVisitor;

import java.util.ArrayList;

public class BaseUI {

    public AbstractPlayer player;

    private BaseActivity baseActivity;
    private MusicPlayerDrawer musicPlayerDrawer;
    private ContentArea contentArea;
    private Colors colors;
    private Drawables drawables;
    private CustomTypeface typeface;


    public BaseUI(UIBuilder builder)
    {
        baseActivity = builder.baseActivity;
        contentArea = builder.contentArea;
        colors = builder.colors;
        musicPlayerDrawer = builder.musicPlayerDrawer;
        player = builder.player;
        drawables = builder.drawables;
        typeface = builder.customTypeface;
        visitElements();
    }



    private void visitElements()
    {
        ArrayList<UIElement> uiElements = new ArrayList<UIElement>();
        uiElements.add(musicPlayerDrawer);
        uiElements.add(player);
        uiElements.add(player.buttons);
        uiElements.add(contentArea);
        colorVisit(uiElements);
        dimensionsVisit(uiElements);
        drawablesVisit(uiElements);
    }

    private void colorVisit(ArrayList<UIElement> uiElements)
    {
        ColorVisitor colorVisitor = new ColorVisitor(colors);

        for (UIElement element : uiElements) {
            colorVisitor.visit(element);
        }
    }

    private void dimensionsVisit(ArrayList<UIElement> uiElements)
    {
        DimensionsVisitor dimensionsVisitor = new DimensionsVisitor(baseActivity);

        for (UIElement element : uiElements) {
            dimensionsVisitor.visit(element);
        }
    }


    private void drawablesVisit(ArrayList<UIElement> uiElements)
    {
        DrawablesVisitor drawablesVisitor = new DrawablesVisitor(drawables);

        for (UIElement element : uiElements) {
            drawablesVisitor.visit(element);
        }
    }
}
