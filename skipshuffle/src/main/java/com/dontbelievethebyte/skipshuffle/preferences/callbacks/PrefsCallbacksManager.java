package com.dontbelievethebyte.skipshuffle.preferences.callbacks;

import android.content.Context;
import android.content.SharedPreferences;

import com.dontbelievethebyte.skipshuffle.R;

import java.util.HashSet;
import java.util.Set;

public class PrefsCallbacksManager implements SharedPreferences.OnSharedPreferenceChangeListener{



    public static interface HapticFeedBackChangedCallback {
        public void onHapticFeedBackChanged();
    }

    public static interface PlaylistChangedCallback {
        public void onPlaylistChange();
    }

    public static interface ThemeChangedCallback {
        public void onThemeChanged();
    }

    public static interface ViewModeChangedCallback {
        public void onViewModeChanged();
    }

    private Context context;
    private Set<ThemeChangedCallback> themeChangedCallbacks;
    private Set<HapticFeedBackChangedCallback> hapticFeedbackChangedCallbacks;
    private Set<PlaylistChangedCallback> playlistChangedCallbacks;
    private Set<ViewModeChangedCallback> viewModeChangedCallbacks;

    public PrefsCallbacksManager(Context context)
    {
        this.context = context;
        themeChangedCallbacks = new HashSet<ThemeChangedCallback>();
        hapticFeedbackChangedCallbacks = new HashSet<HapticFeedBackChangedCallback>();
        playlistChangedCallbacks = new HashSet<PlaylistChangedCallback>();
        viewModeChangedCallbacks = new HashSet<ViewModeChangedCallback>();
        registerCallBack(context);
    }

    private void registerCallBack(Context context)
    {
        registerHapticFeedBackChanged(context);
        registerPlaylistChanged(context);
        registerThemeChanged(context);
        registerViewModeChanged(context);
    }

    private void registerHapticFeedBackChanged(Context context)
    {
        if (context instanceof HapticFeedBackChangedCallback)
            hapticFeedbackChangedCallbacks.add((HapticFeedBackChangedCallback) context);
    }

    private void registerThemeChanged(Context context)
    {
        if (context instanceof ThemeChangedCallback)
            themeChangedCallbacks.add((ThemeChangedCallback) context);
    }

    private void registerPlaylistChanged(Context context)
    {
        if (context instanceof PlaylistChangedCallback) {
            playlistChangedCallbacks.add(
                    (PlaylistChangedCallback) context
            );
        }
    }

    private void registerViewModeChanged(Context context)
    {
        if (context instanceof ViewModeChangedCallback)
            viewModeChangedCallbacks.add((ViewModeChangedCallback) context);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefsKey)
    {
        handleChangedKey(prefsKey);
    }

    public void handleChangedKey(String prefsKey)
    {
        if(context.getString(R.string.pref_current_ui_type).equals(prefsKey))
            onThemeChanged();
        else if (context.getString(R.string.pref_haptic_feedback).equals(prefsKey))
            onHapticFeedBackChanged();
        else if (context.getString(R.string.pref_current_playlist_id).equals(prefsKey))
            onPlaylistChanged();
        else if (context.getString(R.string.pref_current_ui_view_mode).equals(prefsKey))
            onViewModeChanged();
    }

    public void onHapticFeedBackChanged()
    {
        for(HapticFeedBackChangedCallback hapticFeedBackChangedCallback : hapticFeedbackChangedCallbacks) {
            hapticFeedBackChangedCallback.onHapticFeedBackChanged();
        }
    }

    public void onThemeChanged()
    {
        for(ThemeChangedCallback themeChangedCallback : themeChangedCallbacks) {
            themeChangedCallback.onThemeChanged();
        }
    }

    public void onPlaylistChanged()
    {
        for(PlaylistChangedCallback playlistChangedCallback : playlistChangedCallbacks) {
            playlistChangedCallback.onPlaylistChange();
        }
    }

    public void onViewModeChanged()
    {
        for(ViewModeChangedCallback viewModeChangedCallback : viewModeChangedCallbacks) {
            viewModeChangedCallback.onViewModeChanged();
        }
    }
}
