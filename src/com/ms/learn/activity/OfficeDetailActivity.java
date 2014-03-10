package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.OfficeDetailAdapter;
import com.ms.learn.bean.OfficeDetailItem;
import com.ms.learn.net.CheckConnectNet;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlOfficeDetail;

public class OfficeDetailActivity extends Activity {
	private ListView listView;
	private TextView tv_title;
	private Context mContext=OfficeDetailActivity.this;
	private List<OfficeDetailItem> officeDetailItems;
	OfficeDetailAdapter mDetailAdapter;
	String cName;
	String parentCatetory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ÎÞtitle
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		setContentView(R.layout.officedetail_activity);
		initUI();
	}

	private void initUI() {
		
		listView=(ListView) findViewById(R.id.lv_officeDetail);
		tv_title=(TextView) findViewById(R.id.tv_officeDtail);
		String cid=getIntent().getExtras().getString("cid");
		cName=getIntent().getExtras().getString("cName");
		parentCatetory=getIntent().getExtras().getString("parentCategory");
		
		tv_title.setText(cName);
		
		if(CheckConnectNet.isNetworkConnected(mContext)){
			
			List<NameValuePair> params_GetOfficeDtailById = new ArrayList<NameValuePair>();
			params_GetOfficeDtailById.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
			params_GetOfficeDtailById.add(new BasicNameValuePair("function","GetDocumentByTID"));
			params_GetOfficeDtailById.add(new BasicNameValuePair("cid",cid));
			new GetOfficeDetailById(mContext, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GetOfficeDtailById, ConnectNetAsyncTask.GETOFFICEDETAILBYID).execute();
		}else {
			ShowToast.ShowTos(mContext, R.string.noNetwork);
		}
		
		findViewById(R.id.bt_officeDetailBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				OfficeDetailActivity.this.finish();
				
			}
		});
	}
	
	private class GetOfficeDetailById extends ConnectNetAsyncTask{

		public GetOfficeDetailById(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			 ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
			 officeDetailItems=ParseXmlOfficeDetail.parseXmlOfficeDtail(stream);
			 mDetailAdapter=new OfficeDetailAdapter(mContext, officeDetailItems,parentCatetory,cName);
			 listView.setAdapter(mDetailAdapter);
			 
		}
		
	}

}
