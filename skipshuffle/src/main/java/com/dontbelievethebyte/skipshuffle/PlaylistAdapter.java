package com.dontbelievethebyte.skipshuffle;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaylistAdapter extends BaseAdapter {

    PlaylistInterface playlist;
    LayoutInflater layoutInflater;
    Context context;

    public PlaylistAdapter(Context context, PlaylistInterface playlist){
        this.context = context;
        this.playlist = playlist;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return playlist.getSize();
    }

    @Override
    public Object getItem(int position) {
        return playlist.getAtPosition(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        Track track = playlist.getAtPosition(position);

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.playlist_item, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title = setTitle(convertView, R.id.title, track);
        viewHolder.artist = setArtist(convertView, R.id.artist, track);
        viewHolder.image = setImage(convertView, R.id.image, track);
        return convertView;
    }

    private ImageView setImage(View view, int resourceId, Track track){
        ImageView iv = (ImageView) view.findViewById(resourceId);
        iv.setImageDrawable(context.getResources().getDrawable(R.drawable.play_btn));
        return iv;
    }

    private TextView setTitle(View view, int resourceId, Track track){
        TextView tv = (TextView) view.findViewById(resourceId);
        String title;
        if(track.getTitle() != null){
            title = track.getTitle();
        } else {
            title = track.getPath();
        }
        Log.d("TITLE", "TITLE : " + title);
        tv.setText(title);
        return tv;
    }

    private TextView setArtist(View view, int resourceId, Track track) {
        String artist;
        TextView tv = (TextView) view.findViewById(resourceId);
        if(track.getArtist() != null){
            artist = track.getArtist();
        } else {
            artist = context.getString(R.string.metadata_unknown_artist);
        }
        tv.setText(artist);
        return tv;
    }

    private class ViewHolder {
        TextView title;
        TextView artist;
        ImageView image;
    }
}