/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.theme;

import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Colors;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.structure.Drawables;

public class Theme {

    private CustomTypeface customTypeface;
    private Colors colors;
    private Drawables drawables;

    public Theme(Colors colors, Drawables drawables, CustomTypeface customTypeface)
    {
        this.colors = colors;
        this.drawables = drawables;
        this.customTypeface = customTypeface;
    }

    public CustomTypeface getCustomTypeface()
    {
        return customTypeface;
    }

    public Colors getColors()
    {
        return colors;
    }

    public Drawables getDrawables()
    {
        return drawables;
    }

}
