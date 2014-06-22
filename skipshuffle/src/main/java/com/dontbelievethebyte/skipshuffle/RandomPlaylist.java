package com.dontbelievethebyte.skipshuffle;

import java.io.File;
import java.util.List;
import java.util.Collections;

/**
 * Created by killthesystem on 2014-06-11.
 */
public class RandomPlaylist implements Playlist{

    protected List<File> playlist;

    @Override
    public String getFirst() {
        return null;
    }

    @Override
    public String getNext() {
        return null;
    }

    @Override
    public String getPrev() {
        return null;
    }

    @Override
    public int getCursorPosition() {
        return 0;
    }

    @Override
    public void setCursorPosition(int position) {

    }

    @Override
    public void shuffle(){

    }

}
