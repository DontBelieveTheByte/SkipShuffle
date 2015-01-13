/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.mapper;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.ui.mapper.types.UITypes;

public class ColorMapper {
    public static int getBackground(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.background_mono_light;
            case UITypes.MONO_DARK :
                return R.color.background_mono_dark;
            case UITypes.NEON :
                return R.color.background_neon;
            case UITypes.XMAS :
                return R.color.background_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.background_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.background_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.background_shamrock;
            case UITypes.MURICA :
                return R.color.background_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.background_psychedelic;
            case UITypes.RASTA :
                return R.color.background_rasta;
            case UITypes.USSR :
                return R.color.background_ussr;
            case UITypes.TRON :
                return R.color.background_tron;
            case UITypes.RAMEN :
                return R.color.background_ramen;
            case UITypes.RAINBOW :
                return R.color.background_rainbow;
            case UITypes.KOSHER :
                return R.color.background_kosher;
            case UITypes.STAR_WARS :
                return R.color.background_star_wars;
            default:
                return R.color.background_neon;
        }
    }

    public static int getEmptyListText(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.list_empty_text_mono_light;
            case UITypes.MONO_DARK :
                return R.color.list_empty_text_mono_dark;
            case UITypes.NEON :
                return R.color.list_empty_text_neon;
            case UITypes.XMAS :
                return R.color.list_empty_text_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.list_empty_text_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.list_empty_text_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.list_empty_text_shamrock;
            case UITypes.MURICA :
                return R.color.list_empty_text_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.list_empty_text_psychedelic;
            case UITypes.RASTA :
                return R.color.list_empty_text_rasta;
            case UITypes.USSR :
                return R.color.list_empty_text_ussr;
            case UITypes.TRON :
                return R.color.list_empty_text_tron;
            case UITypes.RAMEN :
                return R.color.list_empty_text_ramen;
            case UITypes.RAINBOW :
                return R.color.list_empty_text_rainbow;
            case UITypes.KOSHER :
                return R.color.list_empty_text_kosher;
            case UITypes.STAR_WARS :
                return R.color.list_empty_text_star_wars;
            default:
                return R.color.list_empty_text_neon;
        }
    }

    public static int getListDivider(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.list_divider_mono_light;
            case UITypes.MONO_DARK :
                return R.color.list_divider_mono_dark;
            case UITypes.NEON :
                return R.color.list_divider_neon;
            case UITypes.XMAS :
                return R.color.list_divider_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.list_divider_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.list_divider_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.list_divider_shamrock;
            case UITypes.MURICA :
                return R.color.list_divider_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.list_divider_psychedelic;
            case UITypes.RASTA :
                return R.color.list_divider_rasta;
            case UITypes.USSR :
                return R.color.list_divider_ussr;
            case UITypes.TRON :
                return R.color.list_divider_tron;
            case UITypes.RAMEN :
                return R.color.list_divider_ramen;
            case UITypes.RAINBOW :
                return R.color.list_divider_rainbow;
            case UITypes.KOSHER :
                return R.color.list_divider_kosher;
            case UITypes.STAR_WARS :
                return R.color.list_divider_star_wars;
            default:
                return R.color.list_divider_neon;
        }
    }

    public static int getNavDrawerBackground(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.nav_drawer_background_mono_light;
            case UITypes.MONO_DARK :
                return R.color.nav_drawer_background_mono_dark;
            case UITypes.NEON :
                return R.color.nav_drawer_background_neon;
            case UITypes.XMAS:
                return R.color.nav_drawer_background_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.nav_drawer_background_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.nav_drawer_background_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.nav_drawer_background_shamrock;
            case UITypes.MURICA :
                return R.color.nav_drawer_background_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.nav_drawer_background_psychedelic;
            case UITypes.RASTA :
                return R.color.nav_drawer_background_rasta;
            case UITypes.USSR :
                return R.color.nav_drawer_background_ussr;
            case UITypes.TRON :
                return R.color.nav_drawer_background_tron;
            case UITypes.RAMEN :
                return R.color.nav_drawer_background_ramen;
            case UITypes.RAINBOW :
                return R.color.nav_drawer_background_rainbow;
            case UITypes.KOSHER :
                return R.color.nav_drawer_background_kosher;
            case UITypes.STAR_WARS :
                return R.color.nav_drawer_background_star_wars;
            default:
                return R.color.nav_drawer_background_neon;
        }
    }

    public static int getNavDrawerText(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.nav_drawer_text_mono_light;
            case UITypes.MONO_DARK :
                return R.color.nav_drawer_text_mono_dark;
            case UITypes.NEON :
                return R.color.nav_drawer_text_neon;
            case UITypes.XMAS :
                return R.color.nav_drawer_text_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.nav_drawer_text_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.nav_drawer_text_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.nav_drawer_text_shamrock;
            case UITypes.MURICA :
                return R.color.nav_drawer_text_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.nav_drawer_text_psychedelic;
            case UITypes.RASTA :
                return R.color.nav_drawer_text_rasta;
            case UITypes.USSR :
                return R.color.nav_drawer_text_ussr;
            case UITypes.TRON :
                return R.color.nav_drawer_text_tron;
            case UITypes.RAMEN :
                return R.color.nav_drawer_text_ramen;
            case UITypes.RAINBOW :
                return R.color.nav_drawer_text_rainbow;
            case UITypes.KOSHER :
                return R.color.nav_drawer_text_kosher;
            case UITypes.STAR_WARS :
                return R.color.nav_drawer_text_star_wars;
            default:
                return R.color.nav_drawer_text_neon;
        }
    }

    public static int getSongLabel(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.song_label_mono_light;
            case UITypes.MONO_DARK :
                return R.color.song_label_mono_dark;
            case UITypes.NEON :
                return R.color.song_label_neon;
            case UITypes.XMAS :
                return R.color.song_label_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.song_label_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.song_label_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.song_label_shamrock;
            case UITypes.MURICA :
                return R.color.song_label_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.song_label_psychedelic;
            case UITypes.RASTA :
                return R.color.song_label_rasta;
            case UITypes.USSR :
                return R.color.song_label_ussr;
            case UITypes.TRON :
                return R.color.song_label_tron;
            case UITypes.RAMEN :
                return R.color.song_label_ramen;
            case UITypes.RAINBOW :
                return R.color.song_label_rainbow;
            case UITypes.KOSHER :
                return R.color.song_label_kosher;
            case UITypes.STAR_WARS :
                return R.color.song_label_star_wars;
            default:
                return R.color.song_label_neon;
        }
    }

    public static int getPlaylistTitle(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.playlist_item_track_title_mono_light;
            case UITypes.MONO_DARK :
                return R.color.playlist_item_track_title_mono_dark;
            case UITypes.NEON :
                return R.color.playlist_item_track_title_neon;
            case UITypes.XMAS :
                return R.color.playlist_item_track_title_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.playlist_item_track_title_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.playlist_item_track_title_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.playlist_item_track_title_shamrock;
            case UITypes.MURICA :
                return R.color.playlist_item_track_title_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.playlist_item_track_title_psychedelic;
            case UITypes.RASTA :
                return R.color.playlist_item_track_title_rasta;
            case UITypes.USSR :
                return R.color.playlist_item_track_title_ussr;
            case UITypes.TRON :
                return R.color.playlist_item_track_title_tron;
            case UITypes.RAMEN :
                return R.color.playlist_item_track_title_ramen;
            case UITypes.RAINBOW :
                return R.color.playlist_item_track_title_rainbow;
            case UITypes.KOSHER :
                return R.color.playlist_item_track_title_kosher;
            case UITypes.STAR_WARS :
                return R.color.playlist_item_track_title_star_wars;
            default:
                return R.color.playlist_item_track_title_neon;
        }
    }


    public static int getPlaylistArtist(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.playlist_item_track_artist_mono_light;
            case UITypes.MONO_DARK :
                return R.color.playlist_item_track_artist_mono_dark;
            case UITypes.NEON :
                return R.color.playlist_item_track_artist_neon;
            case UITypes.XMAS :
                return R.color.playlist_item_track_artist_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.playlist_item_track_artist_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.playlist_item_track_artist_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.playlist_item_track_artist_shamrock;
            case UITypes.MURICA :
                return R.color.playlist_item_track_artist_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.playlist_item_track_artist_psychedelic;
            case UITypes.RASTA :
                return R.color.playlist_item_track_artist_rasta;
            case UITypes.USSR :
                return R.color.playlist_item_track_artist_ussr;
            case UITypes.TRON :
                return R.color.playlist_item_track_artist_tron;
            case UITypes.RAMEN :
                return R.color.playlist_item_track_artist_ramen;
            case UITypes.RAINBOW :
                return R.color.playlist_item_track_artist_rainbow;
            case UITypes.KOSHER :
                return R.color.playlist_item_track_artist_kosher;
            case UITypes.STAR_WARS :
                return R.color.playlist_item_track_artist_star_wars;
            default:
                return R.color.playlist_item_track_artist_neon;
        }
    }

    public static int getPlayButton(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.play_button_mono_light;
            case UITypes.MONO_DARK :
                return R.color.play_button_mono_dark;
            case UITypes.NEON :
                return R.color.play_button_neon;
            case UITypes.XMAS :
                return R.color.play_button_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.play_button_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.play_button_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.play_button_shamrock;
            case UITypes.MURICA :
                return R.color.play_button_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.play_button_psychedelic;
            case UITypes.RASTA :
                return R.color.play_button_rasta;
            case UITypes.USSR :
                return R.color.play_button_ussr;
            case UITypes.TRON :
                return R.color.play_button_tron;
            case UITypes.RAMEN :
                return R.color.play_button_ramen;
            case UITypes.RAINBOW :
                return R.color.play_button_rainbow;
            case UITypes.KOSHER :
                return R.color.play_button_kosher;
            case UITypes.STAR_WARS :
                return R.color.play_button_star_wars;
            default:
                return R.color.play_button_neon;
        }
    }

    public static int getPauseButton(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.pause_button_mono_light;
            case UITypes.MONO_DARK :
                return R.color.pause_button_mono_dark;
            case UITypes.NEON :
                return R.color.pause_button_neon;
            case UITypes.XMAS:
                return R.color.pause_button_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.pause_button_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.pause_button_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.pause_button_shamrock;
            case UITypes.MURICA :
                return R.color.pause_button_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.pause_button_psychedelic;
            case UITypes.RASTA :
                return R.color.pause_button_rasta;
            case UITypes.USSR :
                return R.color.pause_button_ussr;
            case UITypes.TRON :
                return R.color.pause_button_tron;
            case UITypes.RAMEN :
                return R.color.pause_button_ramen;
            case UITypes.RAINBOW :
                return R.color.pause_button_rainbow;
            case UITypes.KOSHER :
                return R.color.pause_button_kosher;
            case UITypes.STAR_WARS :
                return R.color.pause_button_star_wars;
            default:
                return R.color.pause_button_neon;
        }
    }

    public static int getSkipButton(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.skip_button_mono_light;
            case UITypes.MONO_DARK :
                return R.color.skip_button_mono_dark;
            case UITypes.NEON :
                return R.color.skip_button_neon;
            case UITypes.XMAS :
                return R.color.skip_button_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.skip_button_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.skip_button_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.skip_button_shamrock;
            case UITypes.MURICA :
                return R.color.skip_button_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.skip_button_psychedelic;
            case UITypes.RASTA :
                return R.color.skip_button_rasta;
            case UITypes.USSR :
                return R.color.skip_button_ussr;
            case UITypes.TRON :
                return R.color.skip_button_tron;
            case UITypes.RAMEN :
                return R.color.skip_button_ramen;
            case UITypes.RAINBOW :
                return R.color.skip_button_rainbow;
            case UITypes.KOSHER :
                return R.color.skip_button_kosher;
            case UITypes.STAR_WARS :
                return R.color.skip_button_star_wars;
            default:
                return R.color.skip_button_neon;
        }
    }

    public static int getPrevButton(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.prev_button_mono_light;
            case UITypes.MONO_DARK :
                return R.color.prev_button_mono_dark;
            case UITypes.NEON :
                return R.color.prev_button_neon;
            case UITypes.XMAS :
                return R.color.prev_button_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.prev_button_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.prev_button_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.prev_button_shamrock;
            case UITypes.MURICA :
                return R.color.prev_button_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.prev_button_psychedelic;
            case UITypes.RASTA :
                return R.color.prev_button_rasta;
            case UITypes.USSR :
                return R.color.prev_button_ussr;
            case UITypes.TRON :
                return R.color.prev_button_tron;
            case UITypes.RAMEN :
                return R.color.prev_button_ramen;
            case UITypes.RAINBOW :
                return R.color.prev_button_rainbow;
            case UITypes.KOSHER :
                return R.color.prev_button_kosher;
            case UITypes.STAR_WARS :
                return R.color.prev_button_star_wars;
            default:
                return R.color.prev_button_neon;
        }
    }

    public static int getShuffleButton(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.shuffle_button_mono_light;
            case UITypes.MONO_DARK :
                return R.color.shuffle_button_mono_dark;
            case UITypes.NEON :
                return R.color.shuffle_button_neon;
            case UITypes.XMAS :
                return R.color.shuffle_button_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.shuffle_button_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.shuffle_button_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.shuffle_button_shamrock;
            case UITypes.MURICA :
                return R.color.shuffle_button_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.shuffle_button_psychedelic;
            case UITypes.RASTA :
                return R.color.shuffle_button_rasta;
            case UITypes.USSR :
                return R.color.shuffle_button_ussr;
            case UITypes.TRON :
                return R.color.shuffle_button_tron;
            case UITypes.RAMEN :
                return R.color.shuffle_button_ramen;
            case UITypes.RAINBOW :
                return R.color.shuffle_button_rainbow;
            case UITypes.KOSHER :
                return R.color.shuffle_button_kosher;
            case UITypes.STAR_WARS :
                return R.color.shuffle_button_star_wars;
            default:
                return R.color.shuffle_button_neon;
        }
    }

    public static int getPlaylistButton(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.playlist_button_mono_light;
            case UITypes.MONO_DARK :
                return R.color.playlist_button_mono_dark;
            case UITypes.NEON :
                return R.color.playlist_button_neon;
            case UITypes.XMAS:
                return R.color.playlist_button_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.playlist_button_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.playlist_button_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.playlist_button_shamrock;
            case UITypes.MURICA :
                return R.color.playlist_button_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.playlist_button_psychedelic;
            case UITypes.RASTA :
                return R.color.playlist_button_rasta;
            case UITypes.USSR :
                return R.color.playlist_button_ussr;
            case UITypes.TRON :
                return R.color.playlist_button_tron;
            case UITypes.RAMEN :
                return R.color.playlist_button_ramen;
            case UITypes.RAINBOW :
                return R.color.playlist_button_rainbow;
            case UITypes.KOSHER :
                return R.color.playlist_button_kosher;
            case UITypes.STAR_WARS :
                return R.color.playlist_button_star_wars;
            default:
                return R.color.playlist_button_neon;
        }
    }

    public static int getSeekBarProgress(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.seekbar_progress_mono_light;
            case UITypes.MONO_DARK :
                return R.color.seekbar_progress_mono_dark;
            case UITypes.NEON :
                return R.color.seekbar_progress_neon;
            case UITypes.XMAS:
                return R.color.seekbar_progress_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.seekbar_progress_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.seekbar_progress_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.seekbar_progress_shamrock;
            case UITypes.MURICA :
                return R.color.seekbar_progress_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.seekbar_progress_psychedelic;
            case UITypes.RASTA :
                return R.color.seekbar_progress_rasta;
            case UITypes.USSR :
                return R.color.seekbar_progress_ussr;
            case UITypes.TRON :
                return R.color.seekbar_progress_tron;
            case UITypes.RAMEN :
                return R.color.seekbar_progress_ramen;
            case UITypes.RAINBOW :
                return R.color.seekbar_progress_rainbow;
            case UITypes.KOSHER :
                return R.color.seekbar_progress_kosher;
            case UITypes.STAR_WARS :
                return R.color.seekbar_progress_star_wars;
            default:
                return R.color.seekbar_progress_neon;
        }
    }

    public static int getSeekBarThumb(Integer uiType)
    {
        switch (uiType) {
            case UITypes.MONO_LIGHT :
                return R.color.seekbar_thumb_mono_light;
            case UITypes.MONO_DARK :
                return R.color.seekbar_thumb_mono_dark;
            case UITypes.NEON :
                return R.color.seekbar_thumb_neon;
            case UITypes.XMAS:
                return R.color.seekbar_thumb_xmas;
            case UITypes.PINK_PANTHER :
                return R.color.seekbar_thumb_pink_panther;
            case UITypes.JACK_O_LANTERN :
                return R.color.seekbar_thumb_jack_o_lantern;
            case UITypes.SHAMROCK :
                return R.color.seekbar_thumb_shamrock;
            case UITypes.MURICA :
                return R.color.seekbar_thumb_murica;
            case UITypes.PSYCHEDELIC :
                return R.color.seekbar_thumb_psychedelic;
            case UITypes.RASTA :
                return R.color.seekbar_thumb_rasta;
            case UITypes.USSR :
                return R.color.seekbar_thumb_ussr;
            case UITypes.TRON :
                return R.color.seekbar_thumb_tron;
            case UITypes.RAMEN :
                return R.color.seekbar_thumb_ramen;
            case UITypes.RAINBOW :
                return R.color.seekbar_thumb_rainbow;
            case UITypes.KOSHER :
                return R.color.seekbar_thumb_kosher;
            case UITypes.STAR_WARS :
                return R.color.seekbar_thumb_star_wars;
            default:
                return R.color.seekbar_thumb_neon;
        }
    }

}
