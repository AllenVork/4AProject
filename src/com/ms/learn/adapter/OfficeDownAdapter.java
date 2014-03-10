package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.DownFileItem;
import com.ms.learn.bean.OfficeDownLoadEntry;

public class OfficeDownAdapter extends BaseExpandableListAdapter  {
	
	private Context mContext;
	 private LayoutInflater mInflater;
	List<OfficeDownLoadEntry> officeDownLoadEntrys;
	
	
	public OfficeDownAdapter(Context context,List<OfficeDownLoadEntry> officeDownLoadEntrys) {
		super();
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.officeDownLoadEntrys = officeDownLoadEntrys;
	}
	
	@Override
	public DownFileItem getChild(int arg0, int arg1) {
		return officeDownLoadEntrys.get(arg0).getDownFileItems().get(arg1);
	}
	@Override
	public long getChildId(int arg0, int arg1) {
		
		return arg1;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition,	boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView =mInflater.inflate(R.layout.officededownchilditem, parent, false);
		}
		DownFileItem downFileItem= getChild(groupPosition, childPosition);
		if(downFileItem!=null){
			TextView tv_fileName=(TextView) convertView.findViewById(R.id.tv_officeDownName);
			tv_fileName.setText(downFileItem.getFileName());
			
			//TextView tv_fileTime=(TextView) convertView.findViewById(R.id.tv_officeDownTime);
			//tv_fileTime.setText(downFileItem.getFileTime());
		}
		
		convertView.setTag(R.id.tv_abouttitle, groupPosition);
		convertView.setTag(R.id.tv_categoryname, childPosition);
		
		return convertView;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return officeDownLoadEntrys.get(groupPosition).getDownFileItems().size();
	}
	@Override
	public List<DownFileItem> getGroup(int groupPosition) {
		return officeDownLoadEntrys.get(groupPosition).getDownFileItems();
	}
	@Override
	public int getGroupCount() {
		return officeDownLoadEntrys.size();
	}
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		if(convertView==null){
			 convertView=mInflater.inflate(R.layout.officededownparentitem, parent, false);
		}
		TextView tv_catetoryName=(TextView) convertView.findViewById(R.id.tv_officeDownParentName);
		tv_catetoryName.setText(officeDownLoadEntrys.get(groupPosition).getParenFileName() +"["+officeDownLoadEntrys.get(groupPosition).getDownFileItems().size()+"]");
		
		convertView.setTag(R.id.tv_abouttitle, groupPosition);
		convertView.setTag(R.id.tv_categoryname, -1);
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
