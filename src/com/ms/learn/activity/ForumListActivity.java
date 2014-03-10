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
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.ms.learn.ALearnApplication;
import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.ForumListAdapter;
import com.ms.learn.bean.ForumEntrylist;
import com.ms.learn.bean.ForumListEntry;
import com.ms.learn.fragment.TabFragmentForumDetail;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.xml.ParseXmlForumList;

public class ForumListActivity extends Activity {

	private ExpandableListView listView;
	ForumListAdapter  mForumListAdapter;
	
	private Context mContext=ForumListActivity.this;
	private List<ForumListEntry> forumEntrylist_1s=new ArrayList<ForumListEntry>();
	private List<ForumListEntry> forumEntrylist_2s=new ArrayList<ForumListEntry>();
	private List<ForumEntrylist> forumEntrylists=new ArrayList<ForumEntrylist>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ˙∆¡
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forumnew_activity);
		initUI();
	}

	private void initUI() {
		
		listView=(ExpandableListView) findViewById(R.id.lv_forum);
		listView.setGroupIndicator(null);
		listView.setOnChildClickListener(listViewOnchildClick);
		
	   findViewById(R.id.bt_forumBack).setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ForumListActivity.this.finish();
			
		}
	});
		
		List<NameValuePair> params_GetForumType = new ArrayList<NameValuePair>();
		params_GetForumType.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
		params_GetForumType.add(new BasicNameValuePair("function","GetForumType"));
		params_GetForumType.add(new BasicNameValuePair("pid","-1"));
		new GetForumType(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST,params_GetForumType,ConnectNetAsyncTask.GETFORUMTYPE).execute();
	}
   private OnChildClickListener  listViewOnchildClick =new  OnChildClickListener(){

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		ForumListEntry  forumListEntry=mForumListAdapter.getChild(groupPosition, childPosition);
		Intent intent=new Intent(ForumListActivity.this,ForumDetailActivity.class);
		ALearnApplication.getInstance().setPostsTid(forumListEntry.getId());
		intent.putExtra("title",forumListEntry.getForumTypeName());
		intent.putExtra("id", forumListEntry.getId());
		mContext.startActivity(intent);
		
		return true;
	}
	   
   };
	private class GetForumType extends ConnectNetAsyncTask{

		public GetForumType(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if(result!=null){
				
				System.out.println("++++++++++++++++++result"+result);
				ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
			    List<ForumListEntry> forumListEntrys=ParseXmlForumList.parseXmlForumList(stream);
			    //∑÷¿‡
			    if(forumListEntrys!=null){
			    	for(int i=0;i<forumListEntrys.size();i++){
			    		int id=Integer.valueOf(forumListEntrys.get(i).getpId());
			    		  if(0==id){
			    			  forumEntrylist_1s.add(forumListEntrys.get(i));
			    		  }else {
			    			  forumEntrylist_2s.add(forumListEntrys.get(i));
			    		  }
			    	}
			    	
			    	for(int j=0;j<forumEntrylist_1s.size();j++){
			    		
			    		ForumEntrylist  forumEntrylist=new ForumEntrylist();
			    		forumEntrylist.setForumListEntry(forumEntrylist_1s.get(j));
			    		String  id=forumEntrylist_1s.get(j).getId();
			    		 
			    		 List<ForumListEntry> forumlistEntrys=new ArrayList<ForumListEntry>();
			    		 
			    	     for(int k=0;k<forumEntrylist_2s.size();k++){
			    			if(id.equals(forumEntrylist_2s.get(k).getpId())){
			    				forumlistEntrys.add(forumEntrylist_2s.get(k));
			    			}
			    		 }
			    	     forumEntrylist.setForumListEntrys(forumlistEntrys);
			    	     forumEntrylists.add(forumEntrylist);
			    	}
			    }
			    
			    //…Ë÷√  ≈‰∆˜
			    mForumListAdapter=new ForumListAdapter(mContext, forumEntrylists);
			    listView.setAdapter(mForumListAdapter);
			}
			
		}
		
	}
	
}
