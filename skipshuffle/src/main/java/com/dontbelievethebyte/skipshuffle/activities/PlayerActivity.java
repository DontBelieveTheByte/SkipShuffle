package com.dontbelievethebyte.skipshuffle.activities;

import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.listeners.CurrentPlaylistClick;
import com.dontbelievethebyte.skipshuffle.playlists.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.AbstractLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.SongLabel;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class PlayerActivity extends BaseActivity {


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
        AbstractLayout contentArea = new PlayerLayout(this);
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
        ListLayout contentArea = new ListLayout(this);
        CustomTypeface customTypeface = new CustomTypeface(this, type);
        Drawables drawables = new Drawables(this, type);
        Colors colors = new Colors(type);

        ListPlayerButtons buttons = new ListPlayerButtons(contentArea);
        buttons.animations = new PlayerButtonsAnimations(this);
        buttons.drawables = drawables;

        SongLabel songLabel = new SongLabel(contentArea, R.id.song_label);
        songLabel.setTypeFace(customTypeface);

        ListView listView = (ListView) findViewById(R.id.current_list);
        TextView emptyText = (TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);

        try {
            SkipShuffleMediaPlayer mediaPlayer = getMediaPlayer();
            RandomPlaylist randomPlaylist = (RandomPlaylist) mediaPlayer.getPlaylist();
            CurrentPlaylistAdapter playlistAdapter = new CurrentPlaylistAdapter(
                    getApplicationContext(),
                    randomPlaylist,
                    mediaPlayer
            );
            playlistAdapter.setDrawables(drawables);
            playlistAdapter.setColors(colors);
            listView.setAdapter(playlistAdapter);
            listView.setOnItemClickListener(new CurrentPlaylistClick(this));
            listView.smoothScrollToPosition(randomPlaylist.getPosition());
        } catch (NoMediaPlayerException e) {
            e.printStackTrace();
        }

        ListPlayer player = new ListPlayer(
                this,
                buttons,
                songLabel,
                listView
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(this);
        uiBuilder.setContentArea(contentArea);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer(customTypeface));
        uiBuilder.setColors(colors);
        uiBuilder.setDrawables(drawables);
        uiBuilder.setPlayer(player);
        ui = uiBuilder.build();


        ui.player.reboot();
    }
}
