/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.exceptions.NoMediaPlayerException;
import com.dontbelievethebyte.skipshuffle.exceptions.PlaylistEmptyException;
import com.dontbelievethebyte.skipshuffle.playlist.RandomPlaylist;
import com.dontbelievethebyte.skipshuffle.playlist.Track;
import com.dontbelievethebyte.skipshuffle.playlist.TrackPrinter;
import com.dontbelievethebyte.skipshuffle.service.SkipShuffleMediaPlayer;
import com.dontbelievethebyte.skipshuffle.ui.elements.player.labels.MainPlayerSongLabel;
import com.dontbelievethebyte.skipshuffle.utilities.preferences.PreferencesHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizDialog {

    private final int COUNTDOWN_DURATION = 5000;

    private BaseActivity baseActivity;
    private Dialog dialog;
    private TrackPrinter trackPrinter;
    private CountDownTimer countDownTimer;
    private int goodAnswer;

    public QuizDialog(BaseActivity activity)
    {
        this.baseActivity = activity;
        trackPrinter = new TrackPrinter(baseActivity);
    }

    private CharSequence[] buildChoices() throws NoMediaPlayerException, PlaylistEmptyException {
        ArrayList<Track> tracks = new ArrayList<>();
        SkipShuffleMediaPlayer mp = baseActivity.getMediaPlayer();
        RandomPlaylist playlist = mp.getPlaylist();
        Track currentTrack = playlist.getCurrent();
        tracks.add(currentTrack);
        int choicesSize = getNumberOfChoices(playlist);

        for (int i = 1; i < choicesSize - 1; i++) {
            tracks.add(getAnyTrackButThese(tracks, playlist));
        }

        Collections.shuffle(tracks);
        goodAnswer = tracks.indexOf(currentTrack);
        CharSequence[] choices = new CharSequence[tracks.size()];
        for (int i = 0; i < choices.length; i++ ) {
            choices[i] = trackPrinter.printTitle(tracks.get(i));
        }
        return choices;
    }

    private void startCountDown()
    {
        countDownTimer = new CountDownTimer(COUNTDOWN_DURATION, 1000) {

            public void onTick(long millisUntilFinished)
            {
                String title = baseActivity.getString(R.string.menu_quizz)  +
                               " " +
                               millisUntilFinished / 1000 +
                               " Score: " +
                               baseActivity.getPreferencesHelper().getQuizScore();

                dialog.setTitle(title);
            }

            public void onFinish()
            {
                if (null != dialog) {
                    dialog.dismiss();
                    if (dialog.isShowing())
                        handleBadAnswer();
                    else
                        showLabel();
                }
            }
        };
        countDownTimer.start();
    }

    private void hideLabel()
    {
        MainPlayerSongLabel label = baseActivity.ui.player.getSongLabel();
        label.hide();
    }

    private void showLabel()
    {
        MainPlayerSongLabel label = baseActivity.ui.player.getSongLabel();
        label.show();
    }

    private int getNumberOfChoices(RandomPlaylist randomPlaylist)
    {
        int size = randomPlaylist.getSize();
        if (size <= 0)
            return 0;
        else if (size > 2 && size < 10)
            return 3;
        else
            return 5;
    }

    private Track getAnyTrackButThese(ArrayList<Track> tracks, RandomPlaylist playlist)
    {
        Random random = new Random();
        Track randomTrack;
        do {
            int selection = random.nextInt(playlist.getSize());
            randomTrack = playlist.getAtPosition(selection);
        } while (tracks.contains(randomTrack));
        return randomTrack;
    }

    public void build() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(baseActivity);

        dialogBuilder.setTitle(baseActivity.getString(R.string.menu_quizz));
        try {
            dialogBuilder.setItems(
                    buildChoices(),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int userChoice)
                        {
                            if (userChoice == goodAnswer)
                                handleGoodAnswer(baseActivity.getPreferencesHelper());
                            else
                                handleBadAnswer();
                        }
                    });
            dialog = dialogBuilder.create();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog)
                {
                    showLabel();
                    if(null != countDownTimer)
                        countDownTimer.cancel();
                    baseActivity.quizDialog = null;
                }
            });
        } catch (NoMediaPlayerException n) {
            baseActivity.handleNoMediaPlayerException(n);
        } catch (PlaylistEmptyException p) {
            baseActivity.handlePlaylistEmptyException(p);
        }
    }

    private void handleGoodAnswer(PreferencesHelper preferencesHelper)
    {
        preferencesHelper.increaseQuizScore();
        handleBadAnswer();
    }

    private void handleBadAnswer()
    {
        baseActivity.ui.player.buttons.skip.performClick();
    }

    public void show ()
    {
        dialog.show();
        hideLabel();
        startCountDown();
    }
}
