package com.dontbelievethebyte.skipshuffle.listeners;

import android.view.View;
import android.widget.AdapterView;

public class SongsClick implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
    {
//        Cursor c = adapter.getCursor();
//        c.moveToPosition(position);
        //        try {
//            SkipShuffleMediaPlayer mediaPlayer = getMediaPlayer();
//            if ( (playlist.getPosition() == position) && ((mediaPlayer.isPlaying())) ) {
//                ImageView imageView = (ImageView) view.findViewById(R.id.track_image);
//                imageView.setImageDrawable(
//                        getResources().getDrawable(
//                                DrawableMapper.getPause(preferencesHelper.getUIType())
//                        )
//                );
//                mediaPlayer.doPause();
//                ui.player.doPause();
//            } else {
//                mediaPlayer.doPlay(playlist.getPosition());
//                ui.player.doPlay();
//            }
//        } catch (NoMediaPlayerException noMediaPlayerException) {
//            handleNoMediaPlayerException(noMediaPlayerException);
//        } catch (PlaylistEmptyException playlistEmptyException) {
//            handlePlaylistEmptyException();
//        }
    }
}
