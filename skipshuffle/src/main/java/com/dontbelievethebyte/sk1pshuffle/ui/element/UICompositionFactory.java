/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.activity.ContentBrowserActivity;
import com.dontbelievethebyte.sk1pshuffle.activity.PlayerActivity;
import com.dontbelievethebyte.sk1pshuffle.adapter.NavigationDrawerAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.listener.CurrentPlaylistItemClickListener;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.builder.UICompositionBuilder;
import com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.navdrawer.listeners.ContentBrowserClickListener;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.MainPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.concrete.ListPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.concrete.MainPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.seekbar.seeklisteners.SeekListener;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.CustomTypeface;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.Theme;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Colors;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Drawables;

public class UICompositionFactory {

    public static UIComposition createMainPlayer(PlayerActivity activity, int uiType)
    {
        activity.setContentView(R.layout.player_mode);
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.bottom);

        Theme theme = buildTheme(activity, uiType);

        MainPlayerButtons buttons = new MainPlayerButtons(viewGroup);
        buttons.animations = new PlayerButtonsAnimations(activity);
        buttons.drawables = theme.getDrawables();

        MainPlayerSongLabel songLabel = new MainPlayerSongLabel(viewGroup , R.id.song_label);
        songLabel.setTypeFace(theme.getCustomTypeface());

        CustomSeekBar customSeekBar = new CustomSeekBar(activity);
        customSeekBar.setSeekListener(new SeekListener(activity));

        MainPlayer player = new MainPlayer(
                activity,
                buttons,
                songLabel,
                customSeekBar
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(activity);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer(
                activity,
                theme.getCustomTypeface()
        ));
        uiBuilder.setTheme(theme);
        uiBuilder.setPlayer(player);
        uiBuilder.setSeekbar(customSeekBar);
        return uiBuilder.build();
    }

    public static UIComposition createListPlayer(PlayerActivity activity, int uiType) throws NoMediaPlayerException
    {
        Theme theme = buildTheme(activity, uiType);

        activity.setContentView(R.layout.playlist_mode);
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.bottom);

        ListPlayerButtons buttons = new ListPlayerButtons(viewGroup);
        buttons.animations = new PlayerButtonsAnimations(activity);
        buttons.drawables = theme.getDrawables();

        CustomSeekBar customSeekBar = new CustomSeekBar(activity);
        customSeekBar.setSeekListener(new SeekListener(activity));

        ListView listView = (ListView) viewGroup.findViewById(R.id.current_list);
        TextView emptyText = (TextView) viewGroup.findViewById(R.id.emptyView);
        listView.setEmptyView(emptyText);

        SkipShuffleMediaPlayer mediaPlayer = activity.getMediaPlayer();
        RandomPlaylist randomPlaylist = mediaPlayer.getPlaylist();

        CurrentPlaylistAdapter playlistAdapter = new CurrentPlaylistAdapter(
                activity,
                randomPlaylist,
                mediaPlayer
        );

        playlistAdapter.setDrawables(theme.getDrawables());
        playlistAdapter.setColors(theme.getColors());
        playlistAdapter.setTypeface(theme.getCustomTypeface().getTypeFace());
        listView.setAdapter(playlistAdapter);

        CurrentPlaylistItemClickListener currentPlaylistClick = new CurrentPlaylistItemClickListener(activity);
        listView.setOnItemClickListener(currentPlaylistClick);
        listView.setOnItemLongClickListener(currentPlaylistClick);

        ListPlayer player = new ListPlayer(
                activity,
                buttons,
                listView,
                customSeekBar
        );

        listView.setSelection(randomPlaylist.getCurrentPosition());

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(activity);
        uiBuilder.setNavigationDrawer(buildNavigationDrawer(
                activity,
                theme.getCustomTypeface()
        ));
        uiBuilder.setTheme(theme);
        uiBuilder.setPlayer(player);
        uiBuilder.setSeekbar(customSeekBar);
        return uiBuilder.build();
    }

    public static UIComposition createContentBrowser(ContentBrowserActivity activity, int uiType)
    {
        Theme theme = buildTheme(activity, uiType);
        activity.setContentView(R.layout.activity_content_browser);

        activity.setContentView(R.layout.playlist_mode);
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.bottom);

        ListView listView = (ListView) viewGroup.findViewById(R.id.current_list);
        TextView emptyText = (TextView) viewGroup.findViewById(R.id.emptyView);
        listView.setEmptyView(emptyText);

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(activity);
        uiBuilder.setTheme(theme);
        return uiBuilder.build();
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

    private static Theme buildTheme(Context context, int uiType)
    {
        return new Theme(
                new Colors(uiType),
                new Drawables(context, uiType),
                new CustomTypeface(context, uiType)
        );
    }
}
