/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.listener;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.playlist.exception.PlaylistBuildFailsException;
import com.dontbelievethebyte.sk1pshuffle.playlist.Interface.PlaylistBuilderInterface;
import com.dontbelievethebyte.sk1pshuffle.playlist.PlaylistData;

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
        Log.d(BaseActivity.TAG, "Position : " + Integer.toString(position));
            try {
                PlaylistData playlistData = buildPlaylist(position);
                playlistBuilderInterface.setPlaylist(playlistData);
            } catch (PlaylistBuildFailsException e) {
                playlistBuilderInterface.handleBuildPlaylistFailsException(e);
            }
        }
        private PlaylistData buildPlaylist(int position) throws PlaylistBuildFailsException
        {
            PlaylistData playlistData = new PlaylistData();
            playlistData.trackIds = new ArrayList<String>();
            playlistData.currentPosition = position;
//            while (cursor.moveToNext()) {
//                playlistData.trackIds.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
//            }
            return playlistData;
        }
    }

