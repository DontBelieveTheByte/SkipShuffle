/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.notification;

import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.ui.remote.remote.AbstractRemoteViewsBuilder;
import com.dontbelievethebyte.skipshuffle.ui.remote.remote.widget.PlayerState;

public class NotificationRemoteViewsBuilder extends AbstractRemoteViewsBuilder{

    public NotificationRemoteViewsBuilder(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        super(skipShuffleMediaPlayer);
    }

    @Override
    public RemoteViews build(PlayerState playerState)
    {

        buildContainer(R.layout.notification);

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

        buildTitleLabelContent(playerState.getTitle());
        buildArtistLabelContent(playerState.getArtist());

        colorize(playerState);

        return remoteViews;
    }

    @Override
    protected int getRequestCodeFactor() {
        return 1;
    }

    private void colorize(PlayerState playerState)
    {
        int uiType = playerState.getUiType();
        colorize.label(R.id.track_title,
                context.getResources().getColor(
                        ColorMapper.getPlaylistTitle(uiType)
                )
        );

        colorize.label(
                R.id.track_artist, context.getResources().getColor(
                        ColorMapper.getPlaylistArtist(uiType)
                )
        );

        colorize.drawable(
                R.id.notif_play,
                context.getResources().getColor(
                        playerState.isPlaying() ?
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
