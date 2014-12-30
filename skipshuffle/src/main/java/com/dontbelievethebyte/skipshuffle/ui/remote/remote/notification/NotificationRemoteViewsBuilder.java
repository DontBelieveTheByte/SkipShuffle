/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.notification;

import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.remote.remote.AbstractRemoteViewsBuilder;
import com.dontbelievethebyte.skipshuffle.ui.structure.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structure.Drawables;

public class NotificationRemoteViewsBuilder extends AbstractRemoteViewsBuilder{

    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    private Colors colors;
    private Drawables drawables;

    public NotificationRemoteViewsBuilder(SkipShuffleMediaPlayer skipShuffleMediaPlayer, Drawables drawables, Colors colors)
    {
        super(skipShuffleMediaPlayer);
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
        this.colors = colors;
        this.drawables = drawables;
    }

    public void setColors(Colors colors)
    {
        this.colors = colors;
    }

//    public RemoteViews build()
//    {
//        RandomPlaylist playlist = skipShuffleMediaPlayer.getPlaylist();
//        TrackPrinter trackPrinter = new TrackPrinter(skipShuffleMediaPlayer);
//
//        buildContainer();
//
//        buildPrev();
//        buildPlay(
//
//                skipShuffleMediaPlayer.isPlaying()
//        );
//        buildShuffle((null != playlist) && playlist.isShuffle());
//        buildSkip();
//        buildDefault();
//
//        colorize();
//
//        if (null != playlist) {
//            try {
//                buildTitleLabelContent(trackPrinter.printTitle(playlist.getCurrent()));
//                buildArtistLabelContent(trackPrinter.printArtist(playlist.getCurrent()));
//            } catch (PlaylistEmptyException e) {
//                buildTitleLabelContent(null);
//                buildArtistLabelContent(null);
//            }
//        } else
//            buildTitleLabelContent(null);
//        return remoteViews;
//    }
//
    private void colorize()
    {
        colorize.label(R.id.track_title, skipShuffleMediaPlayer.getResources().getColor(colors.playlistTitle));
        colorize.label(R.id.track_artist, skipShuffleMediaPlayer.getResources().getColor(colors.playlistArtist));

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
    }

    @Override
    public RemoteViews build() {
        return null;
    }
}
