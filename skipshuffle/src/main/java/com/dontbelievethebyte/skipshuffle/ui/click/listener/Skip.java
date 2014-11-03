package com.dontbelievethebyte.skipshuffle.ui.click.listener;

import android.util.Log;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class Skip extends Custom {

    public Skip(BaseActivity baseActivity)
    {
        super(baseActivity);
    }

    @Override
    public void onClick(View view) {
        try {
            SkipShuffleMediaPlayer mediaPlayer = baseActivity.getMediaPlayer();
            mediaPlayer.doSkip();
            baseActivity.ui.player.doSkip();
        } catch (NoMediaPlayerException n) {
            Log.d(BaseActivity.TAG, "No media player");
        } catch (PlaylistEmptyException playlistEmptyException) {
            baseActivity.getPreferencesHelper().handlePlaylistEmptyException(playlistEmptyException);
        }
    }
}
