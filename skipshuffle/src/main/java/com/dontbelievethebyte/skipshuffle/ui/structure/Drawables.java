/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.structure;

import android.content.Context;
import android.graphics.drawable.Drawable;

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
    public int notificationBackground;

    private Context context;

    public Drawables(Context context, int uiType)
    {
        this.context = context;

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
        notificationBackground = DrawableMapper.getNotificationBackgroun(uiType);
    }

    public Drawable getBack()
    {
        return toDrawable(back);
    }

    public Drawable getCancel()
    {
        return toDrawable(cancel);
    }

    public Drawable getFile()
    {
        return toDrawable(file);
    }

    public Drawable getFolder()
    {
        return toDrawable(folder);
    }

    public Drawable getOk()
    {
        return toDrawable(ok);
    }

    public Drawable getPlay()
    {
        return toDrawable(play);
    }

    public Drawable getPlayPressed()
    {
        return toDrawable(playPressed);
    }

    public Drawable getPause()
    {
        return toDrawable(pause);
    }

    public Drawable getPausePressed()
    {
        return toDrawable(pausePressed);
    }

    public Drawable getPrev()
    {
        return toDrawable(prev);
    }

    public Drawable getPrevPressed()
    {
        return toDrawable(prevPressed);
    }

    public Drawable getRemove()
    {
        return toDrawable(remove);
    }

    public Drawable getSkip()
    {
        return toDrawable(skip);
    }

    public Drawable getSkipPressed()
    {
        return toDrawable(skipPressed);
    }

    public Drawable getShuffle()
    {
        return toDrawable(shuffle);
    }

    public Drawable getShufflePressed()
    {
        return toDrawable(shufflePressed);
    }

    public Drawable getPlaylist()
    {
        return toDrawable(playlist);
    }

    private Drawable toDrawable(int drawableId)
    {
        return context.getResources().getDrawable(drawableId);
    }

}
