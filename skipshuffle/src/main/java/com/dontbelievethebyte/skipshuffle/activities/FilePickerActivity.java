package com.dontbelievethebyte.skipshuffle.activities;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.adapters.FilePickerDrawerAdapter;
import com.dontbelievethebyte.skipshuffle.adapters.FilePickerListAdapter;
import com.dontbelievethebyte.skipshuffle.exceptions.ParentDirectoryException;
import com.dontbelievethebyte.skipshuffle.exceptions.SubdirectoryException;
import com.dontbelievethebyte.skipshuffle.utilities.FilePickerClickListener;
import com.dontbelievethebyte.skipshuffle.utilities.FilePickerNavDrawerClickListener;
import com.dontbelievethebyte.skipshuffle.ui.FilePickerUI;
import com.dontbelievethebyte.skipshuffle.ui.UIFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FilePickerActivity extends BaseActivity {

    private static final String LAST_CURRENT_DIRECTORY = "lastCurrentDirectory";
    private static final String LAST_CURRENT_WATCHED_DIRECTORIES = "lastCurrentWatchedDirectories";

    private ArrayList<BaseAdapter> childAdapters;
    private ArrayList<File> currentSelectedDirectories;
    private File currentListedDirectory;
    private File externalStorageRootDirectory;
    private FilePickerListAdapter filePickerListAdapter;
    private FilePickerUI filePickerUI;

    public void addSelectedDirectory(File newDirectory)
    {
        try {
            if (!currentSelectedDirectories.contains(newDirectory)) {
                isSubdirectorySelected(newDirectory);
                isParentDirectorySelected(newDirectory);
                currentSelectedDirectories.add(newDirectory);
                toastHelper.showShortToast(
                        String.format(
                                getString(R.string.directory_added),
                                newDirectory.getName()
                        )
                );
            }
        } catch (SubdirectoryException subdirectoryException) {
            currentSelectedDirectories.remove(
                    subdirectoryException.getSubdirectory()
            );
            toastHelper.showShortToast(
                    String.format(
                            getString(R.string.sub_directory_selected),
                            newDirectory.getName()
                    )
            );
        } catch (ParentDirectoryException parentDirectoryException) {
            currentSelectedDirectories.remove(
                    parentDirectoryException.getParentDirectory()
            );
            toastHelper.showShortToast(
                    String.format(
                            getString(R.string.parent_directory_selected),
                            newDirectory.getName()
                    )
            );
        } finally {
            notifyAdaptersDataSetChanged();
        }
    }

    public File getCurrentListedDirectory()
    {
        return currentListedDirectory;
    }

    public ArrayList<File> getCurrentSelectedDirectories()
    {
        return currentSelectedDirectories;
    }

    public boolean isSubdirectorySelected(File verifyingDirectory) throws SubdirectoryException
    {
        try {
            String verifyingDirectoryPath = verifyingDirectory.getCanonicalPath();

            for (File directory : currentSelectedDirectories) {
                String directoryPath = directory.getCanonicalPath();
                if (!verifyingDirectoryPath.equals(directoryPath) &&
                        directory.getCanonicalPath().startsWith(
                                verifyingDirectory.getCanonicalPath()
                        )
                        ) {
                    toastHelper.showLongToast("Children was found");
                    throw new SubdirectoryException(directory);
                }
            }
            return false;
        } catch (IOException exception){
            return false;
        }
    }

    public boolean isParentDirectorySelected(File verifyingDirectory) throws ParentDirectoryException
    {
        try {
            String verifyingDirectoryPath = verifyingDirectory.getCanonicalPath();

            for (File directory : currentSelectedDirectories) {
                String directoryPath = directory.getCanonicalPath();
                if (!verifyingDirectoryPath.equals(directoryPath) &&
                        directory.getCanonicalPath().startsWith(
                                verifyingDirectory.getCanonicalPath()
                        )
                        ) {
                    toastHelper.showLongToast("Parent was found");
                    throw new ParentDirectoryException(directory);
                }
            }
            return false;
        } catch (IOException exception){
            return false;
        }
    }

    @Override
    public void mediaBroadcastReceiverCallback() {/*Implements abstract for template method pattern.*/}

    public void notifyAdaptersDataSetChanged()
    {
        for (BaseAdapter adapter : childAdapters) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);

        externalStorageRootDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        childAdapters = new ArrayList<BaseAdapter>();

        currentListedDirectory = null != savedInstanceState &&
                                 null != savedInstanceState.getSerializable(LAST_CURRENT_DIRECTORY) ?
                                     (File) savedInstanceState.getSerializable(LAST_CURRENT_DIRECTORY) :
                                     externalStorageRootDirectory;

        currentSelectedDirectories = null != savedInstanceState &&
                                     null != savedInstanceState.getSerializable(LAST_CURRENT_WATCHED_DIRECTORIES) ?
                                         (ArrayList<File>) savedInstanceState.getSerializable(LAST_CURRENT_WATCHED_DIRECTORIES) :
                                         null;
    }

	@Override
	protected void onResume()
    {
        super.onResume();
        if (null == currentSelectedDirectories) {
            currentSelectedDirectories = preferencesHelper.getMediaDirectories();
        }
        filePickerListAdapter.refreshFilesList();
        toastHelper.showLongToast(
                getString(R.string.media_scan_sel_target_directories)
        );
        setNavigationDrawerContent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(
                LAST_CURRENT_DIRECTORY,
                getCurrentListedDirectory()
        );
        outState.putSerializable(
                LAST_CURRENT_WATCHED_DIRECTORIES,
                currentSelectedDirectories
        );
        super.onSaveInstanceState(outState);
    }

    public void removeSelectedDirectory(File directoryToRemove)
    {
        if (currentSelectedDirectories.contains(directoryToRemove)) {
            currentSelectedDirectories.remove(directoryToRemove);
            notifyAdaptersDataSetChanged();
            toastHelper.showShortToast(
                    String.format(
                            getString(R.string.directory_removed),
                            directoryToRemove
                    )
            );
        }
    }

    public void setCurrentListedDirectory(File currentListedDirectory)
    {
        this.currentListedDirectory = currentListedDirectory;
    }

    @Override
    protected void handleBackPressed()
    {
        if (externalStorageRootDirectory.equals(getCurrentListedDirectory())) {
            toastHelper.showShortToast(
                    getString(R.string.directory_top_level)
            );
        } else if (null != getCurrentListedDirectory().getParentFile()) {
            setCurrentListedDirectory(
                    getCurrentListedDirectory().getParentFile()
            );
            filePickerListAdapter.refreshFilesList();
        } else {
            finish();
        }
    }

    @Override
    protected void setUI(Integer type) {

        filePickerUI = UIFactory.createFilePickerUI(this, type);

        ArrayList<File> listedFile = new ArrayList<File>(
                Arrays.asList(currentListedDirectory.listFiles())
        );
        filePickerListAdapter = new FilePickerListAdapter(
                this,
                listedFile
        );
        filePickerListAdapter.setTypeface(filePickerUI.getTypeFace());
        childAdapters.add(filePickerListAdapter);

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
                if (getCurrentSelectedDirectories().size() < 1) {
                    toastHelper.showShortToast(
                        getString(R.string.pick_media_nothing_selected)
                    );
                    setResult(RESULT_CANCELED);
                } else {
                    preferencesHelper.setMediaDirectories(getCurrentSelectedDirectories());
                    setResult(RESULT_OK);
                }
                finish();
            }
        };

        View.OnClickListener cancelClickListener= new View.OnClickListener() {
            public void onClick(View v)
            {
                toastHelper.showShortToast(
                        getString(R.string.pick_media_nothing_selected)
                );
                setResult(RESULT_CANCELED);
                finish();
            }
        };

        ImageButton backButton = (ImageButton)findViewById(R.id.back);
        backButton.setOnTouchListener(this);
        backButton.setOnClickListener(backClickListener);

        ImageButton cancelButton = (ImageButton)findViewById(R.id.cancel);
        backButton.setOnTouchListener(this);
        cancelButton.setOnClickListener(cancelClickListener);

        ImageButton okButton = (ImageButton)findViewById(R.id.ok);
        okButton.setOnTouchListener(this);
        okButton.setOnClickListener(okClickListener);
    }

    @Override
    protected void setNavigationDrawerContent()
    {
        ListView drawerList = (ListView) findViewById(R.id.nav_drawer);
        drawerList.setOnTouchListener(this);
        drawerList.setOnItemClickListener(new FilePickerNavDrawerClickListener());

//        TextView headerView = (TextView) findViewById(R.id.drawer_header);
//        headerView.setText(
//                getString(R.string.file_picker_drawer_title)
//        );
//        drawerList.addHeaderView(headerView);
//        headerView.setTypeface(filePickerUI.getTypeFace());
        drawerList.setOnItemClickListener(
                new FilePickerNavDrawerClickListener()
        );
        navDrawerListAdapter = new FilePickerDrawerAdapter(
                this,
                R.layout.file_picker_drawer_list_item,
                filePickerUI.getTypeFace()
        );
        drawerList.setAdapter(navDrawerListAdapter);
//        drawerList.setOnItemClickListener(navDrawerItemClickListener);

        childAdapters.add(navDrawerListAdapter);
    }
}
