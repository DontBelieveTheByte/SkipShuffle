/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.sk1pshuffle.playlist.Track;
import com.dontbelievethebyte.sk1pshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.sk1pshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.Theme;

public class CurrentPlaylistAdapter extends BaseAdapter {

    private Context context;

    static class ViewHolder {
        ImageView image;
        TextView title;
        TextView artist;
    }

    private Theme theme;
    private RandomPlaylist randomPlaylist;
    private LayoutInflater layoutInflater;
    private SkipShuffleMediaPlayer mediaPlayer;
    private TrackPrinter trackPrinter;

    public CurrentPlaylistAdapter(Context context, SkipShuffleMediaPlayer mediaPlayer)
    {
        this.context = context;
        trackPrinter = new TrackPrinter(context);
        this.randomPlaylist = mediaPlayer.getPlaylist();
        layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.mediaPlayer = mediaPlayer;
    }


    public void setTheme(Theme theme)
    {
        this.theme = theme;
    }

    @Override
    public int getCount() {
        return randomPlaylist.getSize();
    }

    @Override
    public Object getItem(int i) {
        return randomPlaylist.getAtPosition(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item_current_playlist, viewGroup, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.track_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.track_title);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.track_artist);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Track track = randomPlaylist.getAtPosition(position);
        setImage(viewHolder.image, position);
        setTitle(viewHolder.title, trackPrinter.printTitle(track), position);
        setArtist(viewHolder.artist, trackPrinter.printArtist(track));
        return convertView;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void setImage(ImageView imageLabel, int position)
    {
        ViewGroup.LayoutParams params = imageLabel.getLayoutParams();
        int height = params.height;

        if (randomPlaylist.getCurrentPosition() == position) {
            imageLabel.setColorFilter(
                    mediaPlayer.isPlaying() ?
                            context.getResources().getColor(theme.getColors().playButton) :
                            context.getResources().getColor(theme.getColors().pauseButton)

            );
            imageLabel.setImageDrawable(mediaPlayer.isPlaying() ? theme.getDrawables().getPlay() : theme.getDrawables().getPause());
            params.width = height;

        } else {
            imageLabel.setImageDrawable(null);
            params.width = (int) (height * 0.66);
        }
        imageLabel.setLayoutParams(params);
    }

    private void setTitle(TextView trackTitle, String string, int position)
    {
        trackTitle.setText(string);
        if(randomPlaylist.getCurrentPosition() == position) {
            trackTitle.setSelected(true);
            trackTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            trackTitle.setSelected(false);
            trackTitle.setEllipsize(TextUtils.TruncateAt.END);
        }
        trackTitle.setTextColor(
                context.getResources().getColor(theme.getColors().playlistTitle)
        );
        trackTitle.setTypeface(theme.getCustomTypeface().getTypeFace());
    }

    private void setArtist(TextView trackArtist, String string)
    {
        trackArtist.setText(string);
        trackArtist.setTextColor(
                context.getResources().getColor(theme.getColors().playlistArtist)
        );
        trackArtist.setTypeface(theme.getCustomTypeface().getTypeFace());
    }
}
