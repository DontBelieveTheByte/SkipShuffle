package com.dontbelievethebyte.skipshuffle.ui.playlistselector;

import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlaylistSelectorActivity;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public class NeonPlaylistSelectorUI extends PlaylistSelectorUI {

    public NeonPlaylistSelectorUI(PlaylistSelectorActivity playlistSelectorActivity)
    {
        super(playlistSelectorActivity, UIFactory.NEON);
        playlistSelectorActivity.setContentView(R.layout.common_activity_playlist);
        playlistPlayBtn = (ImageButton) playlistSelectorActivity.findViewById(R.id.playlist_layout_play);
        playlistShuffleBtn = (ImageButton) playlistSelectorActivity.findViewById(R.id.playlist_layout_shuffle);
        playlistSkipBtn = (ImageButton) playlistSelectorActivity.findViewById(R.id.playlist_layout_skip);
        playlistPrevBtn = (ImageButton) playlistSelectorActivity.findViewById(R.id.playlist_layout_prev);
    }
}
