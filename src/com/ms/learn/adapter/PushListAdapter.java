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
import com.ms.learn.bean.PostsReciveEnrty;
import com.ms.learn.bean.PushItem;
import com.ms.learn.bean.VideoKind;
import com.ms.learn.bean.VideoKindList;
import com.ms.learn.widgets.RemoteImageView;

public  class PushListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<PushItem> pushItems=new ArrayList<PushItem>();
    Context context;

    public PushListAdapter(Context context,  List<PushItem> pushItems) {
        mInflater = LayoutInflater.from(context);
        this.pushItems=pushItems;
        this.context=context;

    }

    public int getCount() {
        return pushItems.size();
    }
    public PushItem getItem(int position) {
        return pushItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pushlistitem_layout, parent ,false);

            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_pushTilte);
            holder.tv_pushTime = (TextView) convertView.findViewById(R.id.tv_pushTime);
           // holder.imgV = (RemoteImageView) convertView.findViewById(R.id.img_about);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PushItem pushItem=pushItems.get(position);
        holder.tv_title.setText(pushItem.getTitle());
        String time =pushItem.getPushTime();
        
        holder.tv_pushTime.setText(time.replace("T", " ").substring(0, time.lastIndexOf(":")+3));
       // holder.imgV.setDefaultImage(R.drawable.default_small);
        //œ¬‘ÿÕº∆¨
      //  holder.imgV.setImageUrl(galleryItems.get(position).getVideoImagUrl());
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_title,tv_pushTime;
       
    }
}