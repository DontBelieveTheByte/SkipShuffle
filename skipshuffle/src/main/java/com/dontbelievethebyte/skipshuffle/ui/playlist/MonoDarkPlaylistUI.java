package com.dontbelievethebyte.skipshuffle.ui.playlist;

import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public class MonoDarkPlaylistUI extends PlaylistUI {

    public MonoDarkPlaylistUI(PlaylistActivity playlistActivity)
    {
        super(playlistActivity, UIFactory.MONO_DARK);
        playlistActivity.setContentView(R.layout.mono_dark_activity_playlist);
        playlistPlayBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_play);
        playlistShuffleBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_shuffle);
        playlistSkipBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_skip);
        playlistPrevBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_prev);
    }
}
