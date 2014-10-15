package com.dontbelievethebyte.skipshuffle.activities;

import android.content.Intent;
import android.view.View;

import com.dontbelievethebyte.skipshuffle.ui.MainUI;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

public class MainActivity extends BaseActivity {

    private MainUI ui;

    @Override
    protected void handleBackPressed()
    {

    }

    @Override
    protected void onPause(){
        //Give a break to GPU when hidden
        ui.playBtn.clearAnimation();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Register class specific callbacks.
        ui.reboot();
    }

    @Override
    protected void setUI(Integer type)
    {
        ui = UIFactory.createMainUI(this, type);

        //Useful for parent class.
        playerUIInterface = ui;

        ui.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.getPlayerWrapper().isPlaying()) {
                    ui.doPause();
                } else {
                    ui.doPlay();
                }
            }
        });

        ui.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.getPlayerWrapper().doSkip();
                ui.doSkip();
            }
        });

        ui.prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.getPlayerWrapper().doPrev();
                ui.doPrev();
            }
        });

        ui.shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.getPlayerWrapper().doShuffle();
                ui.doShuffle();
            }
        });

        ui.playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playlistActivity = new Intent(getApplicationContext(), PlaylistActivity.class);
                startActivity(playlistActivity);
            }
        });
        ui.reboot();

        //Set up navigation drawer for selecting playlist.
        setNavigationDrawerContent();
    }
}
