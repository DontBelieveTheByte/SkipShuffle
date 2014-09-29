package com.dontbelievethebyte.skipshuffle.ui;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;

public class MainUI extends AbstractPlayerUI {

    public MainUI(MainActivity activity)
    {
        super(activity, R.layout.activity_main);

        playlistBtn = (ImageButton) activity.findViewById(R.id.playlistBtn);
        songTitle = (TextView) activity.findViewById(R.id.song_label);

        playlistBtn.setOnTouchListener(baseActivity);

        setUpDrawables();
        setUpDimensions();
        setUpDrawer();
        setUpColors();
        setUpAnimations();
    }

    @Override
    public void doPlay()
    {
        playBtn.setImageDrawable(playDrawable);
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doPause()
    {
        playBtn.setImageDrawable(pauseDrawable);
        playBtn.startAnimation(blinkAnimation);
    }

    @Override
    public void doSkip()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(pauseDrawable);
        playBtn.startAnimation(blinkAnimation);
        skipBtn.startAnimation(flipRightAnimation);
        playBtn.setImageDrawable(playDrawable);
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doPrev()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(pauseDrawable);
        playBtn.startAnimation(blinkAnimation);
        prevBtn.startAnimation(flipLeftAnimation);
        playBtn.setImageDrawable(playDrawable);
        playBtn.startAnimation(ltr);
    }

    @Override
    public void doShuffle()
    {
        playBtn.clearAnimation();
        playBtn.setImageDrawable(pauseDrawable);
        playBtn.startAnimation(blinkAnimation);
        shuffleBtn.startAnimation(flipDownAnimation);
        playBtn.setImageDrawable(playDrawable);
        playBtn.startAnimation(ltr);
    }

    @Override
    public void reboot()
    {
        setSongTitle(
                baseActivity.getMediaPlayerBroadcastReceiver()
                        .getCurrentSongTitle()
        );

        if (SkipShuflleMediaPlayerCommandsContract.STATE_PLAY.equals(
                baseActivity.getMediaPlayerBroadcastReceiver()
                        .getPlayerState())
                ) {
            doPlay();
        } else {
            doPause();
            if (baseActivity.getMediaPlayerBroadcastReceiver().getCurrentSongTitle().equals(
                    baseActivity.getResources().getString(
                            R.string.meta_data_unknown_current_song_title
                    )
            )) {
                playBtn.clearAnimation();
            }
        }
    }

    protected void setUpAnimations()
    {
        ltr = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_ltr
        );
        flipRightAnimation  = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_flip_right
        );
        flipDownAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_flip_down
        );
        flipLeftAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_flip_left
        );
        blinkAnimation = AnimationUtils.loadAnimation(
                baseActivity.getApplicationContext(),
                R.anim.common_blink
        );
        flipRightAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {
                doPause();
                skipBtn.setImageDrawable(skipPressedDrawable);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                skipBtn.setImageDrawable(skipDrawable);
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
                prevBtn.setImageDrawable(prevPressedDrawable);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                prevBtn.setImageDrawable(prevDrawable);
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
                shuffleBtn.setImageDrawable(shufflePressedDrawable);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                shuffleBtn.setImageDrawable(shuffleDrawable);
                doPlay();
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
    }

    protected void setUpDrawables()
    {
        super.setUpDrawables();
        playlistBtn.setImageDrawable(playlistDrawable);
    }

    protected void setUpColors()
    {
        super.setUpColors();
        songTitle.setTextColor(
                baseActivity.getResources().getColor(
                    ColorMapper.getSongLabel(uiType)
                )
        );
    }

    @Override
    public void setSongTitle(String title)
    {
        songTitle.setText(title);
        songTitle.setTypeface(getTypeFace());
        songTitle.setSelected(true);
    }
}
