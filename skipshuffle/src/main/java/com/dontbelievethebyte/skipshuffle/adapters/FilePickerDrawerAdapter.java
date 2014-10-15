package com.dontbelievethebyte.skipshuffle.adapters;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dontbelievethebyte.skipshuffle.R;
import com.dontbelievethebyte.skipshuffle.activities.FilePickerActivity;
import com.dontbelievethebyte.skipshuffle.ui.ColorMapper;
import com.dontbelievethebyte.skipshuffle.ui.DrawableMapper;

import java.io.File;

public class FilePickerDrawerAdapter extends ArrayAdapter<File> {

    private static class ViewHolder {
        public TextView title;
        public ImageButton removeButton;
    }

    private Drawable removeButtonDrawable;
    private FilePickerActivity filePickerActivity;
    private int selectedTextBackgroundColor;
    private int textColor;
    private Typeface typeface;

    public FilePickerDrawerAdapter(FilePickerActivity filePickerActivity, int layoutResource, Typeface typeFace)
    {
        super(
                filePickerActivity,
                layoutResource,
                filePickerActivity.getCurrentSelectedDirectories()
        );
        this.filePickerActivity = filePickerActivity;
        this.typeface = typeFace;

        selectedTextBackgroundColor = ColorMapper.getListDivider(
                filePickerActivity.getPreferencesHelper().getUIType()
        );
        textColor = filePickerActivity.getResources().getColor(
                ColorMapper.getNavDrawerText(filePickerActivity.getPreferencesHelper().getUIType())
        );
        removeButtonDrawable = filePickerActivity.getResources().getDrawable(
                DrawableMapper.getRemove(filePickerActivity.getPreferencesHelper().getUIType())
        );
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;

        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = filePickerActivity.getLayoutInflater().inflate(
                    R.layout.file_picker_drawer_list_item,
                    parent,
                    false)
            ;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title = setTitle(
                convertView,
                getItem(position).getAbsolutePath(),
                R.id.drawer_item_text,
                position
        );

//        viewHolder.title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                notifyDataSetChanged();
//                viewHolder.title.setSelected(true);
//                Log.d(BaseActivity.TAG, "CLOCKCKCK");
//            }
//        });
        viewHolder.removeButton = setRemoveButton(
                convertView,
                R.id.remove
        );

//        if (null != selectedItem && position == selectedItem) {@TODO Fix this fucking shit.
//            convertView.setBackgroundColor(selectedTextBackgroundColor);
//        }

        return convertView;
    }

    private TextView setTitle(View view, String text, int resourceId, int position)
    {
        TextView tv = (TextView) view.findViewById(resourceId);
        if (null != typeface) {
            tv.setTypeface(typeface);
        }
        tv.setText(text);
        tv.setTextColor(textColor);
        return tv;
    }

    private ImageButton setRemoveButton(View view, int resourceId)
    {
        ImageButton removeButton = (ImageButton) view.findViewById(resourceId);
        removeButton.setImageDrawable(removeButtonDrawable);
        removeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView txt = (TextView) view.findViewById(R.id.drawer_item_text);
                        Log.d("SHIT MOTHERFUCKER BITCH CUNT§", "Touched : " + txt.getText());
                    }
                }
        );
        return removeButton;
    }
}
