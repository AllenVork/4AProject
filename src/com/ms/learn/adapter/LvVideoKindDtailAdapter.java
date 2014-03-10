package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.VideoKindDetailItem;
import com.ms.learn.widgets.RemoteImageView;

public  class LvVideoKindDtailAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    List<VideoKindDetailItem> videoKindDetailItems;
    Context context;

    public LvVideoKindDtailAdapter(Context context,  List<VideoKindDetailItem> videoKindDetailItems) {
        mInflater = LayoutInflater.from(context);
        this.videoKindDetailItems=videoKindDetailItems;
        this.context=context;

    }

    public int getCount() {
        return videoKindDetailItems.size();
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
            convertView = mInflater.inflate(R.layout.videokinddtail_item, parent,false);

            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_kindNameDetail); 
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_videoChangetime); 
            holder.imgV = (RemoteImageView) convertView.findViewById(R.id.img_videokinddetail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        VideoKindDetailItem detailItem=videoKindDetailItems.get(position);
        holder.tv_name.setText(detailItem.getVideoName());
        String time=detailItem.getVideoChangeTime();
        
        holder.tv_time.setText(time.replace("T", "  ").substring(0, time.indexOf(".")+1));
        holder.imgV.setDefaultImage(R.drawable.default_small);
        //œ¬‘ÿÕº∆¨
       holder.imgV.setImageUrl(detailItem.getVideoImagUrl());
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_name,tv_time;
        RemoteImageView  imgV;
    }
}