/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.playlist;

import java.util.List;

public class PlaylistData {
    public int currentPosition = 0;
    public boolean isShuffleOn = false;
    public List<String> trackIds;
    public List<String> shuffledTrackIds;
}
