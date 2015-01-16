/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.player;

import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.media.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.sk1pshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.playlist.Track;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.buttons.concrete.ListPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.player.seekbar.CustomSeekBar;
import com.dontbelievethebyte.sk1pshuffle.utilities.ScrollOffsetCalculator;

public class ListPlayer extends AbstractPlayerUI implements UIElementCompositeInterface {

    public ListView listView;

    public ListPlayer(BaseActivity baseActivity, ListPlayerButtons playerButtons, ListView listView, CustomSeekBar customSeekBar)
    {
        this.baseActivity = baseActivity;
        this.type = baseActivity.getPreferencesHelper().getUIType();
        this.listView = listView;
        this.customSeekBar = customSeekBar;
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

    @Override
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
            baseActivity.handlePlaylistEmptyException(e);
        }
    }

    @Override
    protected void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        baseActivity.handlePlaylistEmptyException(playlistEmptyException);
    }
}
