package com.dontbelievethebyte.skipshuffle.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.ListNavigatorActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class SongsClick extends AbstractListClick {

    public SongsClick(ListNavigatorActivity listActivity)
    {
        super(listActivity);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = listActivity.getMediaPlayer();
            mediaPlayer.doPlay();
            PlaylistInterface playlist = mediaPlayer.getPlaylist();
            if ( (playlist.getPosition() == position) && ((mediaPlayer.isPlaying())) ) {
                ImageView imageView = (ImageView) view.findViewById(R.id.track_image);
                imageView.setImageDrawable(listActivity.ui.player.buttons.drawables.getPause());
                mediaPlayer.doPause();
                listActivity.ui.player.doPause();
            } else {
                mediaPlayer.doPlay(playlist.getPosition());
                listActivity.ui.player.doPlay();
            }
        } catch (NoMediaPlayerException noMediaPlayerException) {
            listActivity.handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            listActivity.handlePlaylistEmptyException(playlistEmptyException);
        }
    }
}
