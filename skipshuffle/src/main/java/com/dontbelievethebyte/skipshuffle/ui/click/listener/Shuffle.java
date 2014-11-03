package com.dontbelievethebyte.skipshuffle.ui.click.listener;

import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class Shuffle extends Custom {

    public Shuffle(BaseActivity baseActivity)
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
