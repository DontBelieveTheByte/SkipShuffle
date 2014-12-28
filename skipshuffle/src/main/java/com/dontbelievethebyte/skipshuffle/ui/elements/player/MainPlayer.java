/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.concrete.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.seekbar.CustomSeekBar;

public class MainPlayer extends AbstractPlayerUI {

    private MainPlayerSongLabel songLabel;
    private TrackPrinter trackPrinter;

    public MainPlayer(BaseActivity baseActivity, MainPlayerButtons playerButtons, MainPlayerSongLabel songLabel, CustomSeekBar customSeekBar)
    {
        this.baseActivity = baseActivity;
        this.customSeekBar = customSeekBar;
        this.type = baseActivity.getPreferencesHelper().getUIType();
        trackPrinter = new TrackPrinter(baseActivity);
        this.songLabel = songLabel;
        buttons = playerButtons;
        buttons.animations.setPlayerUIListeners(this, baseActivity);
        setButtonsOnClickListeners();
    }

    @Override
    public void doPlay()
    {
        buttons.play.startAnimation(buttons.animations.playAnimation);
    }

    @Override
    public void doPause()
    {
        buttons.play.startAnimation(buttons.animations.pauseAnimation);
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(trackPrinter.printTitle(track));
        stringBuilder.append(" - ");
        stringBuilder.append(trackPrinter.printArtist(track));
        songLabel.setContent(stringBuilder.toString());
        customSeekBar.reset();
    }

    public MainPlayerSongLabel getSongLabel()
    {
        return songLabel;
    }

    @Override
    protected void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        songLabel.setContent(baseActivity.getString(R.string.meta_data_no_playlist));
        baseActivity.handlePlaylistEmptyException(playlistEmptyException);
    }


}
