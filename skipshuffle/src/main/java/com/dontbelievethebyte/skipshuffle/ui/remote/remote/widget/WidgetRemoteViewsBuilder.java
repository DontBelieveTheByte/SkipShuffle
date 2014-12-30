/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.widget;

import android.app.PendingIntent;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.mapper.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.structure.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structure.Drawables;

public class WidgetRemoteViewsBuilder {

    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    private RemoteViews remoteViews;
    private Drawables drawables;
    private Colors colors;

    public WidgetRemoteViewsBuilder(SkipShuffleMediaPlayer skipShuffleMediaPlayer, Drawables drawables, Colors colors)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
        this.drawables = drawables;
        this.colors = colors;
    }

    public void setDrawables(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public void setColors(Colors colors)
    {
        this.colors = colors;
    }

    public RemoteViews buildNotification()
    {
        RandomPlaylist playlist = skipShuffleMediaPlayer.getPlaylist();
        TrackPrinter trackPrinter = new TrackPrinter(skipShuffleMediaPlayer);
        buildContainer();
        buildPrev();
        buildPlay(skipShuffleMediaPlayer.isPlaying());
        buildShuffle((null != playlist) && playlist.isShuffle());
        buildSkip();
        buildDefault();
        buildTextLabelsColor();
        if (null != playlist) {
            try {
                buildTitleLabelContent(trackPrinter.printTitle(playlist.getCurrent()));
            } catch (PlaylistEmptyException e) {
                buildTitleLabelContent(null);
            }
        } else
            buildTitleLabelContent(null);
        return remoteViews;
    }

    public RemoteViews buildWidget()
    {
        boolean isPlaying = false;
        boolean isShuffle = true;
        String title = "derp";
        String artist = "merp";
        buildContainer();
        buildPrev();
        buildPlay(isPlaying);
        buildShuffle(isShuffle);
        buildSkip();
        buildDefault();
        buildTextLabelsColor();
        buildTitleLabelContent(title);
        buildArtistLabelContent(artist);

        return remoteViews;
    }

    private void buildTextLabelsColor()
    {
        int trackTitleLabelColor = skipShuffleMediaPlayer.getResources().getColor(colors.playlistTitle);
        remoteViews.setTextColor(
                R.id.track_title,
                trackTitleLabelColor
        );

        int trackArtistLabelColor = skipShuffleMediaPlayer.getResources().getColor(colors.playlistArtist);
        remoteViews.setTextColor(
                R.id.track_artist,
                trackArtistLabelColor
        );
    }

    private void buildTitleLabelContent(String title)
    {
        remoteViews.setTextViewText(
                R.id.track_title,
                title
        );
    }

    private void buildArtistLabelContent(String artist)
    {
        remoteViews.setTextViewText(
                R.id.track_artist,
                artist
        );
    }

    private void buildContainer()
    {
        remoteViews = new RemoteViews(
                skipShuffleMediaPlayer.getPackageName(),
                R.layout.notification
        );

        remoteViews.setImageViewResource(
                R.id.buttons_background_image,
                drawables.notificationBackground
        );

        remoteViews.setImageViewResource(
                R.id.buttons_background_image_overflow_protection,
                drawables.notificationBackground
        );
    }

    private void buildPrev()
    {
        remoteViews.setImageViewResource(
                R.id.notif_prev,
                drawables.prev
        );

        remoteViews.setInt(
                R.id.notif_prev,
                "setColorFilter",
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getPrevButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_prev,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.PREV,
                        0
                )
        );
    }

    private void buildPlay(boolean isPlaying)
    {
        int imageResource = isPlaying ? drawables.play : drawables.pause;

        remoteViews.setImageViewResource(
                R.id.notif_play,
                imageResource
        );

        String command = (skipShuffleMediaPlayer.isPlaying()) ?
                SkipShuflleMediaPlayerCommandsContract.PAUSE :
                SkipShuflleMediaPlayerCommandsContract.PLAY;

        remoteViews.setInt(
                R.id.notif_play,
                "setColorFilter",
                skipShuffleMediaPlayer.getResources().getColor(
                        skipShuffleMediaPlayer.isPlaying() ?
                                ColorMapper.getPlayButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType()) :
                                ColorMapper.getPauseButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_play,
                buildNotificationButtonsPendingIntent(
                        command,
                        2
                )
        );
    }

    private void buildShuffle(boolean isShuffle)
    {
        remoteViews.setImageViewResource(
                R.id.notif_shuffle,
                isShuffle ? drawables.shufflePressed : drawables.shuffle
        );

        remoteViews.setInt(
                R.id.notif_shuffle,
                "setColorFilter",
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getShuffleButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_shuffle,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.SHUFFLE,
                        1
                )
        );
    }

    private void buildSkip()
    {
        remoteViews.setImageViewResource(
                R.id.notif_skip,
                drawables.skip
        );

        remoteViews.setInt(
                R.id.notif_skip,
                "setColorFilter",
                skipShuffleMediaPlayer.getResources().getColor(
                        ColorMapper.getSkipButton(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
                )
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_skip,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.SKIP,
                        3
                )
        );
    }

    private void buildDefault()
    {
        Intent playerActivityIntent = new Intent(skipShuffleMediaPlayer, PlayerActivity.class);

        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(
                skipShuffleMediaPlayer,
                4,
                playerActivityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_all,
                mainActivityPendingIntent
        );
    }

    private PendingIntent buildNotificationButtonsPendingIntent(String command, int requestCode)
    {
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, command);
        intent.setPackage(skipShuffleMediaPlayer.getPackageName());
        return PendingIntent.getBroadcast(
                skipShuffleMediaPlayer,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_ONE_SHOT
        );
    }
}
