package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.List;

public class PlaylistActivity extends Activity {

    private static final String TAG = "SkipShufflePlaylist";

    private RandomPlaylist playlist;
    private PreferencesHelper preferencesHelper;
    private List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        playlist = new RandomPlaylist(
                preferencesHelper.getCurrentPlaylist(),
                new DbHandler(getApplicationContext())
        );
        populate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void populate(){

        tracks = playlist.getAllTracks();
        List<Long> list = playlist.getList();
        Log.d(TAG, list.toString());
    }
}
