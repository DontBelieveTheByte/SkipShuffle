package com.dontbelievethebyte.skipshuffle;

import android.graphics.Typeface;
import android.widget.ImageButton;

public class MonoLightPlaylistUI extends PlaylistUI {

    public MonoLightPlaylistUI(PlaylistActivity playlistActivity) {
        super(playlistActivity);
        playlistActivity.setContentView(R.layout.mono_light_activity_playlist);
        playlistPlayBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_play);
        playlistShuffleBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_shuffle);
        playlistSkipBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_skip);
        playlistPrevBtn = (ImageButton) playlistActivity.findViewById(R.id.playlist_layout_prev);
    }

    @Override
    public void doPlay() {

    }

    @Override
    public void doPause() {

    }

    @Override
    public void doSkip() {

    }

    @Override
    public void doPrev() {

    }

    @Override
    public void doShuffle() {

    }

    @Override
    public void reboot() {

    }

    @Override
    public void setSongTitle(String title) {

    }

    @Override
    public Typeface getTypeFace() {
        return null;
    }
}
