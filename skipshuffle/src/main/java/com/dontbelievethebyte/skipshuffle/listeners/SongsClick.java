/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.listeners;

import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.MusicContentBrowserActivity;
import com.dontbelievethebyte.skipshuffle.adapters.SongsAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

import java.util.ArrayList;

public class SongsClick extends AbstractListClick {

    private boolean isPlaylistSet = false;

    public SongsClick(MusicContentBrowserActivity listActivity)
    {
        super(listActivity);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = listActivity.getMediaPlayer();
            if (!isPlaylistSet) {
                SongsAdapter adapter = (SongsAdapter) listActivity.getAdapter();
                Cursor cursor = adapter.getCursor();
                ArrayList<String> trackIds = new ArrayList<String>();
                while (cursor.moveToNext()){
                    trackIds.add(
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    );
                }
                mediaPlayer.setPlaylist(trackIds);
                isPlaylistSet = true;

            }
            mediaPlayer.doPlay();
            RandomPlaylist playlist = mediaPlayer.getPlaylist();
            if ( (playlist.getPosition() == position) && ((mediaPlayer.isPlaying())) ) {
                ImageView imageView = (ImageView) view.findViewById(R.id.track_image);
                imageView.setImageDrawable(listActivity.ui.player.buttons.drawables.getPause());
                mediaPlayer.doPause();
                listActivity.ui.player.doPause();
            } else {
                mediaPlayer.doPlay(playlist.getPosition());
                listActivity.ui.player.doPlay();
            }
        } catch (NoMediaPlayerException noMediaPlayerException) {
            listActivity.handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            listActivity.handlePlaylistEmptyException(playlistEmptyException);
        }
    }
}
