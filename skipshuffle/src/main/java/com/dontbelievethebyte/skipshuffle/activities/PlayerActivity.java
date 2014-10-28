package com.dontbelievethebyte.skipshuffle.activities;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public class PlayerActivity extends BaseActivity {

    private PlayerUI ui;

    @Override
    protected void handleBackPressed()
    {

    }

    @Override
    protected void onPause()
    {
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ui.reboot();
    }

    @Override
    protected void setUI(Integer type)
    {
        final SkipShuffleMediaPlayer mediaPlayer;
        try {
            mediaPlayer = getMediaPlayer();

            ui = UIFactory.createPlayerUI(this, type);

            //Useful for parent class.
            playerUIInterface = ui;

            ui.playBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer.isPlaying()) {
                        ui.doPause();
                    } else {
                        ui.doPlay();
                    }
                }
            });

            ui.skipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.doSkip();
                        ui.doSkip();
                    } catch (PlaylistEmptyException playlistEmptyException) {
                        preferencesHelper.handlePlaylistEmptyException(playlistEmptyException);
                    }
                }
            });

            ui.prevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.doPrev();
                        ui.doPrev();
                    } catch (PlaylistEmptyException playlistEmptyException) {
                        preferencesHelper.handlePlaylistEmptyException(playlistEmptyException);
                    }
                }
            });

            ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        mediaPlayer.doShuffle();
                        ui.doShuffle();
                    } catch (PlaylistEmptyException playlistEmptyException) {
                        preferencesHelper.handlePlaylistEmptyException(playlistEmptyException);
                    }
                }
            });

            ui.playlistBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent playlistActivity = new Intent(getApplicationContext(), PlaylistActivity.class);
                    startActivity(playlistActivity);
                }
            });
            ui.reboot();

            //Set up navigation drawer for selecting playlist.
            setNavigationDrawerContent();
        } catch (NoMediaPlayerException noMediaPlayerException){
            Log.d(BaseActivity.TAG, "NO MEDIA PLAYER FOUNDS NOW");
            handleNoMediaPlayerException(noMediaPlayerException);
        }
    }
}
