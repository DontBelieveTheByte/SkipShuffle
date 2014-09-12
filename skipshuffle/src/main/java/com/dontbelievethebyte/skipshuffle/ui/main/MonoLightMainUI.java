package com.dontbelievethebyte.skipshuffle.ui.main;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;

public class MonoLightMainUI extends MainUI {

    public MonoLightMainUI(MainActivity mainActivity)
    {
        super(mainActivity);

        playDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_play_states);
        pauseDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_pause_states);
        prevDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_prev_states);
        skipDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_next_states);
        shuffleDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_shuffle_states);
        playlistDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_playlist_states);

        playDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_play_btn_pressed);
        pauseDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_pause_btn_pressed);
        prevDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_prev_btn_pressed);
        skipDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_next_btn_pressed);
        shuffleDrawable = mainActivity.getResources().getDrawable(R.drawable.mono_light_shuffle_btn_pressed);

        setUpDrawables();
        setUpColors();
        setUpAnimations();
    }

    @Override
    protected void setUpColors()
    {
        bottom.setBackgroundResource(R.color.mono_light_background);
        songTitle.setTextColor(mainActivity.getResources().getColor(R.color.mono_light_song_label));
    }
}
