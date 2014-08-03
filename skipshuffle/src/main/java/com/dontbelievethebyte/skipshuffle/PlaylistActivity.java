package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;

public class PlaylistActivity extends Activity {

    private static final String TAG = "SkipShufflePlaylist";

    private RandomPlaylist playlist;
    private Integer currentPlaylistId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        currentPlaylistId = sharedPreferences.getInt(getString(R.string.pref_current_playlist_id), 1);
        currentPlaylistId = 1;
        playlist = new RandomPlaylist(currentPlaylistId, new DbHandler(getApplicationContext()));
        populate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void populate(){
        playlist.getAllTracks();
        Track first = playlist.getFirst();
        Log.d(TAG, first.getPath());
    }
}
