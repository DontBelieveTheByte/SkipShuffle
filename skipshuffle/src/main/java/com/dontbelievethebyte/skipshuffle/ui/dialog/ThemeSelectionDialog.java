/*
 * Copyright (c) 2014. Jean-Francois Berube, all rights reserved.
 */

package com.dontbelievethebyte.skipshuffle.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.BaseActivity;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.mapper.types.UITypes;

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
