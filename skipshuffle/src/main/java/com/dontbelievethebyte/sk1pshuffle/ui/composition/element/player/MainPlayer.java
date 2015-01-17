/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.playlist.Track;
import com.dontbelievethebyte.sk1pshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.buttons.concrete.MainPlayerButtons;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.sk1pshuffle.ui.composition.element.player.seekbar.CustomSeekBar;

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
