/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;

public class SkipShuffleWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {

        // Construct the RemoteViews object
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.skip_shuffle_widget);

        Intent intent = new Intent(SkipShuflleMediaPlayerCommandsContract.COMMAND);
        intent.putExtra(SkipShuflleMediaPlayerCommandsContract.COMMAND, SkipShuflleMediaPlayerCommandsContract.PLAY);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                20,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteViews.setOnClickPendingIntent(R.id.notif_play, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

//    public Notification buildWidget()
//    {
//        RemoteViews remoteViews = getRemoteViews();
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(skipShuffleMediaPlayer);
//        notificationBuilder.setSmallIcon(R.drawable.ic_notification);
//        notificationBuilder.setContent(remoteViews);
//        return notificationBuilder.build();
//    }

//    private RemoteViews getRemoteViews()
//    {
//        NotificationRemoteViewsBuilder remoteViewsBuilder = new NotificationRemoteViewsBuilder(
//                skipShuffleMediaPlayer,
//                new Drawables(skipShuffleMediaPlayer, skipShuffleMediaPlayer.getPreferencesHelper().getUIType()),
//                new Colors(skipShuffleMediaPlayer.getPreferencesHelper().getUIType())
//        );
//        return remoteViewsBuilder.build();
//    }
}


