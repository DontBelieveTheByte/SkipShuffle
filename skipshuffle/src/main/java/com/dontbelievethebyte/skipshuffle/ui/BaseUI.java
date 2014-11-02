package com.dontbelievethebyte.skipshuffle.ui;

import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.ui.builder.UIBuilder;
import com.dontbelievethebyte.skipshuffle.ui.mapper.TypeFaceMapper;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.visitor.ColorVisitor;

import java.util.ArrayList;

public class BaseUI {

    private int uiType;
    private Colors colors;
    private BaseActivity baseActivity;
    private ListView drawerList;
    private Typeface typeface;
    private PlayerUI playerUI;

    protected ViewGroup bottomLayout;
    private ArrayList<UIElement> uiElements;

    public BaseUI(UIBuilder builder)
    {


    }

    public BaseActivity getBaseActivity()
    {
        return baseActivity;
    }

    public BaseUI(BaseActivity activity, int contentLayout)
    {
        this.baseActivity = activity;
        uiType = baseActivity.getPreferencesHelper().getUIType();
        baseActivity.setContentView(R.layout.base_ui);

        drawerList = (ListView) activity.findViewById(R.id.drawer_list);
        bottomLayout = (ViewGroup) baseActivity.findViewById(R.id.bottom);

    }

    public Typeface getTypeFace()
    {
        if (null == typeface) {
            typeface = Typeface.createFromAsset(
                    baseActivity.getAssets(),
                    TypeFaceMapper.getTypeFace(uiType)
            );
        }
        return typeface;
    }

    public ViewGroup getBottomLayout()
    {
        return bottomLayout;
    }

    protected void setUpColors()
    {
        ColorVisitor colorVisitor = new ColorVisitor(colors);

        for (UIElement element : uiElements) {
            colorVisitor.visit(element);
        }
    }
}
