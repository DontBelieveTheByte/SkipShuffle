package com.dontbelievethebyte.skipshuffle.ui.elements.visitor;

import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.ui.elements.content.AbstractContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;

public class ColorVisitor {

    public static interface Visitable {
        public void acceptColorVisitor(ColorVisitor colorVisitor);
    }

    Colors colors;

    public ColorVisitor(Colors colors)
    {
        this.colors = colors;
    }

    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof MusicPlayerDrawer)
            visitMusicPlayerDrawer((MusicPlayerDrawer) uiElement);
        else if (uiElement instanceof AbstractContentArea)
            visitContentArea((AbstractContentArea) uiElement);
        else if (uiElement instanceof MainPlayer)
            visitPlayerUI((MainPlayer) uiElement);
    }

    private void visitMusicPlayerDrawer(MusicPlayerDrawer musicPlayerDrawer)
    {
        ListView drawerList = musicPlayerDrawer.getDrawerList();
        if (null != drawerList) {
            drawerList.setBackgroundResource(colors.navDrawerBackground);

            drawerList.setDivider(
                    Colors.toColorDrawable(colors.listDivider)
            );
        }
    }

    private void visitContentArea(AbstractContentArea contentArea)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();
        bottomLayout.setBackgroundResource(colors.background);
    }

    private void visitPlayerUI(MainPlayer playerUI)
    {
        TextView songLabel = playerUI.songLabel.getLabel();
        if (null != songLabel)
            songLabel.setTextColor(colors.songLabel);
    }

}
