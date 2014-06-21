package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import com.coderplus.filepicker.FilePickerActivity;

public class MainActivity extends Activity {


    protected static int REQUEST_PICK_FILE = 777;

    protected ImageButton playBtn;
    protected ImageButton skipBtn;
    protected ImageButton prevBtn;
    protected ImageButton playlistBtn;
    protected ImageButton suffleBtn;

    List<File> mediaDirectories;
    List<File> mediaFiles;

    protected boolean isPaused = true;

    private static final String TAG = "SkipShuffleMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = (ImageButton) findViewById(R.id.playBtn);

        skipBtn = (ImageButton) findViewById(R.id.skipBtn);

        prevBtn = (ImageButton) findViewById(R.id.prevBtn);

        suffleBtn = (ImageButton) findViewById(R.id.shuffleBtn);

        playlistBtn = (ImageButton) findViewById(R.id.playlistBtn);

        //Register haptic feedback for all buttons.
        playBtn.setOnTouchListener(onTouchDownHapticFeedback);
        skipBtn.setOnTouchListener(onTouchDownHapticFeedback);
        playlistBtn.setOnTouchListener(onTouchDownHapticFeedback);
        prevBtn.setOnTouchListener(onTouchDownHapticFeedback);
        suffleBtn.setOnTouchListener(onTouchDownHapticFeedback);


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastMessage;

                if (isPaused == true) {
                    toastMessage = getResources().getString(R.string.pause);
                    Animation ltr = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ltr);
                    playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
                    playBtn.startAnimation(ltr);
                    isPaused = false;
                } else {
                    toastMessage = getResources().getString(R.string.play);
                    playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
                    Animation blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                    playBtn.startAnimation(blinkAnimation);
                    isPaused = true;
                }

                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation flipRightAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_right);
                skipBtn.startAnimation(flipRightAnimation);
                Toast.makeText(getApplicationContext(), R.string.skip, Toast.LENGTH_SHORT).show();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation flipLeftAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_left);
                prevBtn.startAnimation(flipLeftAnimation);
                Toast.makeText(getApplicationContext(), R.string.prev, Toast.LENGTH_LONG).show();
            }
        });

        suffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation flipDownAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_down);
                suffleBtn.startAnimation(flipDownAnimation);
                Toast.makeText(getApplicationContext(), R.string.shuffle, Toast.LENGTH_LONG).show();
            }
        });

        playlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyPlaylist()){
                    pickMediaDirectories();
                }
            }
        });

        getMediaFiles();
    }

    protected void getMediaFiles() {
        Log.v(TAG, "I ran");
    }

    protected boolean isEmptyPlaylist(){
        return true;
    }

    protected void pickMediaDirectories() {

        Intent intent = new Intent(this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.EXTRA_SELECT_MULTIPLE, true);
        intent.putExtra(FilePickerActivity.EXTRA_SELECT_DIRECTORIES_ONLY, true);

        //Set the initial directory to the sdcard.
        intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
        startActivityForResult(intent, REQUEST_PICK_FILE);

        Toast.makeText(getApplicationContext(), R.string.sel_target_directories, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case 777:
                    if(data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
                        // Get the file path
                        @SuppressWarnings("unchecked")
                        List<File> directories = (List<File>)data.getSerializableExtra(FilePickerActivity.EXTRA_FILE_PATH);
                        if(directories.isEmpty()){
                            Toast.makeText(getApplicationContext(), R.string.no_directory, Toast.LENGTH_LONG).show();
                        } else {
                            scanMedia(directories, mediaFiles);
                        }
                    }
            }
        }
    }

    protected void scanMedia (List<File> files, List<File> media) {

        //List<File> foundDirectories;

        for(File file:files){

            if(file.isDirectory()){
                Toast.makeText(getApplicationContext(), "Directory : "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
                //foundDirectories.add(file);
                //scanMedia(foundDirectories, media);
            } else {
                media.add(file);
                Log.v(TAG,file.getAbsolutePath());

                Toast.makeText(getApplicationContext(), "File : "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private View.OnTouchListener onTouchDownHapticFeedback = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()){
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
            }
            return false;
        }
    };
}
