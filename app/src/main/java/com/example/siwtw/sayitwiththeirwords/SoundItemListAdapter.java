package com.example.siwtw.sayitwiththeirwords;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matt on 4/2/2016.
 */
public class SoundItemListAdapter extends ArrayAdapter<SoundItem> {
    private Activity context;
    private ArrayList<SoundItem> soundItems = new ArrayList<>();

    public SoundItemListAdapter(Activity context, ArrayList<SoundItem> soundItems) {
        super(context, R.layout.sound_list_item, soundItems);

        this.context=context;
        this.soundItems=soundItems;
    }

    @Override
    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.sound_list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.soundDisplayName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.soundDisplayIcon);

        txtTitle.setText(soundItems.get(position).displayName);

        if (soundItems.get(position).hasIcon()) {
            Bitmap iconBitmap = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath() + "/SIWTW/icons/" + soundItems.get(position).iconPath);
            imageView.setImageBitmap(iconBitmap);
        }
        else {
            imageView.setImageResource(R.drawable.ic_launcher);
        }
        return rowView;
    };
}
