/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.media.playlist;

import java.util.List;

public class PlaylistData {
    public int currentPosition = 0;
    public boolean isShuffleOn = false;
    public List<String> trackIds;
    public List<String> shuffledTrackIds;
}
