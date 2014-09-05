package com.dontbelievethebyte.skipshuffle.ui.playlist;

import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistActivity;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public class MonoLightPlaylistUI extends PlaylistUI {

    public MonoLightPlaylistUI(PlaylistActivity playlistActivity)
    {
        super(playlistActivity, UIFactory.MONO_LIGHT);
        playlistActivity.setContentView(R.layout.mono_light_activity_playlist);
        playlistPlayBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_play);
        playlistShuffleBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_shuffle);
        playlistSkipBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_skip);
        playlistPrevBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_prev);
    }
}
