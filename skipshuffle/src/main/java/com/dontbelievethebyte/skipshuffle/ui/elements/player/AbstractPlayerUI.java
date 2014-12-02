/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import android.graphics.drawable.Drawable;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.AbstractPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.PlayClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.PlaylistClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.PrevClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.ShuffleClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.SkipClickListener;

public abstract class AbstractPlayerUI implements UIElementCompositeInterface {

    public AbstractPlayerButtons buttons;
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
        buttons.play.setOnClickListener(new PlayClickListener(baseActivity));
        buttons.skip.setOnClickListener(new SkipClickListener(baseActivity));
        buttons.prev.setOnClickListener(new PrevClickListener(baseActivity));
        buttons.shuffle.setOnClickListener(new ShuffleClickListener(baseActivity));
        buttons.playlist.setOnClickListener(new PlaylistClickListener((PlayerActivity)baseActivity));
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
