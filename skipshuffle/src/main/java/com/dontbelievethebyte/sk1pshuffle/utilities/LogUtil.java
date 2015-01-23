/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.utilities;

import android.util.Log;

public class LogUtil {
    private static final String TAG = "SkipShuffle";
    public static void writeDebug(String message)
    {
        Log.d(TAG, message);
    }
}
