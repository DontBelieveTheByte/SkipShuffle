/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.AbstractPlayerButtons;

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

    public void reboot()
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = baseActivity.getMediaPlayer();
            if (mediaPlayer.isPlaying())
                doPlay();
            else
                doPause();
            RandomPlaylist playlist = (RandomPlaylist) mediaPlayer.getPlaylist();
            setTrack(playlist.getCurrent());
            checkShuffle(playlist);
        } catch (NoMediaPlayerException e) {
            baseActivity.handleNoMediaPlayerException(e);
        } catch (PlaylistEmptyException e) {
            handlePlaylistEmptyException(e);
        }
    }

    protected void checkShuffle(RandomPlaylist playlist)
    {
        if (null != playlist && playlist.isShuffle())
            buttons.shuffle.setImageDrawable(buttons.drawables.getShufflePressed());
        else
            buttons.shuffle.setImageDrawable(buttons.drawables.getShuffle());
    }

}
