package com.ms.learn.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.activity.VideoKindDtailActivity;
import com.ms.learn.bean.VideoKind;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.widgets.ShowSecondDialog;

public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	List<VideoKind> videoKind_2s;
	
	        public GridViewAdapter(Context c,List<VideoKind> videoKind_2s) {
	              mContext = c;
	              this.videoKind_2s=videoKind_2s;
	        }

	        public int getCount() {
	            return videoKind_2s.size();
	        }

	        public Object getItem(int position) {
	            return position;
	        }

	        public long getItemId(int position) {
	            return position;
	        }

	        @SuppressLint("ResourceAsColor")
			public View getView(final int position, View convertView, ViewGroup parent) {
	           Button  button;
	            if (convertView == null) {
	            	button = new Button(mContext);
	            	button.setGravity(Gravity.CENTER_HORIZONTAL);
	            	button.setLayoutParams(new GridView.LayoutParams(150, 70));
	            	button.setPadding(15, 15, 15, 15);
	            } else {
	            	button = (Button) convertView;
	            }

	            button.setText(videoKind_2s.get(position).getpName());
	            button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						System.out.println("kinds.get(arg2).getpID()"+videoKind_2s.get(position).getpID());
						
		             //获取视频的列表
					 //获取所有的视频分类
					List<NameValuePair> params_GeVideoList = new ArrayList<NameValuePair>();
					params_GeVideoList.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
					params_GeVideoList.add(new BasicNameValuePair("function","GetVideoByTID"));
					params_GeVideoList.add(new BasicNameValuePair("cid",videoKind_2s.get(position).getId()));
					if(CheckConnectNet.isNetworkConnected(mContext)){
						new GetVideoListByCategory(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GeVideoList, ConnectNetAsyncTask.GETVIDEOLISTBYCATEGORY).execute();
					}else{
						ShowToast.ShowTos(mContext, R.string.noNetwork);
					}
						
					}
				});

	            return button;
	        }

	    	private class GetVideoListByCategory extends ConnectNetAsyncTask{

	    		public GetVideoListByCategory(Context context, String url,List<NameValuePair> params, int flag) {
	    			super(context, url, params, flag);
	    			
	    		}

	    		@Override
	    		public void doResult(String result) {
	    			System.out.println("result===="+result);
	    			if(result!=null){
	    				Intent intent=new Intent(mContext,VideoKindDtailActivity.class);
	    				intent.putExtra("result", result);
	    				mContext.startActivity(intent);
	    				
	    			}
	    		}
	    		
	    	}
}
