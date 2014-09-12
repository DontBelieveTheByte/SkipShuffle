package com.dontbelievethebyte.skipshuffle.activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.adapters.FilePickerListAdapter;
import com.dontbelievethebyte.skipshuffle.activities.util.MediaScannerDialog;
import com.dontbelievethebyte.skipshuffle.callback.PreferenceChangedCallback;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.FilePickerUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FilePickerActivity extends ListActivity implements PreferenceChangedCallback{

	protected File mDirectory;
	protected ArrayList<File> mFiles;
	protected boolean singleMode= false;

	protected FilePickerListAdapter mAdapter;
	protected boolean mShowHiddenFiles = false;

    private static final String TAG = "SkipShuffleFilePicker";
    private FilePickerUI filePickerUI;
    private PreferencesHelper preferencesHelper;
    private MediaScannerDialog mediaScannerDialog;
//    private FilePickerListAdapter filePickerListAdapter;

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        preferencesHelper = new PreferencesHelper(this);
        filePickerUI = new FilePickerUI(this);
		// Set the view to be shown if the list is empty
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View emptyView = inflator.inflate(
                R.layout.file_picker_empty_view,
                null
        );

		((ViewGroup)getListView().getParent()).addView(emptyView);

        getListView().setEmptyView(emptyView);


		// Point to external storage as root.
        mDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

		// Initialize the ArrayList
		mFiles = new ArrayList<File>();

		// Set the ListAdapter
		mAdapter = new FilePickerListAdapter(
                this,
                mFiles,
                preferencesHelper
        );

        setListAdapter(mAdapter);

		Button ok = (Button)findViewById(R.id.ok);
		ok.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				returnResults();
			}
		});

		this.getListView().setLongClickable(true);

        this.getListView().setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				File newFile = (File)parent.getItemAtPosition(position);
				if (newFile.isDirectory()) {
					mAdapter.toggleCheckBox(newFile);
					if (singleMode) {
						returnResults();
					} else {
                        mAdapter.notifyDataSetChanged();
                    }
				}
				return true;
			}
		});
        Toast.makeText(getApplicationContext(), R.string.media_scan_sel_target_directories, Toast.LENGTH_LONG).show();
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
		mFiles.clear();
		//clear the checked item list
		mAdapter.clearBoxes();
		// Set the extension file filter
		File[] files;

		// Get the files in the directory
        files = mDirectory.listFiles();

		if (files != null && files.length > 0) {
			for(File f : files) {
				if (f.isHidden() && !mShowHiddenFiles|| f.isFile()) {
					// Don't add the file
					continue;
				}
				// Add the file the ArrayAdapter
				mFiles.add(f);
			}

			Collections.sort(mFiles, new FileComparator());
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onBackPressed()
    {
		if (mDirectory.getParentFile() != null) {
			// Go to parent directory
			mDirectory = mDirectory.getParentFile();
			refreshFilesList();
			return;
		}

		finish();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
    {
        mDirectory = (File)l.getItemAtPosition(position);
        refreshFilesList();
		super.onListItemClick(l, v, position, id);
	}

    public void preferenceChangedCallback(String prefsKey)
    {
        if (prefsKey.equals(getString(R.string.pref_media_directories))) {
            Log.d(TAG, "PREF DIRECTORY CHANGED!!@!#!!#%%%$$$");
            mediaScannerDialog = new MediaScannerDialog(
                    this,
                    new ProgressDialog(FilePickerActivity.this)
            );
            mediaScannerDialog.doScan();
        }
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

    private void returnResults()
    {
        if (mAdapter.getFiles().length < 1) {
            Toast.makeText(
                    this,
                    R.string.pick_media_nothing_selected,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            preferencesHelper.setMediaDirectories(mAdapter.getFiles());
            finish();
        }
    }
}
