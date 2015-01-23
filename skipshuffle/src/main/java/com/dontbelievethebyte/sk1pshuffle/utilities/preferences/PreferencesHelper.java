/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.utilities.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.PlaylistData;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.UITypes;
import com.google.gson.Gson;

public class PreferencesHelper {

    private Context context;
    private Boolean isHapticFeedback;
    private UITypes currentUIType;
    private SharedPreferences sharedPreferences;
    private Boolean isListViewMode;
    private Integer numberTimesAppWasOpened;
    private Boolean canRateApp;
    private boolean hasVibrator;
    private PrefsCallbacksManager callbacksManager;

    public PreferencesHelper(Context context)
    {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        hasVibrator = vibrator.hasVibrator();
    }

    public boolean canRateApp()
    {
        if (null == canRateApp) {
            canRateApp = sharedPreferences.getBoolean(
                    context.getString(R.string.pref_can_rate_app),
                    true
            );
        }
        return canRateApp;
    }

    public void setCanRateApp(boolean canRateApp)
    {
        this.canRateApp = canRateApp;

        sharedPreferences.edit()
                .putBoolean(
                        context.getString(R.string.pref_can_rate_app),
                        canRateApp
                ).apply();
    }

    public int getNumberTimesAppWasOpened()
    {
        if (null == numberTimesAppWasOpened) {
            numberTimesAppWasOpened = sharedPreferences.getInt(
                    context.getString(R.string.pref_number_times_app_opened),
                    0
            );
        }
        return numberTimesAppWasOpened;
    }

    public void increaseNumberTimesAppWasOpened()
    {
        this.numberTimesAppWasOpened = getNumberTimesAppWasOpened() + 1;

        sharedPreferences.edit()
                .putInt(
                        context.getString(R.string.pref_number_times_app_opened),
                        numberTimesAppWasOpened
                ).apply();
    }

    public PlaylistData getPlaylist()
    {
        String serialized = sharedPreferences.getString(context.getString(R.string.pref_current_playlist), null);
        if (null != serialized) {
            Gson gson = new Gson();
            return gson.fromJson(serialized, PlaylistData.class);
        } else
            return null;
    }

    public void setPlaylist(PlaylistData playlistData)
    {
        Gson gson = new Gson();
        String json = gson.toJson(playlistData);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(context.getString(R.string.pref_current_playlist), json);
        prefsEditor.apply();
    }

    public void setListViewMode(boolean isListMode)
    {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(context.getString(R.string.pref_current_ui_view_mode), isListMode);
        prefsEditor.apply();
        isListViewMode = isListMode;
    }

    public boolean getListViewMode()
    {
        if (null == isListViewMode) {
            isListViewMode = sharedPreferences.getBoolean(context.getString(R.string.pref_current_ui_view_mode), false);
        }
        return isListViewMode;
    }

    public UITypes getUIType()
    {
        if (null == currentUIType) {
            int indexPosition = sharedPreferences.getInt(
                    context.getString(R.string.pref_current_ui_type),
                    UITypes.NEON.ordinal() //Default UI in case anything goes wrong.
            );
            currentUIType = UITypes.values()[indexPosition];
        }
        return currentUIType;
    }

    public void setUIType(UITypes uiType)
    {
        currentUIType = uiType;
        sharedPreferences.edit()
                .putInt(
                        context.getString(R.string.pref_current_ui_type),
                        uiType.ordinal()
                ).apply();
    }

    public boolean isHapticFeedback()
    {
        if (null == isHapticFeedback) {
            isHapticFeedback = sharedPreferences.getBoolean(
                                       context.getString(R.string.pref_haptic_feedback),
                                       true
                               );
        }
        return hasVibrator;
    }

    public void toggleHapticFeedback()
    {
        sharedPreferences.edit()
                         .putBoolean(
                               context.getString(R.string.pref_haptic_feedback),
                               !isHapticFeedback()
                         ).apply();
    }

    public void setCallbacksManager(PrefsCallbacksManager callbacksManager)
    {
        this.callbacksManager = callbacksManager;
    }

    public void registerPrefsChangedListener()
    {
        sharedPreferences.registerOnSharedPreferenceChangeListener(callbacksManager);
    }

    public void unRegisterPrefsChangedListener()
    {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(callbacksManager);
    }
}
