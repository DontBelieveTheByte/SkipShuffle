package com.dontbelievethebyte.skipshuffle.ui.playlistselector;

import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistSelectorActivity;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public class MonoLightPlaylistSelectorUI extends PlaylistSelectorUI {

    public MonoLightPlaylistSelectorUI(PlaylistSelectorActivity playlistSelectorActivity)
    {
        super(playlistSelectorActivity, UIFactory.MONO_LIGHT);
        playlistSelectorActivity.setContentView(R.layout.mono_light_activity_playlist);
        playlistPlayBtn = (ImageButton) playlistSelectorActivity.findViewById(R.id.playlist_layout_play);
        playlistShuffleBtn = (ImageButton) playlistSelectorActivity.findViewById(R.id.playlist_layout_shuffle);
        playlistSkipBtn = (ImageButton) playlistSelectorActivity.findViewById(R.id.playlist_layout_skip);
        playlistPrevBtn = (ImageButton) playlistSelectorActivity.findViewById(R.id.playlist_layout_prev);
    }
}