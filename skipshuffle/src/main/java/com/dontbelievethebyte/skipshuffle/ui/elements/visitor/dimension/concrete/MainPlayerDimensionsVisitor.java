package com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.concrete;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DimensionsMapper;

public class MainPlayerDimensionsVisitor extends AbstractDimensionsVisitor {

    public MainPlayerDimensionsVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        setPlayButtonSize();
        setPrevButtonSize();
        setPlaylistButtonSize();
        setShuffleButtonSize();
        setSkipButtonSize();
        setSongLabelSize();
    }

    private void setPlayButtonSize()
    {
        double playButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Top.Landscape.buttonSize :
                computedScreenWidth * DimensionsMapper.Player.Top.Portrait.buttonSize;
        setSquareImageButtonSize(R.id.playBtn, playButtonDimension);
    }

    private void setPrevButtonSize()
    {
        double prevButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Bottom.Landscape.buttonSideSize :
                computedScreenWidth * DimensionsMapper.Player.Bottom.Portrait.buttonSideSize;
        setSquareImageButtonSize(R.id.prevBtn, prevButtonDimension);
    }

    private void setSkipButtonSize()
    {
        double skipButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Top.Landscape.buttonSize :
                computedScreenWidth * DimensionsMapper.Player.Top.Portrait.buttonSize;
        setSquareImageButtonSize(R.id.skipBtn, skipButtonDimension);
    }

    private void setPlaylistButtonSize()
    {
        double playlistButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Bottom.Landscape.buttonCenterSize :
                computedScreenWidth * DimensionsMapper.Player.Bottom.Portrait.buttonCenterSize;
        setSquareImageButtonSize(R.id.playlistBtn, playlistButtonDimension);
    }

    private void setShuffleButtonSize()
    {
        double shuffleButtonDimension = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Bottom.Landscape.buttonSideSize :
                computedScreenWidth * DimensionsMapper.Player.Bottom.Portrait.buttonSideSize;
        setSquareImageButtonSize(R.id.shuffleBtn, shuffleButtonDimension);
    }

    private void setSongLabelSize()
    {
        TextView songLabel = (TextView) activity.findViewById(R.id.song_label);

        LinearLayout.LayoutParams songLabelLayoutParams = createLinearLayoutParams();

        double songLabelTextSize = (computedScreenHeight * (isLandScape ? DimensionsMapper.Player.Center.Landscape.textSize : DimensionsMapper.Player.Center.Portrait.textSize));
        double songLabelTextHeight = (songLabelTextSize * (isLandScape ? DimensionsMapper.Player.Center.Landscape.textHeight : DimensionsMapper.Player.Center.Portrait.textHeight));
        double totalHeight = 55;

//        while ( computeTotalHeight() > computedScreenHeight) {//@TODO Fix song label size.
//            songLabelTextHeight = songLabelTextHeight - 1 ;
//        }

        double songLabelWidth = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Center.Landscape.width:
                computedScreenWidth * DimensionsMapper.Player.Center.Portrait.width;

        songLabelLayoutParams.width = (int) songLabelWidth;
        songLabelLayoutParams.height = (int) songLabelTextHeight;

        songLabel.setLayoutParams(songLabelLayoutParams);
        songLabel.setTextSize((int) songLabelTextHeight);
    }
}
