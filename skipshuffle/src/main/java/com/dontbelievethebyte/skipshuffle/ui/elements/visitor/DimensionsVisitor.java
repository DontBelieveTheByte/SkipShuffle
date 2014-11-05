package com.dontbelievethebyte.skipshuffle.ui.elements.visitor;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.elements.navdrawer.MusicPlayerDrawer;
import com.dontbelievethebyte.skipshuffle.ui.UIComposition;
import com.dontbelievethebyte.skipshuffle.ui.elements.ContentArea;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.MainPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.mapper.DimensionsMapper;

public class DimensionsVisitor {

    public static interface Visitable {
        public void acceptDimensionsVisitor(DimensionsVisitor dimensionsVisitor);
    }

    protected boolean isLandScape;
    protected int computedScreenHeight;
    protected int computedScreenWidth;
    private Activity activity;

    public DimensionsVisitor(Activity activity)
    {
        this.activity = activity;
        initBasicDimensions();
    }

    private void initBasicDimensions()
    {
        isLandScape = Configuration.ORIENTATION_LANDSCAPE == activity.getResources().getConfiguration().orientation;
        computedScreenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        computedScreenWidth = activity.getResources().getDisplayMetrics().widthPixels;
    }

    public void visit(UIElementCompositeInterface uiElement)
    {
        if (uiElement instanceof MusicPlayerDrawer)
            visitMusicPlayerDrawer((MusicPlayerDrawer) uiElement);
        else if (uiElement instanceof UIComposition)
            visitBaseUI((UIComposition) uiElement);
        else if (uiElement instanceof MainPlayer)
            visitPlayerUI((MainPlayer) uiElement);
        else if (uiElement instanceof ContentArea)
            visitContentArea((ContentArea) uiElement);

    }

    private void visitMusicPlayerDrawer(MusicPlayerDrawer musicPlayerDrawer)
    {
        setNavDrawerSize();
    }

    private void visitBaseUI(UIComposition baseUI)
    {
        setActionBarHeight();
    }

    private void visitPlayerUI(MainPlayer playerUI)
    {
        setPlayButtonSize();
        setPrevButtonSize();
        setPlaylistButtonSize();
        setShuffleButtonSize();
        setSkipButtonSize();
        setSongLabelSize();
    }

    private void visitContentArea(ContentArea contentArea)
    {
        setBottomLayoutSize(contentArea);
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
        ImageButton imageButton = (ImageButton) activity.findViewById(buttonId);

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
        if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(
                    tv.data,
                    activity.getResources().getDisplayMetrics()
            );
        } else {
            return (int) (computedScreenHeight * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.top : DimensionsMapper.Player.Padding.Portrait.top));
        }
    }

    private void setBottomLayoutSize(ContentArea contentArea)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();

        bottomLayout.setPadding(
        /* left   */ (int) (computedScreenWidth * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.left : DimensionsMapper.Player.Padding.Portrait.left)),
        /* top    */ computeActionBarHeight(),
        /* right  */ (int) (computedScreenWidth * (isLandScape ? DimensionsMapper.Player.Padding.Landscape.right : DimensionsMapper.Player.Padding.Portrait.right)),
        /* bottom */ 0
        );
    }

    private void setNavDrawerSize()
    {
        ListView drawerList = (ListView) activity.findViewById(R.id.drawer_list);
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerList.getLayoutParams();
        params.width = (int) (computedScreenWidth * ( isLandScape ? DimensionsMapper.Drawer.Landscape.width : DimensionsMapper.Drawer.Portrait.width));
        drawerList.setLayoutParams(params);

        int listDividerHeight = (int) activity.getResources().getDimension(R.dimen.list_divider_height);
        drawerList.setDividerHeight(33);
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

    private LinearLayout.LayoutParams createLinearLayoutParams()
    {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }
}
