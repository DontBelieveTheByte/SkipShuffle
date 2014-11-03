package com.dontbelievethebyte.skipshuffle.activities;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.BaseUI;
import com.dontbelievethebyte.skipshuffle.ui.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.Play;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.Playlist;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.Prev;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.Shuffle;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.Skip;
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
        ui.player.reboot();
    }

    @Override
    protected void handleBackPressed()
    {

    }

    @Override
    protected void setUI(Integer type)
    {
        CustomTypeface customTypeface = new CustomTypeface(this, type);
        Drawables drawables = new Drawables(this, type);
        PlayerUI player = new PlayerUI(
            this,
            customTypeface,
            drawables
        );

        BaseUI.UIBuilder uiBuilder = new BaseUI.UIBuilder();
        uiBuilder.setActivity(this);
        uiBuilder.setContentArea(new ContentArea(this, R.layout.main_activity));
        uiBuilder.setPlayer(player);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer());
        uiBuilder.setColors(new Colors(type));
        uiBuilder.setDrawables(drawables);
        uiBuilder.setCustomTypeFace(customTypeface);
        ui = uiBuilder.build();

        ui.player.buttons.play.setOnClickListener(new Play(this));
        ui.player.buttons.skip.setOnClickListener(new Skip(this));
        ui.player.buttons.prev.setOnClickListener(new Prev(this));
        ui.player.buttons.shuffle.setOnClickListener(new Shuffle(this));
        ui.player.buttons.playlist.setOnClickListener(new Playlist(this));

        ui.player.reboot();
    }
}
