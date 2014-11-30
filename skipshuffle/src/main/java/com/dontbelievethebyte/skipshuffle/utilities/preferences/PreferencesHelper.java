/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.utilities.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.playlist.PlaylistData;
import com.dontbelievethebyte.skipshuffle.ui.mapper.types.UITypes;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.callbacks.PrefsCallbacksManager;
import com.google.gson.Gson;

public class PreferencesHelper {

    private Context context;
    private Boolean isHapticFeedback;
    private Integer currentUIType;
    private SharedPreferences sharedPreferences;
    private Boolean isListViewMode;
    private PrefsCallbacksManager callbacksManager;

    public PreferencesHelper(Context context)
    {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        callbacksManager = new PrefsCallbacksManager(context);
    }

    public PlaylistData getLastPlaylist()
    {
        String serialized = sharedPreferences.getString(context.getString(R.string.pref_current_playlist_id), null);
        if (null != serialized) {
            Gson gson = new Gson();
            return gson.fromJson(serialized, PlaylistData.class);
        } else
            return null;
    }

    public void setLastPlaylist(PlaylistData playlistData)
    {
        Gson gson = new Gson();
        String json = gson.toJson(playlistData);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(context.getString(R.string.pref_current_playlist_id), json);
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

    public Integer getUIType()
    {
        if (null == currentUIType) {
            currentUIType = sharedPreferences.getInt(
                    context.getString(R.string.pref_current_ui_type),
                    UITypes.NEON //Default UI in case anything goes wrong.
            );
        }
        return currentUIType;
    }

    public void setUIType(int UIType)
    {
        currentUIType = UIType;
        sharedPreferences.edit()
                .putInt(
                        context.getString(R.string.pref_current_ui_type),
                        currentUIType
                ).apply();
    }

    public boolean isHapticFeedback()
    {
        if (null == isHapticFeedback) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

            isHapticFeedback = vibrator.hasVibrator() &&
                               sharedPreferences.getBoolean(
                                       context.getString(R.string.pref_haptic_feedback),
                                       false
                               );
        }
        return isHapticFeedback;
    }

    public void setHapticFeedback(boolean isHapticFeedback)
    {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        long[] pattern = {
                0L,
                500L,
                110L,
                500L,
                110L,
                450L,
                110L,
                200L,
                110L,
                170L,
                40L,
                450L,
                110L,
                200L,
                110L,
                170L,
                40L,
                500L
        };

        if (vibrator.hasVibrator() && isHapticFeedback) {

            vibrator.vibrate(pattern, -1);
            this.isHapticFeedback = true;
        } else {
            this.isHapticFeedback = false;
        }
        sharedPreferences.edit()
                         .putBoolean(
                               context.getString(R.string.pref_haptic_feedback),
                               this.isHapticFeedback
                         ).apply();
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
