package com.dontbelievethebyte.skipshuffle.ui;

import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MainActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.services.SkipShuffleMediaPlayer;

public class MainUI extends AbstractPlayerUI {

    public MainUI(MainActivity activity)
    {
        super(activity, R.layout.main_activity);
        playlistBtn = (ImageButton) activity.findViewById(R.id.playlistBtn);
        playlistBtn.setOnTouchListener(baseActivity);
        songLabel = (TextView) activity.findViewById(R.id.song_label);
        setUpDrawables();
        setUpDimensions();
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
        try {
            if (baseActivity.getMediaPlayer().getPlayerWrapper().isPlaying())
                doPlay();
            else
                doPause();

            setSongLabel(buildFormattedTitle());

        } catch (NoMediaPlayerException noMediaPlayerException) {
            handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            handlePlaylistEmptyException(playlistEmptyException);
        }
    }

    @Override
    public void setSongLabel(String title)
    {
        songLabel.setText(title);
        songLabel.setTypeface(getTypeFace());
        songLabel.setSelected(true);
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
    protected void setUpDimensions()
    {
        super.setUpDimensions();

        /* Play button */
        LinearLayout.LayoutParams playButtonLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        double playButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Top.Landscape.buttonSize :
            computedScreenWidth * DimensionsMapper.Player.Top.Portrait.buttonSize;

        playButtonLayoutParams.height = (int) playButtonDimension;
        playButtonLayoutParams.width = (int) playButtonDimension;
        playBtn.setLayoutParams(playButtonLayoutParams);

        /* Skip button */
        LinearLayout.LayoutParams skipButtonLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        double skipButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Top.Landscape.buttonSize :
                computedScreenWidth * DimensionsMapper.Player.Top.Portrait.buttonSize;

        skipButtonLayoutParams.height = (int) skipButtonDimension;
        skipButtonLayoutParams.width = (int) skipButtonDimension;

        skipBtn.setLayoutParams(skipButtonLayoutParams);

        /* Shuffle button */
        LinearLayout.LayoutParams shuffleButtonLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        double shuffleButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Bottom.Landscape.buttonSideSize :
                computedScreenWidth * DimensionsMapper.Player.Bottom.Portrait.buttonSideSize;

        shuffleButtonLayoutParams.height = (int) shuffleButtonDimension;
        shuffleButtonLayoutParams.width = (int) shuffleButtonDimension;

        shuffleBtn.setLayoutParams(shuffleButtonLayoutParams);

        /* Playlist button */
        LinearLayout.LayoutParams playlistButtonLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        double playlistButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Bottom.Landscape.buttonCenterSize :
                computedScreenWidth * DimensionsMapper.Player.Bottom.Portrait.buttonCenterSize;

        playlistButtonLayoutParams.height = (int) playlistButtonDimension;
        playlistButtonLayoutParams.width = (int) playlistButtonDimension;


        playlistBtn.setLayoutParams(playlistButtonLayoutParams);

        /* Prev button */
        LinearLayout.LayoutParams prevButtonLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        double prevButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Bottom.Landscape.buttonSideSize :
                computedScreenWidth * DimensionsMapper.Player.Bottom.Portrait.buttonSideSize;

        prevButtonLayoutParams.height = (int) prevButtonDimension;
        prevButtonLayoutParams.width = (int) prevButtonDimension;

        prevBtn.setLayoutParams(prevButtonLayoutParams);

        /*Adjust player padding */

        // Special way to calculate the ActionBar height
        int actionBarHeight;
        TypedValue tv = new TypedValue();
        if (baseActivity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data,
                    baseActivity.getResources().getDisplayMetrics()
            );
        } else {
            actionBarHeight = (int) (computedScreenHeight * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.top : DimensionsMapper.Player.Padding.Portrait.top));
        }

        bottomLayout.setPadding(
        /* left   */ (int) (computedScreenWidth * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.left : DimensionsMapper.Player.Padding.Portrait.left)),
        /* top    */ actionBarHeight,
        /* right  */ (int) (computedScreenWidth * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.right : DimensionsMapper.Player.Padding.Portrait.right)),
        /* bottom */ 0
        );

        /* Song title */
        LinearLayout.LayoutParams songLabelLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        double songLabelTextSize = (computedScreenHeight * (isLandScape ? DimensionsMapper.Player.Center.Landscape.textSize : DimensionsMapper.Player.Center.Portrait.textSize));
        double songLabelTextHeight = (songLabelTextSize * (isLandScape ? DimensionsMapper.Player.Center.Landscape.textHeight : DimensionsMapper.Player.Center.Portrait.textHeight));
        double totalHeight = actionBarHeight + playButtonDimension + shuffleButtonDimension + songLabelTextHeight;

        while ( totalHeight > computedScreenHeight) {//@TODO Fix song label size.
            songLabelTextHeight = songLabelTextHeight - 1 ;
//            Log.d("COMPUTED HEIUGT : ", Double.toString(songLabelTextHeight));
//            Log.d("COMPUTED HEIUGT : ", Double.toString(songLabelTextHeight));

        }
//        Log.d("COMPUTED HEIUGT : ", Double.toString(songLabelTextHeight));
//        Log.d("COMPUTED SCREEN : ", Integer.toString(computedScreenHeight));
//        Log.d("COMPUTED TOTAL HEIUGT : ", Double.toString(totalHeight));
        double songLabelWidth = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Center.Landscape.width:
                computedScreenWidth * DimensionsMapper.Player.Center.Portrait.width;

        songLabelLayoutParams.width = (int) songLabelWidth;
        songLabelLayoutParams.height = (int) songLabelTextHeight;

        songLabel.setLayoutParams(songLabelLayoutParams);
        songLabel.setTextSize((int) songLabelTextHeight);

    }
    protected void setUpColors()
    {
        super.setUpColors();
        songLabel.setTextColor(
                baseActivity.getResources().getColor(
                        ColorMapper.getSongLabel(uiType)
                )
        );
    }

    private String buildFormattedTitle() throws PlaylistEmptyException
    {
        try {
            SkipShuffleMediaPlayer skipShuffleMediaPlayer = baseActivity.getMediaPlayer();
            PlaylistInterface playlist = skipShuffleMediaPlayer.getPlaylist();
            Track currentTrack = playlist.getCurrent();
            if (null == currentTrack.getArtist() || null == currentTrack.getTitle()) {
                return (null == currentTrack.getPath()) ?
                        baseActivity.getString(R.string.meta_data_unknown_current_song_title) :
                        currentTrack.getPath().substring(currentTrack.getPath().lastIndexOf("/") + 1);
            } else {
                return currentTrack.getArtist() + " - " + currentTrack.getTitle();
            }
        } catch (NoMediaPlayerException noMediaPlayerException){
            throw new PlaylistEmptyException(0L);
        }
    }

    private void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        setSongLabel(
            baseActivity.getString(R.string.meta_data_unknown_current_song_title)
        );
    }

    private void handleNoMediaPlayerException(NoMediaPlayerException noMediaPlayerException)
    {
        try {
            buildFormattedTitle();
        } catch (PlaylistEmptyException playListEmptyException) {
            handlePlaylistEmptyException(playListEmptyException);
        }
        doPause();
    }
}
