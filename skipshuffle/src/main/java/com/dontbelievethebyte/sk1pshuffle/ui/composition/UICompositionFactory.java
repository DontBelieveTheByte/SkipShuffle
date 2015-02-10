/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.activity.ContentBrowserActivity;
import com.dontbelievethebyte.sk1pshuffle.activity.PlayerActivity;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.adapter.ContentBrowserDrawerAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.listener.CurrentPlaylistItemClickListener;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.builder.UICompositionBuilder;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.navdrawer.ContentBrowserDrawer;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.ListPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.MainPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.animations.PlayerButtonsAnimations;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.concrete.ListPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.concrete.MainPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.seekbar.seeklisteners.SeekListener;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.CustomTypeface;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.Theme;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.UITypes;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Colors;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Drawables;

public class UICompositionFactory {

    public static UIComposition createMainPlayer(PlayerActivity activity, UITypes uiType)
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

        ContentBrowserDrawer contentBrowserDrawer = buildNavigationDrawer(
                activity,
                theme
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(activity);
        uiBuilder.setNavigationDrawer(contentBrowserDrawer);
        uiBuilder.setTheme(theme);
        uiBuilder.setPlayer(player);
        uiBuilder.setSeekbar(customSeekBar);
        return uiBuilder.build();
    }

    public static UIComposition createListPlayer(PlayerActivity activity, UITypes uiType) throws NoMediaPlayerException
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

        CurrentPlaylistAdapter playlistAdapter = new CurrentPlaylistAdapter(mediaPlayer);

        playlistAdapter.setTheme(theme);
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

        ContentBrowserDrawer contentBrowserDrawer = buildNavigationDrawer(
                activity,
                theme
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(activity);
        uiBuilder.setNavigationDrawer(contentBrowserDrawer);
        uiBuilder.setTheme(theme);
        uiBuilder.setPlayer(player);
        uiBuilder.setSeekbar(customSeekBar);
        return uiBuilder.build();
    }

    public static UIComposition createContentBrowser(ContentBrowserActivity activity, UITypes uiType)
    {
        Theme theme = buildTheme(activity, uiType);
        activity.setContentView(R.layout.activity_content_browser);

        activity.setContentView(R.layout.playlist_mode);
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(R.id.bottom);

        ListView listView = (ListView) viewGroup.findViewById(R.id.current_list);
        TextView emptyText = (TextView) viewGroup.findViewById(R.id.emptyView);
        listView.setEmptyView(emptyText);

        ContentBrowserDrawer contentBrowserDrawer = buildNavigationDrawer(
                activity,
                theme
        );

        UICompositionBuilder uiBuilder = new UICompositionBuilder();
        uiBuilder.setActivity(activity);
        uiBuilder.setNavigationDrawer(contentBrowserDrawer);
        uiBuilder.setTheme(theme);
        return uiBuilder.build();
    }

    private static ContentBrowserDrawer buildNavigationDrawer(Activity activity, Theme theme)
    {
        ContentBrowserDrawer contentBrowserDrawer = new ContentBrowserDrawer(activity, R.id.drawer_list);
        contentBrowserDrawer.setAdapter(
                new ContentBrowserDrawerAdapter(
                        activity,
                        R.layout.drawer_list_item,
                        activity.getResources().getStringArray(R.array.drawer_menu),
                        theme
                )
        );
        return contentBrowserDrawer;
    }

    private static Theme buildTheme(Context context, UITypes uiType)
    {
        return new Theme(
                new Colors(uiType),
                new Drawables(context, uiType),
                new CustomTypeface(context, uiType)
        );
    }
}
