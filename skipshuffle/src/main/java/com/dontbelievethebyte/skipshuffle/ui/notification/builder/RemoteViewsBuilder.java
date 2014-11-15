package com.dontbelievethebyte.skipshuffle.ui.notification.builder;


import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class RemoteViewsBuilder {

    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    private RemoteViews remoteViews;
    private Drawables drawables;

    public RemoteViewsBuilder(SkipShuffleMediaPlayer skipShuffleMediaPlayer, Drawables drawables)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
        this.drawables = drawables;
    }

    public void setDrawables(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public RemoteViews build()
    {
        computeDimensions();
        buildInit();
        buildPrev();
        buildPlay();
        buildShuffle();
        buildSkip();
        buildDefault();
        return remoteViews;
    }

    private void computeDimensions()
    {
        int totalScreenWidth = skipShuffleMediaPlayer.getResources().getDisplayMetrics().widthPixels;
        int iconWidth = skipShuffleMediaPlayer.getResources().getDimensionPixelSize(R.dimen.notification_icon_holder_width);
        int availableWidth = totalScreenWidth - iconWidth;
        Log.d(BaseActivity.TAG, "COMPUTED : " + Integer.toString(availableWidth));
    }

    private float pxFromDp(float dp)
    {
        return dp * skipShuffleMediaPlayer.getResources().getDisplayMetrics().density;
    }

    private void buildInit()
    {
        remoteViews = new RemoteViews(
                skipShuffleMediaPlayer.getPackageName(),
                R.layout.notification
        );

        remoteViews.setImageViewResource(
                R.id.buttons_background_image,
                drawables.notificationBackground
        );
    }

    private void buildPrev()
    {
        remoteViews.setImageViewResource(
                R.id.notif_prev,
                drawables.prev
        );

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
                drawables.play :
                drawables.pause;

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
        remoteViews.setImageViewResource(
                R.id.notif_shuffle,
                drawables.shuffle
        );

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
        remoteViews.setImageViewResource(
                R.id.notif_skip,
                drawables.skip
        );

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
