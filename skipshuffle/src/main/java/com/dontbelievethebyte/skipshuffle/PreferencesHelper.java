package com.dontbelievethebyte.skipshuffle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PreferencesHelper {

    private static final String TAG = "SkipShufflePrefsHelper";

    private boolean hapticFeedback;
    private Integer currentPlaylist;
    private Integer currentPlaylistPosition;
    private Integer currentUIType;
    private String[] directories;
    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private List<PreferenceChangedCallback> preferenceChangeCallbacks;


    public PreferencesHelper(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferenceChangeCallbacks = new ArrayList<PreferenceChangedCallback>();
    }

    public boolean isHapticFeedback() {
        return hapticFeedback;
    }

    public void hapticFeedbackToggle(){
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0L, 500L, 110L, 500L, 110L, 450L, 110L, 200L, 110L, 170L, 40L, 450L, 110L, 200L, 110L, 170L, 40L, 500L};

        if(hapticFeedback && vibrator.hasVibrator()){
            setHapticFeedback(false);
            Toast.makeText(context, R.string.haptic_feedback_off, Toast.LENGTH_SHORT).show();
        } else {
            setHapticFeedback(true);
            if(vibrator.hasVibrator()){
                vibrator.vibrate(pattern, -1);
            } else {
                Toast.makeText(context, R.string.haptic_feedback_not_available, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setHapticFeedback(boolean hapticFeedback) {
        sharedPreferences.edit().putBoolean(context.getString(R.string.pref_haptic_feedback), true).apply();
        this.hapticFeedback = hapticFeedback;
    }

    public String[] getMediaDirectories() {
        if(null == directories){
            String directoriesString = sharedPreferences.getString(
                    context.getString(R.string.pref_media_directories),
                    Environment.getExternalStorageDirectory().getAbsolutePath()
            );
            directories = directoriesString.split(context.getString(R.string.pref_media_directories_separator));
            //Cleanup the last path because it won't split.
            directories[directories.length-1] = directories[directories.length-1].replace(
                    context.getString(R.string.pref_media_directories_separator),
                    "");
        }
        return directories;
    }

    public void setMediaDirectories(String[] newDirectories) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String directory : newDirectories){
            stringBuilder.append(directory).append(context.getString(R.string.pref_media_directories_separator));
        }
        sharedPreferences.edit().putString(context.getString(R.string.pref_media_directories), stringBuilder.toString()).apply();
        directories = newDirectories;
    }
    public void setLastPlaylist(int id){
        currentPlaylist = id;
        sharedPreferences.edit().putLong(context.getString(R.string.pref_current_playlist_id), currentPlaylist).apply();
    }

    public Integer getLastPlaylist(){
        if(null == currentPlaylist){
            currentPlaylist = sharedPreferences.getInt(context.getString(R.string.pref_current_playlist_id), 0);
        }
        return currentPlaylist;
    }
    public Integer getLastPlaylistPosition(){
        if(null == currentPlaylistPosition){
            currentPlaylistPosition = sharedPreferences.getInt(context.getString(R.string.pref_current_playlist_position), 0);
        }
        return currentPlaylistPosition;
    }
    public void setLastPlaylistPosition(int position){
        currentPlaylistPosition = position;
        sharedPreferences.edit().putInt(context.getString(R.string.pref_current_playlist_position), currentPlaylistPosition).apply();
    }

    public Integer getUIType(){
        if(null == currentUIType){
            currentPlaylistPosition = sharedPreferences.getInt(context.getString(R.string.pref_current_ui_type), 2);
        }
        return currentPlaylistPosition;
    }
    public void setUIType(int type){
        currentUIType = type;
        sharedPreferences.edit().putInt(context.getString(R.string.pref_current_ui_type), currentUIType).apply();
    }

    public void registerPrefsChangedListener(){
        if(null == preferenceChangeListener){
            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String prefsKey) {
                    onPrefsChangedCallback(prefsKey);
                }
            };
            sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        }
    }

    public void unRegisterPrefsChangedListener(){
        if(null != preferenceChangeListener){
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
        }
    }

    public void registerCallBack(PreferenceChangedCallback preferenceChangeCallback){
        preferenceChangeCallbacks.add(preferenceChangeCallback);
    }

    public void onPrefsChangedCallback(String prefsKey){
        for(PreferenceChangedCallback preferenceChangeCallback : preferenceChangeCallbacks) {
            preferenceChangeCallback.preferenceChangedCallback(prefsKey);
        }
    }
}
