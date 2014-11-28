/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.playlists.TrackPrinter;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PlayClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PlaylistClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PrevClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.ShuffleClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.SkipClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.MainPlayerSongLabel;

public class MainPlayer extends AbstractPlayerUI {

    private MainPlayerSongLabel songLabel;
    private TrackPrinter trackPrinter;

    public MainPlayer(BaseActivity baseActivity, MainPlayerButtons playerButtons, MainPlayerSongLabel songLabel)
    {
        this.baseActivity = baseActivity;
        trackPrinter = new TrackPrinter(baseActivity);
        this.songLabel = songLabel;
        buttons = playerButtons;
        buttons.animations.setPlayerUIListeners(this);
        setButtonsOnClickListeners();
    }

    private void setButtonsOnClickListeners()
    {
        buttons.play.setOnClickListener(new PlayClick(baseActivity));
        buttons.skip.setOnClickListener(new SkipClick(baseActivity));
        buttons.prev.setOnClickListener(new PrevClick(baseActivity));
        buttons.shuffle.setOnClickListener(new ShuffleClick(baseActivity));
        buttons.playlist.setOnClickListener(new PlaylistClick((PlayerActivity)baseActivity));
    }

    @Override
    public void doPlay()
    {
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.jerkRightAnimation);
    }

    @Override
    public void doPause()
    {
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.blinkAnimation);
    }

    @Override
    public void doSkip()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.blinkAnimation);
        buttons.skip.startAnimation(buttons.animations.spinRightAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.jerkRightAnimation);
    }

    @Override
    public void doPrev()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.blinkAnimation);
        buttons.prev.startAnimation(buttons.animations.spinLeftAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.jerkRightAnimation);
    }

    @Override
    public void doShuffle()
    {
        buttons.shuffle.setImageDrawable(buttons.drawables.getShuffle());
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.blinkAnimation);
        buttons.shuffle.startAnimation(buttons.animations.spinDownAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.jerkRightAnimation);
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
