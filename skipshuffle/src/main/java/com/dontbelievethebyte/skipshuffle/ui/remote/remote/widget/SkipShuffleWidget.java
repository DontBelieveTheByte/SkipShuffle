/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.remote.remote.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.ui.mapper.types.UITypes;

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
        WidgetRemoteViewsBuilder widgetRemoteViewsBuilder = new WidgetRemoteViewsBuilder(context);
        int uiType = UITypes.JACK_O_LANTERN;
        boolean isPlaying = true;
        boolean isShuffle = false;
        String title = "DERPADASD";
        String artist = "MOFOOF";

        RemoteViews remoteViews = widgetRemoteViewsBuilder.build(uiType, isPlaying, isShuffle, title, artist);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}


