package com.dontbelievethebyte.skipshuffle;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class PlaylistAdapter extends BaseAdapter {

    private PlaylistInterface playlist;
    private LayoutInflater layoutInflater;
    private Context context;
    private View currentPlayView;
    private PreferencesHelper preferencesHelper;

    public PlaylistAdapter(Context context, PreferencesHelper preferencesHelper, PlaylistInterface playlist){
        this.context = context;
        this.playlist = playlist;
        this.preferencesHelper = preferencesHelper;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return playlist.getSize();
    }

    @Override
    public Object getItem(int position) {
        new TrackFetchTask().execute(playlist);
        
        return playlist.getAtPosition(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        Track track = playlist.getAtPosition(position);

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(
                    UIFactory.getSinglePlaylistItemLayout(preferencesHelper.getUIType()),
                    null)
            ;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title = setTitle(convertView, R.id.track_title, track);
        viewHolder.artist = setArtist(convertView, R.id.track_artist, track);
        viewHolder.image = setImage(convertView, R.id.track_image, track);
        return convertView;
    }

    private class TrackFetchTask extends AsyncTask<PlaylistInterface, Integer, Track> {
        @Override
        protected Track doInBackground(PlaylistInterface... playlistInterfaces) {
                Track track = playlist.getCurrent();
                return track;
        }
    }
    private ImageView setImage(View view, int resourceId, Track track){
        ImageView iv = (ImageView) view.findViewById(resourceId);
        LayoutParams params = iv.getLayoutParams();
        if(track.getId() == playlist.getCurrent().getId()){
            params.width = params.height;
            iv.setImageDrawable(
                    context.getResources().getDrawable(
                            UIFactory.getPlayDrawable(
                                    preferencesHelper.getUIType()
                            )
                    )
            );
        } else {
            iv.setImageDrawable(null);
            params.width = 0;
        }
        iv.setLayoutParams(params);
        return iv;
    }

    private TextView setTitle(View view, int resourceId, Track track){
        TextView tv = (TextView) view.findViewById(resourceId);
        String title = track.getTitle();
        if(title == null){
            title = track.getPath();
            title = title.substring(title.lastIndexOf("/") + 1);
        }
        tv.setText(title);
        return tv;
    }

    private TextView setArtist(View view, int resourceId, Track track) {
        String artist = track.getArtist();
        TextView tv = (TextView) view.findViewById(resourceId);
        if(artist == null){
            artist = context.getString(R.string.meta_data_unknown_artist);
        }
        tv.setText(artist);
        return tv;
    }

    private class ViewHolder {
        public TextView title;
        public TextView artist;
        public ImageView image;
    }
}