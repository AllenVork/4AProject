package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.PushListAdapter;
import com.ms.learn.bean.PushItem;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.xml.ParseXmlPush;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MessageActivity extends Activity {
	
	private ListView mListView;
	private List<PushItem> pushItems;
    private PushListAdapter mPushListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message_activity);
		initUI();
	}

	private void initUI() {
		mListView=(ListView) findViewById(R.id.lv_message);
		mListView.setOnItemClickListener(itemClickListener);
		findViewById(R.id.bt_messageBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MessageActivity.this.finish();
			}
		});
		
		List<NameValuePair> params_GetPut = new ArrayList<NameValuePair>();
		params_GetPut.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
		params_GetPut.add(new BasicNameValuePair("function","GetPutMnager"));
		params_GetPut.add(new BasicNameValuePair("tput","2"));
		
		new GetPutMsg(MessageActivity.this, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_GetPut, ConnectNetAsyncTask.GETPUTMNAGER).execute();
        		
	}

	private class GetPutMsg extends ConnectNetAsyncTask{

		public GetPutMsg(Context context, String url,List<NameValuePair> params, int flag) {
			super(context, url, params, flag);
		}

		@Override
		public void doResult(String result) {
			
			if(result!=null&&!"113".equals(result)){
				 ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
				 pushItems=ParseXmlPush.parseXmlPush(stream);
				 mPushListAdapter=new PushListAdapter(MessageActivity.this, pushItems);
				 mListView.setAdapter(mPushListAdapter);
			}
			
		}
		
	}
	private OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
		PushItem pushItem=pushItems.get(arg2);
		Intent intent=new Intent(MessageActivity.this,ShowDetailPushMsgActivity.class);
		Bundle bundle=new Bundle();
		bundle.putSerializable("pushItem", pushItem);
		intent.putExtras(bundle);
		startActivity(intent);
			
    	}
		
		
	};
	
}
