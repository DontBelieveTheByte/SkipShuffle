/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.concrete.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.utilities.ScrollOffsetCalculator;

public class ListPlayer extends AbstractPlayerUI implements UIElementCompositeInterface {

    public ListView listView;

    public ListPlayer(BaseActivity baseActivity, ListPlayerButtons playerButtons, ListView listView)
    {
        this.baseActivity = baseActivity;
        this.type = baseActivity.getPreferencesHelper().getUIType();
        this.listView = listView;
        buttons = playerButtons;
        buttons.animations.setPlayerUIListeners(this, baseActivity);
        setButtonsOnClickListeners();
    }

    @Override
    public void doPlay()
    {
        buttons.play.startAnimation(buttons.animations.playAnimation);
        notifyAdapter();
    }

    @Override
    public void doPause()
    {
        buttons.play.startAnimation(buttons.animations.pauseAnimation);
        notifyAdapter();
    }

    @Override
    public void doSkip()
    {
        buttons.skip.startAnimation(buttons.animations.skipAnimation);
    }

    @Override
    public void doPrev()
    {
        buttons.prev.startAnimation(buttons.animations.prevAnimation);
    }

    @Override
    public void doShuffle()
    {
        buttons.shuffle.startAnimation(buttons.animations.shuffleAnimation);
    }

    @Override
    public void setTrack(Track track)
    {
        listView.smoothScrollToPosition(track.getPosition() + ScrollOffsetCalculator.compute(listView));
    }

    private void notifyAdapter()
    {
        CurrentPlaylistAdapter adapter;
        if (null != listView) {
            adapter = (CurrentPlaylistAdapter)listView.getAdapter();
            if (null != adapter)
            adapter.notifyDataSetChanged();
        }
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
            baseActivity.handlePlaylistEmptyException(e);
        }
    }

    @Override
    protected void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        baseActivity.handlePlaylistEmptyException(playlistEmptyException);
    }
}
