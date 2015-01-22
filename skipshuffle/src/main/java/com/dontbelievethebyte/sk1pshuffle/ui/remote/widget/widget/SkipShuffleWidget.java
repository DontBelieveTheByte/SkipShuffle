/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.remote.widget.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dontbelievethebyte.sk1pshuffle.ui.theme.UITypes;

public class SkipShuffleWidget extends AppWidgetProvider {

    private static PlayerState playerState = new PlayerState(null, null, null, null, null);

    private static int appWidgetIds[];

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
        WidgetRemoteViewsBuilder widgetRemoteViewsBuilder = new WidgetRemoteViewsBuilder(context);
        RemoteViews remoteViews = widgetRemoteViewsBuilder.build(playerState);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);
        parseReceivedIntent(intent);
        onUpdate(
                context,
                AppWidgetManager.getInstance(context),
                appWidgetIds
        );
    }

    private void parseReceivedIntent(Intent intent)
    {
        playerState = new PlayerState(
                UITypes.values()[intent.getIntExtra(WidgetContract.UI_TYPE, 0)],
                intent.getBooleanExtra(WidgetContract.IS_PLAYING, false),
                intent.getBooleanExtra(WidgetContract.IS_SHUFFLE, false),
                intent.getStringExtra(WidgetContract.TITLE),
                intent.getStringExtra(WidgetContract.ARTIST)
        );
        appWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
    }
}


