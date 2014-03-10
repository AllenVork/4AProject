package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.OfficeCategoryItem;
import com.ms.learn.bean.OfficeCategorylist;
import com.ms.learn.bean.VideoKind;
import com.ms.learn.bean.VideoKindList;

public class CourseAdapter extends BaseExpandableListAdapter  {
	
	private Context mContext;
	 private LayoutInflater mInflater;
	 List<VideoKindList> mKindLists;
	
	public CourseAdapter(Context context,List<VideoKindList> kindLists) {
		super();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mKindLists = kindLists;
	}
	
	@Override
	public VideoKind getChild(int arg0, int arg1) {
		return mKindLists.get(arg0).getVideoKinds().get(arg1);
	}
	@Override
	public long getChildId(int arg0, int arg1) {
		
		return arg1;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition,	boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView =mInflater.inflate(R.layout.categoryitem, parent, false);
		}
		TextView tv_catetoryName=(TextView) convertView.findViewById(R.id.tv_categoryname);
		tv_catetoryName.setText(getChild(groupPosition, childPosition).getpName());
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return mKindLists.get(groupPosition).getVideoKinds().size();
	}
	@Override
	public List<VideoKind> getGroup(int groupPosition) {
		return mKindLists.get(groupPosition).getVideoKinds();
	}
	@Override
	public int getGroupCount() {
		return mKindLists.size();
	}
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		if(convertView==null){
			 convertView=mInflater.inflate(R.layout.groupcategoryitem, parent, false);
		}
		TextView tv_catetoryName=(TextView) convertView.findViewById(R.id.tv_categoryname);
		tv_catetoryName.setText(mKindLists.get(groupPosition).getVideoKind().getpName()+" ["+mKindLists.get(groupPosition).getVideoKinds().size()+"]");
		
		
		return convertView;
	}
	@Override
	public boolean hasStableIds() {
		return false;
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
    
	    	
}
