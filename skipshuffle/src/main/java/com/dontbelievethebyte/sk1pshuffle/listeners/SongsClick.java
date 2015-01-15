/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.listeners;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.adapters.SongsAdapter;
import com.dontbelievethebyte.sk1pshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.sk1pshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;

import java.util.ArrayList;

public class SongsClick implements AdapterView.OnItemClickListener{

    private boolean isPlaylistSet = false;

    public SongsClick(Context listActivity)
    {
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {

    }
}
