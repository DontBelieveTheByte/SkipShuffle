package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.app.Activity;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;

public class ListPlayerColorVisitor extends AbstractColorVisitor {

    public ListPlayerColorVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof ListPlayer) {
            ListPlayer listPlayer = (ListPlayer) uiElement;
            colorListView(listPlayer);
        }
    }

    private void colorListView(ListPlayer listPlayer)
    {
        ListView listView = listPlayer.listView;
        listView.setDivider(Colors.toColorDrawable(colors.listDivider));
    }
}
