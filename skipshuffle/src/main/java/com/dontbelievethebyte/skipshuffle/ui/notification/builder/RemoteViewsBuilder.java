package com.dontbelievethebyte.skipshuffle.ui.notification.builder;


import android.app.PendingIntent;
import android.content.Intent;
import android.widget.RemoteViews;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.service.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class RemoteViewsBuilder {

    private SkipShuffleMediaPlayer skipShuffleMediaPlayer;
    private RemoteViews remoteViews;
    private Drawables drawables;
    private Colors colors;

    public RemoteViewsBuilder(SkipShuffleMediaPlayer skipShuffleMediaPlayer, Drawables drawables, Colors colors)
    {
        this.skipShuffleMediaPlayer = skipShuffleMediaPlayer;
        this.drawables = drawables;
        this.colors = colors;
    }

    public void setDrawables(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public void setColors(Colors colors)
    {
        this.colors = colors;
    }

    public RemoteViews build()
    {
        buildContainer();
        buildPrev();
        buildPlay();
        buildShuffle();
        buildSkip();
        buildDefault();
        buildTextLabelsColor();
        buildTextLabelsContent();
        return remoteViews;
    }

    private void buildTextLabelsColor()
    {
        int trackTitleLabelColor = skipShuffleMediaPlayer.getResources().getColor(colors.playlistTitle);
        remoteViews.setTextColor(
                R.id.track_title,
                trackTitleLabelColor
        );

        int trackArtistLabelColor = skipShuffleMediaPlayer.getResources().getColor(colors.playlistArtist);
        remoteViews.setTextColor(
                R.id.track_artist,
                trackArtistLabelColor
        );
    }

    private void buildTextLabelsContent()
    {
        RandomPlaylist playlist = (RandomPlaylist) skipShuffleMediaPlayer.getPlaylist();
        try {
            Track track = playlist.getCurrent();
            remoteViews.setTextViewText(
                    R.id.track_title,
                    track.getTitle()
            );

            remoteViews.setTextViewText(
                    R.id.track_artist,
                    track.getArtist()
            );
        } catch (PlaylistEmptyException e) {//@TODO make decorator catch the exception?
            e.printStackTrace();
        }
    }

    private void buildContainer()
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
