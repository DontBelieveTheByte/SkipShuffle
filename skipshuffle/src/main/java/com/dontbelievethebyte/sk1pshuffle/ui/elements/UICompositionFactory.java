/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements;

import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.activities.ContentBrowserActivity;
import com.dontbelievethebyte.sk1pshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.sk1pshuffle.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.sk1pshuffle.adapters.NavigationDrawerAdapter;
import com.dontbelievethebyte.sk1pshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.listeners.CurrentPlaylistClick;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.sk1pshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.layout.AbstractLayout;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.layout.ListLayout;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.layout.PlayerLayout;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.navdrawer.listeners.ContentBrowserClickListener;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.concrete.ListPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.concrete.MainPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.seekbar.seeklisteners.SeekListener;
import com.dontbelievethebyte.sk1pshuffle.ui.structure.Colors;
import com.dontbelievethebyte.sk1pshuffle.ui.structure.Drawables;

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

        CustomSeekBar customSeekBar = new CustomSeekBar(playerActivity);
        customSeekBar.setSeekListener(new SeekListener(playerActivity));

        MainPlayer player = new MainPlayer(
                playerActivity,
                buttons,
                songLabel,
                customSeekBar
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(playerActivity);
        uiBuilder.setContentArea(contentArea);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer(
                playerActivity,
                customTypeface
        ));
        uiBuilder.setColors(new Colors(uiType));
        uiBuilder.setDrawables(drawables);
        uiBuilder.setPlayer(player);
        uiBuilder.setSeekbar(customSeekBar);
        return uiBuilder.build();
    }

    public static UIComposition makeListPlayer(PlayerActivity playerActivity, int uiType) throws NoMediaPlayerException
    {
        ListLayout contentArea = new ListLayout(playerActivity);
        CustomTypeface customTypeface = new CustomTypeface(playerActivity, uiType);
        Drawables drawables = new Drawables(playerActivity, uiType);
        Colors colors = new Colors(uiType);

        ListPlayerButtons buttons = new ListPlayerButtons(contentArea);
        buttons.animations = new PlayerButtonsAnimations(playerActivity);
        buttons.drawables = drawables;

        CustomSeekBar customSeekBar = new CustomSeekBar(playerActivity);
        customSeekBar.setSeekListener(new SeekListener(playerActivity));

        MainPlayerSongLabel songLabel = new MainPlayerSongLabel(contentArea, R.id.song_label);
        songLabel.setTypeFace(customTypeface);

        ListView listView = (ListView) playerActivity.findViewById(R.id.current_list);
        TextView emptyText = (TextView) playerActivity.findViewById(R.id.emptyView);
        listView.setEmptyView(emptyText);

        SkipShuffleMediaPlayer mediaPlayer = playerActivity.getMediaPlayer();
        RandomPlaylist randomPlaylist = mediaPlayer.getPlaylist();
        CurrentPlaylistAdapter playlistAdapter = new CurrentPlaylistAdapter(
                playerActivity,
                randomPlaylist,
                mediaPlayer
        );
        playlistAdapter.setDrawables(drawables);
        playlistAdapter.setColors(colors);
        playlistAdapter.setTypeface(customTypeface.getTypeFace());
        listView.setAdapter(playlistAdapter);
        CurrentPlaylistClick currentPlaylistClick = new CurrentPlaylistClick(playerActivity);
        listView.setOnItemClickListener(currentPlaylistClick);
        listView.setOnItemLongClickListener(currentPlaylistClick);

        ListPlayer player = new ListPlayer(
                playerActivity,
                buttons,
                listView,
                customSeekBar
        );

        listView.setSelection(randomPlaylist.getCurrentPosition());

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(playerActivity);
        uiBuilder.setContentArea(contentArea);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer(
                playerActivity,
                customTypeface
        ));
        uiBuilder.setColors(colors);
        uiBuilder.setDrawables(drawables);
        uiBuilder.setPlayer(player);
        uiBuilder.setSeekbar(customSeekBar);
        return uiBuilder.build();
    }

    public static UIComposition makeContentBrowser(ContentBrowserActivity contentBrowserActivity, int uiType) throws NoMediaPlayerException
    {
          return null;
    }

    private static ContentBrowserDrawer buildNavigationDrawer(BaseActivity baseActivity, CustomTypeface customTypeface)
    {
        ContentBrowserDrawer contentBrowserDrawer = new ContentBrowserDrawer(baseActivity, R.id.drawer_list);
        contentBrowserDrawer.setClickListener(
                new ContentBrowserClickListener(
                        (DrawerLayout) baseActivity.findViewById(R.id.drawer_layout)
                )
        );
        contentBrowserDrawer.setAdapter(
                new NavigationDrawerAdapter(
                        baseActivity,
                        R.layout.drawer_list_item,
                        baseActivity.getResources().getStringArray(R.array.drawer_menu),
                        baseActivity.getPreferencesHelper(),
                        customTypeface.getTypeFace()
                )
        );
        return contentBrowserDrawer;
    }
}
