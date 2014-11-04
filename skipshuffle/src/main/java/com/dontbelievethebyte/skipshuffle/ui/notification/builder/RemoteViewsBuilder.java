package com.dontbelievethebyte.skipshuffle.ui.notification.builder;


import android.app.PendingIntent;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DrawableMapper;

public class RemoteViewsBuilder {

    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    private RemoteViews remoteViews;

    public RemoteViewsBuilder(SkipShuffleMediaPlayer skipShuffleMediaPlayer)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
    }

    public RemoteViews build()
    {
        buildInit();
        buildPrev();
        buildPlay();
        buildShuffle();
        buildSkip();
        buildDefault();
        return remoteViews;
    }

    private void buildInit()
    {
        remoteViews = new RemoteViews(
                skipShuffleMediaPlayer.getPackageName(),
                R.layout.notification
        );
    }

    private void buildPrev()
    {
        remoteViews.setOnClickPendingIntent(
                R.id.notif_prev,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PREV,
                        0
                )
        );
    }

    private void buildPlay()
    {
        int imageResource = (skipShuffleMediaPlayer.isPlaying()) ?
                DrawableMapper.getPause(skipShuffleMediaPlayer.getPreferencesHelper().getUIType()) :
                DrawableMapper.getPlay(skipShuffleMediaPlayer.getPreferencesHelper().getUIType()) ;

        remoteViews.setImageViewResource(
                R.id.notif_play,
                imageResource
        );

        remoteViews.setOnClickPendingIntent(
                R.id.notif_play,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_PLAY_PAUSE_TOGGLE,
                        2
                )
        );
    }

    private void buildShuffle()
    {
        remoteViews.setOnClickPendingIntent(
                R.id.notif_shuffle,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SHUFFLE_PLAYLIST,
                        1
                )
        );
    }

    private void buildSkip()
    {
        remoteViews.setOnClickPendingIntent(
                R.id.notif_skip,
                buildNotificationButtonsPendingIntent(
                        SkipShuflleMediaPlayerCommandsContract.CMD_SKIP,
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
        return PendingIntent.getBroadcast(
                skipShuffleMediaPlayer,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
    }
}
