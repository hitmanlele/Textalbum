package com.bbg.textalbum;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class ChildAdapter extends BaseAdapter {
    List<String> paths = new ArrayList<>();
    Context mContext;
    public ChildAdapter(Context mContext,List<String>paths){
        this.paths = paths;
        this.mContext=mContext;

    }
    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public Object getItem(int i) {
        return paths.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View cView = inflater.inflate(R.layout.child_item,null);
        ImageView imageView = (ImageView) cView.findViewById(R.id.c_image);
        Glide.with(mContext)
                .load(paths.get(i))
                .into(imageView);
        return cView;
    }
}
