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
import com.ms.learn.bean.VideoHistoryEntry;

public  class HistoryAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<VideoHistoryEntry> videoHistoryEntries=new ArrayList<VideoHistoryEntry>();
    Context context;

 
    
    public HistoryAdapter(	List<VideoHistoryEntry> videoHistoryEntries, Context context) {
		super();
		
		this.videoHistoryEntries = videoHistoryEntries;
		this.context = context;
		mInflater = LayoutInflater.from(context); 
	}
    

    public int getCount() {
        return videoHistoryEntries.size();
    }
   
	public Object getItem(int position) {
        return videoHistoryEntries.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lv_historyitem, parent ,false);

            holder = new ViewHolder();
            holder.tv_videoName = (TextView) convertView.findViewById(R.id.tv_HistoryViedeoName);
            holder.tv_videoHadSee = (TextView) convertView.findViewById(R.id.tv_HistoryHadseeTime);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VideoHistoryEntry historyEntry=videoHistoryEntries.get(position);
        holder.tv_videoName.setText(historyEntry.getVideoName());
       String complite= historyEntry.getVideoWhen();
       if("yes".equals(complite)){
    	   holder.tv_videoHadSee.setText("≤•∑≈ÕÍ±œ");
       }else{
    	   holder.tv_videoHadSee.setText(historyEntry.getVideoHadSeeTime());
       }
       
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_videoName,tv_videoHadSee;
    }
}