/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.concrete.MainPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.PlayClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.PlaylistClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.PrevClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.ShuffleClickListener;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.clickListeners.concrete.SkipClickListener;
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
    }

    @Override
    public void doPause()
    {
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.pauseAnimation);
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
