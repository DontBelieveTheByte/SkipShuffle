package com.dontbelievethebyte.skipshuffle.activities.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;
import com.dontbelievethebyte.skipshuffle.activities.exception.ParentDirectoryException;
import com.dontbelievethebyte.skipshuffle.activities.exception.SubdirectoryException;
import com.dontbelievethebyte.skipshuffle.activities.util.DirectoryComparator;
import com.dontbelievethebyte.skipshuffle.activities.util.ToastHelper;
import com.dontbelievethebyte.skipshuffle.ui.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FilePickerListAdapter extends ArrayAdapter<File>
{
    private static class ViewHolder {
        public ImageView image;
        public TextView fileName;
        public CheckBox checkBox;
    }

    private List<File> listedFiles;
    private Drawable folderDrawable;
    private Drawable fileDrawable;
    private DirectoryComparator directoryComparator;
    private File currentListedDirectory;
    private ArrayList<File> currentSelectedDirectories;
    private int fileNameColor;
    private int checkBoxDrawable;
    private int checkBoxSubDirectorySelectedDrawable;
    private Typeface typeface;
    private ToastHelper toastHelper;

    public FilePickerListAdapter(FilePickerActivity filePickerActivity, List<File> files)
    {
        super(
                filePickerActivity,
                R.layout.file_picker_list_item,
                android.R.id.text1,
                files
        );

        this.listedFiles = files;
        toastHelper = filePickerActivity.getToastHelper();

        folderDrawable = filePickerActivity.getResources().getDrawable(
                    DrawableMapper.getFolder(filePickerActivity.getPreferencesHelper().getUIType()
                    )
        );

        fileDrawable = filePickerActivity.getResources().getDrawable(
                DrawableMapper.getFile(filePickerActivity.getPreferencesHelper().getUIType()
                )
        );

        checkBoxDrawable = DrawableMapper.getCheckbox(
                filePickerActivity.getPreferencesHelper().getUIType()
        );
        checkBoxSubDirectorySelectedDrawable = DrawableMapper.getCheckboxSubdirectorySelected(
                filePickerActivity.getPreferencesHelper().getUIType()
        );

        fileNameColor = filePickerActivity.getResources().getColor(
                ColorMapper.getSongLabel(filePickerActivity.getPreferencesHelper().getUIType())
        );
        directoryComparator = new DirectoryComparator();
    }

    public ArrayList<File> getSelectedDirectories()
    {
        return currentSelectedDirectories;
    }

    public void addWatchedDirectory(File checkBoxAssociatedFile){
        try {
            if (!currentSelectedDirectories.contains(checkBoxAssociatedFile)) {
                isSubdirectorySelected(checkBoxAssociatedFile);
                isParentDirectorySelected(checkBoxAssociatedFile);
                currentSelectedDirectories.add(checkBoxAssociatedFile);
                toastHelper.showShortToast(
                        String.format(
                                getContext().getString(R.string.directory_added),
                                checkBoxAssociatedFile.getName()
                        )
                );
            }
        } catch (SubdirectoryException subdirectoryException) {
            currentSelectedDirectories.remove(
                    subdirectoryException.getSubdirectory()
            );
            toastHelper.showShortToast(
                    String.format(
                            getContext().getString(R.string.sub_directory_selected),
                            checkBoxAssociatedFile.getName()
                    )
            );
        } catch (ParentDirectoryException parentDirectoryException) {
            currentSelectedDirectories.remove(
                    parentDirectoryException.getParentDirectory()
            );
            toastHelper.showShortToast(
                    String.format(
                            getContext().getString(R.string.parent_directory_selected),
                            checkBoxAssociatedFile.getName()
                    )
            );
        } finally {
            notifyDataSetChanged();
        }
    }

    public File getCurrentListedDirectory() {
        return currentListedDirectory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(
                    R.layout.file_picker_list_item,
                    parent,
                    false
            );
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        File file = listedFiles.get(position);

        viewHolder.image = setFolderImage(
                convertView,
                R.id.file_picker_image,
                file
        );

        viewHolder.fileName = setFileName(
                convertView,
                R.id.file_picker_text,
                file
        );

        viewHolder.checkBox = setCheckbox(
                convertView,
                R.id.file_picker_checkbox,
                file
        );
        return convertView;
    }

    public boolean isSubdirectorySelected(File parentDirectory) throws SubdirectoryException
    {
        try {
            String parentDirectoryName = parentDirectory.getCanonicalPath();

            for (File directory : currentSelectedDirectories) {
                String directoryName = directory.getCanonicalPath();
                if (!parentDirectoryName.equals(directoryName) &&
                        directory.getCanonicalPath().startsWith(
                                parentDirectory.getCanonicalPath()
                        )
                        ) {
                    throw new SubdirectoryException(directory);
                }
            }
            return false;
        } catch (IOException exception){
            return false;
        }
    }

    public boolean isParentDirectorySelected(File subDirectory) throws ParentDirectoryException
    {
//        try {
//            String parentDirectoryName = subDirectory.getCanonicalPath();
//
//            for (File directory : currentSelectedDirectories) {
//                String directoryName = directory.getCanonicalPath();
//                if (!parentDirectoryName.equals(directoryName) &&
//                        directory.getCanonicalPath().startsWith(
//                                subDirectory.getCanonicalPath()
//                        )
//                        ) {
//                    return true;
//                }
//            }
//            return false;
//        } catch (IOException exception){
        return false;
//        }
    }

    public void refreshFilesList()
    {
        File newFiles[] = currentListedDirectory.listFiles();
        if (null != newFiles) {
            listedFiles.clear(); // Clear the listedFiles ArrayList
            listedFiles.addAll(Arrays.asList(newFiles));
            Collections.sort(
                    listedFiles,
                    directoryComparator
            );
            notifyDataSetChanged();
        } else {
            currentListedDirectory = currentListedDirectory.getParentFile();
            toastHelper.showShortToast(
                    getContext().getString(R.string.no_access)
            );
        }
    }

    public void removeWatchedDirectory(File checkBoxAssociatedFile){
        try {
            if (currentSelectedDirectories.contains(checkBoxAssociatedFile)) {
                isSubdirectorySelected(checkBoxAssociatedFile);
                isParentDirectorySelected(checkBoxAssociatedFile);
                currentSelectedDirectories.add(checkBoxAssociatedFile);
                toastHelper.showShortToast(
                        getContext().getString(
                                R.string.directory_removed
                        )
                );
            }
        } catch (SubdirectoryException subdirectoryException) {
            currentSelectedDirectories.remove(
                    subdirectoryException.getSubdirectory()
            );
            toastHelper.showShortToast(
                    getContext().getString(
                            R.string.sub_directory_selected
                    )
            );
        } catch (ParentDirectoryException parentDirectoryException) {
            currentSelectedDirectories.remove(
                    parentDirectoryException.getParentDirectory()
            );

            toastHelper.showShortToast(
                    getContext().getString(
                            R.string.parent_directory_selected
                    )
            );
        } finally {
            notifyDataSetChanged();
        }
    }

    public void setCurrentListedDirectory(File currentListedDirectory) {
        this.currentListedDirectory = currentListedDirectory;
        refreshFilesList();
    }

    public void setCurrentSelectedDirectories(ArrayList<File> currentSelectedDirectories)
    {
        this.currentSelectedDirectories = currentSelectedDirectories;
    }

    public void setTypeface(Typeface typeface)
    {
        this.typeface = typeface;
    }

    private ImageView setFolderImage(View view, int resourceId, final File file)
    {
        ImageView folderImage = (ImageView) view.findViewById(resourceId);
        folderImage.setImageDrawable(
                file.isDirectory() ?
                        folderDrawable:
                        fileDrawable
        );
        return folderImage;
    }

    private TextView setFileName(View view, int resourceId, File file)
    {
        TextView fileName = (TextView) view.findViewById(resourceId);
        fileName.setText(file.getName());
        fileName.setTextColor(fileNameColor);
        if (null != typeface) {
            fileName.setTypeface(typeface);
        }
        return fileName;
    }

    private CheckBox setCheckbox(View view, int resourceId, final File associatedDirectory)
    {
        CheckBox checkBox = (CheckBox) view.findViewById(resourceId);
        if (associatedDirectory.isDirectory()) {
            boolean subDirectorySelected = false;
            boolean parentDirectorySelected = false;
            try {
                subDirectorySelected = isSubdirectorySelected(associatedDirectory);
                parentDirectorySelected = isParentDirectorySelected(associatedDirectory);
            } catch (SubdirectoryException subDirectoryException) {
                subDirectorySelected = true;
            } catch (ParentDirectoryException parentDirectoryException) {
               parentDirectorySelected = true;
            }
            checkBox.setBackgroundResource(
                    !subDirectorySelected  && !parentDirectorySelected?
                            checkBoxDrawable :
                            checkBoxSubDirectorySelectedDrawable
            );

            checkBox.setVisibility(View.VISIBLE);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if (isChecked) {
                        addWatchedDirectory(associatedDirectory);
                    } else {
                        removeWatchedDirectory(associatedDirectory);
                    }
                }
            });

            checkBox.setChecked(
                    currentSelectedDirectories.contains(associatedDirectory) || currentSelectedDirectories.contains(associatedDirectory) || subDirectorySelected
            );
        } else {
            checkBox.setVisibility(View.GONE);
        }
        return checkBox;
    }
}

