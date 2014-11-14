package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.concrete;

import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.color.AbstractColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;

public class ListPlayerColorVisitor extends AbstractColorVisitor {

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof ListPlayer) {
            ListPlayer listPlayer = (ListPlayer) uiElement;
            colorSongLabel(listPlayer);
            colorListView(listPlayer);
        }
    }

    private void colorSongLabel(ListPlayer listPlayer)
    {
        TextView songLabel = listPlayer.songLabel.getLabel();
        if (null != songLabel)
            songLabel.setTextColor(colors.songLabel);
    }

    private void colorListView(ListPlayer listPlayer)
    {
        ListView listView = listPlayer.listView;
        listView.setDivider(Colors.toColorDrawable(colors.listDivider));
        listView.setDividerHeight(1);
    }
}
