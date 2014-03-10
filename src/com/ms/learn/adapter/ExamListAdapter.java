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

public  class ExamListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ExamListEntry> mExamListEntrys=new ArrayList<ExamListEntry>();
    Context context;

 
    
    public ExamListAdapter(List<ExamListEntry> mExamListEntry, Context context) {
		super();
		
		this.mExamListEntrys = mExamListEntry;
		this.context = context;
		mInflater = LayoutInflater.from(context); 
	}
    

    public int getCount() {
        return mExamListEntrys.size();
    }
   
	public ExamListEntry getItem(int position) {
        return mExamListEntrys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.examlist_item, parent ,false);

            holder = new ViewHolder();
            holder.tv_ExamName = (TextView) convertView.findViewById(R.id.tv_examName);
            holder.tv_doit = (TextView) convertView.findViewById(R.id.tv_showDoit);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ExamListEntry mExamListEntry=getItem(position);
        holder.tv_ExamName.setText(mExamListEntry.getExamName());
        if(mExamListEntry.getExamGrade().equals("-1")){
        	 holder.tv_doit.setText("Î´×ö");
        }else {
        	 holder.tv_doit.setText(mExamListEntry.getExamGrade());
        }
        
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_ExamName,tv_doit;
    }
}