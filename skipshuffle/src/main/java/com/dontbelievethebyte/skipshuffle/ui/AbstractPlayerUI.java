package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;

public abstract class AbstractPlayerUI extends AbstractUI implements PlayerUIInterface {

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

    protected TextView songTitle;

    public AbstractPlayerUI(BaseActivity baseActivity, int contentLayout)
    {
        super(baseActivity, contentLayout);

        prevBtn = (ImageButton) baseActivity.findViewById(R.id.prevBtn);
        playBtn = (ImageButton) baseActivity.findViewById(R.id.playBtn);
        shuffleBtn = (ImageButton) baseActivity.findViewById(R.id.shuffleBtn);
        skipBtn = (ImageButton) baseActivity.findViewById(R.id.skipBtn);

        playDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getPlay(uiType)
        );
        pauseDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getPause(uiType)
        );
        prevDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getPrev(uiType)
        );
        skipDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getSkip(uiType)
        );
        shuffleDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getShuffle(uiType)
        );
        playlistDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getPlaylist(uiType)
        );

        playPressedDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getPlayPressed(uiType)
        );
        pausePressedDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getPausePressed(uiType)
        );
        prevPressedDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getPrevPressed(uiType)
        );
        skipPressedDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getSkipPressed(uiType)
        );
        shufflePressedDrawable = baseActivity.getResources().getDrawable(
                DrawableMapper.getShufflePressed(uiType)
        );

        //Register haptic feedback for all buttons.
        playBtn.setOnTouchListener(baseActivity);
        skipBtn.setOnTouchListener(baseActivity);
        prevBtn.setOnTouchListener(baseActivity);
        shuffleBtn.setOnTouchListener(baseActivity);
    }
    @Override
    public void doPlay()
    {
        playBtn.setImageDrawable(playDrawable);
    }

    @Override
    public void doPause()
    {
        playBtn.setImageDrawable(pauseDrawable);
    }

    @Override
    public void doSkip() {

    }

    @Override
    public void doPrev() {

    }

    @Override
    public void doShuffle() {

    }

    @Override
    public void reboot() {

    }

    @Override
    public void setSongTitle(String title) {

    }

    protected void setUpDrawables()
    {
        prevBtn.setImageDrawable(prevDrawable);
        playBtn.setImageDrawable(playDrawable);
        shuffleBtn.setImageDrawable(shuffleDrawable);
        skipBtn.setImageDrawable(skipDrawable);
    }

    protected void setUpDrawer()
    {
//        TextView headerView = (TextView) baseActivity.getLayoutInflater().inflate(
//                R.layout.drawer_list_header,
//                drawerList
//        );
//
//        headerView.setTextColor(
//                baseActivity.getResources().getColor(
//                        ColorMapper.getNavHeaderText(baseActivity.getPreferencesHelper().getUIType())
//                )
//        );
//        headerView.setText(
//                baseActivity.getString(R.string.drawer_header_text)
//        );
//        headerView.setTypeface(getTypeFace());
//
//        drawerList.addHeaderView(headerView);
    }

}
