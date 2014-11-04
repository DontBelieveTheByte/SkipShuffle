package com.dontbelievethebyte.skipshuffle.ui.visitor;

import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.PlayerUI;
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
        else if (uiElement instanceof ContentArea)
            visitContentArea((ContentArea) uiElement);
        else if (uiElement instanceof PlayerUI)
            visitPlayerUI((PlayerUI) uiElement);
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

    private void visitContentArea(ContentArea contentArea)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();
        bottomLayout.setBackgroundResource(colors.background);
    }

    private void visitPlayerUI(PlayerUI playerUI)
    {
        TextView songLabel = playerUI.songLabel.getLabel();
        if (null != songLabel)
            songLabel.setTextColor(colors.songLabel);
    }

}
