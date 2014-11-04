package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.PlayClick;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.PlaylistClick;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.PrevClick;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.ShuffleClick;
import com.dontbelievethebyte.skipshuffle.ui.click.listener.SkipClick;
import com.dontbelievethebyte.skipshuffle.ui.structured.PlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.visitor.ColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DrawablesVisitor;

public class PlayerUI extends AbstractPlayer implements UIElement {

    private BaseActivity baseActivity;

    public PlayerUI(BaseActivity baseActivity, PlayerButtons playerButtons, SongLabel songLabel)
    {
        this.baseActivity = baseActivity;
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
        buttons.playlist.setOnClickListener(new PlaylistClick(baseActivity));
    }

    @Override
    public void doPlay()
    {
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.ltr);
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
        buttons.skip.startAnimation(buttons.animations.flipRightAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.ltr);
    }

    @Override
    public void doPrev()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.blinkAnimation);
        buttons.prev.startAnimation(buttons.animations.flipLeftAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.ltr);
    }

    @Override
    public void doShuffle()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.blinkAnimation);
        buttons.shuffle.startAnimation(buttons.animations.flipDownAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.ltr);
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

            setTitle(buildFormattedTitle());
        } catch (NoMediaPlayerException noMediaPlayerException) {
            handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            handlePlaylistEmptyException(playlistEmptyException);
        }
    }

    @Override
    public void setTitle(String title)
    {
        songLabel.setContent(title);
    }

    private String buildFormattedTitle() throws PlaylistEmptyException
    {
        try {
            SkipShuffleMediaPlayer skipShuffleMediaPlayer = baseActivity.getMediaPlayer();
            PlaylistInterface playlist = skipShuffleMediaPlayer.getPlaylist();
            Track currentTrack = playlist.getCurrent();
            if (null == currentTrack.getArtist() || null == currentTrack.getTitle()) {
                return (null == currentTrack.getPath()) ?
                        baseActivity.getString(R.string.meta_data_unknown_current_song_title) :
                        currentTrack.getPath().substring(currentTrack.getPath().lastIndexOf("/") + 1);
            } else {
                return currentTrack.getArtist() + " - " + currentTrack.getTitle();
            }
        } catch (NoMediaPlayerException noMediaPlayerException){
            throw new PlaylistEmptyException(0L);
        }
    }

    private void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        String label = baseActivity.getString(R.string.meta_data_unknown_current_song_title);
        setTitle(label);
    }

    private void handleNoMediaPlayerException(NoMediaPlayerException noMediaPlayerException)
    {
        try {
            buildFormattedTitle();
        } catch (PlaylistEmptyException playListEmptyException) {
            handlePlaylistEmptyException(playListEmptyException);
        }
        doPause();
    }

    @Override
    public void acceptColorVisitor(ColorVisitor colorVisitor)
    {
        colorVisitor.visit(this);
    }

    @Override
    public void acceptDimensionsVisitor(DimensionsVisitor dimensionsVisitor)
    {
        dimensionsVisitor.visit(this);
    }

    @Override
    public void acceptDrawablesVisitor(DrawablesVisitor drawablesVisitor)
    {
        drawablesVisitor.visit(this);
    }
}
