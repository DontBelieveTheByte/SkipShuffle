package com.dontbelievethebyte.skipshuffle.ui.elements.player;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlists.PlaylistInterface;
import com.dontbelievethebyte.skipshuffle.playlists.Track;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.UIElementCompositeInterface;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.buttons.AbstractPlayerButtons;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.SongLabel;

public abstract class AbstractPlayerUI implements UIElementCompositeInterface {

    public AbstractPlayerButtons buttons;
    public SongLabel songLabel;
    protected BaseActivity baseActivity;

    public abstract void doPlay();

    public abstract void doPause();

    public abstract void doSkip();

    public abstract void doPrev();

    public abstract void doShuffle();

    public abstract void reboot();

    public abstract void setTitle(String title);

    protected String buildFormattedTitle() throws PlaylistEmptyException
    {
        try {
            SkipShuffleMediaPlayer skipShuffleMediaPlayer = baseActivity.getMediaPlayer();
            PlaylistInterface playlist = skipShuffleMediaPlayer.getPlaylist();
            Track currentTrack = playlist.getCurrent();
            if (null == currentTrack.getArtist() || null == currentTrack.getTitle()) {
                return (null == currentTrack.getPath()) ?
                        baseActivity.getString(R.string.meta_data_unknown_current_song_title) :
                        currentTrack.getPath().substring(currentTrack.getPath().lastIndexOf("/") + 1);
            } else {
                return currentTrack.getArtist() + " - " + currentTrack.getTitle();
            }
        } catch (NoMediaPlayerException noMediaPlayerException){
            throw new PlaylistEmptyException(0L);
        }
    }

    protected void handlePlaylistEmptyException(PlaylistEmptyException playlistEmptyException)
    {
        String label = baseActivity.getString(R.string.meta_data_unknown_current_song_title);
        setTitle(label);
    }

    protected void handleNoMediaPlayerException(NoMediaPlayerException noMediaPlayerException)
    {
        try {
            buildFormattedTitle();
        } catch (PlaylistEmptyException playListEmptyException) {
            handlePlaylistEmptyException(playListEmptyException);
        }
        doPause();
    }

}
