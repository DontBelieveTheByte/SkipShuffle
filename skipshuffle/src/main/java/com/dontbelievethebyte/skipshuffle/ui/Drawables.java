package com.dontbelievethebyte.skipshuffle.ui;

import com.dontbelievethebyte.skipshuffle.ui.mapper.DrawableMapper;

public class Drawables {

    public int back;
    public int cancel;
    public int file;
    public int folder;
    public int ok;
    public int play;
    public int playPressed;
    public int pause;
    public int pausePressed;
    public int prev;
    public int prevPressed;
    public int remove;
    public int skip;
    public int skipPressed;
    public int shuffle;
    public int shufflePressed;
    public int playlist;


    public void Drawable(int uiType)
    {
        back = DrawableMapper.getBack(uiType);
        cancel = DrawableMapper.getCancel(uiType);
        file = DrawableMapper.getFile(uiType);
        folder = DrawableMapper.getFolder(uiType);
        ok = DrawableMapper.getOk(uiType);
        play = DrawableMapper.getPlay(uiType);
        playPressed = DrawableMapper.getPlayPressed(uiType);
        pause = DrawableMapper.getPause(uiType);
        pausePressed = DrawableMapper.getPausePressed(uiType);
        prev = DrawableMapper.getPrev(uiType);
        prevPressed = DrawableMapper.getPrevPressed(uiType);
        remove = DrawableMapper.getRemove(uiType);
        skip = DrawableMapper.getSkip(uiType);
        skipPressed = DrawableMapper.getSkipPressed(uiType);
        shuffle = DrawableMapper.getShuffle(uiType);
        shufflePressed = DrawableMapper.getShufflePressed(uiType);
        playlist = DrawableMapper.getPlaylist(uiType);
    }
}
