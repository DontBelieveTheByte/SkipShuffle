package com.dontbelievethebyte.skipshuffle.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.services.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DrawableMapper;

public class PlayerNotification {

    public static int getNotificationId()
    {
        return NOTIFICATION_ID;
    }

    private static final int NOTIFICATION_ID = 9001;
    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;

    public PlayerNotification(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
    }

    private PendingIntent buildNotificationButtonsPendingIntent(String command, int requestCode)
    {
        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, command);
        return PendingIntent.getBroadcast(
                skipShuffleMediaPlayer,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
    }

    public void cancel()
    {
        NotificationManager notificationManager = (NotificationManager) skipShuffleMediaPlayer.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public void showNotification()
    {
        NotificationManager notificationManager = (NotificationManager) skipShuffleMediaPlayer.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(
                NOTIFICATION_ID,
                buildNotification()
        );
    }

    public Notification buildNotification()
    {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(skipShuffleMediaPlayer);
        notificationBuilder.setSmallIcon(R.drawable.ic_notification);
        notificationBuilder.setContent(
                buildUI()
        );
        return notificationBuilder.build();
    }

    private RemoteViews buildUI()
    {
        Intent mainActivityIntent = new Intent(skipShuffleMediaPlayer, PlayerActivity.class);
        SkipShuffleMediaPlayer.AndroidPlayerWrapper androidPlayerWrapper = skipShuffleMediaPlayer.getPlayerWrapper();

        RemoteViews remoteViews = new RemoteViews(
                skipShuffleMediaPlayer.getPackageName(),
                R.layout.notification
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_prev,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PREV,
                        0
                )
        );
        remoteViews.setOnClickPendingIntent(
                R.id.notif_shuffle,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST,
                        1
                )
        );
        remoteViews.setOnClickPendingIntent(
                R.id.notif_skip,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SKIP,
                        3
                )
        );

        if (!androidPlayerWrapper.isPlaying()) {
            remoteViews.setImageViewResource(
                    R.id.notif_play,
                    DrawableMapper.getPause(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
            );
        } else {
            remoteViews.setImageViewResource(
                    R.id.notif_play,
                    DrawableMapper.getPlay(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
            );
        }

        remoteViews.setOnClickPendingIntent(
                R.id.notif_play,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE,
                        2
                )
        );

        PendingIntent mainActivityPendingIntent = PendingIntent.getActivity(
                skipShuffleMediaPlayer,
                4,
                mainActivityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_all,
                mainActivityPendingIntent
        );
        return remoteViews;
    }
}
