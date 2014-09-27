package com.dontbelievethebyte.skipshuffle.activities;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
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

public class FilePickerActivity extends BaseActivity {

    private static final String TAG = "SkipShuffleFilePicker";
    private static final String LAST_CURRENT_DIRECTORY = "lastCurrentDirectory";
    private static final String LAST_CURRENT_WATCHED_DIRECTORIES = "lastCurrentWatchedDirectories";

    private File rootDirectory;
    private File externalStorageRootDirectory;
    private FilePickerListAdapter filePickerListAdapter;
    private FilePickerUI filePickerUI;
    private PreferencesHelper preferencesHelper;
    private ArrayList<File> currentWatchedDirectories;

    @Override
    protected void handleBackPressed(){
        if (externalStorageRootDirectory.equals(filePickerListAdapter.getCurrentListedDirectory())) {
            toastHelper.showShortToast(
                    getString(R.string.directory_top_level)
            );
        } else if (null != filePickerListAdapter.getCurrentListedDirectory().getParentFile()) {
            filePickerListAdapter.setCurrentListedDirectory(
                    filePickerListAdapter.getCurrentListedDirectory().getParentFile()
            );
            filePickerListAdapter.refreshFilesList();
        } else {
            finish();
        }
    }

    @Override
    public void mediaBroadcastReceiverCallback() {}

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);

        externalStorageRootDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        preferencesHelper = new PreferencesHelper(this);
        currentWatchedDirectories = new ArrayList<File>();

        rootDirectory = ( null != savedInstanceState &&
                          null != savedInstanceState.getSerializable(LAST_CURRENT_DIRECTORY)) ?
                                (File) savedInstanceState.getSerializable(LAST_CURRENT_DIRECTORY) :
                                externalStorageRootDirectory;

        currentWatchedDirectories = null != savedInstanceState &&
                                    null != savedInstanceState.getSerializable(LAST_CURRENT_WATCHED_DIRECTORIES) ?
                                    (ArrayList<File>) savedInstanceState.getSerializable(LAST_CURRENT_WATCHED_DIRECTORIES) :
                                        preferencesHelper.getMediaDirectories();
    }

	@Override
	protected void onResume()
    {
        super.onResume();
        filePickerListAdapter.refreshFilesList();
        toastHelper.showLongToast(
                getString(R.string.media_scan_sel_target_directories)
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(LAST_CURRENT_DIRECTORY, filePickerListAdapter.getCurrentListedDirectory());
        outState.putSerializable(LAST_CURRENT_WATCHED_DIRECTORIES, currentWatchedDirectories);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void setUI(Integer type) {

        filePickerUI = UIFactory.createFilePickerUI(this, type);

        filePickerListAdapter = new FilePickerListAdapter(
                this,
                new ArrayList<File>()
        );

        filePickerListAdapter.setCurrentSelectedDirectories(currentWatchedDirectories);
        filePickerListAdapter.setCurrentListedDirectory(rootDirectory);
        filePickerListAdapter.setTypeface(filePickerUI.getTypeFace());

        ListView listView = (ListView) findViewById(R.id.current_list);
        listView.setOnItemClickListener(new FilePickerClickListener(this));
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
                if (filePickerListAdapter.getSelectedDirectories().size() < 1) {
                    toastHelper.showShortToast(
                        getString(R.string.pick_media_nothing_selected)
                    );
                    setResult(RESULT_CANCELED);
                } else {
                    preferencesHelper.setMediaDirectories(filePickerListAdapter.getSelectedDirectories());
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
    protected void setUpDrawer()
    {
        super.setUpDrawer();

        ListView drawerList = (ListView) findViewById(R.id.left_drawer1);
        TextView headerView = (TextView) drawerList.findViewById(R.id.drawer_header);
        headerView.setText(
                getString(R.string.file_picker_drawer_title)
        );
        headerView.setTypeface(filePickerUI.getTypeFace());
        drawerList.addHeaderView(headerView);

        FilePickerDrawerAdapter filePickerDrawerAdapter = new FilePickerDrawerAdapter(
                this,
                R.layout.file_picker_drawer_list_item,
                currentWatchedDirectories,
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

}
