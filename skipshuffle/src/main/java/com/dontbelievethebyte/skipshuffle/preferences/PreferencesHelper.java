package com.dontbelievethebyte.skipshuffle.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.callbacks.HapticFeedBackChangedCallback;
import com.dontbelievethebyte.skipshuffle.callbacks.PlaylistChangedCallback;
import com.dontbelievethebyte.skipshuffle.callbacks.ThemeChangedCallback;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.ui.UITypes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PreferencesHelper {

    private static final String TAG = "SkipShufflePrefsHelper";

    private Boolean isHapticFeedback;
    private Long currentPlaylist;
    private Integer currentPlaylistPosition;
    private Integer currentUIType;
    private ArrayList<File> directories;
    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private Set<ThemeChangedCallback> themeChangedCallbacks;
    private Set<HapticFeedBackChangedCallback> hapticFeedbackChangedCallbacks;
    private Set<PlaylistChangedCallback> playlistChangedCallbacks;

    public PreferencesHelper(Context context)
    {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        themeChangedCallbacks = new HashSet<ThemeChangedCallback>();
        hapticFeedbackChangedCallbacks = new HashSet<HapticFeedBackChangedCallback>();
        playlistChangedCallbacks = new HashSet<PlaylistChangedCallback>();
    }

    public Long getLastPlaylist()
    {
        if (null == currentPlaylist) {
            currentPlaylist = sharedPreferences.getLong(
                    context.getString(R.string.pref_current_playlist_id),
                    0
            );
        }
        return currentPlaylist;
    }

    public Integer getLastPlaylistPosition()
    {
        if (null == currentPlaylistPosition) {
            currentPlaylistPosition = sharedPreferences.getInt(
                    context.getString(R.string.pref_current_playlist_position),
                    0
            );
        }
        return currentPlaylistPosition;
    }

    public ArrayList<File> getMediaDirectories()
    {
        if (null == directories) {
            directories = new ArrayList<File>();

            String directoriesString = sharedPreferences.getString(
                    context.getString(R.string.pref_media_directories),
                    null
            );
            if (null != directoriesString) {
                ArrayList <String> directoryBuilder = new ArrayList<String>();
                directoryBuilder.addAll(
                        Arrays.asList(
                                directoriesString.split(
                                        context.getString(R.string.pref_media_directories_separator)
                                )
                        )
                );
                //Cleanup the last path because it won't split.
                directoryBuilder.set(
                        directoryBuilder.size()-1,
                        directoryBuilder.get(
                                directoryBuilder.size() -1).replace(
                                context.getString(R.string.pref_media_directories_separator),
                                ""
                        )
                );
                for (String directoryName : directoryBuilder) {
                    directories.add(new File(directoryName));
                }
            }
        }
        return directories;
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

    public void registerCallBack(Context context)
    {
        registerHapticFeedBackChanged(context);
        registerPlaylistChanged(context);
        registerThemeChanged(context);
    }

    public void registerPrefsChangedListener()
    {
        if (null == preferenceChangeListener) {
            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefsKey) {
                    handleChangedKey(prefsKey);
                }
            };
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    private void handleChangedKey(String prefsKey)
    {
        if(context.getString(R.string.pref_current_ui_type).equals(prefsKey))
            onThemeChanged();
        else if (context.getString(R.string.pref_haptic_feedback).equals(prefsKey))
            onHaptikFeedBackChanged();
        else if (context.getString(R.string.pref_current_playlist_id).equals(prefsKey))
            onPlaylistChanged();
    }

    public void onHaptikFeedBackChanged()
    {
        for(HapticFeedBackChangedCallback hapticFeedBackChangedCallback : hapticFeedbackChangedCallbacks) {
            hapticFeedBackChangedCallback.onHapticFeedBackChanged(isHapticFeedback);
        }
    }

    public void onThemeChanged()
    {
        for(ThemeChangedCallback themeChangedCallback : themeChangedCallbacks) {
            themeChangedCallback.onThemeChanged(getUIType());
        }
    }

    public void onPlaylistChanged()
    {
        for(PlaylistChangedCallback playlistChangedCallback : playlistChangedCallbacks) {
            playlistChangedCallback.onPlaylistChange(currentPlaylist);
        }
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

    public void setLastPlaylist(long lastPlaylistId)
    {
        currentPlaylist = lastPlaylistId;
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putLong(
            context.getString(R.string.pref_current_playlist_id),
            currentPlaylist
        );
        prefsEditor.apply();
    }

    public void setLastPlaylistPosition(int lastPlaylistPosition)
    {
        currentPlaylistPosition = lastPlaylistPosition;
        sharedPreferences.edit()
                .putInt(
                        context.getString(R.string.pref_current_playlist_position),
                        currentPlaylistPosition
                ).apply();
    }

    public void setMediaDirectories(ArrayList<File> newDirectories)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(File directory : newDirectories){
            stringBuilder.append(directory)
                         .append(
                             context.getString(R.string.pref_media_directories_separator)
                         );
        }
        sharedPreferences.edit()
                .putString(
                        context.getString(R.string.pref_media_directories),
                        stringBuilder.toString()
                ).apply();
        directories = newDirectories;
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

    public void unRegisterPrefsChangedListener()
    {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    private void registerHapticFeedBackChanged(Context context)
    {
        if (context instanceof HapticFeedBackChangedCallback) {
            hapticFeedbackChangedCallbacks.add(
                    (HapticFeedBackChangedCallback) context
            );
        }
    }

    private void registerThemeChanged(Context context)
    {
        if (context instanceof ThemeChangedCallback) {
            themeChangedCallbacks.add(
                    (ThemeChangedCallback) context
            );
        }
    }

    private void registerPlaylistChanged(Context context)
    {
        if (context instanceof PlaylistChangedCallback) {
            playlistChangedCallbacks.add(
                    (PlaylistChangedCallback) context
            );
        }
    }

    public void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        setLastPlaylist(0);
        setLastPlaylistPosition(0);
    }
}
