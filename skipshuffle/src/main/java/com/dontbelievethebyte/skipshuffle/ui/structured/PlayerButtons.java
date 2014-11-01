package com.dontbelievethebyte.skipshuffle.ui.structured;

import android.app.Activity;
import android.widget.ImageButton;

import com.dontbelievethebyte.skipshuffle.R;

public class PlayerButtons {

    public ImageButton playlist;
    public ImageButton prev;
    public ImageButton play;
    public ImageButton shuffle;
    public ImageButton skip;

    private PlayerButtonsAnimations animations;

    public PlayerButtons(Activity activity)
    {
        playlist = (ImageButton) activity.findViewById(R.id.playlistBtn);
    }

    public void setAnimations(PlayerButtonsAnimations animations)
    {
        this.animations = animations;
    }

}
