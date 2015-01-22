/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.ListView;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.activity.BaseActivity;
import com.dontbelievethebyte.sk1pshuffle.ui.theme.UITypes;
import com.dontbelievethebyte.sk1pshuffle.utilities.preferences.PreferencesHelper;

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
                preferencesHelper.getUIType().ordinal(),
                null
        );

        builder.setPositiveButton(
                R.string.dialog_positive,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int indexPosition) {
                        ListView lw = ((AlertDialog)dialog).getListView();
                        preferencesHelper.setUIType(
                                UITypes.values()[lw.getCheckedItemPosition()]
                        );
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
