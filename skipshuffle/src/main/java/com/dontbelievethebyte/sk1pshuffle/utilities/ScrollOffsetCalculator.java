/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.utilities;

import android.widget.ListView;

public class ScrollOffsetCalculator {

    public static int compute(ListView listView)
    {
        int firstSeeing = listView.getFirstVisiblePosition();
        int lastSeeing = listView.getLastVisiblePosition();
        int numberOfTotalElements = lastSeeing - firstSeeing;
        return (numberOfTotalElements > 1) ? numberOfTotalElements / 2 : 2;
    }
}
