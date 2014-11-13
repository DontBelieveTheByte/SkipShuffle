package com.dontbelievethebyte.skipshuffle.activities;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.content.AbstractContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.content.ListContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.content.PlayerContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.SongLabel;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class PlayerActivity extends BaseActivity {

//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        preferencesHelper.re
//    }

    @Override
    protected void setUI(Integer type)
    {
        if (preferencesHelper.getListViewMode())
            setPlayerUI(type);
        else
            setListUI(type);
    }

    private void setPlayerUI(Integer type)
    {
        AbstractContentArea contentArea = new PlayerContentArea(this);
        CustomTypeface customTypeface = new CustomTypeface(this, type);
        Drawables drawables = new Drawables(this, type);

        MainPlayerButtons buttons = new MainPlayerButtons(contentArea);
        buttons.animations = new PlayerButtonsAnimations(this);
        buttons.drawables = drawables;

        SongLabel songLabel = new SongLabel(contentArea, R.id.song_label);
        songLabel.setTypeFace(customTypeface);

        MainPlayer player = new MainPlayer(
                this,
                buttons,
                songLabel
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(this);
        uiBuilder.setContentArea(contentArea);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer(customTypeface));
        uiBuilder.setColors(new Colors(type));
        uiBuilder.setDrawables(drawables);
        uiBuilder.setPlayer(player);
        ui = uiBuilder.build();
        ui.player.reboot();
    }

    private void setListUI(Integer type)
    {
        ListContentArea contentArea = new ListContentArea(this);
        CustomTypeface customTypeface = new CustomTypeface(this, type);
        Drawables drawables = new Drawables(this, type);

        ListPlayerButtons buttons = new ListPlayerButtons(contentArea);
        buttons.animations = new PlayerButtonsAnimations(this);
        buttons.drawables = drawables;

        SongLabel songLabel = new SongLabel(contentArea, R.id.song_label);
        songLabel.setTypeFace(customTypeface);

        ListPlayer player = new ListPlayer(
                this,
                buttons,
                songLabel
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(this);
        uiBuilder.setContentArea(contentArea);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer(customTypeface));
        uiBuilder.setColors(new Colors(type));
        uiBuilder.setDrawables(drawables);
        uiBuilder.setPlayer(player);
        ui = uiBuilder.build();
        ui.player.reboot();
    }

    @Override
    public void onMediaPlayerAvailable()
    {
        ui.player.reboot();
    }

}
