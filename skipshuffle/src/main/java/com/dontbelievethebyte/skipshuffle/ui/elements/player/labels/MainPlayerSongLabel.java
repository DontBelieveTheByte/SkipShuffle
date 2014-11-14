package com.dontbelievethebyte.skipshuffle.ui.elements.player.labels;

import android.view.ViewGroup;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.ui.CustomTypeface;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.layout.AbstractLayout;

public class MainPlayerSongLabel implements UIElementCompositeInterface {

    private TextView label;

    public MainPlayerSongLabel(AbstractLayout contentArea, int labelId)
    {
        ViewGroup bottomLayout = contentArea.getBottomLayout();
        label = (TextView) bottomLayout.findViewById(labelId);
    }

    public void setContent(Track track)
    {
        if (null != label) {
            label.setText(track.getTitle());
            label.setSelected(true);
        }
    }

    public void setTypeFace(CustomTypeface typeFace)
    {
        if (null != label && null != typeFace.getTypeFace())
            label.setTypeface(typeFace.getTypeFace());
    }

    public void setColor(int color)
    {
        label.setTextColor(color);
    }

    public TextView getLabel()
    {
        return label;
    }

//    protected String buildFormattedTitle(Track track)
//    {
//        try {
//            SkipShuffleMediaPlayer skipShuffleMediaPlayer = baseActivity.getMediaPlayer();
//            PlaylistInterface playlist = skipShuffleMediaPlayer.getPlaylist();
//            Track currentTrack = playlist.getCurrent();
//            if (null == currentTrack.getArtist() || null == currentTrack.getTitle()) {
//                return (null == currentTrack.getPath()) ?
//                        baseActivity.getString(R.string.meta_data_unknown_current_song_title) :
//                        currentTrack.getPath().substring(currentTrack.getPath().lastIndexOf("/") + 1);
//            } else {
//                return currentTrack.getArtist() + " - " + currentTrack.getTitle();
//            }
//        return baseActivity.getString(R.string.meta_data_unknown_current_song_title);
//        } catch (NoMediaPlayerException noMediaPlayerException){
//            throw new PlaylistEmptyException(0L);
//        }
//    }
}
