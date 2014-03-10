package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.OtherVideo;
import com.ms.learn.widgets.RemoteImageView;

public  class LvAboutAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    List<OtherVideo> OtherVideos;
    Context context;

    public LvAboutAdapter(Context context,  List<OtherVideo> OtherVideos) {
        mInflater = LayoutInflater.from(context);
        this.OtherVideos=OtherVideos;
        this.context=context;

    }

    public int getCount() {
        return OtherVideos.size();
    }
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lvaboutitem_layout, parent,false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tv_abouttitle);
         //   holder.imgV = (RemoteImageView) convertView.findViewById(R.id.img_about);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

      
        holder.text.setText(OtherVideos.get(position).getVideoName());
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