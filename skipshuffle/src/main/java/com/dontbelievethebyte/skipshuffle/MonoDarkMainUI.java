package com.dontbelievethebyte.skipshuffle;

import android.graphics.Typeface;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

public class MonoDarkMainUI extends MainUI {

    public Animation ltr = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_dark_ltr);
    public Animation flipRightAnimation  = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_dark_flip_right);
    public Animation flipDownAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_dark_flip_down);
    public Animation flipLeftAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_dark_flip_left);
    public Animation blinkAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.mono_dark_blink);

    public MonoDarkMainUI(MainActivity mainActivity){
        super(mainActivity);
        mainActivity.setContentView(R.layout.mono_dark_activity_main);

        playlistBtn = (ImageButton) mainActivity.findViewById(R.id.playlistBtn);
        prevBtn = (ImageButton) mainActivity.findViewById(R.id.prevBtn);
        playBtn = (ImageButton) mainActivity.findViewById(R.id.playBtn);
        shuffleBtn = (ImageButton) mainActivity.findViewById(R.id.shuffleBtn);
        skipBtn = (ImageButton) mainActivity.findViewById(R.id.skipBtn);
        songTitle = (TextView) mainActivity.findViewById(R.id.song_label);

        songTitle.setTypeface(getTypeFace());

        flipRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                doPause();
                skipBtn.setImageDrawable(MonoDarkMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_dark_next_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                skipBtn.setImageDrawable(MonoDarkMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_dark_next_states));
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        flipLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                doPause();
                prevBtn.setImageDrawable(MonoDarkMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_dark_prev_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                prevBtn.setImageDrawable(MonoDarkMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_dark_prev_states));
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        flipDownAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                doPause();
                shuffleBtn.setImageDrawable(MonoDarkMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_dark_shuffle_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shuffleBtn.setImageDrawable(MonoDarkMainUI.this.mainActivity.getResources().getDrawable(R.drawable.mono_dark_shuffle_states));
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void doPlay() {
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.mono_dark_play_states));
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doPause() {
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.mono_dark_pause_states));
        playBtn.startAnimation(blinkAnimation);
    }

    @Override
    public void doSkip() {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.mono_dark_pause_states));
        playBtn.startAnimation(blinkAnimation);
        skipBtn.startAnimation(flipRightAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.mono_dark_play_states));
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doPrev() {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.mono_dark_pause_states));
        playBtn.startAnimation(blinkAnimation);
        prevBtn.startAnimation(flipLeftAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.mono_dark_play_states));
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doShuffle() {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.mono_dark_pause_states));
        playBtn.startAnimation(blinkAnimation);
        shuffleBtn.startAnimation(flipDownAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.mono_dark_play_states));
        playBtn.startAnimation(ltr);
    }

    @Override
    public void reboot(){
        if(mainActivity.mediaPlayerBroadcastReceiver.getPlayerState() == SkipShuflleMediaPlayerCommandsContract.STATE_PLAY) {
            doPlay();
        } else {
            doPause();
        }
    }
    @Override
    public void setSongTitle(String title){
        songTitle.setText(title);
    }

    public Typeface getTypeFace(){
        if(typeface == null){
            typeface = Typeface.createFromAsset(mainActivity.getAssets(), "fonts/UbuntuMono-B.ttf");
        }
        return typeface;
    }
}
