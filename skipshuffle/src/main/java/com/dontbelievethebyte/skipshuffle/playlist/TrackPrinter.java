/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.playlist;

import android.content.Context;
import android.provider.MediaStore;

import com.dontbelievethebyte.skipshuffle.R;

public class TrackPrinter {

    private Context context;

    public TrackPrinter(Context context)
    {
        this.context = context;
    }

    public String printTitle(Track track)
    {
        if (null == track)
            return "";
        else {
            if (null == track.getTitle() || MediaStore.UNKNOWN_STRING.equals(track.getTitle()) ) {
                return (null == track.getPath()) ?
                        context.getString(R.string.meta_data_unknown_title) :
                        track.getPath().substring(track.getPath().lastIndexOf("/") + 1);
            }
            return track.getTitle();
        }
    }

    public String printArtist(Track track)
    {
        if (null == track)
            return "";
        else {
            if (null == track.getArtist() || MediaStore.UNKNOWN_STRING.equals(track.getArtist()))
                return context.getString(R.string.meta_data_unknown_artist);
            return track.getArtist();
        }
    }

    public String printAlbum(Track track)
    {
        if (null == track)
            return "";
        else {
            if (null == track.getAlbum() || MediaStore.UNKNOWN_STRING.equals(track.getAlbum()))
                return context.getString(R.string.meta_data_unknown);
            return track.getAlbum();
        }
    }

    public String printDuration(Track track)
    {
        if (null == track)
            return "";
        else {
            if (null == track.getDuration() || MediaStore.UNKNOWN_STRING.equals(track.getDuration()))
                return context.getString(R.string.meta_data_unknown);
            return track.getDuration();
        }
    }

    public String printFullReport(Track track)
    {
        String report = context.getString(R.string.meta_data_title) + ": ";
        report += printTitle(track) + "\n";
        report += context.getString(R.string.meta_data_artist) + ": ";
        report += printArtist(track) + "\n";
        report += context.getString(R.string.meta_data_album) + ": ";
        report += printAlbum(track) + "\n";
        report += context.getString(R.string.meta_data_duration) + ": ";
        report += printDuration(track);
        return report;
    }
}
