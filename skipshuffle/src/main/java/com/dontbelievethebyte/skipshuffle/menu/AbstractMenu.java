package com.dontbelievethebyte.skipshuffle.menu;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.actionbar.CustomActionBarWrapper;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.BackPressedNotHandledException;
import com.dontbelievethebyte.skipshuffle.exceptions.MenuOptionNotHandledException;
import com.dontbelievethebyte.skipshuffle.exceptions.NoHardwareMenuKeyException;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;

public abstract class AbstractMenu implements CustomOptionsMenuInterface {

    protected BaseActivity baseActivity;
    protected boolean isOptionsMenuOpen = false;
    protected CustomActionBarWrapper customActionBarWrapper;

    public AbstractMenu(BaseActivity baseActivity, Menu menu)
    {
        this.baseActivity = baseActivity;
        MenuInflater menuInflater = baseActivity.getMenuInflater();
        menuInflater.inflate(getMenuResourceId(), menu);
    }

    public void setCustomActionBarWrapper(CustomActionBarWrapper customActionBarWrapper)
    {
        this.customActionBarWrapper = customActionBarWrapper;
    }

    protected abstract int getMenuResourceId();

    @Override
    public boolean handleSelection(MenuItem menuItem) throws MenuOptionNotHandledException {
        switch (menuItem.getItemId()) {
            case R.id.refresh_media:
                return handleRefreshMedia();
            case R.id.haptic_feedback_toggle:
                return handleHapticFeedBack();
            case R.id.theme:
                return handleThemeSelection();
            default:
                throw new MenuOptionNotHandledException();
        }
    }

    protected void handleNoHardwareMenuKeyException()
    {

    }

    @Override
    public boolean isShowing() {
        return false;
    }

    @Override
    public void showToggle() {

    }

    @Override
    public boolean handleMenuKeyDown(int keyCode, KeyEvent event)
    {
        boolean returnValue = false;

        if (isOptionsMenuOpen) {
            if (customActionBarWrapper.isShowing()) {
                try {
                    customActionBarWrapper.showToggle();
                    returnValue = true;
                } catch (NoHardwareMenuKeyException noHardWareMenuKeyException) {
                    returnValue = true;
                }
        }
        } else {
            baseActivity.closeOptionsMenu();
        }
        isOptionsMenuOpen = !isOptionsMenuOpen;
        return returnValue;
    }

    public boolean handleBackPressed() throws BackPressedNotHandledException
    {
        throw new BackPressedNotHandledException();
    }

    private boolean handleRefreshMedia()
    {
        baseActivity.showMediaScannerDialog();
        return true;
    }

    protected boolean handleHapticFeedBack()
    {
        PreferencesHelper preferencesHelper = baseActivity.getPreferencesHelper();
        preferencesHelper.setHapticFeedback(!preferencesHelper.isHapticFeedback());
        return true;
    }

    protected boolean handleThemeSelection()
    {
        baseActivity.showThemeSelectionDialog();
        return true;
    }
}
