package com.dontbelievethebyte.skipshuffle.listeners;

import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;

public class TouchHandler {

    BaseActivity baseActivity;

    public TouchHandler(BaseActivity baseActivity)
    {
        this.baseActivity = baseActivity;
    }

    public boolean handleTouch(View view, MotionEvent motionEvent)
    {

        if (MotionEvent.ACTION_DOWN == motionEvent.getAction()){
            handleActiveFeedback(view);

//            if ((preferencesHelper.getLastPlaylist() == 0)  && !(this instanceof FilePickerActivity)) {
//                pickMediaDirectories();
//                //Return true because we already handled the event and want to prevent bubbling.
//                return true;
//            }
        }
        return false;
    }

    private void handleActiveFeedback(View view)
    {
        PreferencesHelper preferencesHelper = baseActivity.getPreferencesHelper();
        if (preferencesHelper.isHapticFeedback()) {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        }
    }
}
