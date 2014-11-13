package com.dontbelievethebyte.skipshuffle.listeners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.PlayerActivity;
import com.dontbelievethebyte.skipshuffle.adapters.CurrentPlaylistAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;

public class CurrentPlaylistClick implements AdapterView.OnItemClickListener {

    private PlayerActivity playerActivity;

    public CurrentPlaylistClick(PlayerActivity playerActivity)
    {
        this.playerActivity = playerActivity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
        try {
            SkipShuffleMediaPlayer mediaPlayer = playerActivity.getMediaPlayer();
            RandomPlaylist randomPlaylist = (RandomPlaylist) mediaPlayer.getPlaylist();
            ImageView image = (ImageView) view.findViewById(R.id.track_image);

            if (position == randomPlaylist.getPosition()) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.doPause();
                    image.setImageDrawable(playerActivity.ui.player.buttons.drawables.getPause());
                    playerActivity.ui.player.doPause();
                } else
                    mediaPlayer.doPlay();
                image.setImageDrawable(playerActivity.ui.player.buttons.drawables.getPlay());
                playerActivity.ui.player.doPlay();
            } else {
                mediaPlayer.doPlay(position);
                image.setImageDrawable(playerActivity.ui.player.buttons.drawables.getPlay());
                playerActivity.ui.player.doPlay();
            }

            CurrentPlaylistAdapter adapter = (CurrentPlaylistAdapter)adapterView.getAdapter();
            adapter.notifyDataSetChanged();

        } catch (NoMediaPlayerException noMediaPlayerException) {
            playerActivity.handleNoMediaPlayerException(noMediaPlayerException);
        } catch (PlaylistEmptyException playlistEmptyException) {
            playerActivity.handlePlaylistEmptyException(playlistEmptyException);
        }
    }
}
