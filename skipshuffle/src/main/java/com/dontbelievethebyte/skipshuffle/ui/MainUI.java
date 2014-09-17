package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.services.SkipShuflleMediaPlayerCommandsContract;

public class MainUI implements PlayerUIInterface {

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
    protected ListView drawerList;

    protected int uiType;

    public MainUI(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;

        uiType = mainActivity.getPreferencesHelper().getUIType();

        mainActivity.setContentView(R.layout.activity_main);

        drawerList = (ListView) mainActivity.findViewById(R.id.left_drawer1);

        playlistBtn = (ImageButton) mainActivity.findViewById(R.id.playlistBtn);
        prevBtn = (ImageButton) mainActivity.findViewById(R.id.prevBtn);
        playBtn = (ImageButton) mainActivity.findViewById(R.id.playBtn);
        shuffleBtn = (ImageButton) mainActivity.findViewById(R.id.shuffleBtn);
        skipBtn = (ImageButton) mainActivity.findViewById(R.id.skipBtn);
        songTitle = (TextView) mainActivity.findViewById(R.id.song_label);

        playDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getPlay(uiType)
        );
        pauseDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getPause(uiType)
        );
        prevDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getPrev(uiType)
        );
        skipDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getSkip(uiType)
        );
        shuffleDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getShuffle(uiType)
        );
        playlistDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getPlaylist(uiType)
        );

        playPressedDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getPlayPressed(uiType)
        );
        pausePressedDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getPausePressed(uiType)
        );
        prevPressedDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getPrevPressed(uiType)
        );
        skipPressedDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getSkipPressed(uiType)
        );
        shufflePressedDrawable = mainActivity.getResources().getDrawable(
                DrawableMapper.getShufflePressed(uiType)
        );

        setUpDrawables();
        setUpColors();
        setUpAnimations();

        songTitle.setTypeface(getTypeFace());
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
                            R.string.meta_data_unknown_current_song_title
                    )
            )) {
                playBtn.clearAnimation();
            }
        }
    }

    protected void setUpDrawables()
    {
        playlistBtn.setImageDrawable(playlistDrawable);
        prevBtn.setImageDrawable(prevDrawable);
        playBtn.setImageDrawable(playDrawable);
        shuffleBtn.setImageDrawable(shuffleDrawable);
        skipBtn.setImageDrawable(skipDrawable);
    }

    protected void setUpColors()
    {
        //@TODO should probably be in future setUpDimension method.
        int listDividerHeight = (int)mainActivity.getResources().getDimension(R.dimen.list_divider_height);

        RelativeLayout bottomLayout = (RelativeLayout) mainActivity.findViewById(R.id.bottom);
        bottomLayout.setBackgroundResource(
                ColorMapper.getBackground(uiType)
        );

        ColorDrawable navDrawerColorDrawable = new ColorDrawable(
                mainActivity.getResources().getColor(
                        ColorMapper.getListDivider(uiType)
                )
        );

        drawerList.setBackgroundResource(
                ColorMapper.getNavDrawerBackground(uiType)
        );

        drawerList.setDivider(navDrawerColorDrawable);
        drawerList.setDividerHeight(listDividerHeight);

        songTitle.setTextColor(
                mainActivity.getResources().getColor(
                    ColorMapper.getSongLabel(uiType)
                )
        );
    }

    protected void setUpAnimations()
    {
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
    public void setSongTitle(String title)
    {
        songTitle.setText(title);
        songTitle.setSelected(true);
    }
}
