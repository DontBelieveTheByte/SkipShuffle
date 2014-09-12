package com.dontbelievethebyte.skipshuffle.ui.main;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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

    protected Drawable playDrawable;
    protected Drawable pauseDrawable;
    protected Drawable prevDrawable;
    protected Drawable skipDrawable;
    protected Drawable shuffleDrawable;
    protected Drawable playlistDrawable;

    protected Drawable playPressedDrawable;
    protected Drawable pausePressedDrawable;
    protected Drawable prevPressedDrawable;
    protected Drawable skipPressedDrawable;
    protected Drawable shufflePressedDrawable;

    protected Animation ltr;
    protected Animation flipRightAnimation;
    protected Animation flipDownAnimation;
    protected Animation flipLeftAnimation;
    protected Animation blinkAnimation;

    protected MainActivity mainActivity;
    protected TextView songTitle;
    protected Typeface typeface;
    protected RelativeLayout bottom;

    public MainUI(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;

        mainActivity.setContentView(R.layout.common_activity_main);

        playlistBtn = (ImageButton) mainActivity.findViewById(R.id.playlistBtn);
        prevBtn = (ImageButton) mainActivity.findViewById(R.id.prevBtn);
        playBtn = (ImageButton) mainActivity.findViewById(R.id.playBtn);
        shuffleBtn = (ImageButton) mainActivity.findViewById(R.id.shuffleBtn);
        skipBtn = (ImageButton) mainActivity.findViewById(R.id.skipBtn);
        songTitle = (TextView) mainActivity.findViewById(R.id.song_label);

        songTitle.setTypeface(getTypeFace());

        ltr = AnimationUtils.loadAnimation(
                mainActivity.getApplicationContext(),
                R.anim.common_ltr
        );
        flipRightAnimation  = AnimationUtils.loadAnimation(
                mainActivity.getApplicationContext(),
                R.anim.common_flip_right
        );
        flipDownAnimation = AnimationUtils.loadAnimation(
                mainActivity.getApplicationContext(),
                R.anim.common_flip_down
        );
        flipLeftAnimation = AnimationUtils.loadAnimation(
                mainActivity.getApplicationContext(),
                R.anim.common_flip_left
        );
        blinkAnimation = AnimationUtils.loadAnimation(
                mainActivity.getApplicationContext(),
                R.anim.common_blink
        );

        bottom = (RelativeLayout) mainActivity.findViewById(R.id.bottom);

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
    public Typeface getTypeFace()
    {
        if (null == typeface) {
            typeface = Typeface.createFromAsset(mainActivity.getAssets(), "fonts/UbuntuMono-B.ttf" );
        }
        return typeface;
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

    protected void setUpDrawables(){
        playlistBtn.setImageDrawable(playlistDrawable);
        prevBtn.setImageDrawable(prevDrawable);
        playBtn.setImageDrawable(playDrawable);
        shuffleBtn.setImageDrawable(shuffleDrawable);
        skipBtn.setImageDrawable(skipDrawable);
    }

    protected abstract void setUpColors();

    protected void setUpAnimations()
    {
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

    @Override
    public void setSongTitle(String title) {
        songTitle.setText(title);
        songTitle.setSelected(true);
    }
}
