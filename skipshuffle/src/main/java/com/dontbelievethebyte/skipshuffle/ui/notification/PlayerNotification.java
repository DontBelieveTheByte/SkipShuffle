/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.notification.builder.RemoteViewsBuilder;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.structure.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structure.Drawables;

public class PlayerNotification {

    public static int getNotificationId()
    {
        return NOTIFICATION_ID;
    }

    private static final int NOTIFICATION_ID = 9001;
    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    private NotificationManager notificationManager;

    public PlayerNotification(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
        notificationManager = (NotificationManager) skipShuffleMediaPlayer.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void cancel()
    {
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public void showNotification()
    {
        notificationManager.notify(
                NOTIFICATION_ID,
                buildNotification()
        );
    }

    public Notification buildNotification()
    {
        RemoteViews remoteViews = getRemoteViews();
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(skipShuffleMediaPlayer);
        notificationBuilder.setSmallIcon(R.drawable.ic_notification);
        notificationBuilder.setContent(remoteViews);
        return notificationBuilder.build();
    }

    private RemoteViews getRemoteViews()
    {
        RemoteViewsBuilder remoteViewsBuilder = new RemoteViewsBuilder(
                skipShuffleMediaPlayer,
                new Drawables(skipShuffleMediaPlayer, skipShuffleMediaPlayer.getPreferencesHelper().getUIType()),
                new Colors(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
        );
        return remoteViewsBuilder.build();
    }
}
