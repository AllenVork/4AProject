package com.ms.learn.activity;

import java.util.List;

import com.ms.learn.R;
import com.ms.learn.adapter.HistoryAdapter;
import com.ms.learn.bean.VideoHistoryEntry;
import com.ms.learn.download.DownloadDatabase;
import com.ms.learn.service.DownloadService;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class HistoryActivity extends Activity {
	private ListView listView;
	private HistoryAdapter historyAdapter;
	private List<VideoHistoryEntry> historyEntries;
	//数据库
    DownloadDatabase databaseImpl=DownloadService.getDownloadDatabase();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//无title
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
		setContentView(R.layout.history_activity);
		initUI();
	}

	private void initUI() {
		listView=(ListView) findViewById(R.id.lv_history);
		
		listView.setOnItemClickListener(itemClickListener);
		
		findViewById(R.id.bt_historyBack).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HistoryActivity.this.finish();
				
			}
		});
		
	}
	
	
	
	@Override
	protected void onResume() {
		historyEntries=databaseImpl.getHistoryEntries();
		if(historyEntries!=null){
			if(historyEntries.size()!=0){
				historyAdapter=new  HistoryAdapter(historyEntries, HistoryActivity.this);
				listView.setAdapter(historyAdapter);
			}else{
				Toast.makeText(HistoryActivity.this, "没有观看记录", 3000).show();
			}
		}
		super.onResume();
	}



	private OnItemClickListener itemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			VideoHistoryEntry historyEntry= historyEntries.get(arg2);
			Intent intent=new Intent(HistoryActivity.this,VideoDetailActivity.class);
			intent.putExtra("videoID", historyEntry.getVideoUrl());
			intent.putExtra("hadseetime", historyEntry.getVideoHadSeeTime());
			startActivity(intent);
			
		}
	};

}
