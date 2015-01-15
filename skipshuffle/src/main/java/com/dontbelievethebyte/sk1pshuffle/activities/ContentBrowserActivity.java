/*
 * Copyright (c) 2015. Jean-François Bérubé, all rights reserved.
 */

package com.dontbelievethebyte.sk1pshuffle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dontbelievethebyte.sk1pshuffle.R;
import com.dontbelievethebyte.sk1pshuffle.ui.elements.UIElementCompositeInterface;

public class ContentBrowserActivity extends BaseActivity implements UIElementCompositeInterface {

    public final static String CONTENT_TYPE = "com.dontbelievethebyte.CONTENT_TYPE";

    public static enum ContentTypes {
        SONGS,
        ARTISTS,
        ALBUMS,
        GENRES
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_browser);
        parseActivityIntent();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.container, new PlaceholderFragment())
                                       .commit();
        }
    }

    private void parseActivityIntent()
    {
        Intent intent = getIntent();
        Integer extra = intent.getIntExtra(CONTENT_TYPE, 0);
        Log.d(BaseActivity.TAG, "EXTRA IS : " + extra);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements UIElementCompositeInterface {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_content_browser, container, false);
            return rootView;
        }
    }

    @Override
    public void onViewModeChanged()
    {

    }

    @Override
    protected void setUI(Integer type)
    {
//        try {
////            ui = UICompositionFactory.makeContentBrowser(this, preferencesHelper.getUIType());
////            ui.player.reboot();
//        } catch (NoMediaPlayerException e) {
//            handleNoMediaPlayerException(e);
//        }
    }
}
