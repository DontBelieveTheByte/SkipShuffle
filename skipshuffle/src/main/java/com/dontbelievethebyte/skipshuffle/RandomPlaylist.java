package com.dontbelievethebyte.skipshuffle;

import java.io.File;
import java.util.List;
import java.util.Collections;

/**
 * Created by killthesystem on 2014-06-11.
 */
public class RandomPlaylist implements Playlist{

    protected List<File> playlist;

    public RandomPlaylist(List list) {
        playlist = list;
        Collections.shuffle(playlist);
    }

    @Override
    public File next() {
        //playlist.remove();
        return null;
    }

    @Override
    public File prev() {
        return null;
    }

    public void shuffle(){
        Collections.shuffle(playlist);
    }
}
