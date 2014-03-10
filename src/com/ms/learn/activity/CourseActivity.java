package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.CourseAdapter;
import com.ms.learn.adapter.LvVideoKindAdapter;
import com.ms.learn.bean.VideoKind;
import com.ms.learn.bean.VideoKindList;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlVideoKind;

public class CourseActivity extends Activity {
	
	private Context mContext=CourseActivity.this;
	private List<VideoKind> videoKind_1s=new ArrayList<VideoKind>();
	private List<VideoKind> videoKind_2s=new ArrayList<VideoKind>();
	private ExpandableListView listViewKind;
	private List<VideoKindList> kindLists=new ArrayList<VideoKindList>();
	private CourseAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.course_activity);
		initUI();
	}

	private void initUI() {
		findViewById(R.id.bt_courseBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CourseActivity.this.finish();
			}
		});
		listViewKind=(ExpandableListView) findViewById(R.id.lv_courseKind);
		listViewKind.setGroupIndicator(null);
		listViewKind.setOnChildClickListener(childClickListener);
		 //获取所有的视频分类
		List<NameValuePair> params_Getall = new ArrayList<NameValuePair>();
		params_Getall.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
		params_Getall.add(new BasicNameValuePair("function","GetVideoClass"));
        new GetAllVideos(CourseActivity.this, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_Getall, ConnectNetAsyncTask.GETALLVIDEOSKIND).execute();
	}
	
	class GetAllVideos extends ConnectNetAsyncTask{

		public GetAllVideos(Context context, String url,	List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if(result!=null){
				ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
			    List<VideoKind>	videoKinds=ParseXmlVideoKind.parseXmlVideoKind(stream);
			    //分类
			    if(videoKinds!=null){
			    	for(int i=0;i<videoKinds.size();i++){
			    		int id=Integer.valueOf(videoKinds.get(i).getpID());
			    		  if(0==id){
			    			  videoKind_1s.add(videoKinds.get(i));
			    		  }else {
			    			  videoKind_2s.add(videoKinds.get(i));
			    		  }
			    	}
			    	
			    	for(int j=0;j<videoKind_1s.size();j++){
			    		
			    		VideoKindList  videoKindList=new VideoKindList();
			    		 videoKindList.setVideoKind(videoKind_1s.get(j));
			    		 String  id=videoKind_1s.get(j).getId();
			    		 
			    		 List<VideoKind> kinds=new ArrayList<VideoKind>();
			    		 
			    	     for(int k=0;k<videoKind_2s.size();k++){
			    			if(id.equals(videoKind_2s.get(k).getpID())){
			    				kinds.add(videoKind_2s.get(k));
			    			}
			    		 }
			    	     videoKindList.setVideoKinds(kinds);
			    	     kindLists.add(videoKindList);
			    	}
			    	
			    	
			    }
				adapter=new CourseAdapter(CourseActivity.this, kindLists);
				listViewKind.setAdapter(adapter);
			}
		}
		
	}
	
	private OnChildClickListener childClickListener=new OnChildClickListener() {
		
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			 //获取视频的列表
			 //获取所有的视频分类
			List<NameValuePair> params_GeVideoList = new ArrayList<NameValuePair>();
			params_GeVideoList.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			params_GeVideoList.add(new BasicNameValuePair("function","GetVideoByTID"));
			params_GeVideoList.add(new BasicNameValuePair("cid",kindLists.get(groupPosition).getVideoKinds().get(childPosition).getId()));
			if(CheckConnectNet.isNetworkConnected(mContext)){
				new GetVideoListByCategory(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GeVideoList, ConnectNetAsyncTask.GETVIDEOLISTBYCATEGORY).execute();
			}else{
				ShowToast.ShowTos(mContext, R.string.noNetwork);
			}
			
			return true;
		}
	};
	
	
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
