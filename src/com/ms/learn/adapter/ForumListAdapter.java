package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.ForumEntrylist;
import com.ms.learn.bean.ForumListEntry;
import com.ms.learn.bean.OfficeCategoryItem;

public class ForumListAdapter extends BaseExpandableListAdapter  {
	
	private Context mContext;
	 private LayoutInflater mInflater;
	List<ForumEntrylist> forumEntrylists;
	
	
	public ForumListAdapter(Context context,List<ForumEntrylist> forumEntrylistlists) {
		super();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.forumEntrylists = forumEntrylistlists;
	}
	
	@Override
	public ForumListEntry getChild(int arg0, int arg1) {
		return forumEntrylists.get(arg0).getForumListEntrys().get(arg1);
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
		tv_catetoryName.setText(getChild(groupPosition, childPosition).getForumTypeName());
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return forumEntrylists.get(groupPosition).getForumListEntrys().size();
	}
	@Override
	public List<ForumListEntry> getGroup(int groupPosition) {
		return forumEntrylists.get(groupPosition).getForumListEntrys();
	}
	@Override
	public int getGroupCount() {
		return forumEntrylists.size();
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
		tv_catetoryName.setText(forumEntrylists.get(groupPosition).getForumListEntry().getForumTypeName()+" ["+forumEntrylists.get(groupPosition).getForumListEntrys().size()+"]");
		
		
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
