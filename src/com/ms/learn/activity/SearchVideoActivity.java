package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.ms.learn.R;
import com.ms.learn.ShareData;
import com.ms.learn.adapter.LvVideoKindDtailAdapter;
import com.ms.learn.bean.VideoKindDetailItem;
import com.ms.learn.net.ConnectNetAsyncTask;
import com.ms.learn.util.ShowToast;
import com.ms.learn.xml.ParseXmlKindDetail;

public class SearchVideoActivity extends Activity {

	private Button bt_back,bt_search;
	private EditText ed_content;
	private ListView mListView;
	private List<VideoKindDetailItem> kindDetailItems = new ArrayList<VideoKindDetailItem>();
	private LvVideoKindDtailAdapter lvDtailAdapter;
	private Context mContext = SearchVideoActivity.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ÎÞtitle
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ÊúÆÁ
		setContentView(R.layout.searchvideo_activity);
		initUI();
	}

	private void initUI() {
		bt_back=(Button) findViewById(R.id.bt_searchvideoBack);
		bt_back.setOnClickListener(btOnclickListener);
		bt_search=(Button) findViewById(R.id.bt_search);
		bt_search.setOnClickListener(btOnclickListener);
		ed_content=(EditText) findViewById(R.id.ed_searchContent);
		mListView=(ListView) findViewById(R.id.lv_searchVideo);
	}
	
  private OnClickListener btOnclickListener=new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		
		if(bt_back==v){
			
			SearchVideoActivity.this.finish();
			
		}else if(bt_search==v){
			String textContent=ed_content.getText().toString();
			if(textContent.equals(null)||textContent.equals("")){
				ShowToast.ShowTos(SearchVideoActivity.this, R.string.pleaseentercontent);
			}else{
				List<NameValuePair> params_search = new ArrayList<NameValuePair>();
				params_search.add(new BasicNameValuePair("code",ShareData.REQUEST_CODE));
				params_search.add(new BasicNameValuePair("function","GetVideoByWD"));
				try {
					params_search.add(new BasicNameValuePair("wd",URLEncoder.encode(textContent, "utf-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			   new SearchVideoTask(SearchVideoActivity.this, ShareData.LANSHAOQI_ADDRESS_DOPOST, params_search, ConnectNetAsyncTask.GETVIDEOBYWD).execute();
			}
				
				
			}
			
			
		
	}
};
	
  private class SearchVideoTask extends ConnectNetAsyncTask{

	public SearchVideoTask(Context context, String url,List<NameValuePair> params, int flag) {
		super(context, url, params, flag);
		
	}

	@Override
	public void doResult(String result) {
		
		ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
		kindDetailItems = ParseXmlKindDetail.parseXmlKindDetail(stream);
		if(kindDetailItems.size()==0){
			ShowToast.ShowTos(mContext, R.string.novideoContent);
		}
		lvDtailAdapter = new LvVideoKindDtailAdapter(mContext, kindDetailItems);
		mListView.setAdapter(lvDtailAdapter);
		mListView.setOnItemClickListener(listOnItemClickListener);
	}
	  
  }
  
  private OnItemClickListener listOnItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			VideoKindDetailItem videoKindDetailItem=kindDetailItems.get(arg2);
			Intent intent=new Intent(mContext,VideoDetailActivity.class);
			intent.putExtra("videoID", videoKindDetailItem.getVideoId());
			startActivity(intent);
		}
	};
}
