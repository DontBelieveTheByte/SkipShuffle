package com.dontbelievethebyte.skipshuffle.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.adapters.FilePickerDrawerAdapter;
import com.dontbelievethebyte.skipshuffle.activities.adapters.FilePickerListAdapter;
import com.dontbelievethebyte.skipshuffle.activities.util.FilePickerClickListener;
import com.dontbelievethebyte.skipshuffle.activities.util.FilePickerNavDrawerClickListener;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.FilePickerUI;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class FilePickerActivity extends BaseActivity {

    private static final String TAG = "SkipShuffleFilePicker";
    private static final String LAST_CURRENT_DIRECTORY = "lastCurrentDirectory";
    private ArrayList<File> files;

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    private File currentDirectory;
    private File externalRootDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
    private FilePickerListAdapter filePickerListAdapter;
    private FilePickerUI filePickerUI;
    private PreferencesHelper preferencesHelper;

    @Override
    public void mediaBroadcastReceiverCallback() {}

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);

        preferencesHelper = new PreferencesHelper(this);
        files = new ArrayList<File>();

        if (null != savedInstanceState) {
            File lastFile = (File) savedInstanceState.getSerializable(LAST_CURRENT_DIRECTORY);
            currentDirectory = (null != lastFile) ?
                    lastFile :
                    new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        } else {
            currentDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        }
    }

    @Override
    protected void setUI(Integer type) {

        filePickerUI = UIFactory.createFilePickerUI(this, type);

        filePickerListAdapter = new FilePickerListAdapter(
                this,
                files,
                preferencesHelper,
                filePickerUI.getTypeFace()
        );

        // Set the view to be shown if the list is empty

        ListView listView = (ListView) findViewById(R.id.current_list);

        listView.setOnItemClickListener(
                new FilePickerClickListener(
                        this,
                        (DrawerLayout) findViewById(R.id.drawer_layout)
                )
        );

        // Set the ListAdapter for list of files

        listView.setAdapter(filePickerListAdapter);

        View.OnClickListener backClickListener= new View.OnClickListener() {
            public void onClick(View v)
            {
                onBackPressed();
            }
        };

        View.OnClickListener okClickListener= new View.OnClickListener() {
            public void onClick(View v)
            {
                if (filePickerListAdapter.getFiles().size() < 1) {
                    Toast.makeText(
                            FilePickerActivity.this,
                            R.string.pick_media_nothing_selected,
                            Toast.LENGTH_SHORT
                    ).show();
                    setResult(RESULT_CANCELED);
                } else {
                    Log.d(TAG, "PREF : " + filePickerListAdapter.getFiles());
                    preferencesHelper.setMediaDirectories(filePickerListAdapter.getFiles());
                    setResult(RESULT_OK);
                }
                finish();
            }
        };

        View.OnClickListener cancelClickListener= new View.OnClickListener() {
            public void onClick(View v)
            {
                Toast.makeText(
                        FilePickerActivity.this,
                        R.string.pick_media_nothing_selected,
                        Toast.LENGTH_SHORT
                ).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        };

        ImageButton backButton = (ImageButton)findViewById(R.id.back);
        backButton.setOnClickListener(backClickListener);

        ImageButton cancelButton = (ImageButton)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(cancelClickListener);

        ImageButton okButton = (ImageButton)findViewById(R.id.ok);
        okButton.setOnClickListener(okClickListener);

        setUpDrawer();
    }

	@Override
	protected void onResume()
    {
        Toast.makeText(
                getApplicationContext(),
                R.string.media_scan_sel_target_directories,
                Toast.LENGTH_LONG
        ).show();
        refreshFilesList();
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar && ViewConfiguration.get(this).hasPermanentMenuKey() && actionBar.isShowing()) {
            actionBar.hide();
        } else if (externalRootDirectory.equals(currentDirectory.getParentFile())) {
            // Go to parent directory
//            currentDirectory = externalRootDirectory;
//            refreshFilesList();
        } else {
            finish();
        }

    }

	public void refreshFilesList()
    {
        File newFiles[] = currentDirectory.listFiles();
        if (null == newFiles) {
            files.clear(); // Clear the files ArrayList
            files.addAll(Arrays.asList(newFiles));
            Collections.sort(files, new FileComparator());
            filePickerListAdapter.clearBoxes(); //clear the checked item list
            filePickerListAdapter.notifyDataSetChanged();
        } else {
            currentDirectory = currentDirectory.getParentFile();
            Toast.makeText(
                    FilePickerActivity.this,
                    getString(R.string.no_access),
                    Toast.LENGTH_SHORT
            ).show();
        }
	}

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(LAST_CURRENT_DIRECTORY, currentDirectory);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void setUpDrawer()
    {
        super.setUpDrawer();

        ListView drawerList = (ListView) findViewById(R.id.left_drawer1);
        TextView headerView = (TextView) drawerList.findViewById(R.id.drawer_header);
        headerView.setText(getString(R.string.file_picker_drawer_title));
        headerView.setTypeface(filePickerUI.getTypeFace());
        drawerList.addHeaderView(headerView);

        ArrayList<String> prefsDirectories = preferencesHelper.getMediaDirectories();

        FilePickerDrawerAdapter filePickerDrawerAdapter = new FilePickerDrawerAdapter(
                this,
                R.layout.file_picker_drawer_list_item,
                (null != prefsDirectories) ? prefsDirectories : new ArrayList<String>(),
                preferencesHelper,
                filePickerUI.getTypeFace()
        );
        drawerList.setOnItemClickListener(
                new FilePickerNavDrawerClickListener(
                        this,
                        (DrawerLayout) findViewById(R.id.drawer_layout)
                )
        );
        drawerList.setAdapter(filePickerDrawerAdapter);
    }

    private class FileComparator implements Comparator<File> {
		public int compare(File f1, File f2) {
			if (f1 == f2) {
				return 0;
			}
			if (f1.isDirectory() && f2.isFile()) {
				// Show directories above files
				return -1;
			}
			if (f1.isFile() && f2.isDirectory()) {
				// Show files below directories
				return 1;
			}
			// Sort the directories alphabetically
			return f1.getName().compareToIgnoreCase(f2.getName());
		}
	}

}
