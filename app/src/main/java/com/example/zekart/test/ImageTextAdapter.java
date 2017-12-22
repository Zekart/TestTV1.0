package com.example.zekart.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Zekart on 22.12.2017.
 */

public class ImageTextAdapter extends BaseAdapter {

    private int icons[];
    private String letters[];
    private Context context;
    private LayoutInflater layoutInflater;
    View view;


    public ImageTextAdapter(Context context,int icons[],String letters[]){
        this.context=context;
        this.icons = icons;
        this.letters = letters;
    }

    @Override
    public int getCount() {
        return letters.length;
    }

    @Override
    public Object getItem(int  position) {
        return letters[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View conview, ViewGroup viewGroup) {
        layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (conview == null){
            view = new View(context);
            view = layoutInflater.inflate(R.layout.fragment_list,null);
            ImageView icon = (ImageView) view.findViewById(R.id.imageView);

            icon.setImageResource(icons[position]);
            TextView textView = (TextView) view.findViewById(R.id.texticon);
            textView.setText(letters[position]);
        }


        return view;
    }
}
