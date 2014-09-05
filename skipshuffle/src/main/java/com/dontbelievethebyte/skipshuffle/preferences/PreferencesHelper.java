package com.dontbelievethebyte.skipshuffle.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.callback.PreferenceChangedCallback;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

import java.util.ArrayList;
import java.util.List;

public class PreferencesHelper {

    private static final String TAG = "SkipShufflePrefsHelper";

    private boolean isHapticFeedback;
    private Long currentPlaylist;
    private Integer currentPlaylistPosition;
    private Integer currentUIType;
    private String[] directories;
    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private List<PreferenceChangedCallback> preferenceChangeCallbacks;

    public PreferencesHelper(Context context)
    {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isHapticFeedback()
    {
        return isHapticFeedback;
    }

    public void hapticFeedbackToggle()
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

        if (vibrator.hasVibrator()) {
            if (isHapticFeedback) {
                setHapticFeedback(false);
                Toast.makeText(
                        context,
                        R.string.haptic_feedback_off,
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                setHapticFeedback(true);
                vibrator.vibrate(pattern, -1);
            }
        } else {
            Toast.makeText(
                    context,
                    R.string.haptic_feedback_not_available,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void setHapticFeedback(boolean isHapticFeedback)
    {
        sharedPreferences.edit()
                         .putBoolean(
                                 context.getString(
                                         R.string.pref_haptic_feedback),
                                         true
                         ).apply();
        this.isHapticFeedback = isHapticFeedback;
    }

    public String[] getMediaDirectories()
    {
        if (null == directories) {
            String directoriesString = sharedPreferences.getString(
                    context.getString(R.string.pref_media_directories),
                    Environment.getExternalStorageDirectory().getAbsolutePath()
            );
            directories = directoriesString.split(context.getString(R.string.pref_media_directories_separator));
            //Cleanup the last path because it won't split.
            directories[directories.length-1] = directories[directories.length-1].replace(
                    context.getString(R.string.pref_media_directories_separator),
                    ""
            );
        }
        return directories;
    }

    public void setMediaDirectories(String[] newDirectories)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(String directory : newDirectories){
            stringBuilder.append(directory).append(context.getString(R.string.pref_media_directories_separator));
        }
        sharedPreferences.edit()
                         .putString(
                                 context.getString(R.string.pref_media_directories),
                                 stringBuilder.toString()
                         ).apply();
        directories = newDirectories;
        Log.d(TAG, "NEW DIRS SAVED");
    }

    public void setLastPlaylist(long lastPlaylistId)
    {
        currentPlaylist = lastPlaylistId;
        sharedPreferences.edit()
                         .putLong(
                                 context.getString(R.string.pref_current_playlist_id),
                                 currentPlaylist
                         ).apply();
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
    public void setLastPlaylistPosition(int lastPlaylistPosition)
    {
        currentPlaylistPosition = lastPlaylistPosition;
        sharedPreferences.edit()
                         .putInt(
                                 context.getString(R.string.pref_current_playlist_position),
                                 currentPlaylistPosition
                         ).apply();
    }

    public Integer getUIType()
    {
        if (null == currentUIType) {
            currentUIType = sharedPreferences.getInt(
                    context.getString(R.string.pref_current_ui_type),
                    UIFactory.NEON //Default UI in case anything goes wrong.
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

    public void registerPrefsChangedListener()
    {
        if (null == preferenceChangeListener) {
            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefsKey) {
                    onPrefsChangedCallback(prefsKey);
                }
            };
            preferenceChangeCallbacks = new ArrayList<PreferenceChangedCallback>();
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        Log.d("REG", "PREFS CHANGED REGISTERED!!!!!!!!!!!!");
    }

    public void unRegisterPrefsChangedListener()
    {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    public void registerCallBack(PreferenceChangedCallback preferenceChangeCallback)
    {
        preferenceChangeCallbacks.add(preferenceChangeCallback);
        Log.d("REG", "CALLBACK PREFS CHANGED REGISTERED+++++++++++");
    }

    public void onPrefsChangedCallback(String prefsKey)
    {
        Log.d("REG", "CALLBACK LAUNCHED-------+++++++++++00");
        for(PreferenceChangedCallback preferenceChangeCallback : preferenceChangeCallbacks) {
            preferenceChangeCallback.preferenceChangedCallback(prefsKey);
        }
    }
}
