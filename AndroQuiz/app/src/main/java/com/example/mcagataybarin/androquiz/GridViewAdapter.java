package com.example.mcagataybarin.androquiz;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by mcagataybarin on 3/26/17.
 */

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<String> flags;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<String> flags) {
        super(context, layoutResourceId, flags);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.flags = flags;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.grid_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.image.setImageBitmap(ImageViaAssets(flags.get(position)));
        return row;
    }


    static class ViewHolder {
        ImageView image;
    }

    public Bitmap ImageViaAssets(String fileName){

        AssetManager assetmanager = context.getAssets();
        InputStream is = null;
        try{

            is = assetmanager.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }

    @Override
    public boolean isEnabled(int position){
        return MemoData.getInstance().isCellClickable(position);
    }

}
