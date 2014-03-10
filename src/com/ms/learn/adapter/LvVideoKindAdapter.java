package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.VideoKind;
import com.ms.learn.bean.VideoKindList;
import com.ms.learn.widgets.RemoteImageView;

public  class LvVideoKindAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<VideoKindList> kindLists=new ArrayList<VideoKindList>();
    Context context;

    public LvVideoKindAdapter(Context context,  List<VideoKindList> kindLists) {
        mInflater = LayoutInflater.from(context);
        this.kindLists=kindLists;
        this.context=context;

    }

    public int getCount() {
        return kindLists.size();
    }
    public Object getItem(int position) {
        return kindLists.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.videokind_layout, parent ,false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tv_kindName);
           // holder.imgV = (RemoteImageView) convertView.findViewById(R.id.img_about);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VideoKind videoKind=kindLists.get(position).getVideoKind();
        holder.text.setText(videoKind.getpName());
       // holder.imgV.setDefaultImage(R.drawable.default_small);
        //œ¬‘ÿÕº∆¨
      //  holder.imgV.setImageUrl(galleryItems.get(position).getVideoImagUrl());
       
        return convertView;
    }

    static class ViewHolder {
        TextView text;
        RemoteImageView  imgV;
    }
}