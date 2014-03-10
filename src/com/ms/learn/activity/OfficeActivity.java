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

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.OfficeCategoryAdapter;
import com.ms.learn.bean.OfficeCategoryItem;
import com.ms.learn.bean.OfficeCategorylist;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.xml.ParseXmlOfficeCategory;

public class OfficeActivity extends Activity {

	private ExpandableListView listView;
	OfficeCategoryAdapter  mOfficeCategoryAdapter;
	
	private Context mContext=OfficeActivity.this;
	private List<OfficeCategoryItem> OfficeCategoryItem_1s=new ArrayList<OfficeCategoryItem>();
	private List<OfficeCategoryItem> OfficeCategoryItem_2s=new ArrayList<OfficeCategoryItem>();
	private List<OfficeCategorylist> officeCategorylists=new ArrayList<OfficeCategorylist>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ˙∆¡
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.office_activity);
		initUI();
	}

	private void initUI() {
		
		listView=(ExpandableListView) findViewById(R.id.lv_office);
		listView.setGroupIndicator(null);
		listView.setOnChildClickListener(listViewOnchildClick);
		
	   findViewById(R.id.bt_officeDownload).setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent=new Intent(OfficeActivity.this,OfficeDownLoadActivity.class);
			startActivity(intent);
			
		}
	});
	   findViewById(R.id.bt_officeBack).setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			OfficeActivity.this.finish();
			
		}
	});
	   
		
		List<NameValuePair> params_GetOfficeCategory = new ArrayList<NameValuePair>();
		params_GetOfficeCategory.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
		params_GetOfficeCategory.add(new BasicNameValuePair("function","GetDocumentClass"));
		new GetOfficeCategory(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GetOfficeCategory,ConnectNetAsyncTask.GETOFFICECATEGORY).execute();
	}
   private OnChildClickListener  listViewOnchildClick =new  OnChildClickListener(){

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		
		String parentCategory=officeCategorylists.get(groupPosition).getCategoryItem().getCategoryName();
		OfficeCategoryItem  officeCategoryItem=mOfficeCategoryAdapter.getChild(groupPosition, childPosition);
		Intent intent=new Intent(OfficeActivity.this,OfficeDetailActivity.class);
		intent.putExtra("cid",officeCategoryItem.getId());
		intent.putExtra("parentCategory",parentCategory);
		intent.putExtra("cName", officeCategoryItem.getCategoryName());
		mContext.startActivity(intent);
		
		return true;
	}
	   
   };
	private class GetOfficeCategory extends ConnectNetAsyncTask{

		public GetOfficeCategory(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			if(result!=null){
				ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
			    List<OfficeCategoryItem> OfficeCategoryItems=ParseXmlOfficeCategory.parseXmlOfficeCategory(stream);
			    //∑÷¿‡
			    if(OfficeCategoryItems!=null){
			    	for(int i=0;i<OfficeCategoryItems.size();i++){
			    		int id=Integer.valueOf(OfficeCategoryItems.get(i).getCategoryId());
			    		  if(0==id){
			    			  OfficeCategoryItem_1s.add(OfficeCategoryItems.get(i));
			    		  }else {
			    			  OfficeCategoryItem_2s.add(OfficeCategoryItems.get(i));
			    		  }
			    	}
			    	
			    	for(int j=0;j<OfficeCategoryItem_1s.size();j++){
			    		
			    		OfficeCategorylist   categorylist=new OfficeCategorylist();
			    		categorylist.setCategoryItem(OfficeCategoryItem_1s.get(j));
			    		 String  id=OfficeCategoryItem_1s.get(j).getId();
			    		 
			    		 List<OfficeCategoryItem> officeCategoryItems=new ArrayList<OfficeCategoryItem>();
			    		 
			    	     for(int k=0;k<OfficeCategoryItem_2s.size();k++){
			    			if(id.equals(OfficeCategoryItem_2s.get(k).getCategoryId())){
			    				officeCategoryItems.add(OfficeCategoryItem_2s.get(k));
			    			}
			    		 }
			    	     categorylist.setCategoryItems(officeCategoryItems);
			    	     officeCategorylists.add(categorylist);
			    	}
			    }
			    
			    //…Ë÷√  ≈‰∆˜
			    mOfficeCategoryAdapter=new OfficeCategoryAdapter(mContext, officeCategorylists);
			    listView.setAdapter(mOfficeCategoryAdapter);
			}
			
		}
		
	}
	
}
