package com.dontbelievethebyte.skipshuffle.activities;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.SongLabel;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class PlayerActivity extends BaseActivity {

    @Override
    protected void onPause()
    {
        //Give a break to GPU when hidden
        ui.player.buttons.play.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void setUI(Integer type)
    {
        ContentArea contentArea = new ContentArea(this, R.layout.main_activity);
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
    }

    @Override
    public void onMediaPlayerAvailable()
    {
        ui.player.reboot();
    }
}
