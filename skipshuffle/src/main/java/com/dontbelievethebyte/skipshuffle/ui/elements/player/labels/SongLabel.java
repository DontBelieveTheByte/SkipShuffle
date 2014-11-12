package com.dontbelievethebyte.skipshuffle.ui.elements.player.labels;

import android.view.ViewGroup;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.ui.elements.content.AbstractContentArea;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;

public class SongLabel implements UIElementCompositeInterface {

    private TextView label;

    public SongLabel(AbstractContentArea contentArea, int labelId)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();

        label = (TextView) bottomLayout.findViewById(labelId);
    }

    public void setContent(String content)
    {
        if (null != label) {
            label.setText(content);
            label.setSelected(true);
        }
    }

    public void setTypeFace(CustomTypeface typeFace)
    {
        if (null != label && null != typeFace.getTypeFace())
            label.setTypeface(typeFace.getTypeFace());
    }

    public TextView getLabel()
    {
        return label;
    }
}
