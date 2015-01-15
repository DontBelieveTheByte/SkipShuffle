/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.utilities;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    private static Toast toast;

    private Context context;

    public ToastHelper(Context context)
    {
        this.context = context;
    }

    public void showShortToast(String toastMessage)
    {
        setUpToast(toastMessage, Toast.LENGTH_SHORT);
    }

    public void showLongToast(String toastMessage)
    {
        setUpToast(toastMessage, Toast.LENGTH_LONG);
    }

    private void setUpToast(String toastMessage, int duration)
    {
        if (null != toast) {
            toast.cancel();
        }
        toast = Toast.makeText(
                context,
                toastMessage,
                duration
        );
        toast.show();
    }
}
