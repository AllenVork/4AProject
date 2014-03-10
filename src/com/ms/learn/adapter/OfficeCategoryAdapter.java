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

public class OfficeCategoryAdapter extends BaseExpandableListAdapter  {
	
	private Context mContext;
	 private LayoutInflater mInflater;
	List<OfficeCategorylist> officeCategoryLists;
	
	
	public OfficeCategoryAdapter(Context context,List<OfficeCategorylist> officeCategorylists) {
		super();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.officeCategoryLists = officeCategorylists;
	}
	
	@Override
	public OfficeCategoryItem getChild(int arg0, int arg1) {
		return officeCategoryLists.get(arg0).getCategoryItems().get(arg1);
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
		tv_catetoryName.setText(getChild(groupPosition, childPosition).getCategoryName());
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return officeCategoryLists.get(groupPosition).getCategoryItems().size();
	}
	@Override
	public List<OfficeCategoryItem> getGroup(int groupPosition) {
		return officeCategoryLists.get(groupPosition).getCategoryItems();
	}
	@Override
	public int getGroupCount() {
		return officeCategoryLists.size();
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
		tv_catetoryName.setText(officeCategoryLists.get(groupPosition).getCategoryItem().getCategoryName()+" ["+officeCategoryLists.get(groupPosition).getCategoryItems().size()+"]");
		
		
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
