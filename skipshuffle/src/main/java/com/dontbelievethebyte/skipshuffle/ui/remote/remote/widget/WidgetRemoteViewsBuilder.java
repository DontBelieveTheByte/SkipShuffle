/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.widget;

import android.content.Context;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.ui.remote.remote.AbstractRemoteViewsBuilder;

public class WidgetRemoteViewsBuilder extends AbstractRemoteViewsBuilder{

    public WidgetRemoteViewsBuilder(Context context)
    {
        super(context);
    }

    @Override
    public RemoteViews build(int uiType, boolean isPlaying, boolean isShuffle, String title, String artist)
    {
        buildContainer(R.layout.widget);

        buildPrev(R.id.notif_prev);

        buildPlay(
                R.id.notif_play,
                isPlaying ? DrawableMapper.getPlay(uiType) : DrawableMapper.getPause(uiType),
                isPlaying
        );

        buildShuffle(
                R.id.notif_shuffle,
                isShuffle ? DrawableMapper.getShufflePressed(uiType) : DrawableMapper.getShuffle(uiType)
        );

        buildSkip(R.id.notif_skip);

        buildDefault(R.id.notif_all);

        if (null != title)
            buildTitleLabelContent(title);
        if (null != artist)
            buildArtistLabelContent(artist);

        colorize(uiType, isPlaying);
        return remoteViews;
    }

    private void colorize(int uiType, boolean isPlaying)
    {
        colorize.label(R.id.track_title,
                context.getResources().getColor(
                        ColorMapper.getPlaylistTitle(uiType)
                )
        );

        colorize.label(
                R.id.track_artist,
                context.getResources().getColor(
                        ColorMapper.getPlaylistArtist(uiType)
                )
        );

        colorize.drawable(
                R.id.notif_play,
                context.getResources().getColor(
                        isPlaying ?
                                ColorMapper.getPlayButton(uiType) :
                                ColorMapper.getPauseButton(uiType)
                )
        );

        colorize.drawable(
                R.id.notif_prev,
                context.getResources().getColor(
                        ColorMapper.getPrevButton(uiType)
                )
        );

        colorize.drawable(
                R.id.notif_shuffle,
                context.getResources().getColor(
                        ColorMapper.getShuffleButton(uiType)
                )
        );

        colorize.drawable(
                R.id.notif_skip,
                context.getResources().getColor(
                        ColorMapper.getSkipButton(uiType)
                )
        );

        colorize.drawable(
                R.id.buttons_background_image,
                context.getResources().getColor(
                        ColorMapper.getBackground(uiType)
                )
        );

        colorize.drawable(
                R.id.buttons_background_image_overflow_protection,
                context.getResources().getColor(
                        ColorMapper.getBackground(uiType)
                )
        );
    }
}
