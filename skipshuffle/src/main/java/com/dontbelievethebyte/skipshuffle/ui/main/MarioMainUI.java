package com.dontbelievethebyte.skipshuffle.ui.main;

import android.graphics.Typeface;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;

public class MarioMainUI extends MainUI {

    public Animation ltr = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.neon_ltr);
    public Animation flipRightAnimation  = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.neon_flip_right);
    public Animation flipDownAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.neon_flip_down);
    public Animation flipLeftAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.neon_flip_left);
    public Animation blinkAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.neon_blink);

    public MarioMainUI(MainActivity mainActivity)
    {
        super(mainActivity);
        mainActivity.setContentView(R.layout.neon_activity_main);

        playlistBtn = (ImageButton) mainActivity.findViewById(R.id.playlistBtn);
        prevBtn = (ImageButton) mainActivity.findViewById(R.id.prevBtn);
        playBtn = (ImageButton) mainActivity.findViewById(R.id.playBtn);
        shuffleBtn = (ImageButton) mainActivity.findViewById(R.id.shuffleBtn);
        skipBtn = (ImageButton) mainActivity.findViewById(R.id.skipBtn);
        songTitle = (TextView) mainActivity.findViewById(R.id.song_label);

        songTitle.setTypeface(getTypeFace());

        flipRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {
                doPause();
                skipBtn.setImageDrawable(MarioMainUI.this.mainActivity.getResources().getDrawable(R.drawable.neon_next_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                skipBtn.setImageDrawable(MarioMainUI.this.mainActivity.getResources().getDrawable(R.drawable.neon_next_states));
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        flipLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {
                doPause();
                prevBtn.setImageDrawable(MarioMainUI.this.mainActivity.getResources().getDrawable(R.drawable.neon_prev_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                prevBtn.setImageDrawable(MarioMainUI.this.mainActivity.getResources().getDrawable(R.drawable.neon_prev_states));
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        flipDownAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {
                doPause();
                shuffleBtn.setImageDrawable(MarioMainUI.this.mainActivity.getResources().getDrawable(R.drawable.neon_shuffle_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shuffleBtn.setImageDrawable(MarioMainUI.this.mainActivity.getResources().getDrawable(R.drawable.neon_shuffle_states));
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void doPlay()
    {
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.neon_play_states));
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doPause()
    {
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.neon_pause_states));
        playBtn.startAnimation(blinkAnimation);
    }

    @Override
    public void doSkip()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.neon_pause_states));
        playBtn.startAnimation(blinkAnimation);
        skipBtn.startAnimation(flipRightAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.neon_play_states));
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doPrev()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.neon_pause_states));
        playBtn.startAnimation(blinkAnimation);
        prevBtn.startAnimation(flipLeftAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.neon_play_states));
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doShuffle()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.neon_pause_states));
        playBtn.startAnimation(blinkAnimation);
        shuffleBtn.startAnimation(flipDownAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.neon_play_states));
        playBtn.startAnimation(ltr);
    }

    @Override
    public void setSongTitle(String title)
    {
        songTitle.setText(title);
    }

    public Typeface getTypeFace()
    {
        if (null == typeface) {
            typeface = Typeface.createFromAsset(mainActivity.getAssets(), "fonts/UbuntuMono-B.ttf");
        }
        return typeface;
    }
}
