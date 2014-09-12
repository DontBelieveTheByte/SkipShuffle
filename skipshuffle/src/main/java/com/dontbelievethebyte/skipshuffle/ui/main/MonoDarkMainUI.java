package com.dontbelievethebyte.skipshuffle.ui.main;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;

public class MonoDarkMainUI extends MainUI {

    public MonoDarkMainUI(MainActivity mainActivity)
    {
        super(mainActivity);

        playDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_play_states);
        pauseDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_pause_states);
        prevDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_prev_states);
        skipDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_next_states);
        shuffleDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_shuffle_states);
        playlistDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_playlist_states);

        playDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_play_btn_pressed);
        pauseDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_pause_btn_pressed);
        prevDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_prev_btn_pressed);
        skipDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_next_btn_pressed);
        shuffleDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_dark_shuffle_btn_pressed);

        setUpDrawables();
        setUpColors();
        setUpAnimations();
    }

    @Override
    protected void setUpColors()
    {
        bottom.setBackgroundResource(R.color.mono_dark_background);
        songTitle.setTextColor(mainActivity.getResources().getColor(R.color.mono_dark_song_label));
    }
}
