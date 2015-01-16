/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.remote.widget.widget;

import android.content.Context;
import android.widget.RemoteViews;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.remote.widget.AbstractRemoteViewsBuilder;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.ColorMapper;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.DrawableMapper;

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

        colorize(playerState);
        return remoteViews;
    }

    private void colorize(PlayerState playerState)
    {
        colorize.label(R.id.track_title,
                context.getResources().getColor(
                        ColorMapper.getPlaylistTitle(playerState.getUiType())
                )
        );

        colorize.label(
                R.id.track_artist,
                context.getResources().getColor(
                        ColorMapper.getPlaylistArtist(playerState.getUiType())
                )
        );

        colorize.drawable(
                R.id.notif_play,
                context.getResources().getColor(
                        playerState.isPlaying() ?
                                ColorMapper.getPlayButton(playerState.getUiType()) :
                                ColorMapper.getPauseButton(playerState.getUiType())
                )
        );

        colorize.drawable(
                R.id.notif_prev,
                context.getResources().getColor(
                        ColorMapper.getPrevButton(playerState.getUiType())
                )
        );

        colorize.drawable(
                R.id.notif_shuffle,
                context.getResources().getColor(
                        ColorMapper.getShuffleButton(playerState.getUiType())
                )
        );

        colorize.drawable(
                R.id.notif_skip,
                context.getResources().getColor(
                        ColorMapper.getSkipButton(playerState.getUiType())
                )
        );

        colorize.drawable(
                R.id.buttons_background_image,
                context.getResources().getColor(
                        ColorMapper.getBackground(playerState.getUiType())
                )
        );

        colorize.drawable(
                R.id.buttons_background_image_overflow_protection,
                context.getResources().getColor(
                        ColorMapper.getBackground(playerState.getUiType())
                )
        );
    }
}
