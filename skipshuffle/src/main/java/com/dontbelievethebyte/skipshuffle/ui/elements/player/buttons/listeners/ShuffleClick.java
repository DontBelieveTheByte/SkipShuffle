package com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.listeners;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class ShuffleClick extends CustomAbstractClick {

    public ShuffleClick(BaseActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view)
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = baseActivity.getMediaPlayer();
            mediaPlayer.doShuffle();
            baseActivity.ui.player.doShuffle();
        } catch (NoMediaPlayerException n) {
            baseActivity.handleNoMediaPlayerException(n);
        } catch (PlaylistEmptyException playlistEmptyException) {
            baseActivity.getPreferencesHelper().handlePlaylistEmptyException(playlistEmptyException);
        }
    }
}
