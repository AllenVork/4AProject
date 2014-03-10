package com.ms.learn.adapter;

import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.GridView;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.activity.VideoDetailActivity;
import com.ms.learn.bean.VideoInfo;
import com.ms.learn.widgets.RemoteImageView;

public  class GalleryAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    List<VideoInfo> galleryItems;
    Context context;
    HashMap<Integer, View> hashMapView=new HashMap<Integer, View>();

    public GalleryAdapter(Context context,List<VideoInfo> galleryItems) {
        mInflater = LayoutInflater.from(context);
        this.galleryItems=galleryItems;
        this.context=context;

    }

    public int getCount() {
        return galleryItems.size();
    }
    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view=hashMapView.get(position);

        if (view== null) {
        	view = mInflater.inflate(R.layout.galleryitem_layout,parent, false);
        	
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.tv_gallrytitle);
            holder.tv_desc = (TextView) view.findViewById(R.id.tv_gallryDisc);
            holder.imgV = (RemoteImageView) view.findViewById(R.id.img_gallry);
           // convertView.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            hashMapView.put(position, view);
            view.setTag(holder);
        } else {
        	view=hashMapView.get(position);
            holder = (ViewHolder) view.getTag();
        }

      
        holder.text.setText(galleryItems.get(position).getVideoTitle());
        holder.tv_desc.setText(galleryItems.get(position).getVideoInfo());
        
        holder.imgV.setDefaultImage(R.drawable.default_small);
        //œ¬‘ÿÕº∆¨
        holder.imgV.setImageUrl(galleryItems.get(position).getVideoImagUrl());
        holder.imgV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(context,VideoDetailActivity.class);
				System.out.println("++++++++++++++"+galleryItems.get(position).getVideoTypeName());
				
				intent.putExtra("videoID", galleryItems.get(position).getVideoID());
				context.startActivity(intent);
			}
		});
       // convertView.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        
        return view;
    }

    static class ViewHolder {
        TextView text,tv_desc;
        RemoteImageView  imgV;
    }
}