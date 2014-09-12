package com.dontbelievethebyte.skipshuffle.activities;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.preferences.PreferencesHelper;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FilePickerActivity extends ListActivity {

	protected File mDirectory;
	protected ArrayList<File> mFiles;
	protected boolean singleMode= false;

	protected FilePickerListAdapter mAdapter;
	protected boolean mShowHiddenFiles = false;
    private PreferencesHelper preferencesHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        preferencesHelper = new PreferencesHelper(this);
		setContentView(R.layout.common_file_picker);

		// Set the view to be shown if the list is empty
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View emptyView = inflator.inflate(R.layout.file_picker_empty_view, null);
		((ViewGroup)getListView().getParent()).addView(emptyView);
		getListView().setEmptyView(emptyView);


		// Point to external storage as root.
        mDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

		// Initialize the ArrayList
		mFiles = new ArrayList<File>();

		// Set the ListAdapter
		mAdapter = new FilePickerListAdapter(this, mFiles);
		setListAdapter(mAdapter);

		Button ok = (Button)findViewById(R.id.ok);
		ok.setOnClickListener( new OnClickListener() {
			public void onClick(View v) {
				returnResults();
			}
		});
		if (singleMode) {
            ok.setVisibility(View.GONE);
        }
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

	@Override
	protected void onResume()
    {
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

	private class FilePickerListAdapter extends ArrayAdapter<File>
    {

		private List<File> mObjects;
		private ArrayList<File> checkedObjects = new ArrayList<File>();
		public FilePickerListAdapter(Context context, List<File> objects) {
			super(
                    context,
                    R.layout.common_file_picker_list_item,
                    android.R.id.text1, objects);
			mObjects = objects;
		}

		public void clearBoxes()
        {
			checkedObjects = new ArrayList<File>();
		}

		public String[] getFiles()
		{
            String[] mediaDirectoriesToScan = new String[checkedObjects.size()];
            int i = 0;
            //Save to a class instance array in case the activity needs to restart.
            for (File directory : checkedObjects){
                mediaDirectoriesToScan[i] = directory.getAbsolutePath();
                i++;
            }
            return mediaDirectoriesToScan;
		}

		public void toggleCheckBox(File file)
        {
			if (checkedObjects.contains(file)) {
                checkedObjects.remove(file);
            } else {
                checkedObjects.add(file);
            }
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
        {
			View row;
			CheckBox checkBox;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(
                        R.layout.common_file_picker_list_item,
                        parent,
                        false
                );
			} else {
				row = convertView;
			}

			final File object = mObjects.get(position);

			ImageView imageView = (ImageView)row.findViewById(R.id.file_picker_image);
			TextView textView = (TextView)row.findViewById(R.id.file_picker_text);
			checkBox = (CheckBox)row.findViewById(R.id.file_picker_checkbox);
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if ( isChecked ){
						if (!checkedObjects.contains(object))
							checkedObjects.add(object);
					}
					else {
						checkedObjects.remove(object);
					}

				}
			});
			if (singleMode){
				checkBox.setVisibility(View.GONE);
			}

			else {
				if (object.isFile()) {
                    checkBox.setVisibility(View.GONE);
                } else {
                    checkBox.setVisibility(View.VISIBLE);
                }
			}
			// Set single line
			textView.setSingleLine(true);
			if (checkedObjects.contains(object)) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
			textView.setText(object.getName());

    		// Show the folder icon
			imageView.setImageResource(
                    DrawableMapper.getFolderDrawable(preferencesHelper.getUIType())
            );
			return row;
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

}
