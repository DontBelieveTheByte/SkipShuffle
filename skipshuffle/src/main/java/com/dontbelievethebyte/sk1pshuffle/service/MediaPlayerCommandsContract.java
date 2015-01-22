/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.service;

public class MediaPlayerCommandsContract {
    public static final String IDENTIFIER = "com.dontbelievethebyte.cmd.COMMAND";
    public static enum COMMANDS {
        PLAY,
        PAUSE,
        SKIP,
        PREV,
        SHUFFLE
    }
}
