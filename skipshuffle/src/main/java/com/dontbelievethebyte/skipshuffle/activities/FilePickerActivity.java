package com.dontbelievethebyte.skipshuffle.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.adapters.FilePickerListAdapter;
import com.dontbelievethebyte.skipshuffle.activities.adapters.NavigationDrawerAdapter;
import com.dontbelievethebyte.skipshuffle.activities.util.NavDrawerClickListener;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.FilePickerUI;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FilePickerActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "SkipShuffleFilePicker";
    private boolean mShowHiddenFiles = false;
    private ArrayList<File> files;
    private File rootDirectory;
    private FilePickerListAdapter filePickerListAdapter;
    private FilePickerUI filePickerUI;
    private ListView listView;
    private PreferencesHelper preferencesHelper;


    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);

        preferencesHelper = new PreferencesHelper(this);

		// Point to external storage as root.
        rootDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

		// Initialize the ArrayList
		files = new ArrayList<File>();

        setUI(preferencesHelper.getUIType());

        Toast.makeText(
                getApplicationContext(),
                R.string.media_scan_sel_target_directories,
                Toast.LENGTH_LONG
        ).show();
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

        listView = (ListView) findViewById(R.id.current_list);

        listView.setOnItemClickListener(this);

        // Set the ListAdapter

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
                if (filePickerListAdapter.getFiles().length < 1) {
                    Toast.makeText(
                            FilePickerActivity.this,
                            R.string.pick_media_nothing_selected,
                            Toast.LENGTH_SHORT
                    ).show();
                    setResult(RESULT_CANCELED);
                } else {
                    Log.d(TAG, "PREF : " + filePickerListAdapter.getFiles()[0].toString());
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
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

	@Override
	protected void onResume() {
        refreshFilesList();
        super.onResume();
    }

	/**
	 * Updates the list view to the current directory
	 */
	protected void refreshFilesList()
    {
		// Clear the files ArrayList
		files.clear();
		//clear the checked item list
		filePickerListAdapter.clearBoxes();
		// Set the extension file filter
		File[] files;

		// Get the files in the directory
        files = rootDirectory.listFiles();

		if (files != null && files.length > 0) {
			for(File f : files) {
				if (f.isHidden() && !mShowHiddenFiles) {
					// Don't add the file
					continue;
				}
				// Add the file the ArrayAdapter
				this.files.add(f);
			}

			Collections.sort(this.files, new FileComparator());
		}
		filePickerListAdapter.notifyDataSetChanged();
	}

	@Override
	public void onBackPressed()
    {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            if (ViewConfiguration.get(this).hasPermanentMenuKey() && actionBar.isShowing()) {
                actionBar.hide();
            }
        } else if (rootDirectory.getParentFile() != null) {
			// Go to parent directory
			rootDirectory = rootDirectory.getParentFile();
			refreshFilesList();
		} else {
            finish();
        }
	}

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        File newFile = (File)adapterView.getItemAtPosition(i);
//        if (newFile.isDirectory()) {
//            filePickerListAdapter.toggleCheckBox(newFile);
//            filePickerListAdapter.notifyDataSetChanged();
//        }
////        return true;
//    }

	@Override
//	protected void onItemClick(ListView l, View v, int position, long id)
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        rootDirectory = (File)adapterView.getItemAtPosition(i);
        refreshFilesList();
//		super.onListItemClick(l, v, position, id);
	}

    @Override
    public void mediaBroadcastReceiverCallback() {}

    @Override
    protected void setUpDrawer()
    {
        preferencesHelper.getMediaDirectories();
        ListView drawerList = (ListView) findViewById(R.id.left_drawer1);

        int drawerWidth = (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) ?
                getResources().getDisplayMetrics().widthPixels/4 :
                getResources().getDisplayMetrics().widthPixels/4;

        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) drawerList.getLayoutParams();
        params.width = drawerWidth;
        drawerList.setLayoutParams(params);

        drawerList.setAdapter(
                new NavigationDrawerAdapter(
                        this,
                        R.layout.drawer_list_item,
                        preferencesHelper.getMediaDirectories(),
                        preferencesHelper,
                        playerUIInterface.getTypeFace()
                )
        );
        drawerList.setOnTouchListener(onTouchDownHapticFeedback);
        drawerList.setOnItemClickListener(
                new NavDrawerClickListener(
                        this,
                        (DrawerLayout) findViewById(R.id.drawer_layout)
                )
        );
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
