/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.concrete.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PlayClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PlaylistClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PrevClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.ShuffleClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.SkipClickListener;
import com.dontbelievethebyte.skipshuffle.utilities.ScrollOffsetCalculator;

public class ListPlayer extends AbstractPlayerUI implements UIElementCompositeInterface {

    public ListView listView;

    public ListPlayer(BaseActivity baseActivity, ListPlayerButtons playerButtons, ListView listView)
    {
        this.baseActivity = baseActivity;
        this.listView = listView;
        buttons = playerButtons;
        buttons.animations.setPlayerUIListeners(this);
        setButtonsOnClickListeners();
    }

    private void setButtonsOnClickListeners()
    {
        buttons.play.setOnClickListener(new PlayClickListener(baseActivity));
        buttons.skip.setOnClickListener(new SkipClickListener(baseActivity));
        buttons.prev.setOnClickListener(new PrevClickListener(baseActivity));
        buttons.shuffle.setOnClickListener(new ShuffleClickListener(baseActivity));
        buttons.playlist.setOnClickListener(new PlaylistClickListener((PlayerActivity)baseActivity));
    }

    @Override
    public void doPlay()
    {
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.playAnimation);
        notifyAdapter();
    }

    @Override
    public void doPause()
    {
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.pauseAnimation);
        notifyAdapter();
    }

    @Override
    public void doSkip()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.pauseAnimation);
        buttons.skip.startAnimation(buttons.animations.skipAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.playAnimation);
    }

    @Override
    public void doPrev()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.pauseAnimation);
        buttons.prev.startAnimation(buttons.animations.prevAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.playAnimation);
    }

    @Override
    public void doShuffle()
    {
        buttons.shuffle.setImageDrawable(buttons.drawables.getShuffle());
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.pauseAnimation);
        buttons.shuffle.startAnimation(buttons.animations.shuffleAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.playAnimation);
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
            RandomPlaylist playlist = (RandomPlaylist) mediaPlayer.getPlaylist();
            setTrack(playlist.getCurrent());
            checkShuffle(playlist);
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
