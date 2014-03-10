package com.ms.learn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.bean.OfficeDetailItem;
import com.ms.learn.util.DownFile;

public  class OfficeDetailAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
	private List<OfficeDetailItem> officeDetailItems;
    Context context;
   String path;
    public OfficeDetailAdapter(Context context,  List<OfficeDetailItem> officeDetailItems,String parentCategory,String cId ) {
        mInflater = LayoutInflater.from(context);
        this.officeDetailItems=officeDetailItems;
        this.context=context;
       // this.path="/"+parentCategory+"/"+cId;
        this.path="/"+cId;

    }

    public int getCount() {
        return officeDetailItems.size();
    }
    public Object getItem(int position) {
        return officeDetailItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.officedetailitem, parent ,false);

            holder = new ViewHolder();
            holder.tv_officeName = (TextView) convertView.findViewById(R.id.tv_officeName);
            holder.tv_officeDiscr= (TextView) convertView.findViewById(R.id.tv_officeDecrible);
            holder.tv_officeDownTime = (TextView) convertView.findViewById(R.id.tv_officeDowntime);
            holder.ib_download=(Button) convertView.findViewById(R.id.ib_officeDown);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OfficeDetailItem  detailItem=officeDetailItems.get(position);
        holder.tv_officeName.setText(detailItem.getFileName());
        holder.tv_officeDiscr.setText(detailItem.getFileDescribe());
        holder.tv_officeDownTime.setText(detailItem.getDownCount());
        holder.ib_download.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//обтьнд╪Ч
				DownFile downFile=new DownFile();
				downFile.downLoadFile(officeDetailItems.get(position), context,path);
				
			}
		});
       
        return convertView;
    }

    static class ViewHolder {
        TextView tv_officeName,tv_officeDiscr,tv_officeDownTime;
        Button  ib_download;
    }
}