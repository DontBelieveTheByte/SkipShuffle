package com.dontbelievethebyte.skipshuffle;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

public class MiniUI extends UI {

    public Animation ltr = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.ltr);
    public Animation flipRightAnimation  = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.flip_right);
    public Animation flipDownAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.flip_down);
    public Animation flipLeftAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.flip_left);
    public Animation blinkAnimation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.blink);

    public MiniUI(MainActivity mainActivity){
        super(mainActivity);
        mainActivity.setContentView(R.layout.activity_main);

        playlistBtn = (ImageButton) mainActivity.findViewById(R.id.playlistBtn);
        prevBtn = (ImageButton) mainActivity.findViewById(R.id.prevBtn);
        playBtn = (ImageButton) mainActivity.findViewById(R.id.playBtn);
        shuffleBtn = (ImageButton) mainActivity.findViewById(R.id.shuffleBtn);
        skipBtn = (ImageButton) mainActivity.findViewById(R.id.skipBtn);

        flipRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                doPause();
                skipBtn.setImageDrawable(MiniUI.this.mainActivity.getResources().getDrawable(R.drawable.next_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                skipBtn.setImageDrawable(MiniUI.this.mainActivity.getResources().getDrawable(R.drawable.next_states));
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
                prevBtn.setImageDrawable(MiniUI.this.mainActivity.getResources().getDrawable(R.drawable.prev_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                prevBtn.setImageDrawable(MiniUI.this.mainActivity.getResources().getDrawable(R.drawable.prev_states));
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
                shuffleBtn.setImageDrawable(MiniUI.this.mainActivity.getResources().getDrawable(R.drawable.shuffle_btn_pressed));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shuffleBtn.setImageDrawable(MiniUI.this.mainActivity.getResources().getDrawable(R.drawable.shuffle_states));
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void doPlay() {
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.play_states));
        playBtn.startAnimation(ltr);
        Toast.makeText(mainActivity, mainActivity.getString(R.string.play), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void doPause() {
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.pause_states));
        playBtn.startAnimation(blinkAnimation);
        Toast.makeText(mainActivity, mainActivity.getString(R.string.pause), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doSkip() {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.pause_states));
        playBtn.startAnimation(blinkAnimation);
        skipBtn.startAnimation(flipRightAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.play_states));
        playBtn.startAnimation(ltr);
        Toast.makeText(mainActivity, R.string.skip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doPrev() {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.pause_states));
        playBtn.startAnimation(blinkAnimation);
        prevBtn.startAnimation(flipLeftAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.play_states));
        playBtn.startAnimation(ltr);
        Toast.makeText(mainActivity, R.string.prev, Toast.LENGTH_LONG).show();
    }

    @Override
    public void doShuffle() {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.pause_states));
        playBtn.startAnimation(blinkAnimation);
        shuffleBtn.startAnimation(flipDownAnimation);
        playBtn.setImageDrawable(mainActivity.getResources().getDrawable(R.drawable.play_states));
        playBtn.startAnimation(ltr);
        Toast.makeText(mainActivity, R.string.shuffle, Toast.LENGTH_LONG).show();
    }

    @Override
    public void reboot(){
        if(mainActivity.mediaPlayerBroadcastReceiver.getPlayerState() == SkipShuflleMediaPlayerCommandsContract.CMD_PLAY) {
            doPlay();
        } else {
            doPause();
        }
    }
}
