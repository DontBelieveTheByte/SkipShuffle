package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PlaylistActivity extends Activity {
    private ListView playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        playlist = (ListView) findViewById(R.id.playlist);
        String[] tracks = getResources().getStringArray(R.array.tracks);
        ArrayAdapter<String> playlistAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tracks);
        playlist.setAdapter(playlistAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
