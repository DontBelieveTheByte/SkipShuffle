package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

public class PlaylistActivity extends Activity {

    private ImageButton playlistPlay;
    private ImageButton playlistPrev;
    private ImageButton playlistSkip;
    private ImageButton playlistShuffle;

    private static final String TAG = "SkipShufflePlaylist";

    private PlaylistInterface playlist;
    private PreferencesHelper preferencesHelper;
    private List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        populate();

        playlistPlay = (ImageButton) findViewById(R.id.playlist_layout_play);
        playlistShuffle = (ImageButton) findViewById(R.id.playlist_layout_shuffle);
        playlistSkip = (ImageButton) findViewById(R.id.playlist_layout_skip);
        playlistPrev = (ImageButton) findViewById(R.id.playlist_layout_prev);

        playlistPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("YOOO", "WAS CLICKED");
            }
        });

        playlistPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("YOOO", "WAS CLICKED");
            }
        });

        playlistSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("YOOO", "WAS CLICKED");
            }
        });

        playlistShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("YOOO", "WAS CLICKED");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void populate(){
        preferencesHelper = new PreferencesHelper(getApplicationContext());
        playlist = new RandomPlaylist(
                1L,
                new DbHandler(getApplicationContext())
        );

        ListView listView = (ListView) findViewById(R.id.current_playlist);
        listView.setAdapter(new PlaylistAdapter(getApplicationContext(), playlist));
    }
}
