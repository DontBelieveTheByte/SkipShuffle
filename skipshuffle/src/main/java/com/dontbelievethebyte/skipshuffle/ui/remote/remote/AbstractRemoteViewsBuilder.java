/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote;

import android.app.PendingIntent;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;

public abstract class AbstractRemoteViewsBuilder {

    protected SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    protected RemoteViews remoteViews;
    protected Colorizer colorize;

    protected class Colorizer {
        public void label(int resourceId, int color)
        {
            remoteViews.setTextColor(
                    resourceId,
                    color
            );
        }

        public void drawable(int resourceId, int color)
        {
            remoteViews.setInt(
                    resourceId,
                    "setColorFilter",
                    color
            );
        }
    }

    public AbstractRemoteViewsBuilder(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
        colorize = new Colorizer();
    }

    public abstract RemoteViews build();

    protected void buildTitleLabelContent(String title)
    {
        remoteViews.setTextViewText(
                R.id.track_title,
                title
        );
    }

    protected void buildArtistLabelContent(String artist)
    {
        remoteViews.setTextViewText(
                R.id.track_artist,
                artist
        );
    }

    protected void buildContainer()
    {
        remoteViews = new RemoteViews(
                skipShuffleMediaPlayer.getPackageName(),
                R.layout.notification
        );

//        remoteViews.setImageViewResource(
//                R.id.buttons_background_image,
//                drawables.notificationBackground
//        );
//
//        remoteViews.setImageViewResource(
//                R.id.buttons_background_image_overflow_protection,
//                drawables.notificationBackground
//        );
    }

    protected void buildPrev()
    {

        remoteViews.setOnClickPendingIntent(
                R.id.notif_prev,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.PREV,
                        0
                )
        );
    }

    protected void buildPlay(Integer drawable, boolean isPlaying)
    {
        if (null != drawable) {
            remoteViews.setImageViewResource(
                    R.id.notif_play,
                    drawable
            );
        }

        String command = (skipShuffleMediaPlayer.isPlaying()) ?
                SkipShuflleMediaPlayerCommandsContract.PAUSE :
                SkipShuflleMediaPlayerCommandsContract.PLAY;

        remoteViews.setOnClickPendingIntent(
                R.id.notif_play,
                buildNotificationButtonsPendingIntent(
                        command,
                        2
                )
        );
    }

    protected void buildShuffle(boolean isShuffle)
    {
//        remoteViews.setImageViewResource(
//                R.id.notif_shuffle,
//                isShuffle ? drawables.shufflePressed : drawables.shuffle
//        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_shuffle,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.SHUFFLE,
                        1
                )
        );
    }

    protected void buildSkip()
    {
//        remoteViews.setImageViewResource(
//                R.id.notif_skip,
//                drawables.skip
//        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_skip,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.SKIP,
                        3
                )
        );
    }

    protected void buildDefault()
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

    protected PendingIntent buildNotificationButtonsPendingIntent(String command, int requestCode)
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
