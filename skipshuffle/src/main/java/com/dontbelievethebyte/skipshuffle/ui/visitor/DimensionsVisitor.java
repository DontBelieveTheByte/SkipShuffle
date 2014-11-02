package com.dontbelievethebyte.skipshuffle.ui.visitor;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.BaseUI;
import com.dontbelievethebyte.skipshuffle.ui.PlayerUI;
import com.dontbelievethebyte.skipshuffle.ui.UIElement;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DimensionsMapper;

public class DimensionsVisitor {

    protected boolean isLandScape;
    protected int computedScreenHeight;
    protected int computedScreenWidth;
    private BaseActivity baseActivity;

    public DimensionsVisitor(BaseActivity baseActivity)
    {
        isLandScape = Configuration.ORIENTATION_LANDSCAPE == baseActivity.getResources().getConfiguration().orientation;
        computedScreenHeight = baseActivity.getResources().getDisplayMetrics().heightPixels;
        computedScreenWidth = baseActivity.getResources().getDisplayMetrics().widthPixels;
        this.baseActivity = baseActivity;
    }

    public void visit(UIElement uiElement)
    {
        if (uiElement instanceof MusicPlayerDrawer)
            visitMusicPlayerDrawer((MusicPlayerDrawer) uiElement);
        else if (uiElement instanceof BaseUI)
            visitBaseUI((BaseUI) uiElement);
        else if (uiElement instanceof PlayerUI)
            visitPlayerUI((PlayerUI) uiElement);
    }

    private void visitMusicPlayerDrawer(MusicPlayerDrawer musicPlayerDrawer)
    {
        setNavDrawerSize();
    }

    private void visitBaseUI(BaseUI baseUI)
    {
        setActionBarHeight();
    }

    private void visitPlayerUI(PlayerUI playerUI)
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

    private void setSquareImageButtonSize(int buttonId, double size)
    {
        ImageButton imageButton = (ImageButton) baseActivity.findViewById(buttonId);

        LinearLayout.LayoutParams playButtonLayoutParams = createLinearLayoutParams();

        playButtonLayoutParams.height = (int) size;
        playButtonLayoutParams.width = (int) size;
        imageButton.setLayoutParams(playButtonLayoutParams);
    }

    private void setActionBarHeight()
    {

    }

    private int computeActionBarHeight()
    {
        TypedValue tv = new TypedValue();
        if (baseActivity.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data,
                    baseActivity.getResources().getDisplayMetrics()
            );
        } else {
            return (int) (computedScreenHeight * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.top : DimensionsMapper.Player.Padding.Portrait.top));
        }
    }

    private void setBottomLayoutSize(BaseUI baseUI)
    {
        BaseActivity baseActivity = baseUI.getBaseActivity();
        View bottomLayout = baseActivity.findViewById(R.id.bottom);

        bottomLayout.setPadding(
        /* left   */ (int) (computedScreenWidth * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.left : DimensionsMapper.Player.Padding.Portrait.left)),
        /* top    */ computeActionBarHeight(),
        /* right  */ (int) (computedScreenWidth * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.right : DimensionsMapper.Player.Padding.Portrait.right)),
        /* bottom */ 0
        );
    }

    private void setNavDrawerSize()
    {
        ListView drawerList = (ListView) baseActivity.findViewById(R.id.drawer_list);
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerList.getLayoutParams();
        params.width = (int) (computedScreenWidth * ( isLandScape ? DimensionsMapper.Drawer.Landscape.width : DimensionsMapper.Drawer.Portrait.width));
        drawerList.setLayoutParams(params);

        int listDividerHeight = (int)baseActivity.getResources().getDimension(R.dimen.list_divider_height);
        drawerList.setDividerHeight(33);
    }

    private void setSongLabelSize()
    {
        TextView songLabel = (TextView) baseActivity.findViewById(R.id.song_label);

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

    private LinearLayout.LayoutParams createLinearLayoutParams()
    {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
}
