/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.concrete;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.visitor.dimension.AbstractDimensionsVisitor;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.mapper.DimensionsMapper;

public class MainPlayerDimensionsVisitor extends AbstractDimensionsVisitor {

    public MainPlayerDimensionsVisitor(Activity activity)
    {
        super(activity);
    }

    @Override
    public void visit(UIElementCompositeInterface uiElement)
    {
        adjustSongLabelDimensions();
    }

    private void adjustSongLabelDimensions()
    {
        TextView songLabel = (TextView) activity.findViewById(R.id.song_label);
        setSongLabelContainerSize(songLabel);
        SeekBar seekBar = (SeekBar) activity.findViewById(R.id.seekBar);
        setSongLabelContainerSize(seekBar);
    }

    private void setSongLabelContainerSize(View view)
    {
        LinearLayout.LayoutParams songLabelLayoutParams = createLinearLayoutParams();

        double songLabelTextSize = (computedScreenHeight * (isLandScape ? DimensionsMapper.Player.Center.Landscape.textSize : DimensionsMapper.Player.Center.Portrait.textSize));
        double songLabelWidth = isLandScape ?
                computedScreenWidth * DimensionsMapper.Player.Center.Landscape.width:
                computedScreenWidth * DimensionsMapper.Player.Center.Portrait.width;

        songLabelLayoutParams.width = (int) songLabelWidth;
        view.setLayoutParams(songLabelLayoutParams);
    }

}
