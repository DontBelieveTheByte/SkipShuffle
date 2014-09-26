package com.dontbelievethebyte.skipshuffle.activities.util;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    private Toast toast;

    public ToastHelper(Context context)
    {
        toast = new Toast(context);
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
        toast.cancel();
        toast.setText(toastMessage);
        toast.setDuration(duration);
        toast.show();
    }
}
