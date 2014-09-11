package com.dontbelievethebyte.skipshuffle.ui.main;

import android.graphics.Typeface;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;

public class MonoLightMainUI extends MainUI {

    private Animation ltr;
    private Animation flipRightAnimation;
    private Animation flipDownAnimation;
    private Animation flipLeftAnimation;
    private Animation blinkAnimation;

    public MonoLightMainUI(MainActivity mainActivity)
    {
        super(mainActivity);
        mainActivity.setContentView(R.layout.mono_light_activity_main);

        ltr = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_light_ltr);
        flipRightAnimation  = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_light_flip_right);
        flipDownAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_light_flip_down);
        flipLeftAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_light_flip_left);
        blinkAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_light_blink);

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
                skipBtn.setImageDrawable(
                        MonoLightMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_light_next_btn_pressed)
                );
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                skipBtn.setImageDrawable(
                        MonoLightMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_light_next_states)
                );
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
                prevBtn.setImageDrawable(
                        MonoLightMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_light_prev_btn_pressed)
                );
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                prevBtn.setImageDrawable(
                        MonoLightMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_light_prev_states)
                );
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        flipDownAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {
                doPause();
                shuffleBtn.setImageDrawable(
                        MonoLightMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_light_shuffle_btn_pressed)
                );
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                shuffleBtn.setImageDrawable(
                        MonoLightMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_light_shuffle_states)
                );
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    @Override
    public void doPlay()
    {
        playBtn.setImageDrawable(
                mainActivity.getResources().getDrawable(R.drawable.mono_light_play_states)
        );
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doPause()
    {
        playBtn.setImageDrawable(
                mainActivity.getResources().getDrawable(R.drawable.mono_light_pause_states)
        );
        playBtn.startAnimation(blinkAnimation);
    }

    @Override
    public void doSkip()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(
                mainActivity.getResources().getDrawable(R.drawable.mono_light_pause_states)
        );
        playBtn.startAnimation(blinkAnimation);
        skipBtn.startAnimation(flipRightAnimation);
        playBtn.setImageDrawable(
                mainActivity.getResources().getDrawable(R.drawable.mono_light_play_states)
        );
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doPrev()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(
                mainActivity.getResources().getDrawable(R.drawable.mono_light_pause_states)
        );
        playBtn.startAnimation(blinkAnimation);
        prevBtn.startAnimation(flipLeftAnimation);
        playBtn.setImageDrawable(
                mainActivity.getResources().getDrawable(R.drawable.mono_light_play_states)
        );
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doShuffle()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(
                mainActivity.getResources().getDrawable(R.drawable.mono_light_pause_states)
        );
        playBtn.startAnimation(blinkAnimation);
        shuffleBtn.startAnimation(flipDownAnimation);
        playBtn.setImageDrawable(
                mainActivity.getResources().getDrawable(R.drawable.mono_light_play_states)
        );
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
