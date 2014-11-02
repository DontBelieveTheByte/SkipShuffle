package com.dontbelievethebyte.skipshuffle.ui;

import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.animation.listener.FlipDown;
import com.dontbelievethebyte.skipshuffle.ui.animation.listener.FlipLeft;
import com.dontbelievethebyte.skipshuffle.ui.animation.listener.FlipRight;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;
import com.dontbelievethebyte.skipshuffle.ui.structured.PlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.structured.PlayerButtonsAnimations;
import com.dontbelievethebyte.skipshuffle.ui.visitor.ColorVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.visitor.DrawablesVisitor;

public class PlayerUI implements UIElement, PlayerUIInterface {

    private BaseUI baseUI;
    public PlayerButtons buttons;
    public PlayerButtonsAnimations animations;
    public Drawables drawables;
    public SongLabel songLabel;

    public PlayerUI(BaseUI baseUI)
    {
        this.baseUI = baseUI;
        BaseActivity baseActivity = baseUI.getBaseActivity();

        songLabel = new SongLabel(baseUI.getBaseActivity(), R.id.song_label);
        songLabel.setTypeFace(baseUI.getTypeFace());

        animations = new PlayerButtonsAnimations(baseUI.getBaseActivity());
        animations.flipRightAnimation.setAnimationListener(new FlipRight(this));
        animations.flipLeftAnimation.setAnimationListener(new FlipLeft(this));
        animations.flipDownAnimation.setAnimationListener(new FlipDown(this));

        buttons = new PlayerButtons(baseActivity);

        buttons.prev = (ImageButton) baseActivity.findViewById(R.id.prevBtn);
        buttons.play = (ImageButton) baseActivity.findViewById(R.id.playBtn);
        buttons.shuffle = (ImageButton) baseActivity.findViewById(R.id.shuffleBtn);
        buttons.skip = (ImageButton) baseActivity.findViewById(R.id.skipBtn);
        buttons.prev.setImageDrawable(drawables.getPrev());
        buttons.play.setImageDrawable(drawables.getPlay());
        buttons.shuffle.setImageDrawable(drawables.getShuffle());
        buttons.skip.setImageDrawable(drawables.getSkip());
        buttons.playlist.setImageDrawable(drawables.getPlaylist());

        buttons.setAnimations(animations);

        //Register haptic feedback for all buttons.
        buttons.play.setOnTouchListener(baseActivity);
        buttons.skip.setOnTouchListener(baseActivity);
        buttons.prev.setOnTouchListener(baseActivity);
        buttons.shuffle.setOnTouchListener(baseActivity);
        buttons.playlist.setOnTouchListener(baseActivity);
    }

    @Override
    public void doPlay()
    {
        buttons.play.setImageDrawable(drawables.getPlay());
        buttons.play.startAnimation(animations.ltr);
    }

    @Override
    public void doPause()
    {
        buttons.play.setImageDrawable(drawables.getPause());
        buttons.play.startAnimation(animations.blinkAnimation);
    }

    @Override
    public void doSkip()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(drawables.getPause());
        buttons.play.startAnimation(animations.blinkAnimation);
        buttons.skip.startAnimation(animations.flipRightAnimation);
        buttons.play.setImageDrawable(drawables.getPlay());
        buttons.play.startAnimation(animations.ltr);
    }

    @Override
    public void doPrev()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(drawables.getPause());
        buttons.play.startAnimation(animations.blinkAnimation);
        buttons.prev.startAnimation(animations.flipLeftAnimation);
        buttons.play.setImageDrawable(drawables.getPlay());
        buttons.play.startAnimation(animations.ltr);
    }

    @Override
    public void doShuffle()
    {
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(drawables.getPause());
        buttons.play.startAnimation(animations.blinkAnimation);
        buttons.shuffle.startAnimation(animations.flipDownAnimation);
        buttons.play.setImageDrawable(drawables.getPlay());
        buttons.play.startAnimation(animations.ltr);
    }

    @Override
    public void reboot()
    {
        try {
            BaseActivity baseActivity = baseUI.getBaseActivity();
            if (baseActivity.getMediaPlayer().isPlaying())
                doPlay();
            else
                doPause();

            setSongLabel(buildFormattedTitle());

        } catch (NoMediaPlayerException noMediaPlayerException) {
            handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            handlePlaylistEmptyException(playlistEmptyException);
        }
    }

    @Override
    public void setSongLabel(String title)
    {
        songLabel.setContent(title);
    }

    private String buildFormattedTitle() throws PlaylistEmptyException
    {
        try {
            BaseActivity baseActivity = baseUI.getBaseActivity();
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
        BaseActivity baseActivity = baseUI.getBaseActivity();
        String label = baseActivity.getString(R.string.meta_data_unknown_current_song_title);
        setSongLabel(label);
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
