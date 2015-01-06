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
    public RemoteViews build(PlayerState playerState)
    {
        buildContainer(R.layout.widget);

        buildPrev(R.id.notif_prev);

        buildPlay(
                R.id.notif_play,
                playerState.isPlaying() ? DrawableMapper.getPlay(playerState.getUiType()) : DrawableMapper.getPause(playerState.getUiType()),
                playerState.isPlaying()
        );

        buildShuffle(
                R.id.notif_shuffle,
                playerState.isShuffle() ? DrawableMapper.getShufflePressed(playerState.getUiType()) : DrawableMapper.getShuffle(playerState.getUiType())
        );

        buildSkip(R.id.notif_skip);

        buildDefault(R.id.notif_all);

        if (null != playerState.getTitle())
            buildTitleLabelContent(playerState.getTitle());
        if (null != playerState.getArtist())
            buildArtistLabelContent(playerState.getArtist());

        colorize(playerState.getUiType(), playerState.isPlaying());
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
