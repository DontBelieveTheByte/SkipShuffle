package com.dontbelievethebyte.skipshuffle.ui.main;

import android.graphics.Typeface;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;
import com.dontbelievethebyte.skipshuffle.ui.UIInterface;

public abstract class MainUI implements UIInterface {

    public ImageButton playlistBtn;
    public ImageButton prevBtn;
    public ImageButton playBtn;
    public ImageButton shuffleBtn;
    public ImageButton skipBtn;

    protected MainActivity mainActivity;
    protected TextView songTitle;
    protected Typeface typeface;

    public MainUI(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    @Override
    public void setSongTitle(String title) {
        songTitle.setText(title);
        songTitle.setSelected(true);
    }

    @Override
    public Typeface getTypeFace()
    {
       return null;
    }

    @Override
    public void reboot()
    {
        setSongTitle(
                mainActivity.getMediaPlayerBroadcastReceiver()
                        .getCurrentSongTitle()
        );

        if (SkipShuflleMediaPlayerCommandsContract.STATE_PLAY.equals(
                mainActivity.getMediaPlayerBroadcastReceiver()
                        .getPlayerState())
        ) {
            doPlay();
        } else {
            doPause();
            if (mainActivity.getMediaPlayerBroadcastReceiver().getCurrentSongTitle().equals(
                                mainActivity.getResources().getString(
                                        R.string.meta_data_unknown_current_song_title)
                                )
            ) {
                playBtn.clearAnimation();
            }
        }
    }
}
