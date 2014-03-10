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
import com.ms.learn.bean.ExamListEntry;
import com.ms.learn.bean.MostNewsPostsEntry;

public  class MostNewsFragmentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<MostNewsPostsEntry> mMostNewsPostsEntrys=new ArrayList<MostNewsPostsEntry>();
    Context context;

 
    
    public MostNewsFragmentAdapter(List<MostNewsPostsEntry> mMostNewsPostsEntry, Context context) {
		super();
		
		this.mMostNewsPostsEntrys = mMostNewsPostsEntry;
		this.context = context;
		mInflater = LayoutInflater.from(context); 
	}
    

    public int getCount() {
        return mMostNewsPostsEntrys.size();
    }
   
	public MostNewsPostsEntry getItem(int position) {
        return mMostNewsPostsEntrys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.mostnewsfragment_item, parent ,false);

            holder = new ViewHolder();
            holder.tv_postsTitle = (TextView) convertView.findViewById(R.id.tv_postsTitle);
            holder.tv_recieve = (TextView) convertView.findViewById(R.id.tv_recieve);
            holder.tv_userName = (TextView) convertView.findViewById(R.id.tv_userName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MostNewsPostsEntry mMostNewsPostsEntry=getItem(position);
        holder.tv_postsTitle.setText(mMostNewsPostsEntry.getTitle());
        
        holder.tv_userName.setText("×÷Õß:"+mMostNewsPostsEntry.getUserName());
        holder.tv_recieve.setText("ÔÄ¶Á"+mMostNewsPostsEntry.getReadCount()+"/»Ø¸´"+mMostNewsPostsEntry.getReplyCount());
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_postsTitle ,tv_recieve,tv_userName;
    }
}