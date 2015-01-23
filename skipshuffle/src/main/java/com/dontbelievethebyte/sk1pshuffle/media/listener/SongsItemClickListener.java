/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.listener;

import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;

import com.dontbelievethebyte.sk1pshuffle.media.adapters.SongsAdapter;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.Interface.PlaylistBuilderInterface;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.PlaylistData;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.exception.PlaylistBuildFailsException;
import com.dontbelievethebyte.sk1pshuffle.utilities.LogUtil;

import java.util.ArrayList;

public class SongsItemClickListener implements AdapterView.OnItemClickListener{

    PlaylistBuilderInterface playlistBuilderInterface;

    public SongsItemClickListener(PlaylistBuilderInterface playlistBuilderInterface)
    {
        this.playlistBuilderInterface = playlistBuilderInterface;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        LogUtil.writeDebug("Position : " + Integer.toString(position));
        try {
            SongsAdapter songsAdapter = (SongsAdapter) adapterView.getAdapter();
            Cursor cursor = songsAdapter.getCursor();
            PlaylistData playlistData = buildPlaylist(cursor, position);
            playlistBuilderInterface.setPlaylist(playlistData);
        } catch (PlaylistBuildFailsException e) {
            playlistBuilderInterface.handleBuildPlaylistFailsException(e);
        }
    }

    private PlaylistData buildPlaylist(Cursor cursor, int  position) throws PlaylistBuildFailsException
    {
        PlaylistData playlistData = new PlaylistData();
        playlistData.trackIds = new ArrayList<>();
        playlistData.currentPosition = position;
        while (cursor.moveToNext()) {
            playlistData.trackIds.add(
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
            );
        }
        return playlistData;
    }
}

