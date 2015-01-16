/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.fragment.ContentBrowserFragment;

public class ContentBrowserActivity extends ActionBarActivity {

    public final static String CONTENT_TYPE = "com.dontbelievethebyte.CONTENT_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_browser);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.container, new ContentBrowserFragment())
                                       .commit();
        }
    }

}
