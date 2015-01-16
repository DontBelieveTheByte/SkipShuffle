/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.element.player;

import android.graphics.drawable.Drawable;

import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.activity.PlayerActivity;
import com.dontbelievethebyte.sk1pshuffle.service.exception.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.playlist.Track;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.element.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.AbstractPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.clickListeners.concrete.PlayClickListener;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.clickListeners.concrete.PlaylistClickListener;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.clickListeners.concrete.PrevClickListener;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.clickListeners.concrete.ShuffleClickListener;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.buttons.clickListeners.concrete.SkipClickListener;
import com.dontbelievethebyte.sk1pshuffle.ui.element.player.seekbar.CustomSeekBar;

public abstract class AbstractPlayerUI implements UIElementCompositeInterface {

    public int type;
    public AbstractPlayerButtons buttons;
    public CustomSeekBar customSeekBar;

    protected BaseActivity baseActivity;

    public abstract void doPlay();

    public abstract void doPause();

    public abstract void doSkip();

    public abstract void doPrev();

    public abstract void doShuffle();

    public abstract void setTrack(Track track);

    protected abstract void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException);

    protected void setButtonsOnClickListeners()
    {
        PlayClickListener playClickListener = new PlayClickListener(baseActivity);
        buttons.play.setOnClickListener(playClickListener);
        buttons.play.setOnLongClickListener(playClickListener);

        SkipClickListener skipClickListener = new SkipClickListener(baseActivity);
        buttons.skip.setOnClickListener(skipClickListener);
        buttons.skip.setOnLongClickListener(skipClickListener);

        PrevClickListener prevClickListener = new PrevClickListener(baseActivity);
        buttons.prev.setOnClickListener(prevClickListener);
        buttons.prev.setOnLongClickListener(prevClickListener);

        ShuffleClickListener shuffleClickListener = new ShuffleClickListener(baseActivity);
        buttons.shuffle.setOnClickListener(shuffleClickListener);
        buttons.shuffle.setOnLongClickListener(shuffleClickListener);

        PlaylistClickListener playlistClickListener = new PlaylistClickListener((PlayerActivity)baseActivity);
        buttons.playlist.setOnClickListener(playlistClickListener);
        buttons.playlist.setOnLongClickListener(playlistClickListener);
    }

    public void reboot()
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = baseActivity.getMediaPlayer();
            if (mediaPlayer.isPlaying())
                doPlay();
            else
                doPause();
            RandomPlaylist playlist = mediaPlayer.getPlaylist();
            setTrack(playlist.getCurrent());
            customSeekBar.setEnabled(
                    (mediaPlayer.isPaused() || mediaPlayer.isPlaying())
            );

            buttons.shuffle.setImageDrawable(getShuffleDrawable());
        } catch (NoMediaPlayerException e) {
            baseActivity.handleNoMediaPlayerException(e);
        } catch (PlaylistEmptyException e) {
            handlePlaylistEmptyException(e);
        }
    }

    public Drawable getShuffleDrawable()
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = baseActivity.getMediaPlayer();
            RandomPlaylist playlist = mediaPlayer.getPlaylist();
            return (null != playlist && playlist.isShuffle()) ? buttons.drawables.getShufflePressed() :
                                                                buttons.drawables.getShuffle();
        } catch (NoMediaPlayerException e) {
            return buttons.drawables.getShuffle();
        }
    }

}
