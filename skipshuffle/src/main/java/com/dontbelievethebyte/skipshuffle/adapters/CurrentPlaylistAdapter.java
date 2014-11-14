package com.dontbelievethebyte.skipshuffle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.playlists.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.structured.Colors;
import com.dontbelievethebyte.skipshuffle.ui.structured.Drawables;

public class CurrentPlaylistAdapter extends BaseAdapter {

    private com.dontbelievethebyte.skipshuffle.ui.structured.Colors colors;

    static class ViewHolder {
        ImageView image;
        TextView title;
        TextView artist;
    }

    private RandomPlaylist randomPlaylist;
    private Drawables drawables;
    private LayoutInflater layoutInflater;
    private SkipShuffleMediaPlayer mediaPlayer;

    public CurrentPlaylistAdapter(Context context, RandomPlaylist randomPlaylist, SkipShuffleMediaPlayer mediaPlayer)
    {
        this.randomPlaylist = randomPlaylist;
        layoutInflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.mediaPlayer = mediaPlayer;
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item_song, viewGroup, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.track_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.track_title);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.track_artist);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        Track track = randomPlaylist.getAtPosition(i);
        setImage(viewHolder.image, i);
        setTitle(viewHolder.title, track.getTitle());
        setArtist(viewHolder.artist, track.getArtist());
        return convertView;
    }

    public void setDrawables(Drawables drawables)
    {
        this.drawables = drawables;
    }

    public void setColors(Colors colors)
    {
        this.colors = colors;
    }

    private void setImage(ImageView imageLabel, int position)
    {
        ViewGroup.LayoutParams params = imageLabel.getLayoutParams();
        int height = params.height;

        if (randomPlaylist.getPosition() == position) {
            imageLabel.setImageDrawable(mediaPlayer.isPlaying() ? drawables.getPlay() : drawables.getPause());
            params.width = mediaPlayer.isPlaying() ? height : height / 2;
        } else {
            imageLabel.setImageDrawable(null);
            params.width = height /2;
        }
        imageLabel.setLayoutParams(params);
    }

    private void setTitle(TextView textView, String string)
    {
        textView.setText(string);
        if (null != colors)
            textView.setTextColor(colors.songLabel);
    }

    private void setArtist(TextView textView, String string)
    {
        textView.setText(string);
        if (null != colors)
            textView.setTextColor(colors.emptyListText);
    }
}
