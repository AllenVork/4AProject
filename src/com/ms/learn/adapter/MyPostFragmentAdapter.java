package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.bean.MostNewsPostsEntry;
import com.ms.learn.fragment.MyPostsFragment;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;

public  class MyPostFragmentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private int positon;
    private List<MostNewsPostsEntry> mMostNewsPostsEntrys=new ArrayList<MostNewsPostsEntry>();
    Context context;

 
    
    public MyPostFragmentAdapter(List<MostNewsPostsEntry> mMostNewsPostsEntry, Context context) {
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
            convertView = mInflater.inflate(R.layout.mypostfragment_item, parent ,false);

            holder = new ViewHolder();
            holder.tv_postsTitle = (TextView) convertView.findViewById(R.id.tv_postsTitle);
            holder.tv_postsTime = (TextView) convertView.findViewById(R.id.tv_postsTime);
            holder.tv_recieve = (TextView) convertView.findViewById(R.id.tv_recieve);
            holder.bt_delete=(Button) convertView.findViewById(R.id.bt_delete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final MostNewsPostsEntry mMostNewsPostsEntry=getItem(position);
        holder.tv_postsTitle.setText(mMostNewsPostsEntry.getTitle());
        
        String time=mMostNewsPostsEntry.getUserTime();
        String str_Time=time.replace("T", " ").substring(0, time.lastIndexOf(":")+3);
        holder.tv_postsTime.setText(str_Time);
        holder.tv_recieve.setText("ÔÄ¶Á"+mMostNewsPostsEntry.getReadCount()+"/»Ø¸´"+mMostNewsPostsEntry.getReplyCount());
       
        holder.bt_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				positon=position;
				if(CheckConnectNet.isNetworkConnected(context)){
					List<NameValuePair> params_Delete = new ArrayList<NameValuePair>();
					params_Delete.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
					params_Delete.add(new BasicNameValuePair("function","DeleteTheme"));
					params_Delete.add(new BasicNameValuePair("tid",mMostNewsPostsEntry.getId()));
					new DeleteTheme(context, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Delete, ConnectNetAsyncTask.DELETETHEME).execute();
					
					}else{
						ShowToast.ShowTos(context, R.string.noNetwork);
					}
				
			}
		});
        return convertView;
    }

    static class ViewHolder {
        TextView tv_postsTitle,tv_postsTime ,tv_recieve;
        Button bt_delete;
    }
    
    private class DeleteTheme extends ConnectNetAsyncTask{

		public DeleteTheme(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if("211".equals(result)){
				MyPostsFragment.notifiDataChange(positon);
				ShowToast.ShowTos(context, R.string.deletesuccess);
			}else {
				ShowToast.ShowTos(context, R.string.deletefail);
			}
			
		}
    	
    }
}