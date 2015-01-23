/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.playlist.track;

import android.content.Context;
import android.provider.MediaStore;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.media.playlist.track.Track;

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

    public String printGenre(Track track)
    {
        if (null == track)
            return "";
        else {
            if (null == track.getGenre() || MediaStore.UNKNOWN_STRING.equals(track.getGenre()))
                return context.getString(R.string.meta_data_unknown);
            return track.getGenre();
        }
    }

    public String printDuration(Track track)
    {
        if (null == track)
            return "";
        else {
            if (null == track.getDuration())
                return context.getString(R.string.meta_data_unknown);
            else {
                String duration = "";

                long hours = (track.getDuration() / 3600000);
                long remainingMinutes = (track.getDuration() - (hours * 3600000)) / 60000;
                String minutes = String.valueOf(remainingMinutes);
                if (minutes.equals(0))
                    minutes = "00";
                long remainingSeconds = (track.getDuration() - (hours * 3600000) - (remainingMinutes * 60000));
                String seconds = String.valueOf(remainingSeconds);
                seconds = (seconds.length() < 2) ? seconds = "00" : seconds.substring(0, 2);
                return (hours > 0) ? hours + ":" + minutes + ":" + seconds : minutes + ":" + seconds;
            }
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
