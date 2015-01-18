/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.playlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PlaylistData implements Parcelable{

    public int currentPosition = 0;
    public boolean isShuffleOn = false;
    public List<String> trackIds;
    public List<String> shuffledTrackIds;

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags)
    {
        destination.writeInt(currentPosition);
        destination.writeByte((byte) (isShuffleOn ? 1 : 0));
        destination.writeList(trackIds);
        destination.writeList(shuffledTrackIds);
    }

    static final Parcelable.Creator<PlaylistData> CREATOR = new Parcelable.Creator<PlaylistData>()
    {

        @Override
        public PlaylistData createFromParcel(Parcel incoming)
        {
            PlaylistData playlistData = new PlaylistData();
            playlistData.currentPosition = incoming.readInt();
            playlistData.isShuffleOn = (incoming.readByte() != 0);
            playlistData.trackIds = new ArrayList<>();
            incoming.readList(playlistData.trackIds, getClass().getClassLoader());
            playlistData.shuffledTrackIds = new ArrayList<>();
            incoming.readList(playlistData.shuffledTrackIds, getClass().getClassLoader());
            return playlistData;
        }

        @Override
        public PlaylistData[] newArray(int size)
        {
            return new PlaylistData[size];
        }
    };
}