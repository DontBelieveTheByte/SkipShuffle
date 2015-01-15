/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.remote.widgets.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.service.callbacks.PlayerStateChangedCallback;
import com.dontbelievethebyte.sk1pshuffle.ui.remote.widgets.widget.PlayerState;

public class PlayerNotification implements PlayerStateChangedCallback {

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
        RemoteViews remoteViews = getRemoteViews(
                new PlayerState(skipShuffleMediaPlayer)
        );
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(skipShuffleMediaPlayer);
        notificationBuilder.setSmallIcon(R.drawable.ic_notification);
        notificationBuilder.setContent(remoteViews);
        return notificationBuilder.build();
    }

    private RemoteViews getRemoteViews(PlayerState playerState)
    {
        NotificationRemoteViewsBuilder remoteViewsBuilder = new NotificationRemoteViewsBuilder(skipShuffleMediaPlayer);
        return remoteViewsBuilder.build(playerState);
    }

    @Override
    public void onPlayerStateChanged()
    {
        showNotification();
    }
}
