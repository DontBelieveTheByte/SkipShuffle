/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.activities.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PreferencesHelper;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.UITypes;

public class ThemeSelectionDialog {

    private BaseActivity baseActivity;
    private Dialog themeSelectionDialog;

    public ThemeSelectionDialog(BaseActivity activity)
    {
        this.baseActivity = activity;
    }

    public void build(final PreferencesHelper preferencesHelper)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);

        builder.setTitle(baseActivity.getString(R.string.dialog_theme_title));

        builder.setSingleChoiceItems(
                R.array.dialog_theme_items,
                preferencesHelper.getUIType(),
                null
        );

        builder.setPositiveButton(
                R.string.dialog_positive,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int indexPosition) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        switch (lw.getCheckedItemPosition()){
                            case UITypes.MONO_LIGHT:
                                preferencesHelper.setUIType(UITypes.MONO_LIGHT);
                                break;
                            case UITypes.MONO_DARK:
                                preferencesHelper.setUIType(UITypes.MONO_DARK);
                                break;
                            case UITypes.XMAS:
                                preferencesHelper.setUIType(UITypes.XMAS);
                                break;
                            case UITypes.PINK_PANTHER:
                                preferencesHelper.setUIType(UITypes.PINK_PANTHER);
                                break;
                            case UITypes.JACK_O_LANTERN:
                                preferencesHelper.setUIType(UITypes.JACK_O_LANTERN);
                                break;
                            case UITypes.SHAMROCK:
                                preferencesHelper.setUIType(UITypes.SHAMROCK);
                                break;
                            case UITypes.MURICA:
                                preferencesHelper.setUIType(UITypes.MURICA);
                                break;
                            case UITypes.PSYCHEDELIC:
                                preferencesHelper.setUIType(UITypes.PSYCHEDELIC);
                                break;
                            case UITypes.RASTA:
                                preferencesHelper.setUIType(UITypes.RASTA);
                                break;
                            case UITypes.USSR:
                                preferencesHelper.setUIType(UITypes.USSR);
                                break;
                            case UITypes.TRON:
                                preferencesHelper.setUIType(UITypes.TRON);
                                break;
                            case UITypes.RAMEN:
                                preferencesHelper.setUIType(UITypes.RAMEN);
                                break;
                            case UITypes.RAINBOW:
                                preferencesHelper.setUIType(UITypes.RAINBOW);
                                break;
                            case UITypes.KOSHER:
                                preferencesHelper.setUIType(UITypes.KOSHER);
                                break;
                            case UITypes.STAR_WARS:
                                preferencesHelper.setUIType(UITypes.STAR_WARS);
                                break;
                            default: //Equivalent to UIFactory.NEON
                                preferencesHelper.setUIType(UITypes.NEON);
                                break;
                        }
                        dialog.dismiss();
                    }
                }
        );

        builder.setNegativeButton(
                R.string.dialog_negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexPosition) {
                        dialog.dismiss();
                    }
                }
        );

        themeSelectionDialog = builder.create();
    }

    public void show ()
    {
        themeSelectionDialog.show();
    }
}
