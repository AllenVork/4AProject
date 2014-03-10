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
import com.ms.learn.bean.ResearchListEntry;

public  class ResearchListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ResearchListEntry> mResearchListEntrys=new ArrayList<ResearchListEntry>();
    Context context;

 
    
    public ResearchListAdapter(List<ResearchListEntry> mResearchListEntry, Context context) {
		super();
		
		this.mResearchListEntrys = mResearchListEntry;
		this.context = context;
		mInflater = LayoutInflater.from(context); 
	}
    

    public int getCount() {
        return mResearchListEntrys.size();
    }
   
	public ResearchListEntry getItem(int position) {
        return mResearchListEntrys.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.researchlist_item, parent ,false);

            holder = new ViewHolder();
            holder.tv_reaserchName = (TextView) convertView.findViewById(R.id.tv_researchName);
            holder.tv_doit = (TextView) convertView.findViewById(R.id.tv_showDoit);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ResearchListEntry mResearchListEntry=getItem(position);
        holder.tv_reaserchName.setText(mResearchListEntry.getResearchName());
        if(mResearchListEntry.getState().equals("0")){
        	 holder.tv_doit.setText("Î´×ö");
        }else {
        	 holder.tv_doit.setText("ÒÑ×ö");
        }
        
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_reaserchName,tv_doit;
    }
}