package com.dontbelievethebyte.skipshuffle.ui;

import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.listeners.CurrentPlaylistClick;
import com.dontbelievethebyte.skipshuffle.playlists.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.AbstractLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class UICompositionFactory {

    public static UIComposition makeMainPlayer(PlayerActivity playerActivity, int uiType)
    {
        AbstractLayout contentArea = new PlayerLayout(playerActivity);
        CustomTypeface customTypeface = new CustomTypeface(playerActivity, uiType);
        Drawables drawables = new Drawables(playerActivity, uiType);

        MainPlayerButtons buttons = new MainPlayerButtons(contentArea);
        buttons.animations = new PlayerButtonsAnimations(playerActivity);
        buttons.drawables = drawables;

        MainPlayerSongLabel songLabel = new MainPlayerSongLabel(contentArea, R.id.song_label);
        songLabel.setTypeFace(customTypeface);

        MainPlayer player = new MainPlayer(
                playerActivity,
                buttons,
                songLabel
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(playerActivity);
        uiBuilder.setContentArea(contentArea);
        uiBuilder.setNavigationDrawer(null);
        uiBuilder.setColors(new Colors(uiType));
        uiBuilder.setDrawables(drawables);
        uiBuilder.setPlayer(player);
        return uiBuilder.build();
    }

    public static UIComposition makeListPlayer(PlayerActivity playerActivity, int uiType)
    {
        ListLayout contentArea = new ListLayout(playerActivity);
        CustomTypeface customTypeface = new CustomTypeface(playerActivity, uiType);
        Drawables drawables = new Drawables(playerActivity, uiType);
        Colors colors = new Colors(uiType);

        ListPlayerButtons buttons = new ListPlayerButtons(contentArea);
        buttons.animations = new PlayerButtonsAnimations(playerActivity);
        buttons.drawables = drawables;

        MainPlayerSongLabel songLabel = new MainPlayerSongLabel(contentArea, R.id.song_label);
        songLabel.setTypeFace(customTypeface);

        ListView listView = (ListView) playerActivity.findViewById(R.id.current_list);
        TextView emptyText = (TextView) playerActivity.findViewById(android.R.id.empty);
        listView.setEmptyView(emptyText);

        try {
            SkipShuffleMediaPlayer mediaPlayer = playerActivity.getMediaPlayer();
            RandomPlaylist randomPlaylist = (RandomPlaylist) mediaPlayer.getPlaylist();
            CurrentPlaylistAdapter playlistAdapter = new CurrentPlaylistAdapter(
                    playerActivity,
                    randomPlaylist,
                    mediaPlayer
            );
            playlistAdapter.setDrawables(drawables);
            playlistAdapter.setColors(colors);
            playlistAdapter.setTypeface(customTypeface.getTypeFace());
            listView.setAdapter(playlistAdapter);
            listView.setOnItemClickListener(new CurrentPlaylistClick(playerActivity));
            listView.smoothScrollToPosition(randomPlaylist.getPosition());
        } catch (NoMediaPlayerException e) {
            e.printStackTrace();
        }

        ListPlayer player = new ListPlayer(
                playerActivity,
                buttons,
                listView
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(playerActivity);
        uiBuilder.setContentArea(contentArea);
        uiBuilder.setNavigationDrawer(null);
        uiBuilder.setColors(colors);
        uiBuilder.setDrawables(drawables);
        uiBuilder.setPlayer(player);
        return uiBuilder.build();
    }

}
