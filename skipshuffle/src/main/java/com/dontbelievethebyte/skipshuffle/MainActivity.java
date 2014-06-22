package com.dontbelievethebyte.skipshuffle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.coderplus.filepicker.FilePickerActivity;

import java.io.File;
import java.util.List;
import java.util.Iterator;

public class MainActivity extends Activity {


    protected static int REQUEST_PICK_FILE = 777;

    private ImageButton playBtn;
    private ImageButton skipBtn;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Stop the mediaPlayer service.

        stopService(new Intent(getApplicationContext(), SkipShuffleMediaPlayer.class));
    }

    private ImageButton prevBtn;
    private ImageButton playlistBtn;
    private ImageButton shuffleBtn;

    private Animation ltr;
    private Animation flipRightAnimation;
    private Animation flipDownAnimation;
    private Animation flipLeftAnimation;
    private Animation blinkAnimation;

    private ProgressDialog pd;

    private static final String TAG = "SkipShuffleMain";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = (ImageButton) findViewById(R.id.playBtn);

        skipBtn = (ImageButton) findViewById(R.id.skipBtn);

        prevBtn = (ImageButton) findViewById(R.id.prevBtn);

        shuffleBtn = (ImageButton) findViewById(R.id.shuffleBtn);

        playlistBtn = (ImageButton) findViewById(R.id.playlistBtn);

        //Set up generic animations

        ltr = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.ltr);
        flipRightAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_right);
        flipDownAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_down);
        flipLeftAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flip_left);
        blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);


        //Start the mediaPlayer service.

        startService(new Intent(getApplicationContext(), SkipShuffleMediaPlayer.class));

        //Register haptic feedback for all buttons.
        playBtn.setOnTouchListener(onTouchDownHapticFeedback);
        skipBtn.setOnTouchListener(onTouchDownHapticFeedback);
        playlistBtn.setOnTouchListener(onTouchDownHapticFeedback);
        prevBtn.setOnTouchListener(onTouchDownHapticFeedback);
        shuffleBtn.setOnTouchListener(onTouchDownHapticFeedback);


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastMessage;

                if (true) {
                    toastMessage = getResources().getString(R.string.pause);
                    playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
                    playBtn.startAnimation(ltr);
                } else {
                    toastMessage = getResources().getString(R.string.play);
                    playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
                    playBtn.startAnimation(blinkAnimation);
                }

                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playBtn.clearAnimation();
                playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
                playBtn.startAnimation(blinkAnimation);
                skipBtn.startAnimation(flipRightAnimation);
                playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));

                playBtn.startAnimation(ltr);
                Toast.makeText(getApplicationContext(), R.string.skip, Toast.LENGTH_SHORT).show();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playBtn.clearAnimation();
                playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
                playBtn.startAnimation(blinkAnimation);
                prevBtn.startAnimation(flipLeftAnimation);
                playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
                playBtn.startAnimation(ltr);
                Toast.makeText(getApplicationContext(), R.string.prev, Toast.LENGTH_LONG).show();
            }
        });

        shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playBtn.clearAnimation();
                playBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_states));
                playBtn.startAnimation(blinkAnimation);
                shuffleBtn.startAnimation(flipDownAnimation);
                playBtn.setImageDrawable(getResources().getDrawable(R.drawable.play_states));
                playBtn.startAnimation(ltr);
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

    }

/*    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }*/

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
                            for (Iterator<File> iterator = directories.iterator(); iterator.hasNext(); ) {
                                File directory = iterator.next();
                                new AsyncMediaScanner().execute(directory);
                            }
                        }
                    }
            }
        }
    }

    private class AsyncMediaScanner extends AsyncTask<File, String, Void> {

        protected List<File> validFilesList;

        @Override
        protected Void doInBackground(File... directories) {
            AudioFileTypeValidator audioFileTypeValidator = new AudioFileTypeValidator();
            for(File file:directories){
                recursiveFileList(file, audioFileTypeValidator);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            pd = new ProgressDialog(getApplicationContext());
            pd.setTitle("Scanning Media files...");
            pd.setMessage("Please wait.");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

        @Override
        protected void onPostExecute(Void validFilesList) {
            //super.onPostExecute(validFilesList);
            pd.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            pd.setMessage(values.toString());
            //super.onProgressUpdate(values);
        }
        private void recursiveFileList(File dir, AudioFileTypeValidator audioFileTypeValidator) {
            //Get list of all files and folders in directory
            File[] files = dir.listFiles();

            //For all files and folders in directory
            for (int i = 0; i < files.length; i++) {
                //Check if directory
                if (files[i].isDirectory())
                    //Recursively call file list function on the new directory
                    recursiveFileList(files[i], audioFileTypeValidator);
                else {
                    //If not a directory, print the file path
                    if (audioFileTypeValidator.VALID == audioFileTypeValidator.validate(files[i].getAbsolutePath())) {
                        publishProgress(files[i].getName());
                        //validFilesList.add(files[i]);
                        Log.v(TAG, "I'm a valid File");
                    }
                }
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
