/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.fragments.ContentBrowserFragment;

public class ContentBrowserActivity extends ActionBarActivity {

    public final static String CONTENT_TYPE = "com.dontbelievethebyte.CONTENT_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_browser);
        parseActivityIntent();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.container, new ContentBrowserFragment())
                                       .commit();
        }
    }

    private void parseActivityIntent()
    {
        Intent intent = getIntent();
        Integer extra = intent.getIntExtra(CONTENT_TYPE, 0);
        Log.d(BaseActivity.TAG, "EXTRA IS : " + extra);
    }

}
