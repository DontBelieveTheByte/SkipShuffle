/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.notification;

import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DrawableMapper;
import com.dontbelievethebyte.skipshuffle.ui.remote.remote.AbstractRemoteViewsBuilder;

public class NotificationRemoteViewsBuilder extends AbstractRemoteViewsBuilder{

    public NotificationRemoteViewsBuilder(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        super(skipShuffleMediaPlayer);
        context = skipShuffleMediaPlayer;
    }

    @Override
    public RemoteViews build()
    {
        SkipShuffleMediaPlayer skipShuffleMediaPlayer = (SkipShuffleMediaPlayer) context;
        RandomPlaylist playlist = skipShuffleMediaPlayer.getPlaylist();
        TrackPrinter trackPrinter = new TrackPrinter(skipShuffleMediaPlayer);
        buildContainer(skipShuffleMediaPlayer.getPackageName());
        buildPrev(R.id.notif_prev);

        int uiType = skipShuffleMediaPlayer.getPreferencesHelper().getUIType();

        buildPlay(
                R.id.notif_play,
                skipShuffleMediaPlayer.isPlaying() ? DrawableMapper.getPlay(uiType) : DrawableMapper.getPause(uiType),
                skipShuffleMediaPlayer.isPlaying()
        );
        buildShuffle(
                R.id.notif_shuffle,
                (null != playlist) && playlist.isShuffle() ? DrawableMapper.getShufflePressed(uiType) : DrawableMapper.getShuffle(uiType)
        );

        buildSkip(R.id.notif_skip);

        buildDefault(R.id.notif_all);

        if (null != playlist) {
            try {
                buildTitleLabelContent(trackPrinter.printTitle(playlist.getCurrent()));
                buildArtistLabelContent(trackPrinter.printArtist(playlist.getCurrent()));
            } catch (PlaylistEmptyException e) {
                buildTitleLabelContent(null);
                buildArtistLabelContent(null);
            }
        } else
            buildTitleLabelContent(null);

        colorize();

        return remoteViews;
    }

    private void colorize()
    {
        SkipShuffleMediaPlayer skipShuffleMediaPlayer = (SkipShuffleMediaPlayer) context;
        colorize.label(R.id.track_title,
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getPlaylistTitle(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        colorize.label(
                R.id.track_artist, skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getPlaylistArtist(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        colorize.drawable(
                R.id.notif_play,
                skipShuffleMediaPlayer.getResources().getColor(
                        skipShuffleMediaPlayer.isPlaying() ?
                                ColorMapper.getPlayButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType()) :
                                ColorMapper.getPauseButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        colorize.drawable(
                R.id.notif_prev,
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getPrevButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        colorize.drawable(
                R.id.notif_shuffle,
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getShuffleButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        colorize.drawable(
                R.id.notif_skip,
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getSkipButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        colorize.drawable(
                R.id.buttons_background_image,
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getBackground(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        colorize.drawable(
                R.id.buttons_background_image_overflow_protection,
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getBackground(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );
    }
}
