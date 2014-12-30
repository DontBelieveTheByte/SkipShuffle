/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;

public abstract class AbstractRemoteViewsBuilder {

    protected RemoteViews remoteViews;
    protected Colorizer colorize;
    protected Context context;

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

    public AbstractRemoteViewsBuilder(Context context)
    {
        this.context = context;
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

    protected void buildContainer(String packageName)
    {
        remoteViews = new RemoteViews(
                packageName,
                R.layout.notification
        );
    }

    protected void buildPrev(int resourceId)
    {
        remoteViews.setOnClickPendingIntent(
                resourceId,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.PREV,
                        0
                )
        );
    }

    protected void buildPlay(int resourceId, Integer drawable, boolean isPlaying)
    {
        if (null != drawable) {
            remoteViews.setImageViewResource(
                    resourceId,
                    drawable
            );
        }

        String command = (isPlaying) ?
                SkipShuflleMediaPlayerCommandsContract.PAUSE :
                SkipShuflleMediaPlayerCommandsContract.PLAY;

        remoteViews.setOnClickPendingIntent(
                resourceId,
                buildNotificationButtonsPendingIntent(
                        command,
                        2
                )
        );
    }

    protected void buildShuffle(int resourceId, Integer drawable)
    {
        if (null != drawable) {
            remoteViews.setImageViewResource(
                    resourceId,
                    drawable
            );
        }

        remoteViews.setOnClickPendingIntent(
                R.id.notif_shuffle,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.SHUFFLE,
                        1
                )
        );
    }

    protected void buildSkip(int resourceId)
    {
        remoteViews.setOnClickPendingIntent(
                resourceId,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.SKIP,
                        3
                )
        );
    }

    protected void buildDefault(int resourceId)
    {
        Intent playerActivityIntent = new Intent(context, PlayerActivity.class);

        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(
                context,
                4,
                playerActivityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        remoteViews.setOnClickPendingIntent(
                resourceId,
                mainActivityPendingIntent
        );
    }

    protected PendingIntent buildNotificationButtonsPendingIntent(String command, int requestCode)
    {
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, command);
        intent.setPackage(context.getPackageName());
        return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_ONE_SHOT
        );
    }
}
