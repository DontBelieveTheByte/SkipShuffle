package com.dontbelievethebyte.skipshuffle.ui.main;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;

public class NeonMainUI extends MainUI {

    public NeonMainUI(MainActivity mainActivity)
    {
        super(mainActivity);

        playDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_play_states);
        pauseDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_pause_states);
        prevDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_prev_states);
        skipDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_next_states);
        shuffleDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_shuffle_states);
        playlistDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_playlist_states);


        playPressedDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_play_btn_pressed);
        pausePressedDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_pause_btn_pressed);
        prevPressedDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_prev_btn_pressed);
        skipPressedDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_next_btn_pressed);
        shufflePressedDrawable = mainActivity.getResources().getDrawable(R.drawable.neon_shuffle_btn_pressed);

        setUpDrawables();
        setUpColors();
        setUpAnimations();
    }

    @Override
    protected void setUpColors()
    {
        bottom.setBackgroundResource(R.color.neon_background);
        songTitle.setTextColor(mainActivity.getResources().getColor(R.color.neon_song_label));
    }
}
