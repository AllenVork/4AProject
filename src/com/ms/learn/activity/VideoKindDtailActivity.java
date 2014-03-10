package com.ms.learn.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import com.ms.learn.R;
import com.ms.learn.adapter.LvVideoKindDtailAdapter;
import com.ms.learn.bean.VideoKindDetailItem;
import com.ms.learn.xml.ParseXmlKindDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class VideoKindDtailActivity extends Activity {
	private List<VideoKindDetailItem> kindDetailItems = new ArrayList<VideoKindDetailItem>();
	private ListView listView;
	private LvVideoKindDtailAdapter lvDtailAdapter;
	private Context mContext = VideoKindDtailActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ÊúÆÁ
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.videokinddetail_activity);
		initUI();
	}

	private void initUI() {
		listView = (ListView) findViewById(R.id.lv_videokinddetail);
        findViewById(R.id.bt_videokinddetailBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				VideoKindDtailActivity.this.finish();
			}
		});
		String result = getIntent().getExtras().getString("result");
		ByteArrayInputStream stream = new ByteArrayInputStream(result.getBytes());
		kindDetailItems = ParseXmlKindDetail.parseXmlKindDetail(stream);
		lvDtailAdapter = new LvVideoKindDtailAdapter(mContext, kindDetailItems);
		listView.setAdapter(lvDtailAdapter);
		listView.setOnItemClickListener(listOnItemClickListener);
	}

	 private OnItemClickListener listOnItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			VideoKindDetailItem videoKindDetailItem=kindDetailItems.get(arg2);
			Intent intent=new Intent(VideoKindDtailActivity.this,VideoDetailActivity.class);
			intent.putExtra("videoID", videoKindDetailItem.getVideoId());
			startActivity(intent);
		}
	};
	
}
