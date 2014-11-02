package com.dontbelievethebyte.skipshuffle.ui.visitor;

import android.view.ViewGroup;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.BaseUI;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.UIElement;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;

public class ColorVisitor {

    Colors colors;

    public ColorVisitor(Colors colors)
    {
        this.colors = colors;
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
        ListView drawerList = musicPlayerDrawer.getDrawerList();

        drawerList.setBackgroundResource(colors.navDrawerBackground);

        drawerList.setDivider(
                Colors.toColorDrawable(colors.listDivider)
        );

    }

    private void visitBaseUI(BaseUI baseUI)
    {
        ViewGroup bottomLayout = baseUI.getBottomLayout();
        bottomLayout.setBackgroundResource(colors.background);
    }

    private void visitPlayerUI(PlayerUI playerUI)
    {
//        songLabel.setTextColor(
//                baseActivity.getResources().getColor(
//                        ColorMapper.getSongLabel(uiType)
//                )
//        );
    }

}
