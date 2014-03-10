

package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.DownFileItem;


public class DownloadCompliteAdapter extends BaseAdapter {
	List<DownFileItem> officeDownLoadEntrys;
	Context mContext;

	public DownloadCompliteAdapter(Context context,List<DownFileItem> OfficeDownLoadEntrys) {
		super();
		officeDownLoadEntrys=OfficeDownLoadEntrys;
		mContext=context;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row=convertView;

		ViewHolder holder;

		if (row==null) {
			LayoutInflater inflater =  LayoutInflater.from(mContext);
			row=inflater.inflate(R.layout.videodownloaditem, parent,false);

			holder = new ViewHolder();

			holder.videoName = (TextView)row.findViewById(R.id.tv_videoName);
			
			row.setTag(holder);
		}
		else{
			holder = (ViewHolder) row.getTag();
		}

		holder.videoName.setText(officeDownLoadEntrys.get(position).getFileName());

		

		return row;
	}


	static class ViewHolder {
		TextView videoName;
		
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return officeDownLoadEntrys.size();
	}

	@Override
	public DownFileItem getItem(int position) {
		// TODO Auto-generated method stub
		return officeDownLoadEntrys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
