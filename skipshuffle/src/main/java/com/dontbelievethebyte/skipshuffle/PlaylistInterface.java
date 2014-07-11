package com.dontbelievethebyte.skipshuffle;

public interface PlaylistInterface {

    public String getFirst();

    public String getNext();

    public String getPrev();

    public int getCursorPosition();

    public void setCursorPosition(int position);

    public void shuffle();
    
}
