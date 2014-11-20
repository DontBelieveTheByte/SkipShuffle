package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.ListPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PlayClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PlaylistClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.PrevClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.ShuffleClick;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners.SkipClick;
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
        buttons.play.startAnimation(buttons.animations.ltr);
        notifyAdapter();
    }

    @Override
    public void doPause()
    {
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.blinkAnimation);
        notifyAdapter();
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
        buttons.shuffle.setImageDrawable(buttons.drawables.getShuffle());
        buttons.play.clearAnimation();
        buttons.play.setImageDrawable(buttons.drawables.getPause());
        buttons.play.startAnimation(buttons.animations.blinkAnimation);
        buttons.shuffle.startAnimation(buttons.animations.flipDownAnimation);
        buttons.play.setImageDrawable(buttons.drawables.getPlay());
        buttons.play.startAnimation(buttons.animations.ltr);
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
